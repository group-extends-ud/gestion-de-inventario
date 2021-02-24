import app.gui.Controller;
import app.inventario.Producto;

import java.sql.SQLException;

import app.controllers.BackController;
import app.controllers.BackController.Table;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        BackController.init(args);

        Producto producto = BackController.controller.<Producto>getById(Table.PRODUCTO, "1");

        System.out.println(producto.getNombre());

	    Controller.init();
    }
}
