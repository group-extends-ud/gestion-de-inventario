package app;

import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

    private static Connection connection;
    private static DataBase dataBase;

    private DataBase(String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        Properties properties = new Properties();

        properties.setProperty("user", user);
        properties.setProperty("password", password);
        
        
        connection = DriverManager.getConnection("jdbc:postgresql://localhost/Sales System", properties);
    }

    public static void query(String query) throws SQLException {

        Statement statement = connection.createStatement();

        ResultSet response = statement.executeQuery(query);

        while(response.next()) {
            System.out.println(response.getString("nombre"));
        }

        response.close();
        statement.close();
    }

    public static int validarIngreso(String username, String password) {
        return 1;
    }

    public static void connect(String user, String password) throws ClassNotFoundException, SQLException {
        dataBase = new DataBase(user, password);
    }

}
