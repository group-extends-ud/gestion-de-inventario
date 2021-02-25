import app.gui.Controller;
import app.inventario.Producto;

import java.sql.SQLException;
import java.util.ArrayList;

import app.controllers.BackController;
import app.controllers.BackController.Table;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        BackController.init(args);

        ArrayList<Producto> productos = BackController.controller.<Producto>getAll(Table.PRODUCTO);

        for(Producto producto : productos) {
            System.out.println(producto.getNombre());
        }


        Producto producto = BackController.controller.<Producto>getById(Table.PRODUCTO, "1");

        System.out.println(producto.getNombre());

	    Controller.init();
    }
}
