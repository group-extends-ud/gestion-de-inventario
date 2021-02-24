package app.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import app.DataBase;

public class BackController {

    public static BackController controller;
    private static DatabaseController database;

    private BackController(String user, String password) throws ClassNotFoundException, SQLException {
        database = new DatabaseController(user, password);
    }

    public static void init(String[] args) throws ClassNotFoundException, SQLException {
        controller = new BackController(args[0], args[1]);
    }
/*
    public static void getProductos() throws SQLException {

        ResultSet response = database.getAll("Producto");

        while(response.next()) {
            System.out.println(response.getString("Nombre"));
        }

        response.close();
    }
*/
    public static int validarIngreso(String user, String password) {
        return 1;
    }

    public static void insertarProducto(String nombre, Double precio, int stock, int stockMinimo) {
        //por implementar :D
    }
}

class DatabaseController {

    private DataBase database;

    public DatabaseController(String user, String password) throws ClassNotFoundException, SQLException {
        this.database = new DataBase(user, password);
    }

    public ResultSet Producto() throws SQLException {

        return this.database.getAll("Producto");

    }

    public ResultSet Producto(String id) throws SQLException {

        return this.database.getByID("Producto", id);

    }

    public ResultSet Factura() throws SQLException {

        return this.database.getAll("Factura");

    }

    public ResultSet Factura(String id) throws SQLException {

        return this.database.getByID("Factura", id);

    }

    public ResultSet Cliente() throws SQLException {

        return this.database.getAll("Cliente");

    }

    public ResultSet Cliente(String id) throws SQLException {

        return this.database.getByID("Cliente", id);

    }

}