package app.gui;

import lib.sRAD.gui.sComponent.SLabel;
import lib.sRAD.gui.sComponent.SPanel;
import lib.sRAD.gui.sComponent.SScrollPane;
import lib.sRAD.gui.sComponent.STabbedPane;

import javax.swing.*;

public class View {
    public static View view;
    //componentes
    protected STabbedPane tpTabs;
    protected SPanel pMovimiento;
    protected SPanel pInventario;
    protected SPanel pEstadistica;

    protected View() {
        SLabel lBackground = new SLabel(0, 0, new ImageIcon("resources/appWallpaper.png"));

        pMovimiento = new SPanel(SPanel.INTERNO, 0, 0, 848, 608);
        SScrollPane spMovimiento = new SScrollPane(216, 90, 864, 624, pMovimiento);

        tpTabs = new STabbedPane(STabbedPane.DECORADO, 216, 30, 866, 656);
        tpTabs.addTab("Movimientos", spMovimiento);

        Controller.agregar(tpTabs);
        Controller.agregar(lBackground);
    }

    public static void init() {
        view = new View();
    }

}
