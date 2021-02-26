import app.gui.Controller;
import app.inventario.FacturaProducto;
import app.inventario.Producto;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;

import app.controllers.BackController;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {

        BackController.init(args);

	    Controller.init();
    }
}
