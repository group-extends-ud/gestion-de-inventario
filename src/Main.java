import app.gui.Controller;
import app.inventario.Cliente;
import app.inventario.Factura;
import app.inventario.FacturaProducto;
import app.inventario.Producto;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import app.controllers.BackController;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException, IOException {

        BackController.init(args);

	    Controller.init();
    }
}
