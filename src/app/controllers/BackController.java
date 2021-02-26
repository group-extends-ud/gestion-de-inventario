package app.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;

import com.google.gson.Gson;

import app.DataBase;
import app.inventario.Cliente;
import app.inventario.Factura;
import app.inventario.FacturaProducto;
import app.inventario.General;
import app.inventario.Producto;
import app.inventario.Usuario;

import static lib.sRAD.logic.Extension.isNumber;

public class BackController {

    public static BackController controller;
    private static DatabaseController database;
    private static Gson gson;

    private BackController(String user, String password) throws ClassNotFoundException, SQLException {
        database = new DatabaseController(user, password);
        gson = new Gson();
    }

    public static void init(String[] args) throws ClassNotFoundException, SQLException {
        controller = new BackController(args[0], args[1]);
    }

    public static Cliente getCliente(String nombre) {
        //por implementar
        return null;
    }

    public ArrayList<Producto> Producto() throws SQLException, ParseException {

        return database.<Producto>getAll(DatabaseController.Table.PRODUCTO);

    }

    public Producto Producto(int id) throws SQLException, ParseException {

        return database.<Producto>getById(DatabaseController.Table.PRODUCTO, id);

    }

    public void Producto(Producto producto) throws SQLException, ParseException {

        database.insert(DatabaseController.Table.PRODUCTO, producto);

    }

    public static void updateProducto(Producto producto) throws SQLException {

        database.update(DatabaseController.Table.PRODUCTO, producto);

    }

    public static void deleteProducto(int id) throws SQLException {
        database.delete(DatabaseController.Table.PRODUCTO, id);
    }

    public ArrayList<Cliente> Cliente() throws SQLException, ParseException {

        return database.<Cliente>getAll(DatabaseController.Table.CLIENTE);

    }

    public Cliente Cliente(int id) throws SQLException, ParseException {

        return database.<Cliente>getById(DatabaseController.Table.CLIENTE, id);

    }

    public void Cliente(Cliente cliente) throws SQLException, ParseException {

        database.insert(DatabaseController.Table.CLIENTE, cliente);

    }

    public void updateCliente(Cliente cliente) throws SQLException {

        database.update(DatabaseController.Table.CLIENTE, cliente);

    }

    public void deleteCliente(int id) throws SQLException {

        database.delete(DatabaseController.Table.CLIENTE, id);

    }

    public ArrayList<Usuario> Usuario() throws SQLException, ParseException {

        return database.<Usuario>getAll(DatabaseController.Table.USUARIO);

    }

    public Usuario Usuario(String id) throws SQLException, ParseException {

        return database.<Usuario>getById(DatabaseController.Table.USUARIO, id);

    }

    public void Usuario(Usuario usuario) throws SQLException, ParseException {

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

    public Factura Factura(int id) throws SQLException, ParseException {

        return database.<Factura>getById(DatabaseController.Table.FACTURA, id);

    }

    public static void insert(Factura factura) throws SQLException, ParseException {
        controller.database.insert(DatabaseController.Table.FACTURA, factura);
    }

    public void updateFactura(Factura factura) throws SQLException {
        database.update(DatabaseController.Table.FACTURA, factura);

    }

    public void deleteFactura(String id) throws SQLException {

        database.delete(DatabaseController.Table.FACTURA, id);

    }

    public ArrayList<FacturaProducto> FacturaProducto(int idFactura) throws SQLException, ParseException {

        return database.getRelation(idFactura);

    }

    public FacturaProducto FacturaProducto(int idFactura, int idProducto) throws SQLException, ParseException {

        return database.getRelationUnique(idFactura, idProducto);

    }

    public void FacturaProducto(FacturaProducto facturaProducto) throws SQLException, ParseException {

        database.insert(DatabaseController.Table.FACTURAPRODUCTO, facturaProducto);

    }

    public void updateFacturaProducto(FacturaProducto facturaProducto) throws SQLException {

        database.update(DatabaseController.Table.FACTURAPRODUCTO, facturaProducto);

    }

    public void deleteFacturaProducto(String id) throws SQLException {

        database.delete(DatabaseController.Table.FACTURAPRODUCTO, id);

    }

    public static Usuario validarIngreso(String userName, String password) throws SQLException, ParseException {

        return (getPassword(userName, password)) ? controller.Usuario(userName) : null;

    }

    public static void insertarProducto(String nombre, Double precio, int stock, int stockMinimo) throws SQLException, ParseException {
        controller.Producto(new Producto(null, nombre, precio, stock, stockMinimo));
    }

    public static String[] getNombresProductos() throws SQLException, ParseException {

        ArrayList<Producto> provisional = controller.Producto();

        String[] productos = new String[provisional.size()];

        for (int i = 0; i < provisional.size(); ++i) {
            productos[i] = provisional.get(i).getNombre();
        }

        return productos;
    }

    public static double getPrecio(int idProducto) throws SQLException, ParseException {
        return controller.Producto(idProducto).getPrecio();
    }

    public static int getStock(int idProducto) throws SQLException, ParseException {

        return controller.Producto(idProducto).getStock();

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

        while (response.next()) {
            isUser = response.getString("Pass").equals(password);
        }

        return isUser;

    }

    public void jsonCliente() throws SQLException, ParseException, IOException {

        ArrayList<Cliente> clientes = Cliente();

        String json = gson.toJson(clientes).replaceAll("idcliente", "id");

        Json.writeFile("Clientes", structureJson("Clientes", json));

    }

    public void jsonCliente(int id) throws SQLException, ParseException, IOException {

        Cliente cliente = Cliente(id);

        String json = gson.toJson(cliente).replaceAll("idcliente", "id");

        Json.writeFile("Cliente", structureJson("Cliente", json));

    }

    public void jsonFactura() throws SQLException, ParseException, IOException {

        ArrayList<Factura> facturas = Factura();

        String json = gson.toJson(facturas);

        for (Factura factura : facturas) {
            for (FacturaProducto __ : factura.getItems()) {
                json = json.replaceFirst("idfactura", "id").replaceFirst("idcliente", "Cliente")
                        .replaceFirst("idcliente", "id").replace("\"idfactura\":" + factura.getIdfactura() + ",", "")
                        .replaceFirst("idproducto", "Producto").replaceFirst("idproducto", "id");
            }

        }

        Json.writeFile("Facturas", structureJson("Facturas", json));

    }

    public void jsonFactura(int id) throws SQLException, ParseException, IOException {

        Factura factura = Factura(id);

        String json = gson.toJson(factura);

        for (FacturaProducto __ : factura.getItems()) {
            json = json.replaceFirst("idfactura", "id").replaceFirst("idcliente", "Cliente")
                    .replaceFirst("idcliente", "id").replace("\"idfactura\":" + factura.getIdfactura() + ",", "")
                    .replaceFirst("idproducto", "Producto").replaceFirst("idproducto", "id");
        }

        Json.writeFile("Factura", structureJson("Factura", json));

    }

    public void jsonProducto() throws SQLException, ParseException, IOException {

        ArrayList<Producto> productos = Producto();

        String json = gson.toJson(productos).replaceAll("idproducto", "id");

        Json.writeFile("Productos", structureJson("Productos", json));

    }

    public void jsonProducto(int id) throws SQLException, ParseException, IOException {

        Producto producto = Producto(id);

        String json = gson.toJson(producto).replaceAll("idproducto", "id");

        Json.writeFile("Producto", structureJson("Producto", json));

    }

    public void jsonUsuario() throws SQLException, ParseException, IOException {

        ArrayList<Usuario> usuarios = Usuario();

        String json = gson.toJson(usuarios);

        Json.writeFile("Usuarios", structureJson("Usuarios", json));

    }

    public void jsonUsuario(String userName) throws SQLException, ParseException, IOException {

        Usuario usuario = Usuario(userName);

        String json = gson.toJson(usuario);

        Json.writeFile("Usuario", structureJson("Usuario", json));

    }

    public void jsonAll() throws SQLException, ParseException, IOException {

        jsonCliente();
        jsonFactura();
        jsonProducto();
        jsonUsuario();

    }

    private String structureJson(String name, String json) {

        json = "{\"" + name + "\":" + json;
        json += "}";

        json = json.replaceAll(":", ": ").replace(",\"", ",\n\"");

        json = json.replace("{", "{\n").replace("}", "\n}");
        json = json.replace("[{", "[\n{").replace("}]", "}\n]").replace(",{", ",\n{");

        char[] jsonChar = json.toCharArray();

        String jsonString = "";
        int count = 0;
        boolean saltoLinea = false;

        for (char x : jsonChar) {

            if (x == '{' || x == '[')
                count++;
            else if (x == '}' || x == ']')
                count--;
            else if (x == '\n') {
                saltoLinea = true;
                jsonString += x;
            }

            if (saltoLinea) {
                for (int i = 0; i < count; ++i) {
                    jsonString += "\t";
                }
                saltoLinea = false;
                continue;
            }
            jsonString += x;
        }

        jsonString = jsonString.replace("\t}", "}").replace("\t]", "]");

        return jsonString;

    }

    private static class Json {

        private static String createFile(String nombre) throws IOException {

            String json = "Data/" + nombre + ".json";

            File folder = new File(json.substring(0, 4));

            File file = new File(json);

            folder.mkdir();

            if(!file.createNewFile()) {
                if(file.delete()) file.createNewFile();
            }

            return json;

        }

        public static void writeFile(String nombre, String text) throws IOException {

            FileWriter writer = new FileWriter(createFile(nombre));

            writer.write(text);
            writer.close();

        }

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

    public ArrayList<FacturaProducto> getRelation(int idFactura) throws SQLException, ParseException {

        ResultSet response = this.database.getRelation(FacturaProducto.class.getSimpleName(), idFactura);

        return BuildModels.<FacturaProducto>BuildMultiple(Table.FACTURAPRODUCTO, response);

    }

    public FacturaProducto getRelationUnique(int idFactura, int idProducto) throws SQLException, ParseException {

        ResultSet response = this.database.getUniqueRelation(FacturaProducto.class.getSimpleName(), idFactura,
                idProducto);

        return BuildModels.<FacturaProducto>BuildOne(Table.FACTURAPRODUCTO, response);

    }

    public <T> T getById(Table table, Object id) throws SQLException, ParseException {

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

    public void insert(Table table, General general) throws SQLException, ParseException {

        ResultSet response = switch (table) {

            case PRODUCTO -> database.insert(Producto.class.getSimpleName(), Producto.toArrayAtributes(),
                    general.toArray());

            case CLIENTE -> database.insert(Cliente.class.getSimpleName(), Cliente.toArrayAtributes(),
                    general.toArray());

            case FACTURA -> {
                ResultSet data = database.insert(Factura.class.getSimpleName(), Factura.toArrayAtributes(),
                    general.toArray());

                Factura factura = null;

                factura = BuildModels.<Factura>BuildOne(Table.FACTURA, data);

                for(FacturaProducto item : ((Factura)general).getItems()) {
                    item.setFactura(factura.getIdfactura());
                    System.out.println(item.getFactura());
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

    public void delete(Table table, Object id) throws SQLException {

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
                            response.getInt(atributes[0]),
                            response.getString(atributes[1]),
                            response.getString(atributes[2])
                        );
                    }

                    case FACTURA -> {
                        atributes = Factura.toArrayAtributes();

                        Cliente cliente = BackController.controller.Cliente(response.getInt(atributes[2]));
                        ArrayList<FacturaProducto> items = BackController.controller.FacturaProducto(response.getInt(atributes[0]));

                        yield new Factura(
                            response.getInt(atributes[0]),
                            response.getDate(atributes[1]),
                            cliente,
                            items
                        );
                    }

                    case PRODUCTO -> {
                        atributes = Producto.toArrayAtributes();

                        yield new Producto(
                            response.getInt(atributes[0]),
                            response.getString(atributes[1]),
                            toDouble(response.getString(atributes[2])),
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

                        Producto producto = BackController.controller.Producto(response.getInt(atributes[3]));

                        yield new FacturaProducto(
                            response.getInt(atributes[0]),
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
                            response.getInt(atributes[0]),
                            response.getString(atributes[1]),
                            response.getString(atributes[2])
                        );
                    }

                    case FACTURA -> {
                        atributes = Factura.toArrayAtributes();

                        Cliente cliente = BackController.controller.Cliente(response.getInt(atributes[2]));
                        ArrayList<FacturaProducto> items = BackController.controller.FacturaProducto(response.getInt(atributes[0]));

                        yield new Factura(
                            response.getInt(atributes[0]),
                            response.getDate(atributes[1]),
                            cliente,
                            items
                        );
                    }

                    case PRODUCTO -> {
                        atributes = Producto.toArrayAtributes();

                        yield new Producto(
                                response.getInt(atributes[0]),
                                response.getString(atributes[1]),
                                toDouble(response.getString(atributes[2])),
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

                        Producto producto = BackController.controller.Producto(response.getInt(atributes[3]));

                        yield new FacturaProducto(
                            response.getInt(atributes[0]),
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

    private static Double toDouble(String valueInEuro) {
        var valor = "";
        for(int i=0; i< valueInEuro.length(); i++) {
            if(valueInEuro.charAt(i) == ',') {
                valor += ".";
            }
            else if(isNumber(valueInEuro.charAt(i))) {
                valor += valueInEuro.charAt(i);
            }
        }
        return Double.valueOf(valor);
    }

}