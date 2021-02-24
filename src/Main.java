import app.gui.Controller;

import java.sql.SQLException;

import app.controllers.BackController;
import app.controllers.BackController.Table;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        BackController.init(args);

        BackController.getAll(Table.PRODUCTO);

	    Controller.init();
    }
}
