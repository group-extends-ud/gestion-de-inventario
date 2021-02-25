package app.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.DataBase;
import app.inventario.*;

public class BackController {

    public static BackController controller;
    private static DatabaseController database;

    private BackController(String user, String password) throws ClassNotFoundException, SQLException {
        database = new DatabaseController(user, password);
    }

    public static void init(String[] args) throws ClassNotFoundException, SQLException {
        controller = new BackController(args[0], args[1]);
    }

    public static void deleteMovimiento(String ID) throws SQLException {
        //por implementar
    }

    public static void updateMovimiento(Movimiento movimiento) {
        //por implementar
    }

    public ArrayList<Producto> Producto() throws SQLException {

        return database.<Producto>getAll(DatabaseController.Table.PRODUCTO);

    }

    public Producto Producto(String id) throws SQLException {

        return database.<Producto>getById(DatabaseController.Table.PRODUCTO, id);

    }

    public Producto Producto(Producto producto) throws SQLException {
        return database.insert(DatabaseController.Table.PRODUCTO, producto);
    }

    public static void updateProducto(Producto producto) throws SQLException {

        database.update(DatabaseController.Table.PRODUCTO, producto);

    }

    public static void deleteProducto(String id) throws SQLException {
        database.delete(DatabaseController.Table.PRODUCTO, id);
    }

    public ArrayList<Cliente> Cliente() throws SQLException {

        return database.<Cliente>getAll(DatabaseController.Table.CLIENTE);

    }

    public Cliente Cliente(String id) throws SQLException {

        return database.<Cliente>getById(DatabaseController.Table.CLIENTE, id);

    }

    public Cliente Cliente(Cliente cliente) throws SQLException {

        return database.insert(DatabaseController.Table.CLIENTE, cliente);

    }

    public void updateCliente(Cliente cliente) throws SQLException {

        database.update(DatabaseController.Table.CLIENTE, cliente);

    }

    public void deleteCliente(String id) throws SQLException {

        database.delete(DatabaseController.Table.CLIENTE, id);

    }

    public ArrayList<Usuario> Usuario() throws SQLException {

        return database.<Usuario>getAll(DatabaseController.Table.USUARIO);

    }

    public Usuario Usuario(String id) throws SQLException {

        return database.<Usuario>getById(DatabaseController.Table.USUARIO, id);

    }

    public Usuario Usuario(Usuario usuario) throws SQLException {

        return database.insert(DatabaseController.Table.USUARIO, usuario);

    }

    public void updateUsuario(Usuario usuario) throws SQLException {

        database.update(DatabaseController.Table.USUARIO, usuario);

    }

    public void deleteUsuario(String id) throws SQLException {

        database.delete(DatabaseController.Table.USUARIO, id);

    }

    public ArrayList<Factura> Factura() throws SQLException {

        return database.<Factura>getAll(DatabaseController.Table.FACTURA);

    }

    public Factura Factura(String id) throws SQLException {

        return database.<Factura>getById(DatabaseController.Table.FACTURA, id);

    }

    public Factura Factura(Factura factura) throws SQLException {

        return database.insert(DatabaseController.Table.FACTURA, factura);

    }

    public void updateFactura(Factura factura) throws SQLException {

        database.update(DatabaseController.Table.FACTURA, factura);

    }

    public void deleteFactura(String id) throws SQLException {

        database.delete(DatabaseController.Table.FACTURA, id);

    }

    public ArrayList<FacturaProducto> FacturaProducto() throws SQLException {

        return database.<FacturaProducto>getAll(DatabaseController.Table.FACTURAPRODUCTO);

    }

    public FacturaProducto FacturaProducto(String id) throws SQLException {

        return database.<FacturaProducto>getById(DatabaseController.Table.FACTURAPRODUCTO, id);

    }

    public FacturaProducto FacturaProducto(FacturaProducto facturaProducto) throws SQLException {

        return database.insert(DatabaseController.Table.FACTURAPRODUCTO, facturaProducto);

    }

    public void updateFacturaProducto(FacturaProducto facturaProducto) throws SQLException {

        database.update(DatabaseController.Table.FACTURAPRODUCTO, facturaProducto);

    }

    public void deleteFacturaProducto(String id) throws SQLException {

        database.delete(DatabaseController.Table.FACTURAPRODUCTO, id);

    }

    public static int validarIngreso(String user, String password) {
        return 1;
    }

    public static void insertarProducto(String nombre, Double precio, int stock, int stockMinimo) {
        //por implementar
        //la idea es que el cliente no esté ingresando id, sino que la base de datos asigne uno automáticamente
    }

    public static String[] getNombresProductos() {
        //por implementar
        //para que el usuario tenga una lista de todos los productos al realizar el movimiento
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
        //la misma dinámica que el método insertarProducto (id automático de la que el BackController se encarga)
    }
}

class DatabaseController {

    private DataBase database;

    public static enum Table {
        PRODUCTO, CLIENTE, USUARIO, FACTURA, FACTURAPRODUCTO
    }

    public DatabaseController(String user, String password) throws ClassNotFoundException, SQLException {
        this.database = new DataBase(user, password);
    }

    public <T> ArrayList<T> getAll(Table table) throws SQLException {

        ResultSet response = switch(table) {

            case PRODUCTO -> this.database.getAll(
                Producto.class.getSimpleName()
            );

            case CLIENTE -> this.database.getAll(
                Cliente.class.getSimpleName()
            );

            case FACTURA -> this.database.getAll(
                Factura.class.getSimpleName()
            );

            case USUARIO -> this.database.getAll(
                Usuario.class.getSimpleName()
            );

            case  FACTURAPRODUCTO -> this.database.getAll(
                FacturaProducto.class.getSimpleName()
            );

            default -> throw new IllegalArgumentException("Unexpected value: " + table);

        };

        return BuildModels.BuildMultiple(table, response);

    }

    public <T> T getById(Table table, String id) throws SQLException {

        ResultSet response = switch(table) {

            case PRODUCTO -> this.database.getByID(
                Producto.class.getSimpleName(),
                id
            );

            case CLIENTE -> this.database.getByID(
                Cliente.class.getSimpleName(),
                id
            );

            case FACTURA -> this.database.getByID(
                Factura.class.getSimpleName(),
                id
            );

            case USUARIO -> this.database.getByID(
                Usuario.class.getSimpleName(),
                id
            );

            case  FACTURAPRODUCTO -> this.database.getByID(
                FacturaProducto.class.getSimpleName(),
                id
            );

            default -> throw new IllegalArgumentException("Unexpected value: " + table);

        };

        return BuildModels.BuildOne(table, response);

    }

    public <T> T insert(Table table, General general) throws SQLException {

        ResultSet response = switch (table) {

            case PRODUCTO -> database.insert(Producto.class.getSimpleName(), Producto.toArrayAtributes(), general.toArray());

            case CLIENTE -> database.insert(Cliente.class.getSimpleName(), Cliente.toArrayAtributes(), general.toArray());

            case FACTURA -> database.insert(Factura.class.getSimpleName(), Factura.toArrayAtributes(), general.toArray());

            case USUARIO -> database.insert(Usuario.class.getSimpleName(), Usuario.toArrayAtributes(), general.toArray());

            case FACTURAPRODUCTO -> database.insert(FacturaProducto.class.getSimpleName(), FacturaProducto.toArrayAtributes(), general.toArray());

            default -> throw new IllegalArgumentException("Unexpected value: " + table);

        };

        return BuildModels.BuildOne(table, response);

    }

    public void update(Table table, General general) throws SQLException {

        ResultSet response = switch (table) {

            case PRODUCTO -> database.update(Producto.class.getSimpleName(), Producto.toArrayAtributes(), general.toArray());

            case CLIENTE -> database.update(Cliente.class.getSimpleName(), Cliente.toArrayAtributes(), general.toArray());

            case FACTURA -> database.update(Factura.class.getSimpleName(), Factura.toArrayAtributes(), general.toArray());

            case USUARIO -> database.update(Usuario.class.getSimpleName(), Usuario.toArrayAtributes(), general.toArray());

            case FACTURAPRODUCTO -> database.update(FacturaProducto.class.getSimpleName(), FacturaProducto.toArrayAtributes(), general.toArray());

            default -> throw new IllegalArgumentException("Unexpected value: " + table);
        };

        response.close();

    }

    public void delete(Table table, String id) throws SQLException {

        ResultSet response = switch (table) {

            case PRODUCTO -> database.delete(Producto.class.getSimpleName(), id);

            case CLIENTE -> database.delete(Cliente.class.getSimpleName(), id);

            case FACTURA -> database.delete(Factura.class.getSimpleName(), id);

            case USUARIO -> database.delete(Usuario.class.getSimpleName(), id);

            case FACTURAPRODUCTO -> database.delete(FacturaProducto.class.getSimpleName(), id);

            default -> throw new IllegalArgumentException("Unexpected value: " + table);
        };

        response.close();

    }

    private static class BuildModels {

        public static <T> T BuildOne(DatabaseController.Table className, ResultSet response) throws SQLException {
    
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
        
                    default -> throw new IllegalArgumentException("Unexpected value: " + className);
        
                };
    
            }
    
            response.close();
    
            return (T)object;
    
        }
    
        public static <T> ArrayList<T> BuildMultiple(DatabaseController.Table className, ResultSet response) throws SQLException {
    
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
        
                    default -> throw new IllegalArgumentException("Unexpected value: " + className);
        
                });
    
            }
    
            response.close();
    
            return objects;
    
        }
    
    }

}