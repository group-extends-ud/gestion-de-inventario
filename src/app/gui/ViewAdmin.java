package app.gui;

import lib.sRAD.gui.sComponent.SButton;
import lib.sRAD.gui.sComponent.SLabel;
import lib.sRAD.gui.sComponent.SPanel;
import lib.sRAD.gui.sComponent.SScrollPane;

import javax.swing.*;

public class ViewAdmin extends View{

    public static ViewAdmin view;

    protected ViewAdmin() {
        super();

        pInventario = new SPanel(SPanel.INTERNO, 0, 0, 848, 608);
        SScrollPane spInventario = new SScrollPane(216, 90, 864, 624, pInventario);

        pEstadistica = new SPanel(SPanel.INTERNO, 0, 0, 848, 608);
        SScrollPane spEstadistica = new SScrollPane(216, 90, 864, 624, pEstadistica);

        SButton btRemove = new SButton(1140, 310, new ImageIcon("resources/delete.png"));
        Controller.agregar(btRemove);

        SButton btSetting = new SButton(1140, 410, new ImageIcon("resources/ajustar.png"));
        Controller.agregar(btSetting);

        tpTabs.addTab("Inventario", spInventario);
        tpTabs.addTab("Estadística", spEstadistica);

    }

    public static void init() {
        view = new ViewAdmin();
    }

}
