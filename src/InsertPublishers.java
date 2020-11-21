import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

/*
Class for inserting unique publisher information for the first 500 entries in book_metadata.csv into the Publisher table.
 */

public class InsertPublishers {
    static Connection con = null;
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

    public static void main(String[] args) throws Exception {
        /* Record creation*/
        //Insert publishing house and id
        File myObj = new File("lib/publishers.txt");
        String query = "INSERT INTO Publisher VALUES (?, ?)";

        try {
            reader = new Scanner(myObj);

            PreparedStatement preparedStmt = con.prepareStatement(query);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                preparedStmt.setInt(1, 0);
                preparedStmt.setString(2, data);
                preparedStmt.execute();
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
}
