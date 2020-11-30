import java.sql.*;
import java.util.Random;
import java.util.Scanner;

/*
Class for inserting random information into the Reserves table.
 */

public class InsertReserves {
    static Connection con = null;
    static final int CAP = 15;
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
        //Insert reservation data

        String query = "INSERT INTO Reserves VALUES (?, ?, ?, ?)";

        //Query 1
        int[] ids = new int[CAP];
        Statement stmt = null;
        ResultSet results = null;

        //random cardholder ids
        try {
            stmt = con.createStatement();
            results = stmt.executeQuery("SELECT cardNumber AS 'id' FROM Cardholder ORDER BY RAND() LIMIT 15");
            int i = 0;
            while(results.next()) {
                ids[i] = results.getInt("id");
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //insert borrowing information
        try {
            PreparedStatement preparedStmt = con.prepareStatement(query);

            int count = 0;

            while (count < CAP) {
                boolean trigger = rnd.nextBoolean();
                int memberID = ids[count];
                int floorLocation = rnd.nextInt(4 - 1 + 1) + 1;
                boolean computer = trigger;
                boolean studyroom = !trigger;

                preparedStmt.setInt(1, memberID);
                preparedStmt.setInt(2, floorLocation);
                preparedStmt.setBoolean(3, computer);
                preparedStmt.setBoolean(4, studyroom);
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
