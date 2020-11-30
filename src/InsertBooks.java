import com.opencsv.CSVReader;

import java.awt.print.Book;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/*
Class for inserting book metadata for first 500 entries in book_metadata.csv into the database.
 */

public class InsertBooks {
    static Connection con = null;
    static Random rnd = new Random();
    static Scanner reader = null;

    static {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryDatabase?serverTimezone=UTC", dbInfo.getUsername(), dbInfo.getPassword());
            System.out.println("Connection successful!");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        //Create map of publisher and publisherID
        HashMap<String, Integer> myMap = getPublisherMap("lib/publishers.txt");
        int count = 0;
        //Insert book metadata
        String query = "INSERT INTO Book VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            CSVReader reader = new CSVReader(new FileReader("lib/book_metadata.csv"));

            PreparedStatement preparedStmt = con.prepareStatement(query);


            //remove column names
            String[] nextLine = reader.readNext();

            while((nextLine = reader.readNext()) != null && count < 1500) {
                int floor = 4;

                if (nextLine[1].compareTo("G") < 0) {
                    floor = 1;
                }
                else if (nextLine[1].compareTo("M") < 0) {
                    floor = 2;
                }
                else if (nextLine[1].compareTo("S") < 0) {
                    floor = 3;
                }

                int random = rnd.nextInt(7 - 1 + 1) + 1;
                preparedStmt.setInt(1, 0);

                if (myMap.get(nextLine[4]) != null) {
                    preparedStmt.setInt(2, myMap.get(nextLine[4]));
                }
                else {
                    preparedStmt.setInt(2, 314);
                }

                preparedStmt.setInt(3, floor);
                preparedStmt.setString(4, nextLine[0]);
                preparedStmt.setString(5, nextLine[1]);
                preparedStmt.setString(6, nextLine[2]);
                preparedStmt.setInt(7, random);
                preparedStmt.setInt(8, random);
                preparedStmt.setInt(9, Integer.parseInt(nextLine[3]));
                preparedStmt.setString(10, nextLine[5]);
                preparedStmt.execute();
                count++;
            }

            reader.close();
        }
        catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        finally {
            con.close();

            if (con.isClosed()) {
                System.out.println("Connection is closed!");
            }
            System.out.println("--------------Insertions complete--------------");
        }
    }
    public static HashMap<String, Integer> getPublisherMap(String path) {
        HashMap<String, Integer> myMap = new HashMap<String, Integer>();

        try {
            File myObj = new File(path);
            reader = new Scanner(myObj);

            int i = 1;
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                myMap.put(data, i);
                i++;
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred when trying to read the file");
            System.out.println(e.getMessage());
        }
        finally {
            reader.close();
        }

        return myMap;
    }
}
