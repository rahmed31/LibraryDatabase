import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/*
Class for inserting cardholder information for up to 50 library members into the Member table.
 */

public class InsertCardholders {
    static Connection con = null;
    static Random rnd = new Random();
    static final int CAP = 50;
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

    public static void main(String[] args) throws SQLException {
        String[] addresses = new String[CAP];
        double[] phoneNumbers = new double[CAP];

        ArrayList<String> first = extractNames("lib/first-names.txt");
        ArrayList<String> last = extractNames("lib/last-names.txt");

        String query = "INSERT INTO Cardholder VALUES (?, ?, ?, ?, ?, ?)";

        //Reading address and phone number files and inserting data into their respective arrays
        try {
            File myObj = new File("lib/random-addresses.txt");
            File myObj2 = new File("lib/random-phonenumbers.txt");
            reader = new Scanner(myObj);

            int i = 0;
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                addresses[i] = data;
                i++;
            }

            reader = new Scanner(myObj2);

            int j = 0;
            while (reader.hasNext()) {
                double data = reader.nextDouble();
                phoneNumbers[j] = data;
                j++;
            }
        }
        catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        finally {
            reader.close();
        }

        try {
            PreparedStatement preparedStmt = con.prepareStatement(query);

            int count = 0;

            while (count < 50) {
                int cardNumber = rnd.nextInt(50000 - 10000 + 1) + 10000;
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

    public static String generateEmail(String firstName, String lastName, int type) throws IndexOutOfBoundsException {
        String email = "";
        Random rnd = new Random();
        String[] domains = {"@gmail.com", "@outlook.com", "@yahoo.com", "@icloud.com", "@aol.com", "@comcast.net", "@live.com"};

        if (type == 1) {
            email = firstName.toLowerCase() + "." + lastName.toLowerCase() + domains[rnd.nextInt(6 - 0 + 1)];
        } else if (type == 2) {
            email = firstName.substring(0, 1).toLowerCase() + lastName.toLowerCase() + String.valueOf(rnd.nextInt(20 - 1 + 1) + 1) + domains[rnd.nextInt(6 - 0 + 1)];
        } else if (type == 3) {
            email = lastName.toLowerCase() + String.valueOf(rnd.nextInt(20 - 1 + 1) + 1) + domains[rnd.nextInt(6 - 0 + 1)];
        }
        else {
            throw new IllegalArgumentException("Third argument must be an integer between 1 and 3, inclusive.");
        }

        return email;
    }

    public static ArrayList<String> extractNames(String path) {
        ArrayList<String> names = new ArrayList<String>();

        try {
            File myObj = new File(path);
            reader = new Scanner(myObj);

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String output = data.substring(0, 1) + data.substring(1).toLowerCase();
                names.add(output);
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred when reading the file");
            System.out.println(e.getMessage());
        }
        finally {
            reader.close();
        }

        return names;
    }
}
