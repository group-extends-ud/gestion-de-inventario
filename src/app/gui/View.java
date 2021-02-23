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
    protected SButton btAdd;
    protected SButton btClose;
    protected SButton btRemove;
    protected SButton btSetting;

    protected View() {
        pMovimiento = new SPanel(SPanel.INTERNO, 0, 0, 848, 608);
        SScrollPane spMovimiento = new SScrollPane(216, 90, 864, 624, pMovimiento);

        tpTabs = new STabbedPane(STabbedPane.DECORADO, 216, 30, 866, 656);
        tpTabs.addTab("Movimientos", spMovimiento);

        btAdd = new SButton(1140, 200, new ImageIcon("resources/add.png"));
        btAdd.addActionListener( (e) -> {
            if (tpTabs.isEnabledAt(0) || tpTabs.isEnabledAt(2)) {
                addMovimiento();
            }
            else {
                addItem();
            }
        });
        Controller.agregar(btAdd);

        btClose = new SButton(1140, 500, new ImageIcon("resources/close.png"));
        btClose.addActionListener((e) -> {
            Controller.init();
        });
        Controller.agregar(btClose);

        Controller.agregar(tpTabs);
    }

    public void addItem() {

    }

    public void addMovimiento() {

    }

    public static void init() {
        view = new View();
    }

}
