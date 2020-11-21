import java.io.FileNotFoundException;
import java.sql.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/*
Class for inserting random information for a limit of 30 employees into the Employee table.
 */

public class InsertEmployees {
    static Connection con = null;
    static Random rnd = new Random();
    static Scanner reader = null;

    static {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryDatabase?serverTimezone=UTC", dbInfo.getUsername(), dbInfo.getPassword());
            System.out.println("Connection successful!");
        }
        catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        //Insert Employee data
        ArrayList<String> firstE = extractNames("lib/first-names.txt");
        ArrayList<String> lastE = extractNames("lib/last-names.txt");

        String query = "INSERT INTO Employee VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStmt = con.prepareStatement(query);

            int count = 0;

            while (count < 30) {
                int employeeID = rnd.nextInt( 60000 - 10000 + 1) + 10000;
                String lastName = lastE.get(rnd.nextInt(lastE.size() - 1 - 0 + 1));
                String firstName = firstE.get(rnd.nextInt(firstE.size() - 1 - 0 +1));
                int salary = rnd.nextInt(90000 - 50000 + 1) + 50000;

                preparedStmt.setInt(1, employeeID);
                preparedStmt.setString(2, lastName);
                preparedStmt.setString(3, firstName);
                preparedStmt.setString(4, "hello");
                preparedStmt.setInt(5, salary);
                preparedStmt.execute();

                count++;
            }
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
    public static ArrayList<String> extractNames(String path) {
        ArrayList<String> names = new ArrayList<String>();

        File myObj = new File(path);

        try {
            reader = new Scanner(myObj);
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred when reading the file");
            System.out.println(e.getMessage());
        }

        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            String output = data.substring(0, 1) + data.substring(1).toLowerCase();
            names.add(output);
        }

        reader.close();

        return names;
    }
}
