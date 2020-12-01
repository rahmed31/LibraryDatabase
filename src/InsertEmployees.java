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
    static final int CAP = 30;

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
        ArrayList<String> firstE = InsertCardholders.extractNames("lib/first-names.txt");
        ArrayList<String> lastE = InsertCardholders.extractNames("lib/last-names.txt");

        String query = "INSERT INTO Employee VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStmt = con.prepareStatement(query);

            int count = 0;

            while (count < CAP) {
                int employeeID = rnd.nextInt( 60000 - 10000 + 1) + 10000;
                String lastName = lastE.get(rnd.nextInt(lastE.size() - 1 - 0 + 1));
                String firstName = firstE.get(rnd.nextInt(firstE.size() - 1 - 0 +1));
                int salary = rnd.nextInt(90000 - 50000 + 1) + 50000;
                int extension = rnd.nextInt(9000-1000 + 1) + 1000;
                int floorNumber = rnd.nextInt(4-1 + 1) + 1;

                preparedStmt.setInt(1, employeeID);
                preparedStmt.setString(2, lastName);
                preparedStmt.setString(3, firstName);
                preparedStmt.setInt(4, extension);
                preparedStmt.setInt(5, floorNumber);
                preparedStmt.setInt(6, salary);
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
}
