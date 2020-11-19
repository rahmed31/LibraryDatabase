import com.opencsv.CSVReader;

import java.io.FileReader;
import java.sql.*;
import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;
import java.io.FileNotFoundException;

public class InsertInformation {
    public static void main(String[] args) throws Exception {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryDatabase?serverTimezone=UTC", "user","password");
            System.out.println("Connection successful!");

            /* Record creation*/
            //Insert publishing house and id

            try {
                File myObj = new File("/Users/raihanahmed/IdeaProjects/LibraryDatabase/lib/publishers.txt");
                Scanner myReader = new Scanner(myObj);
                String query = "INSERT INTO Publisher VALUES (?, ?)";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    preparedStmt.setInt(1, 0);
                    preparedStmt.setString(2, data);
                    preparedStmt.execute();
                }
                myReader.close();
            }
            catch (Exception e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            //Insert book metadata

            //Create map of publisher and publisherID
            HashMap<String, Integer> myMap = new HashMap<String, Integer> ();

            try {
                File myObj = new File("/Users/raihanahmed/IdeaProjects/LibraryDatabase/lib/publishers.txt");
                Scanner reader = new Scanner(myObj);
                int i = 1;
                while (reader.hasNextLine()) {
                    String data = reader.nextLine();
                    myMap.put(data, i);
                    i++;
                }
                reader.close();
            }
            catch (Exception e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            try {
                CSVReader reader = new CSVReader(new FileReader("/Users/raihanahmed/IdeaProjects/LibraryDatabase/lib/book_metadata.csv"));
                String query = "INSERT INTO Book VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStmt = con.prepareStatement(query);

                //remove column names
                String[] nextLine = reader.readNext();

                int count = 0;
                Random rnd = new Random();

                while((nextLine = reader.readNext()) != null && count < 500) {
                    int random = rnd.nextInt(7 - 1 + 1) + 1;
                    preparedStmt.setInt(1, 0);
                    preparedStmt.setInt(2, myMap.get(nextLine[4]));
                    preparedStmt.setString(3, null);
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

            con.close();

            if (con.isClosed()) {
                System.out.println("Connection is closed!");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            System.out.println("--------------Insertions finished--------------");
        }
    }
}
