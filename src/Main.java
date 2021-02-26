import app.gui.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import app.controllers.BackController;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException, IOException {

        BackController.init(args);

        BackController.controller.jsonAll();

	    Controller.init();
    }
}
