package app.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import app.DataBase;

public class BackController {

    public static BackController controller;
    private static DatabaseController database;
    public static enum Table {
        PRODUCTO, CLIENTE, USUARIO, FACTURA
    }

    private BackController(String user, String password) throws ClassNotFoundException, SQLException {
        database = new DatabaseController(user, password);
    }

    public static void init(String[] args) throws ClassNotFoundException, SQLException {
        controller = new BackController(args[0], args[1]);
    }

    public static void getAll(Table table) throws SQLException {
        ResultSet response = switch (table) {

            case PRODUCTO -> database.Producto();

            case CLIENTE -> database.Cliente();

            case FACTURA -> database.Factura();

            default -> throw new SQLException();
        };

        while(response.next()) {

            System.out.println(response.getString(2));

        }

        response.close();
    }

    public static int validarIngreso(String user, String password) {
        return 1;
    }

    public static void insertarProducto(String nombre, Double precio, int stock, int stockMinimo) {
        //por implementar
    }

    public static String[] getNombresProductos() {
        //por implementar
        String[] productos = {"Arroz", "Papa", "Néctar"}; //valores para hacer pruebas (mientras se implementa)
        return productos;
    }

    public static double getPrecio(String nombreProducto) {
        //por implementar
        System.out.println(nombreProducto);//para hacer pruebas
        return 1000.0;
    }

    public static int getStock(String nombreProducto) {
        //por implementar
        return 0;
    }

    public static void insertarMovimiento(String nombreProducto, int cantidad) {
        //por implementar
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

    public ResultSet Usuario() throws SQLException {

        return this.database.getAll("Usuario");

    }

    public ResultSet Usuario(String id) throws SQLException {

        return this.database.getByID("Usuario", id);

    }

}