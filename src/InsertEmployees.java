import java.sql.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class InsertEmployees {
    public static void main(String[] args) throws Exception {
        Connection con = null;
        Random rnd = new Random();

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryDatabase?serverTimezone=UTC", dbInfo.getUsername(), dbInfo.getPassword());
            System.out.println("Connection successful!");
        }
        catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //Insert Employee data

        String[] firstEmployee = new String[30];
        String[] lastEmployee = new String[30];

        ArrayList<String> firstE = new ArrayList<String>();
        ArrayList<String> lastE = new ArrayList<String>();

        try {
            File myObj = new File("/Users/raihanahmed/IdeaProjects/LibraryDatabase/lib/first-names.txt");
            File myObj2 = new File("/Users/raihanahmed/IdeaProjects/LibraryDatabase/lib/last-names.txt");

            Scanner reader = new Scanner(myObj);
            Scanner reader2 = new Scanner(myObj2);

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                firstE.add(data);
            }
            reader.close();

            while (reader2.hasNextLine()) {
                String data = reader2.nextLine();
                String output = data.substring(0, 1) + data.substring(1).toLowerCase();
                lastE.add(output);
            }
            reader2.close();
        }
        catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        int maxEsize = firstE.size();
        int maxEsize2 = lastE.size();

        for (int i = 0; i < 30; i++) {
            int random = rnd.nextInt(maxEsize - 1 - 0 + 1);
            int random2 = rnd.nextInt(maxEsize2 - 1 - 0 + 1);
            String firstName = firstE.get(random);
            String lastName = lastE.get(random2);

            firstEmployee[i] = firstName;
            lastEmployee[i] = lastName;
        }

        try {
            String query = "INSERT INTO Employee VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            int count = 0;

            while(count < 30) {
                int employeeID = rnd.nextInt( 60000 - 10000 + 1) + 10000;
                String lastName = lastEmployee[count];
                String firstName = firstEmployee[count];
                int salary = rnd.nextInt(90000 - 50000 + 1) + 50000;

                preparedStmt.setInt(1, employeeID);
                preparedStmt.setString(2, lastName);
                preparedStmt.setString(3, firstName);
                preparedStmt.setString(4, "hello");
                preparedStmt.setInt(5, salary);
                preparedStmt.execute();
                count++;
            }

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
            System.out.println("--------------Insertions finished--------------");
        }
    }
}
