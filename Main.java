import java.sql.*;
import java.util.Scanner;

public class Main {

    static final String URL =
            "jdbc:mysql://localhost:3306/student_db";

    static final String USER = "root";

    static final String PASSWORD = "Selva@2003";

    static Scanner sc = new Scanner(System.in);

    // Add Student
    static void addStudent(Connection con) throws Exception {

        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Course: ");
        String course = sc.nextLine();

        String query =
                "INSERT INTO students VALUES (?, ?, ?, ?)";

        PreparedStatement ps =
                con.prepareStatement(query);

        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setInt(3, age);
        ps.setString(4, course);

        ps.executeUpdate();

        System.out.println("Student Added Successfully!");
    }

    // View Students
    static void viewStudents(Connection con) throws Exception {

        String query = "SELECT * FROM students";

        Statement st = con.createStatement();

        ResultSet rs = st.executeQuery(query);

        while (rs.next()) {

            System.out.println("================================");
            System.out.println("ID      : " + rs.getInt("id"));
            System.out.println("Name    : " + rs.getString("name"));
            System.out.println("Age     : " + rs.getInt("age"));
            System.out.println("Course  : " + rs.getString("course"));
            System.out.println("================================");
        }
    }

    // Search Student
    static void searchStudent(Connection con)
            throws Exception {

        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();

        String query =
                "SELECT * FROM students WHERE id=?";

        PreparedStatement ps =
                con.prepareStatement(query);

        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            System.out.println("================================");
            System.out.println("ID      : " + rs.getInt("id"));
            System.out.println("Name    : " + rs.getString("name"));
            System.out.println("Age     : " + rs.getInt("age"));
            System.out.println("Course  : " + rs.getString("course"));
            System.out.println("================================");

        } else {

            System.out.println("Student Not Found!");
        }
    }

    // Delete Student
    static void deleteStudent(Connection con)
            throws Exception {

        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();

        String query =
                "DELETE FROM students WHERE id=?";

        PreparedStatement ps =
                con.prepareStatement(query);

        ps.setInt(1, id);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("Student Deleted!");
        } else {
            System.out.println("Student Not Found!");
        }
    }
    static void updateStudent(Connection con)
        throws Exception {

    System.out.print("Enter Student ID: ");
    int id = sc.nextInt();
    sc.nextLine();

    String checkQuery =
            "SELECT * FROM students WHERE id=?";

    PreparedStatement checkPs =
            con.prepareStatement(checkQuery);

    checkPs.setInt(1, id);

    ResultSet rs = checkPs.executeQuery();

    if (rs.next()) {

        System.out.print("Enter New Name: ");
        String name = sc.nextLine();

        System.out.print("Enter New Age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter New Course: ");
        String course = sc.nextLine();

        String updateQuery =
                "UPDATE students SET name=?, age=?, course=? WHERE id=?";

        PreparedStatement ps =
                con.prepareStatement(updateQuery);

        ps.setString(1, name);
        ps.setInt(2, age);
        ps.setString(3, course);
        ps.setInt(4, id);

        ps.executeUpdate();

        System.out.println("Student Updated Successfully!");

    } else {

        System.out.println("Student Not Found!");
    }
}
    static boolean login(Connection con)
        throws Exception {

    System.out.println("===== LOGIN =====");

    System.out.print("Enter Username: ");
    String username = sc.next();

    System.out.print("Enter Password: ");
    String password = sc.next();

    String query =
            "SELECT * FROM admin WHERE username=? AND password=?";

    PreparedStatement ps =
            con.prepareStatement(query);

    ps.setString(1, username);
    ps.setString(2, password);

    ResultSet rs = ps.executeQuery();

    if (rs.next()) {

        System.out.println("Login Successful!");
        return true;

    } else {

        System.out.println("Invalid Username or Password!");
        return false;
    }
}

    public static void main(String[] args) {

        try {

            Class.forName(
                    "com.mysql.cj.jdbc.Driver");

            Connection con =
                    DriverManager.getConnection(
                            URL,
                            USER,
                            PASSWORD
                    );

            System.out.println(
                    "Database Connected Successfully!");
                    if (!login(con)) {
                    con.close();
                    return;
}

            while (true) {

                System.out.println("\n===== JDBC STUDENT MANAGEMENT =====");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Search Student");
                System.out.println("4. Update Student");
                System.out.println("5. Delete Student");
                System.out.println("6. Exit");

                System.out.print("Enter Choice: ");

                int choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        addStudent(con);
                        break;

                    case 2:
                        viewStudents(con);
                        break;

                    case 3:
                        searchStudent(con);
                        break;

                    case 4:
                        updateStudent(con);
                        break;

                    case 5:
                        deleteStudent(con);
                        break;

                    case 6:
                        con.close();
                        System.out.println("Thank You!");
                        System.exit(0);

                    default:
                        System.out.println("Invalid Choice!");
                }
            }

        } catch (Exception e) {

            System.out.println(e);
        }
    }
}
