package app.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.DataBase;
import app.inventario.Cliente;
import app.inventario.Factura;
import app.inventario.General;
import app.inventario.Producto;
import app.inventario.Usuario;

public class BackController {

    public static BackController controller;
    private static DatabaseController database;
    public static enum Table {
        PRODUCTO, CLIENTE, USUARIO, FACTURA, FACTURAPRODUCTO
    }

    private BackController(String user, String password) throws ClassNotFoundException, SQLException {
        database = new DatabaseController(user, password);
    }

    public static void init(String[] args) throws ClassNotFoundException, SQLException {
        controller = new BackController(args[0], args[1]);
    }

    public <T> ArrayList<T> getAll(Table table) throws SQLException {
        ResultSet response = switch (table) {

            case PRODUCTO -> database.Producto();

            case CLIENTE -> database.Cliente();

            case FACTURA -> database.Factura();

            case USUARIO -> database.Usuario();

            case FACTURAPRODUCTO -> database.Factura();

            default -> throw new SQLException();
        };

        return BuildModels.<T>BuildMultiple(table, response);
    }

    public <T> T getById(Table table, String id) throws SQLException {
        ResultSet response = switch (table) {

            case PRODUCTO -> database.Producto(id);

            case CLIENTE -> database.Cliente(id);

            case FACTURA -> database.Factura(id);

            case USUARIO -> database.Usuario(id);

            default -> throw new SQLException();
        };

        return BuildModels.<T>BuildOne(table, response);
    }

    public void Create(Table table, General general) throws SQLException {

        ResultSet response = switch (table) {

            case PRODUCTO -> database.Producto(general.toArray());

            case CLIENTE -> database.Cliente();

            case FACTURA -> database.Factura();

            case USUARIO -> database.Usuario();

            case FACTURAPRODUCTO -> database.Factura();

            default -> throw new SQLException();
        };

        response.close();

    }

    public void Update(Table table, General general) throws SQLException {

        ResultSet response = switch (table) {

            case PRODUCTO -> database.UpdateProducto(general.toArray());

            case CLIENTE -> database.UpdateCliente(general.toArray());

            case FACTURA -> database.UpdateFactura(general.toArray());

            case USUARIO -> database.UpdateUsuario(general.toArray());

            case FACTURAPRODUCTO -> database.Factura();

            default -> throw new SQLException();
        };

        response.close();

    }

    public void DeleteProducto(Table table, String id) throws SQLException {

        ResultSet response = switch (table) {

            case PRODUCTO -> database.DeleteProducto(id);

            case CLIENTE -> database.DeleteCliente(id);

            case FACTURA -> database.DeleteFactura(id);

            case USUARIO -> database.DeleteUsuario(id);

            case FACTURAPRODUCTO -> database.Factura();

            default -> throw new SQLException();
        };

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
        String[] productos = { "Arroz", "Papa", "Néctar" }; //valores para hacer pruebas (mientras se implementa)
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

        return this.database.getAll(
            "Producto"
        );

    }

    public ResultSet Producto(String id) throws SQLException {

        return this.database.getByID(
            "Producto",
            id
        );

    }

    public ResultSet Producto(Object[] objects) throws SQLException {

        return this.database.insert(
            "Producto",
            Producto.toArrayAtributes(),
            objects
        );

    }

    public ResultSet UpdateProducto(Object[] objects) throws SQLException {

        return this.database.update(
            "Producto",
            Producto.toArrayAtributes(),
            objects
        );

    }

    public ResultSet DeleteProducto(String id) throws SQLException {

        return this.database.delete(
            "Producto",
            id
        );

    }

    public ResultSet Factura() throws SQLException {

        return this.database.getAll(
            "Factura"
        );

    }

    public ResultSet Factura(String id) throws SQLException {

        return this.database.getByID(
            "Factura",
            id
        );

    }

    public ResultSet Factura(Object[] objects) throws SQLException {

        return this.database.insert(
            "Factura",
            Factura.toArrayAtributes(),
            objects
        );
        
    }

    public ResultSet UpdateFactura(Object[] objects) throws SQLException {

        return this.database.update(
            "Factura",
            Factura.toArrayAtributes(),
            objects
        );

    }

    public ResultSet DeleteFactura(String id) throws SQLException {

        return this.database.delete(
            "Factura",
            id
        );

    }

    public ResultSet Cliente() throws SQLException {

        return this.database.getAll(
            "Cliente"
        );

    }

    public ResultSet Cliente(String id) throws SQLException {

        return this.database.getByID(
            "Cliente",
            id
        );

    }

    public ResultSet Cliente(Object[] objects) throws SQLException {

        return this.database.insert(
            "Cliente",
            Cliente.toArrayAtributes(),
            objects
        );
        
    }

    public ResultSet UpdateCliente(Object[] objects) throws SQLException {

        return this.database.update(
            "Cliente",
            Cliente.toArrayAtributes(),
            objects
        );

    }

    public ResultSet DeleteCliente(String id) throws SQLException {

        return this.database.delete(
            "Cliente",
            id
        );

    }

    public ResultSet Usuario() throws SQLException {

        return this.database.getAll(
            "Usuario"
        );

    }

    public ResultSet Usuario(String id) throws SQLException {

        return this.database.getByID(
            "Usuario",
            id
        );

    }

    public ResultSet Usuario(Object[] objects) throws SQLException {

        return this.database.insert(
            "Usuario",
            Usuario.toArrayAtributes(),
            objects
        );
        
    }

    public ResultSet UpdateUsuario(Object[] objects) throws SQLException {

        return this.database.update(
            "Usuario",
            Usuario.toArrayAtributes(),
            objects
        );

    }

    public ResultSet DeleteUsuario(String id) throws SQLException {

        return this.database.delete(
            "Usuario",
            id
        );

    }

}

class BuildModels {

    public static <T> T BuildOne(BackController.Table className, ResultSet response) throws SQLException {

        String[] atributes;
        General object = null;

        while(response.next()) {

            object = switch(className) {

                case CLIENTE -> {
                    atributes = Cliente.toArrayAtributes();
                    yield new Cliente
                    (
                        response.getString(atributes[0]),
                        response.getString(atributes[1]),
                        response.getString(atributes[2])
                    );
                }
    
                case FACTURA -> {
                    atributes = Factura.toArrayAtributes();
                    
    
                    yield null /*new Factura
                    (
                        response.getString(atributes[0]),
                        response.getDate(atributes[1]),
                        response.getString(atributes[2]),
                        response.getString(atributes[3])
                    )*/;
                }
    
                case PRODUCTO -> {
                    atributes = Producto.toArrayAtributes();
    
                    yield new Producto
                    (
                        response.getString(atributes[0]),
                        response.getString(atributes[1]),
                        response.getString(atributes[2]),
                        response.getInt(atributes[3]),
                        response.getInt(atributes[4])
                    );
                }
    
                case USUARIO -> {
                    atributes = Usuario.toArrayAtributes();
    
                    yield new Usuario
                    (
                        response.getString(atributes[0]),
                        response.getBoolean(atributes[1])
                    );
                }
    
                default -> null;
    
            };

        }

        return (T)object;

    }

    public static <T> ArrayList<T> BuildMultiple(BackController.Table className, ResultSet response) throws SQLException {

        String[] atributes;
        ArrayList<T> objects = new ArrayList<T>();

        while(response.next()) {

            objects.add((T)switch(className) {

                case CLIENTE -> {
                    atributes = Cliente.toArrayAtributes();
                    yield new Cliente
                    (
                        response.getString(atributes[0]),
                        response.getString(atributes[1]),
                        response.getString(atributes[2])
                    );
                }
    
                case FACTURA -> {
                    atributes = Factura.toArrayAtributes();
    
                    yield null /*new Factura
                    (
                        response.getString(atributes[0]),
                        response.getDate(atributes[1]),
                        response.getString(atributes[2]),
                        response.getString(atributes[3])
                    )*/;
                }
    
                case PRODUCTO -> {
                    atributes = Producto.toArrayAtributes();
    
                    yield new Producto
                    (
                        response.getString(atributes[0]),
                        response.getString(atributes[1]),
                        response.getString(atributes[2]),
                        response.getInt(atributes[3]),
                        response.getInt(atributes[4])
                    );
                }
    
                case USUARIO -> {
                    atributes = Usuario.toArrayAtributes();
    
                    yield new Usuario
                    (
                        response.getString(atributes[0]),
                        response.getBoolean(atributes[1])
                    );
                }
    
                default -> null;
    
            });

        }

        response.close();

        return objects;

    }

}