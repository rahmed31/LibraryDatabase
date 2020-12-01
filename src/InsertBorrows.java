import java.sql.*;
import java.sql.Date;
import java.util.*;

/*
Class for inserting random information into the Borrows table.
 */

public class InsertBorrows {

    static Connection con = null;
    static final int CAP = 40;
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
        //Insert borrowing data

        String query = "INSERT INTO Borrows VALUES (?, ?, ?, ?, ?)";

        //Query 1
        int[] ids = new int[CAP];
        Statement stmt = null;
        ResultSet results = null;

        //random cardholder ids
        try {
            stmt = con.createStatement();
            results = stmt.executeQuery("SELECT cardNumber AS 'id' FROM Cardholder ORDER BY RAND() LIMIT 40");
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

            int borrowerID;
            int bookID;
            String borrowDate;
            String dueDate;
            String returnDate;

            int count = 0;

            while (count < CAP) {
                borrowerID = ids[count];
                bookID = rnd.nextInt(500 - 1 + 1) + 1;
                borrowDate = 2020 + "-" + 11 + "-" + (rnd.nextInt(10 - 1 + 1) + 1);
                dueDate = 2020 + "-" + 11 + "-" + (rnd.nextInt(18 - 11 + 1) + 11);
                returnDate = 2020 + "-" + 11 + "-" + (rnd.nextInt(22 - 12 + 1) + 12);
                boolean trigger = rnd.nextBoolean();

                preparedStmt.setInt(1, borrowerID);
                preparedStmt.setInt(2, bookID);
                preparedStmt.setString(3, borrowDate);
                preparedStmt.setString(4, dueDate);

                if (trigger) {
                    preparedStmt.setString(5, returnDate);
                }
                else {
                    preparedStmt.setString(5, null);
                }
                preparedStmt.execute();

                count++;
            }

            count = 0;

            while (count < CAP*2) {
                borrowerID = ids[rnd.nextInt(CAP - 1 + 1)];
                bookID = rnd.nextInt(1500 - 500 + 1) + 300;
                borrowDate = 2020 + "-" + 11 + "-" + (rnd.nextInt(10 - 1 + 1) + 1);
                dueDate = 2020 + "-" + 11 + "-" + (rnd.nextInt(18 - 11 + 1) + 11);
                returnDate = 2020 + "-" + 11 + "-" + (rnd.nextInt(22 - 12 + 1) + 12);
                boolean trigger = rnd.nextBoolean();

                preparedStmt.setInt(1, borrowerID);
                preparedStmt.setInt(2, bookID);
                preparedStmt.setString(3, borrowDate);
                preparedStmt.setString(4, dueDate);

                if (trigger) {
                    preparedStmt.setString(5, returnDate);
                }
                else {
                    preparedStmt.setString(5, null);
                }

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
    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}

