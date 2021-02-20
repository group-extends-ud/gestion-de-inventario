package app.gui;

import lib.sRAD.gui.sComponent.SPanel;
import lib.sRAD.gui.sComponent.SScrollPane;

public class View {
    public static View view;
    //componentes
    protected SPanel contenedor;

    protected View() {
        contenedor = new SPanel(SPanel.INTERNO, 0, 0, 850, 610);
        SScrollPane scroll = new SScrollPane(216, 90, 866, 626, contenedor);

        Controller.agregar(scroll);
    }

    public static void init() {
        view = new View();
    }

}
