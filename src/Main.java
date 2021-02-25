import app.gui.Controller;
import app.inventario.Producto;

import java.sql.SQLException;

import app.DataBase;
import app.controllers.BackController;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        BackController.init(args);

	    Controller.init();
    }
}
