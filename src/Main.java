import app.gui.Controller;
import app.DataBase;

public class Main {

    public static void main(String[] args) {

        try {
            DataBase.connect(args[0], args[1]);
        } catch (Exception e) {}

	    Controller.init();
    }
}
