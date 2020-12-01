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

            System.out.println("Welcome to the library database querying interface!\n\nBelow you'll find a list of 8 queries. Enter the number of the query you wish to execute it:\n");
            System.out.println("1. Select the publishers, and count of books that each publisher has in the database, that have at least 20 books in the library's inventory.");
            System.out.println("2. Find the names and emails of people who have checked out books and not yet returned them.");
            System.out.println("3. Find the names and emails of people who returned a book late.");
            System.out.println("4. Find the amount of employees working on each floor.");
            System.out.println("5. Find the number of available studyrooms on the first floor.");
            System.out.println("6. Find the names of employees, and their salaries, that make more than the average employee salary.");
            System.out.println("7. Find the names and extensions of employees that can help with finding the book 'Goodbye to the Buttermilk Sky.'");
            System.out.println("8. Find the names of the top 8 most frequently checked out books of the month and their information.");

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
                            "HAVING COUNT(bookID) >= 20\n" +
                            "ORDER BY COUNT(bookID) ASC;");

                    System.out.printf("%-25.25s  %-25.25s%n", "Publisher Name", "Book Count");
                    while(results.next()) {
                        System.out.printf("%-25.25s  %-25.25s%n", results.getString("publisherName"), results.getInt("COUNT(bookID)"));
                    }
                    System.out.println();
                }
                else if (query == 2) {
                    results = stmt.executeQuery("SELECT lastName, firstName, email\n" +
                            "FROM Cardholder\n" +
                            "JOIN Borrows ON Cardholder.cardNumber = Borrows.borrowerID\n" +
                            "WHERE returnDate IS NULL GROUP BY cardNumber\n" +
                            "ORDER BY lastName;");

                    System.out.printf("%-15.15s  %-15.15s %-20.30s%n", "Last Name", "First Name", "Email Address");
                    while(results.next()) {
                        System.out.printf("%-15.15s %-15.15s %-20.30s%n", results.getString("lastName"), results.getString("firstName"), results.getString("email"));
                    }
                    System.out.println();
                }
                else if (query == 3) {
                    results = stmt.executeQuery("SELECT lastName, firstName, email\n" +
                            "FROM Cardholder\n" +
                            "JOIN Borrows ON Cardholder.cardNumber = Borrows.borrowerID\n" +
                            "WHERE MONTH(returnDate) - MONTH(dueDate) = 0 AND DAY(returnDate) - DAY(dueDate) > 0 GROUP BY cardNumber\n" +
                            "ORDER BY lastName;");

                    System.out.printf("%-15.15s  %-15.15s %-20.30s%n", "Last Name", "First Name", "Email Address");
                    while(results.next()) {
                        System.out.printf("%-15.15s %-15.15s %-20.30s%n", results.getString("lastName"), results.getString("firstName"), results.getString("email"));
                    }
                    System.out.println();
                }
                else if (query == 4) {
                    results = stmt.executeQuery("SELECT floorNumber, COUNT(employeeID)\n" +
                            "FROM Employee\n" +
                            "GROUP BY floorNumber;");

                    System.out.printf("%-10.10s  %-20.20s%n", "Floor", "Number of Employees");
                    while(results.next()) {
                        System.out.printf("%-10.10s  %-20.20s%n", results.getInt("floorNumber"), results.getInt("COUNT(employeeID)"));
                    }
                    System.out.println();
                }
                else if (query == 5) {
                    results = stmt.executeQuery("SELECT (SELECT studyrooms FROM Location WHERE floorNumber = 1) - (SELECT COUNT(studyroom) FROM Reserves WHERE floorLocation = 1 AND studyroom = 1) AS Difference;");

                    System.out.print("Available studyrooms on the first floor: ");
                    while(results.next()) {
                        System.out.println(results.getInt(1));
                    }
                    System.out.println();
                }
                else if (query == 6) {
                    results = stmt.executeQuery("SELECT lastName, firstName, salary FROM Employee WHERE salary > (SELECT AVG(salary) FROM Employee) ORDER BY salary DESC;");

                    System.out.printf("%-15.15s %-15.15s %-20.20s%n", "Last Name", "First Name", "Salary");
                    while(results.next()) {
                        System.out.printf("%-15.15s %-15.15s %-20.20s%n", results.getString("lastName"), results.getString("firstName"), results.getInt("salary"));
                    }
                    System.out.println();
                }
                else if (query == 7) {
                    results = stmt.executeQuery("SELECT lastName, firstName, extension FROM Employee WHERE floorNumber = (SELECT floorLocation FROM Book WHERE title = 'Goodbye to the Buttermilk Sky');");

                    System.out.printf("%-15.15s  %-15.15s %-20.20s%n", "Last Name", "First Name", "Extension");
                    while(results.next()) {
                        System.out.printf("%-15.15s  %-15.15s %-20.20s%n", results.getString("lastName"), results.getString("firstName"), results.getInt("extension"));
                    }
                    System.out.println();
                }
                else if (query == 8) {
                    results = stmt.executeQuery("SELECT Book.title, bookInfo\n" +
                            "FROM Book\n" +
                            "INNER JOIN \n" +
                            "(SELECT title\n" +
                            "FROM Borrows\n" +
                            "LEFT JOIN Book ON Book.bookID = Borrows.bookID\n" +
                            "GROUP BY title\n" +
                            "ORDER BY COUNT(title) DESC\n" +
                            "LIMIT 8) AS result\n" +
                            "ON result.title = Book.title\n" +
                            "ORDER BY Book.title ASC;");

                    System.out.printf("%-75.75s  %-1000.1000s%n", "Book Title", "Book Info");
                    while(results.next()) {
                        System.out.printf("%-75.75s  %-1000.1000s%n", results.getString("Book.title"), results.getString("bookInfo"));
                    }
                    System.out.println();
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
