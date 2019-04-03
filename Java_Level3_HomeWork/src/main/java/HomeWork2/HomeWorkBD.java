package HomeWork2;




import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

//CREATE TABLE workers_table (
//        Id       INTEGER PRIMARY KEY AUTOINCREMENT,
//        Name     TEXT,
//        Age      INTEGER,
//        Mail     TEXT    UNIQUE,
//        Phone    INTEGER UNIQUE,
//        Position TEXT
//        );

public class HomeWorkBD {

    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement ps;


    public static void main(String[] args) {

        try {
            connect();
            readFile();

//            stmt = connection.createStatement();

            stmt.execute("CREATE TABLE IF NOT EXISTS workers_table (Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Name  TEXT, Age   INTEGER, Mail  TEXT UNIQUE, Phone INTEGER UNIQUE, Position TEXT);");

            //stmt.execute("DELETE FROM workers_table");


            ResultSet rs = stmt.executeQuery("SELECT * FROM workers_table");

            //stmt.execute("INSERT INTO workers_table (Position) VALUES ('Architect')");
//            stmt.executeUpdate("DELETE FROM workers_table WHERE Age < 30");
            //stmt.execute("DELETE FROM workers_table");
            //System.out.println(stmt.executeUpdate("DELETE FROM workers_table"));





//            System.out.println(stmt.executeUpdate("DELETE FROM workers_table WHERE Age > 30"));

            while (rs.next()) {
                System.out.println(rs.getInt("Id") + " " + rs.getString("Name") + " "
                        + rs.getInt("Age") + " " + rs.getString("Mail") + " " +
                        rs.getString("Phone") + " " + rs.getString("Position"));
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        disconnect();
    }

    public static void readFile() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("F:\\1. Java Core\\2. Мои решения задач\\Java_Level3_HomeWork\\Update.txt");
        Scanner scanner = new Scanner(fileInputStream);

        while (scanner.hasNext()) {
            String[] mass = scanner.nextLine().split(" ");
            try {
                updateDB(mass[0], mass[4]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateDB(String id, String newPhone) throws SQLException {
        String sql = String.format("UPDATE workers_table SET Phone = '%s' where id = '%s'", newPhone, id);
        stmt.executeUpdate(sql);
    }

    public static void connect() throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:workers_table.db");
        stmt = connection.createStatement();

    }

    public  static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
