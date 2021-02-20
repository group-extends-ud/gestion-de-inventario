package app.gui;

public class ViewAdmin extends View{

    public static ViewAdmin view;

    protected ViewAdmin() {
        super();
    }

    public static void init() {
        view = new ViewAdmin();
    }

}
