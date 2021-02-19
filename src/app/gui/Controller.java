package app.gui;

public class Controller {
    public static Controller controller;

    private Controller() {

    }

    public static void init() {
        controller = new Controller();
    }
}
