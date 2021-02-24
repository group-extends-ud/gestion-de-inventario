import app.gui.Controller;

import java.sql.SQLException;

import app.controllers.BackController;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        BackController.init(args);

        //BackController.getProductos();

	    Controller.init();
    }
}
