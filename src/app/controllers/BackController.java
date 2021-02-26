package app.controllers;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
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

    public ArrayList<Producto> Producto() throws SQLException, ParseException {

        return database.<Producto>getAll(DatabaseController.Table.PRODUCTO);

    }

    public Producto Producto(String id) throws SQLException, ParseException {

        return database.<Producto>getById(DatabaseController.Table.PRODUCTO, id);

    }

    public void Producto(Producto producto) throws SQLException {

        database.insert(DatabaseController.Table.PRODUCTO, producto);

    }

    public static void updateProducto(Producto producto) throws SQLException {

        database.update(DatabaseController.Table.PRODUCTO, producto);

    }

    public static void deleteProducto(String id) throws SQLException {
        database.delete(DatabaseController.Table.PRODUCTO, id);
    }

    public ArrayList<Cliente> Cliente() throws SQLException, ParseException {

        return database.<Cliente>getAll(DatabaseController.Table.CLIENTE);

    }

    public Cliente Cliente(String id) throws SQLException, ParseException {

        return database.<Cliente>getById(DatabaseController.Table.CLIENTE, id);

    }

    public void Cliente(Cliente cliente) throws SQLException {

        database.insert(DatabaseController.Table.CLIENTE, cliente);

    }

    public void updateCliente(Cliente cliente) throws SQLException {

        database.update(DatabaseController.Table.CLIENTE, cliente);

    }

    public void deleteCliente(String id) throws SQLException {

        database.delete(DatabaseController.Table.CLIENTE, id);

    }

    public ArrayList<Usuario> Usuario() throws SQLException, ParseException {

        return database.<Usuario>getAll(DatabaseController.Table.USUARIO);

    }

    public Usuario Usuario(String id) throws SQLException, ParseException {

        return database.<Usuario>getById(DatabaseController.Table.USUARIO, id);

    }

    public void Usuario(Usuario usuario) throws SQLException {

        database.insert(DatabaseController.Table.USUARIO, usuario);

    }

    public void updateUsuario(Usuario usuario) throws SQLException {

        database.update(DatabaseController.Table.USUARIO, usuario);

    }

    public void deleteUsuario(String id) throws SQLException {

        database.delete(DatabaseController.Table.USUARIO, id);

    }

    public ArrayList<Factura> Factura() throws SQLException, ParseException {

        return database.<Factura>getAll(DatabaseController.Table.FACTURA);

    }

    public Factura Factura(String id) throws SQLException, ParseException {

        return database.<Factura>getById(DatabaseController.Table.FACTURA, id);

    }

    public void Factura(Factura factura) throws SQLException {

        database.insert(DatabaseController.Table.FACTURA, factura);

    }

    public void updateFactura(Factura factura) throws SQLException {

        database.update(DatabaseController.Table.FACTURA, factura);

    }

    public void deleteFactura(String id) throws SQLException {

        database.delete(DatabaseController.Table.FACTURA, id);

    }

    public ArrayList<FacturaProducto> FacturaProducto(String idFactura) throws SQLException, ParseException {

        return database.getRelation(idFactura);

    }

    public FacturaProducto FacturaProducto(String idFactura, String idProducto) throws SQLException, ParseException {

        return database.getRelationUnique(idFactura, idProducto);

    }

    public void FacturaProducto(FacturaProducto facturaProducto) throws SQLException {

        database.insert(DatabaseController.Table.FACTURAPRODUCTO, facturaProducto);

    }

    public void updateFacturaProducto(FacturaProducto facturaProducto) throws SQLException {

        database.update(DatabaseController.Table.FACTURAPRODUCTO, facturaProducto);

    }

    public void deleteFacturaProducto(String id) throws SQLException {

        database.delete(DatabaseController.Table.FACTURAPRODUCTO, id);

    }

    public static Usuario validarIngreso(String userName, String password) throws SQLException, ParseException {

        return (getPassword(userName, password))? controller.Usuario(userName) : null;

    }

    public static void insertarProducto(String nombre, Double precio, int stock, int stockMinimo) throws SQLException {
        controller.Producto(new Producto(null, nombre, new BigDecimal(precio), stockMinimo, stockMinimo));
    }

    public static String[] getNombresProductos() throws SQLException, ParseException {

        ArrayList<Producto> provisional = controller.Producto(); 

        String[] productos = new String[provisional.size()];

        for(int i = 0; i < provisional.size(); ++i) {
            productos[i]= provisional.get(i).getNombre();
        }

        return productos;
    }

    public static double getPrecio(String idProducto) throws SQLException, ParseException {

        return controller.Producto(idProducto).getPrecio().doubleValue();

    }

    public static int getStock(String idProducto) throws SQLException, ParseException {
        
        return controller.Producto(idProducto).getStock();
        
    }

    public static void insertarMovimiento(String idProducto, int cantidad) {
        
        //controller.Factura();

    }

    public static BigDecimal toBigDecimal(String precio) throws ParseException {

        precio = precio.substring(2, precio.length());

        NumberFormat format = NumberFormat.getInstance();

        BigDecimal price = null;

        price = new BigDecimal(format.parse(precio).doubleValue());

        return price;

    }

    private static boolean getPassword(String userName, String password) throws SQLException {

        boolean isUser = false;

        ResultSet response = database.getDataBase().getByID("Usuario", userName);

        while(response.next()) {
            isUser = response.getString("Pass").equals(password);
        }


        return isUser;

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

    public DataBase getDataBase() {
        return database;
    }

    public <T> ArrayList<T> getAll(Table table) throws SQLException, ParseException {

        ResultSet response = switch (table) {

            case PRODUCTO -> this.database.getAll(Producto.class.getSimpleName());

            case CLIENTE -> this.database.getAll(Cliente.class.getSimpleName());

            case FACTURA -> this.database.getAll(Factura.class.getSimpleName());

            case USUARIO -> this.database.getAll(Usuario.class.getSimpleName());

            default -> throw new IllegalArgumentException("Unexpected value: " + table);

        };

        return BuildModels.BuildMultiple(table, response);

    }

    public ArrayList<FacturaProducto> getRelation(String idFactura) throws SQLException, ParseException {

        ResultSet response = this.database.getRelation(FacturaProducto.class.getSimpleName(), idFactura);

        return BuildModels.<FacturaProducto>BuildMultiple(Table.FACTURAPRODUCTO, response);

    }

    public FacturaProducto getRelationUnique(String idFactura, String idProducto) throws SQLException, ParseException {

        ResultSet response = this.database.getUniqueRelation(FacturaProducto.class.getSimpleName(), idFactura,
                idProducto);

        return BuildModels.<FacturaProducto>BuildOne(Table.FACTURAPRODUCTO, response);

    }

    public <T> T getById(Table table, String id) throws SQLException, ParseException {

        ResultSet response = switch (table) {

            case PRODUCTO -> this.database.getByID(Producto.class.getSimpleName(), id);

            case CLIENTE -> this.database.getByID(Cliente.class.getSimpleName(), id);

            case FACTURA -> this.database.getByID(Factura.class.getSimpleName(), id);

            case USUARIO -> this.database.getByID(Usuario.class.getSimpleName(), id);

            case FACTURAPRODUCTO -> this.database.getByID(FacturaProducto.class.getSimpleName(), id);

            default -> throw new IllegalArgumentException("Unexpected value: " + table);

        };

        return BuildModels.BuildOne(table, response);

    }

    public void insert(Table table, General general) throws SQLException {

        ResultSet response = switch (table) {

            case PRODUCTO -> database.insert(Producto.class.getSimpleName(), Producto.toArrayAtributes(),
                    general.toArray());

            case CLIENTE -> database.insert(Cliente.class.getSimpleName(), Cliente.toArrayAtributes(),
                    general.toArray());

            case FACTURA -> {
                database.insert(Factura.class.getSimpleName(), Factura.toArrayAtributes(),
                    general.toArray());

                for(FacturaProducto item : ((Factura)general).getItems()) {
                    insert(Table.FACTURAPRODUCTO, item);
                }

                yield this.database.getAll(Factura.class.getSimpleName());
            }

            case USUARIO -> database.insert(Usuario.class.getSimpleName(), Usuario.toArrayAtributes(),
                    general.toArray());

                    

            case FACTURAPRODUCTO -> database.insert(FacturaProducto.class.getSimpleName(),
                    FacturaProducto.toArrayAtributes(), general.toArray());

            default -> throw new IllegalArgumentException("Unexpected value: " + table);

        };

        response.close();

    }

    public void update(Table table, General general) throws SQLException {

        ResultSet response = switch (table) {

            case PRODUCTO -> database.update(Producto.class.getSimpleName(), Producto.toArrayAtributes(),
                    general.toArray());

            case CLIENTE -> database.update(Cliente.class.getSimpleName(), Cliente.toArrayAtributes(),
                    general.toArray());

            case FACTURA -> database.update(Factura.class.getSimpleName(), Factura.toArrayAtributes(),
                    general.toArray());

            case USUARIO -> database.update(Usuario.class.getSimpleName(), Usuario.toArrayAtributes(),
                    general.toArray());

            case FACTURAPRODUCTO -> database.update(FacturaProducto.class.getSimpleName(),
                    FacturaProducto.toArrayAtributes(), general.toArray());

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

        public static <T> T BuildOne(DatabaseController.Table className, ResultSet response)
                throws SQLException, ParseException {

            String[] atributes;
            General object = null;

            while (response.next()) {

                object = switch (className) {

                    case CLIENTE -> {
                        atributes = Cliente.toArrayAtributes();
                        yield new Cliente(
                            response.getString(atributes[0]),
                            response.getString(atributes[1]),
                            response.getString(atributes[2])
                        );
                    }

                    case FACTURA -> {
                        atributes = Factura.toArrayAtributes();

                        Cliente cliente = BackController.controller.Cliente(response.getString(atributes[2]));
                        ArrayList<FacturaProducto> items = BackController.controller.FacturaProducto(response.getString(atributes[0]));

                        yield new Factura(
                            response.getString(atributes[0]),
                            response.getDate(atributes[1]),
                            cliente,
                            items
                        );
                    }

                    case PRODUCTO -> {
                        atributes = Producto.toArrayAtributes();

                        yield new Producto(
                            response.getString(atributes[0]),
                            response.getString(atributes[1]),
                            BackController.toBigDecimal(response.getString(atributes[2])),
                            response.getInt(atributes[3]),
                            response.getInt(atributes[4])
                        );
                    }

                    case USUARIO -> {
                        atributes = Usuario.toArrayAtributes();

                        yield new Usuario(
                            response.getString(atributes[0]),
                            response.getBoolean(atributes[1])
                        );
                    }

                    case FACTURAPRODUCTO -> {
                        atributes = FacturaProducto.toArrayAtributes();

                        Producto producto = BackController.controller.Producto(response.getString(atributes[3]));

                        yield new FacturaProducto(
                            response.getInt(atributes[0]),
                            BackController.toBigDecimal(response.getString(atributes[1])),
                            null,
                            producto
                        );
                    }

                    default -> throw new IllegalArgumentException("Unexpected value: " + className);

                };

            }

            response.close();

            return (T) object;

        }

        public static <T> ArrayList<T> BuildMultiple(DatabaseController.Table className, ResultSet response)
                throws SQLException, ParseException {

            String[] atributes;
            ArrayList<T> objects = new ArrayList<T>();

            while (response.next()) {

                objects.add((T) switch (className) {

                    case CLIENTE -> {
                        atributes = Cliente.toArrayAtributes();
                        yield new Cliente(
                            response.getString(atributes[0]),
                            response.getString(atributes[1]),
                            response.getString(atributes[2])
                        );
                    }

                    case FACTURA -> {
                        atributes = Factura.toArrayAtributes();

                        Cliente cliente = BackController.controller.Cliente(response.getString(atributes[2]));
                        ArrayList<FacturaProducto> items = BackController.controller.FacturaProducto(response.getString(atributes[0]));

                        yield new Factura(
                            response.getString(atributes[0]),
                            response.getDate(atributes[1]),
                            cliente,
                            items
                        );
                    }

                    case PRODUCTO -> {
                        atributes = Producto.toArrayAtributes();

                        yield new Producto(
                                response.getString(atributes[0]),
                                response.getString(atributes[1]),
                                BackController.toBigDecimal(response.getString(atributes[2])),
                                response.getInt(atributes[3]),
                                response.getInt(atributes[4])
                            );
                    }

                    case USUARIO -> {
                        atributes = Usuario.toArrayAtributes();

                        yield new Usuario(
                            response.getString(atributes[0]),
                            response.getBoolean(atributes[1])
                        );
                    }

                    case FACTURAPRODUCTO -> {
                        atributes = FacturaProducto.toArrayAtributes();

                        Producto producto = BackController.controller.Producto(response.getString(atributes[3]));

                        yield new FacturaProducto(
                            response.getInt(atributes[0]),
                            BackController.toBigDecimal(response.getString(atributes[1])),
                            null,
                            producto
                            
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