import java.sql.*;
import java.util.Random;

public class InsertLocation {
    static Connection con = null;
    static Random rnd = new Random();

    static {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryDatabase?serverTimezone=UTC", dbInfo.getUsername(), dbInfo.getPassword());
            System.out.println("Connection successful!");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main (String[] args) throws SQLException {

        String query = "INSERT INTO Location VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStmt = con.prepareStatement(query);

            int count = 1;

            while (count < 5) {
                int studyRooms = rnd.nextInt(6 - 2 + 1) + 2;
                int computers = rnd.nextInt(10 - 4 + 1) + 4;

                preparedStmt.setInt(1, count);
                preparedStmt.setInt(2, studyRooms);
                preparedStmt.setInt(3, computers);
                preparedStmt.setInt(4, studyRooms);
                preparedStmt.setInt(5, computers);
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
