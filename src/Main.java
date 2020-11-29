import java.sql.*;
import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    static Connection con = null;
    static Scanner input = new Scanner(System.in);

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

            System.out.println("Welcome to the library database querying interface!\nBelow you'll find a list of 8 queries. Enter the number of the query to execute it:\n");
            System.out.println("1. ");
            System.out.println("2. ");
            System.out.println("3. ");
            System.out.println("4. ");
            System.out.println("5. ");
            System.out.println("6. ");
            System.out.println("7. ");
            System.out.println("8. ");

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
                    System.out.println("Test 1");
                }
                else if (query == 2) {
                    System.out.println("Test 2");
                }
                else if (query == 3) {
                    System.out.println("Test 3");
                }
                else if (query == 4) {
                    System.out.println("Test 4");
                }
                else if (query == 5) {
                    System.out.println("Test 5");
                }
                else if (query == 6) {
                    System.out.println("Test 6");
                }
                else if (query == 7) {
                    System.out.println("Test 7");
                }
                else if (query == 8) {
                    System.out.println("Test 8");
                }

//                /* Querying*/
//                //Query 1
//                ResultSet results;
//
//                results = stmt.executeQuery("SELECT AVG(salary) AS 'averageSalary' FROM Employee, Department WHERE Employee.DepartmentID = Department.Did AND Department.Dname = 'Marketing'");
//
//                while(results.next()) {
//                    System.out.println("Average salary amongst all employees: $" + results.getInt("averageSalary") + "\n");
//                }
//
//                //Query 2
//                results = stmt.executeQuery("SELECT Erank, AVG(salary) AS 'averageSalary' FROM Employee GROUP BY Erank");
//
//                System.out.println("Average salary by rank:");
//                while(results.next()) {
//                    System.out.println(results.getInt("Erank") + " " + results.getInt("averageSalary"));
//                }
//                System.out.println();
//
//                //Query 3
//                results = stmt.executeQuery("SELECT Eid, Ename FROM Employee JOIN Department ON Employee.DepartmentID = Department.Did WHERE Department.Dname = 'Human Resources' " +
//                        "AND salary >= (SELECT AVG(salary) FROM Employee, Department WHERE Employee.DepartmentID = Department.Did AND Department.Dname = 'Marketing')");
//
//                System.out.println("Employee IDs and names from Human Resources Department with salary >= average salary of employees from Marketing Department:");
//                while(results.next()) {
//                    System.out.println(results.getInt("Eid") + " " + results.getString("Ename"));
//                }
//                System.out.println();
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
