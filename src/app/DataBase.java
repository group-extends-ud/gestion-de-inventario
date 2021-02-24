package app;

import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

    private Connection connection;

    public DataBase(String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        Properties properties = new Properties();

        properties.setProperty("user", user);
        properties.setProperty("password", password);

        this.connection = DriverManager.getConnection("jdbc:postgresql://localhost/Sales System", properties);
    }

    public ResultSet query(String query) throws SQLException {

        Statement statement = this.connection.createStatement();

        ResultSet response = statement.executeQuery(query);

        statement.close();

        return response;
    }

    public ResultSet query(String query, Object[] objects) throws SQLException {

        String className;

        PreparedStatement statement = this.connection.prepareStatement(query);

        for(int i = 0; i < objects.length; ++i) {
            className = objects[i].getClass().getSimpleName();

            switch(className) {

                case "Byte" -> statement.setByte(i + 1, (Byte) objects[i]);

                case "Short" -> statement.setShort(i + 1, (Short) objects[i]);

                case "Integer" -> statement.setInt(i + 1, (Integer) objects[i]);

                case "Long" -> statement.setLong(i + 1, (Long) objects[i]);

                case "Float" -> statement.setFloat(i + 1, (Float) objects[i]);

                case "Double" -> statement.setDouble(i + 1, (Double) objects[i]);

                case "String" -> statement.setString(i + 1, (String) objects[i]);

                case "Boolean" -> statement.setBoolean(i + 1, (Boolean) objects[i]);
            
                default -> statement.setNull(i + 1, 0);
    
            }

        }


        ResultSet response = statement.executeQuery();

        statement.close();

        return response;
    }

    public ResultSet getAll(String table) throws SQLException {

        String query = "SELECT * FROM " + table + ';';

        return this.query(query);
    }

    public ResultSet getByID(String table, String id) throws SQLException {

        String query = "SELECT * FROM " + table + " WHERE " + this.getIDTable(table) + ';';

        Object[] elements = { id };

        return this.query(query, elements);

    }

    private String getIDTable(String table) {

        return switch(table) {

            case "Cliente" -> "IDCliente = ?";

            case "Factura" -> "IDFactura = ?";

            case "Producto" -> "IDProducto = ?";

            default -> this.getIDTable("Factura") + " AND " + this.getIDTable("Producto");

        };

    }

}