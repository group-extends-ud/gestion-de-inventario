package app.gui;

import lib.sRAD.gui.sComponent.*;

import javax.swing.*;

public class View {
    public static View view;
    //componentes
    protected STabbedPane tpTabs;
    protected SPanel pMovimiento;
    protected SPanel pInventario;
    protected SPanel pEstadistica;

    protected View() {

        pMovimiento = new SPanel(SPanel.INTERNO, 0, 0, 848, 608);
        SScrollPane spMovimiento = new SScrollPane(216, 90, 864, 624, pMovimiento);

        tpTabs = new STabbedPane(STabbedPane.DECORADO, 216, 30, 866, 656);
        tpTabs.addTab("Movimientos", spMovimiento);

        SButton btAdd = new SButton(1140, 210, new ImageIcon("resources/add.png"));
        Controller.agregar(btAdd);

        Controller.agregar(tpTabs);
    }

    public static void init() {
        view = new View();
    }

}
