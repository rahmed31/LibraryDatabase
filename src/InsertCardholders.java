import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/*
Class for inserting cardholder information for up to 50 library members.
 */

public class InsertCardholders {
    public static void main(String[] args) {
        Connection con = null;
        Random rnd = new Random();
        final int CAP = 50;

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryDatabase?serverTimezone=UTC", dbInfo.getUsername(), dbInfo.getPassword());
            System.out.println("Connection successful!");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String[] addresses = new String[CAP];
        double[] phoneNumbers = new double[CAP];

        ArrayList<String> first = new ArrayList<String>();
        ArrayList<String> last = new ArrayList<String>();

        try {
            File myObj = new File("/Users/raihanahmed/IdeaProjects/LibraryDatabase/lib/first-names.txt");
            File myObj2 = new File("/Users/raihanahmed/IdeaProjects/LibraryDatabase/lib/last-names.txt");
            File myObj3 = new File("/Users/raihanahmed/IdeaProjects/LibraryDatabase/lib/random-addresses.txt");
            File myObj4 = new File("/Users/raihanahmed/IdeaProjects/LibraryDatabase/lib/random-phonenumbers.txt");
            Scanner reader = new Scanner(myObj);
            Scanner reader2 = new Scanner(myObj2);
            Scanner reader3 = new Scanner(myObj3);
            Scanner reader4 = new Scanner(myObj4);

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                first.add(data);
            }
            reader.close();

            while (reader2.hasNextLine()) {
                String data = reader2.nextLine();
                String output = data.substring(0, 1) + data.substring(1).toLowerCase();
                last.add(output);
            }
            reader2.close();

            int i = 0;
            while (reader3.hasNextLine()) {
                String data = reader3.nextLine();
                addresses[i] = data;
                i++;
            }
            reader3.close();

            int j = 0;
            while (reader4.hasNext()) {
                double data = reader4.nextDouble();
                phoneNumbers[j] = data;
                j++;
            }
            reader3.close();

        }
        catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            String query = "INSERT INTO Cardholder VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            int count = 0;

            while(count < 50) {
                int cardNumber = rnd.nextInt( 50000 - 10000 + 1) + 10000;
                String lastName = last.get(rnd.nextInt(last.size() - 1 - 0 + 1));
                String firstName = first.get(rnd.nextInt(first.size() - 1 - 0 + 1));
                String address = addresses[count];
                double phoneNumber = phoneNumbers[count];
                int type = rnd.nextInt(3 - 1 + 1) + 1;
                String email = generateEmail(firstName, lastName, type);

                preparedStmt.setInt(1, cardNumber);
                preparedStmt.setString(2, lastName);
                preparedStmt.setString(3, firstName);
                preparedStmt.setString(4, address);
                preparedStmt.setDouble(5, phoneNumber);
                preparedStmt.setString(6, email);
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
            System.out.println("--------------Insertions complete--------------");
        }
    }

    public static String generateEmail(String firstName, String lastName, int type) {
        String email = "";
        Random rnd = new Random();
        String[] domains = {"@gmail.com", "@outlook.com", "@yahoo.com", "@icloud.com", "@aol.com", "@comcast.net", "@live.com"};

        if (type == 1) {
            email = firstName.toLowerCase() + "." + lastName.toLowerCase() + domains[rnd.nextInt(6-1+1) + 1];
        }
        else if (type == 2) {
            email = firstName.substring(0, 1).toLowerCase() + lastName.toLowerCase() + String.valueOf(rnd.nextInt(20-1+1) + 1) + domains[rnd.nextInt(6-1+1) + 1];
        }
        else if (type == 3) {
            email = lastName.toLowerCase() + String.valueOf(rnd.nextInt(20-1+1) + 1) + domains[rnd.nextInt(6-1+1) + 1];
        }

        return email;
    }
}
