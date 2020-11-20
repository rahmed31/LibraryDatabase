import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

/*
Class for inserting unique publisher information for the first 500 entries in book_metadata.csv into the database.
 */

public class InsertPublishers {
    public static void main(String[] args) {

        Connection con = null;

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryDatabase?serverTimezone=UTC", dbInfo.getUsername(), dbInfo.getPassword());
            System.out.println("Connection successful!");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

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

            con.close();

            if (con.isClosed()) {
                System.out.println("Connection is closed!");
            }
        }
        catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        finally {
            System.out.println("--------------Insertions complete--------------");
        }
    }
}
