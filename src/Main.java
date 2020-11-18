import java.sql.*;
public class Main {
    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MyCompany?serverTimezone=UTC", "raihanahmed","password");
            System.out.println("Connection successful!");

            Statement stmt = con.createStatement();

            /* Record creation*/
            //Creating departments
            Boolean insert;
            insert = stmt.execute("INSERT INTO Department VALUES (1, 'Marketing', '4th floor' ), (2, 'Human Resources', '2nd floor');");

            //Creating employees
            insert = stmt.execute("INSERT INTO Employee VALUES (1, 'Raihan', 1, 79000, 1), (2, 'Andrew', 2, 71000, 1), (3, 'Daoud', 3, 63000, 1), " +
                    "(4, 'Tony', 1, 82000, 2), (5, 'Eric', 2, 69000, 2), (6, 'Diego', 3, 70000, 2);");

            //Creating projects
            insert = stmt.execute("INSERT INTO Project VALUES (1, 1, 'Project 1', 59000, 2018), (2, 1, 'Project 2', 72000, 2020), " +
                    "(3, 1, 'Project 3', 64000, 2019), (4, 2, 'Project 4', 91000, 2019), (5, 2, 'Project 5', 71000, 2020), (6, 2, 'Project 6', 51000, 2019);");

            /* Querying*/
            //Query 1
            ResultSet results;

            results = stmt.executeQuery("SELECT AVG(salary) AS 'averageSalary' FROM Employee, Department WHERE Employee.DepartmentID = Department.Did AND Department.Dname = 'Marketing'");

            while(results.next()) {
                System.out.println("Average salary amongst all employees: $" + results.getInt("averageSalary") + "\n");
            }

            //Query 2
            results = stmt.executeQuery("SELECT Erank, AVG(salary) AS 'averageSalary' FROM Employee GROUP BY Erank");

            System.out.println("Average salary by rank:");
            while(results.next()) {
                System.out.println(results.getInt("Erank") + " " + results.getInt("averageSalary"));
            }
            System.out.println();

            //Query 3
            results = stmt.executeQuery("SELECT Eid, Ename FROM Employee JOIN Department ON Employee.DepartmentID = Department.Did WHERE Department.Dname = 'Human Resources' " +
                    "AND salary >= (SELECT AVG(salary) FROM Employee, Department WHERE Employee.DepartmentID = Department.Did AND Department.Dname = 'Marketing')");

            System.out.println("Employee IDs and names from Human Resources Department with salary >= average salary of employees from Marketing Department:");
            while(results.next()) {
                System.out.println(results.getInt("Eid") + " " + results.getString("Ename"));
            }
            System.out.println();

            con.close();

            if (con.isClosed()) {
                System.out.println("Connection is closed!");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            System.out.println("--------------Querying finished--------------");
        }
    }
}
