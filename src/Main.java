import java.sql.*;
import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    static Connection con = null;
    static Scanner input = new Scanner(System.in);
    static ResultSet results;

    static {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryDatabase?serverTimezone=UTC", dbInfo.getUsername(), dbInfo.getPassword());
        }
        catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        try {
            Statement stmt = con.createStatement();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Welcome to the library database querying interface!\n\nBelow you'll find a list of 8 queries. Enter the number of the query to execute it:\n");
            System.out.println("1. Select the publishers, and count of books that each publisher has in the database, that has at least 20 books in the library's inventory.");
            System.out.println("2. Find the count of employees working on each floor grouped by floor number.");
            System.out.println("3. Find the last name, first name, and email address of each library member that has checked out a book and not returned it.");
            System.out.println("4. Find the last name, first name, and email address of each library member that has a book checked out within two days of its return date.");
            System.out.println("5. Find the last name, first name, and email address of each library member that returned a book late and the amount of days the book was late.");
            System.out.println("6. Find the amount of available study rooms on the 3rd floor.");
            System.out.println("7. Find the names of the employees, and their salaries, that make more than the average employee salary.");
            System.out.println("8. Find the extensions of the employees that can help with finding a book on the 2nd floor.");

            System.out.println("\nTo quit the portal, type 'exit'");

            String inputData;

            while (!(inputData = br.readLine()).equals("exit")) {
                int query = 0;

                try {
                    query = Integer.parseInt(inputData);
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid input. Try again.");
                }

                if (query > 8) {
                    System.out.println("Invalid input. Try again.");
                }
                else if (query == 1) {
                    results = stmt.executeQuery("SELECT publisherName, COUNT(bookID) FROM Publisher JOIN Book ON Book.publisherID = Publisher.publisherID\n" +
                            "GROUP BY publisherName\n" +
                            "HAVING COUNT(bookID) >= 20;");

                    System.out.printf("%-20.20s  %-20.20s%n", "Publisher Name", "Book Count");
                    while(results.next()) {
                        System.out.printf("%-20.20s  %-20.20s%n", results.getString("publisherName"), results.getInt("COUNT(bookID)"));
                    }
                    System.out.println();
                }
                else if (query == 2) {
                    results = stmt.executeQuery("SELECT publisherName, COUNT(bookID) FROM Publisher JOIN Book ON Book.publisherID = Publisher.publisherID\n" +
                            "GROUP BY publisherName\n" +
                            "HAVING COUNT(bookID) >= 20;");

                    System.out.printf("%-20.20s  %-20.20s%n", "Publisher Name", "Book Count");
                    while(results.next()) {
                        System.out.printf("%-20.20s  %-20.20s%n", results.getString("publisherName"), results.getInt("COUNT(bookID)"));
                    }
                    System.out.println();
                }
                else if (query == 3) {
//                    results = stmt.executeQuery("SELECT publisherName, COUNT(bookID) FROM Publisher JOIN Book ON Book.publisherID = Publisher.publisherID\n" +
//                            "GROUP BY publisherName\n" +
//                            "HAVING COUNT(bookID) >= 20;");
//
//                    System.out.printf("%-20.20s  %-20.20s%n", "Publisher Name", "Book Count");
//                    while(results.next()) {
//                        System.out.printf("%-20.20s  %-20.20s%n", results.getString("publisherName"), results.getInt("COUNT(bookID)"));
//                    }
//                    System.out.println();
                }
                else if (query == 4) {
//                    results = stmt.executeQuery("SELECT publisherName, COUNT(bookID) FROM Publisher JOIN Book ON Book.publisherID = Publisher.publisherID\n" +
//                            "GROUP BY publisherName\n" +
//                            "HAVING COUNT(bookID) >= 20;");
//
//                    System.out.printf("%-20.20s  %-20.20s%n", "Publisher Name", "Book Count");
//                    while(results.next()) {
//                        System.out.printf("%-20.20s  %-20.20s%n", results.getString("publisherName"), results.getInt("COUNT(bookID)"));
//                    }
//                    System.out.println();
                }
                else if (query == 5) {
//                    results = stmt.executeQuery("SELECT publisherName, COUNT(bookID) FROM Publisher JOIN Book ON Book.publisherID = Publisher.publisherID\n" +
//                            "GROUP BY publisherName\n" +
//                            "HAVING COUNT(bookID) >= 20;");
//
//                    System.out.printf("%-20.20s  %-20.20s%n", "Publisher Name", "Book Count");
//                    while(results.next()) {
//                        System.out.printf("%-20.20s  %-20.20s%n", results.getString("publisherName"), results.getInt("COUNT(bookID)"));
//                    }
//                    System.out.println();
                }
                else if (query == 6) {
//                    results = stmt.executeQuery("SELECT publisherName, COUNT(bookID) FROM Publisher JOIN Book ON Book.publisherID = Publisher.publisherID\n" +
//                            "GROUP BY publisherName\n" +
//                            "HAVING COUNT(bookID) >= 20;");
//
//                    System.out.printf("%-20.20s  %-20.20s%n", "Publisher Name", "Book Count");
//                    while(results.next()) {
//                        System.out.printf("%-20.20s  %-20.20s%n", results.getString("publisherName"), results.getInt("COUNT(bookID)"));
//                    }
//                    System.out.println();
                }
                else if (query == 7) {
//                    results = stmt.executeQuery("SELECT publisherName, COUNT(bookID) FROM Publisher JOIN Book ON Book.publisherID = Publisher.publisherID\n" +
//                            "GROUP BY publisherName\n" +
//                            "HAVING COUNT(bookID) >= 20;");
//
//                    System.out.printf("%-20.20s  %-20.20s%n", "Publisher Name", "Book Count");
//                    while(results.next()) {
//                        System.out.printf("%-20.20s  %-20.20s%n", results.getString("publisherName"), results.getInt("COUNT(bookID)"));
//                    }
//                    System.out.println();
                }
                else if (query == 8) {
//                    results = stmt.executeQuery("SELECT publisherName, COUNT(bookID) FROM Publisher JOIN Book ON Book.publisherID = Publisher.publisherID\n" +
//                            "GROUP BY publisherName\n" +
//                            "HAVING COUNT(bookID) >= 20;");
//
//                    System.out.printf("%-20.20s  %-20.20s%n", "Publisher Name", "Book Count");
//                    while(results.next()) {
//                        System.out.printf("%-20.20s  %-20.20s%n", results.getString("publisherName"), results.getInt("COUNT(bookID)"));
//                    }
//                    System.out.println();
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            con.close();

            if (con.isClosed()) {
                System.out.println("Connection is closed!");
            }

            System.out.println("--------------Querying finished--------------");
        }
    }
}
