package app;

import java.util.Properties;
import java.math.BigDecimal;
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

            if(objects[i] == "") objects[i] = null;

            try {
                className = objects[i].getClass().getSimpleName();
            } catch (NullPointerException e) {
                className = "Null";
            }

            switch(className) {

                case "Byte" -> statement.setByte(i + 1, (Byte) objects[i]);

                case "Short" -> statement.setShort(i + 1, (Short) objects[i]);

                case "Integer" -> statement.setInt(i + 1, (Integer) objects[i]);

                case "Long" -> statement.setLong(i + 1, (Long) objects[i]);

                case "Float" -> statement.setFloat(i + 1, (Float) objects[i]);

                case "Double" -> statement.setDouble(i + 1, (Double) objects[i]);

                case "String" -> statement.setString(i + 1, (String) objects[i]);

                case "Boolean" -> statement.setBoolean(i + 1, (Boolean) objects[i]);

                case "BigDecimal" -> statement.setBigDecimal(i + 1, (BigDecimal) (objects[i]));
            
                default -> statement.setNull(i + 1, 0);
    
            }

        }

        ResultSet response = statement.executeQuery();

        statement.close();

        return response;

    }

    public ResultSet getAll(String table) throws SQLException {

        String query = "SELECT * FROM " + table + " ORDER BY " + this.getIDTableIndex(table) + " asc;";

        return this.query(query);

    }

    public ResultSet getByID(String table, Object id) throws SQLException {

        String query = "SELECT * FROM " + table + " WHERE " + this.getIDTable(table) + ';';

        Object[] elements = { id };

        return this.query(query, elements);

    }

    public ResultSet getRelation(String table, int idFactura)throws SQLException {

        String query = "SELECT * FROM " + table + " WHERE " + this.getIDFacturaProducto()[0] + " ORDER BY " + this.getIDTableIndex(table) + " asc;";

        Object[] elements = { idFactura };

        return this.query(query, elements);

    }

    public ResultSet getUniqueRelation(String table, int idFactura, int idProducto) throws SQLException {

        String[] keys = this.getIDFacturaProducto();

        String query = "SELECT * FROM " + table + " WHERE " + keys[0] + " AND " + keys[1] + ';';

        Object[] elements = { idFactura, idProducto };

        return this.query(query, elements);

    }

    public ResultSet insert(String table, String[] atributes, Object[] objects) throws SQLException {

        Object[] provisional = null;

        for(int i = 0; i < objects.length; ++i) {
            if(objects[i] == null) {
                objects = remueveElement(objects, i);
                provisional = remueveElement(atributes, i);
                atributes = new String[provisional.length];
                i = 0;
                for(int j = 0; j < provisional.length; ++j) {
                    atributes[j] = (String) provisional[j];
                }
            }
        }

        for(int i = 0; i < objects.length; ++i) {
            if(atributes[i] == "fecha") {
                objects = remueveElement(objects, i);
                provisional = remueveElement(atributes, i);
                atributes = new String[provisional.length];
                i = 0;
                for(int j = 0; j < provisional.length; ++j) {
                    atributes[j] = (String) provisional[j];
                }
            }
        }

        String query = "INSERT INTO " + table +"(";

        for(int i = 0; i < atributes.length; ++i) {
            query += atributes[i] + ((i < atributes.length - 1)? ((atributes.length == 1)? ')' : ", ") : ") ");
        }

        query += "VALUES(";

        for(int i = 0; i < objects.length; ++i) {
            query += '?' + ((i < objects.length - 1)? ((objects.length == 1)? ")" : ", ") : ") RETURNING *;");
        }

        return this.query(query, objects);

    }

    public ResultSet update(String table, String[] atributes, Object[] objects) throws SQLException {

        for(int i = 0; i < objects.length - 1; i++) {
            Object burbuja = objects[i];
            objects[i] = objects[i + 1];
            objects[i + 1] = burbuja;
        }

        String query = "UPDATE " + table + " SET ";

        for(int i = 1; i < atributes.length; ++i) {
            query += atributes[i] + " =  ?" + ((i < objects.length - 1)? ", " : " WHERE " + this.getIDTable(table) + " RETURNING *;");
        }

        return this.query(query, objects);

    }

    public ResultSet delete(String table, Object id) throws SQLException {

        String query = "DELETE FROM " + table + " WHERE " + this.getIDTable(table) + " CASCADE RETURNING *;";

        Object[] objects = { id };

        return this.query(query, objects);

    }

    private String getIDTable(String table) {

        return switch(table) {

            case "Cliente" -> "idcliente = ?";

            case "Producto" -> "idproducto = ?";

            case "Factura" -> "idfactura = ?";

            case "Usuario" -> "username = ?";

            default -> throw new IllegalArgumentException("Unexpected value: " + table);

        };

    }

    private String getIDTableIndex(String table) {

        return switch(table) {

            case "Cliente" -> "idcliente";

            case "Producto" -> "idproducto";

            case "Factura", "FacturaProducto" -> "idfactura";

            case "Usuario" -> "username";

            default -> throw new IllegalArgumentException("Unexpected value: " + table);

        };

    }

    private String[] getIDFacturaProducto() {

        String[] ids = { this.getIDTable("Factura"), this.getIDTable("Producto") };

        return ids;
    }

    private Object[]  remueveElement(Object[] objects, int i) {
        Object[] newObjects = new Object[objects.length - 1];
         if (i > 0) {
               System.arraycopy(objects, 0, newObjects, 0, i);
         }
         if (newObjects.length > i) {
          System.arraycopy(objects, i + 1, newObjects, i, newObjects.length - i);
         }
         return newObjects;
       }

}