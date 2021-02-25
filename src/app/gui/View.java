package app.gui;

import app.controllers.BackController;
import app.inventario.Movimiento;
import app.inventario.Producto;
import lib.sRAD.gui.component.VentanaEmergente;
import lib.sRAD.gui.sComponent.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static lib.sRAD.logic.Extension.*;

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
            int selected = tpTabs.getSelectedIndex();
            if (selected == 0 || selected == 2) {
                addMovimiento();
            }
            else {
                addItem();
            }
        });
        Controller.agregar(btAdd);

        btClose = new SButton(1140, 500, new ImageIcon("resources/close.png"));
        btClose.addActionListener((e) -> Controller.init());
        Controller.agregar(btClose);

        Controller.agregar(tpTabs);

        actualizar();
    }

    public void actualizar() {
        //pMovimiento
        pMovimiento.removeAll();
        ArrayList<Movimiento> movimientos = BackController.Movimiento();
        if (movimientos != null && !movimientos.isEmpty()) {
            SLabel encabezado = new SLabel(32, 32, 800, 28, "%20s%20s%20s%20s%20s".formatted(
                    "id", "id del producto", "cantidad", "costo unitario", "costo total"));
            pMovimiento.add(encabezado);
            for (int i=0; i<movimientos.size(); i++) {
                Movimiento movimiento = movimientos.get(i);
                SLabel lMovimiento = new SLabel(32, i*32+64, 800, 28, "%20d%20d%20d%20d%20d".formatted(
                        movimiento.getId(), movimiento.getIdProducto(), movimiento.getCantidad(), movimiento.getCostoUnitario(),
                        movimiento.getCostoTotal()));
                pMovimiento.add(lMovimiento);
            }
        }
        pMovimiento.repaint();
    }

    public void addItem() {
        VentanaEmergente ventana = new VentanaEmergente(Controller.controller, 340, 300);

        SLabel lInsertar = new SLabel(32, 32, 200, 28, "Inserte un producto");
        ventana.add(lInsertar);

        SLabel lNombre = new SLabel(64, 64, 168, 28, "Nombre:");
        ventana.add(lNombre);

        STextField tfNombre = new STextField(200, 62, 100, 32);
        ventana.add(tfNombre);

        SLabel lPrecio = new SLabel(64, 104, 168, 28, "Precio:");
        ventana.add(lPrecio);

        STextField tfPrecio = new STextField(200, 102, 100, 32);
        ventana.add(tfPrecio);

        SLabel lStock = new SLabel(64, 144, 168, 28, "Stock:");
        ventana.add(lStock);

        STextField tfStock = new STextField(200, 142, 100, 32);
        ventana.add(tfStock);

        SLabel lStockMin = new SLabel(64, 184, 168, 28, "Stock mínimo:");
        ventana.add(lStockMin);

        STextField tfStockMin = new STextField(200, 182, 100, 32);
        ventana.add(tfStockMin);

        SButton btConfirm = new SButton(50, 232, 100, 32, "INSERTAR");
        btConfirm.addActionListener( (e) -> {
            if (!tfNombre.getText().isEmpty() && isDouble(tfPrecio.getText()) && isInt(tfStock.getText()) && isInt(tfStockMin.getText())) {
                BackController.insertarProducto(
                        tfNombre.getText(), Double.valueOf(tfPrecio.getText()), Integer.parseInt(tfStock.getText()),
                        Integer.parseInt(tfStockMin.getText())
                );
            }
            else {
                JOptionPane.showMessageDialog(null, "Por favor ingrese valores válidos", "Error", JOptionPane.ERROR_MESSAGE);
            }
            ventana.cerrar();
        });
        ventana.add(btConfirm);

        SButton btClose = new SButton(190, 232, 100, 32, "CERRAR");
        btClose.addActionListener( (e) -> ventana.cerrar());
        ventana.add(btClose);

        ventana.lanzar();
    }

    public void addMovimiento() {
        VentanaEmergente ventana = new VentanaEmergente(Controller.controller, 340, 300);

        SLabel lInsertar = new SLabel(32, 32, 200, 28, "Inserte un movimiento");
        ventana.add(lInsertar);

        SLabel lTipo = new SLabel(64, 64, 168, 28, "Producto:");
        ventana.add(lTipo);

        SLabel lPrecioText = new SLabel(64, 104, 168, 28, "Precio:");
        ventana.add(lPrecioText);

        AtomicReference<Double> precio = new AtomicReference<>(0.0);
        AtomicReference<Double> precioTotal = new AtomicReference<>(0.0);
        AtomicInteger cantidad = new AtomicInteger();

        SLabel lPrecio = new SLabel(200, 102, 100, 32, "");
        ventana.add(lPrecio);

        SLabel lCantidad = new SLabel(64, 144, 168, 28, "Cantidad:");
        ventana.add(lCantidad);

        SLabel lPrecioTotal = new SLabel(200, 182, 100, 32, "");
        ventana.add(lPrecioTotal);

        STextField tfCantidad = new STextField(200, 142, 100, 32);
        tfCantidad.addActionListener( (e) -> {
            if (isInt(tfCantidad.getText()) && Integer.parseInt(tfCantidad.getText()) > 0) {
                cantidad.set(Integer.parseInt(tfCantidad.getText()));
                precioTotal.set(cantidad.get() * precio.get());
                lPrecioTotal.setText(toCOP(precioTotal.get()));
            }
        });
        ventana.add(tfCantidad);

        SLabel lPrecioTotalText = new SLabel(64, 184, 168, 28, "Precio Total:");
        ventana.add(lPrecioTotalText);

        String[] opciones = BackController.getNombresProductos();
        SComboBox cbTipo = new SComboBox(SComboBox.DECORADO, 200, 62, 100, 32, opciones);
        cbTipo.addActionListener( (e) -> {
            precio.set(BackController.getPrecio(cbTipo.getItemAt(cbTipo.getSelectedIndex()).toString()));
            lPrecio.setText(toCOP(precio.get()));
        });
        ventana.add(cbTipo);

        SButton btConfirm = new SButton(50, 232, 100, 32, "INSERTAR");
        btConfirm.addActionListener( (e) -> {
            if (cantidad.get()>0 && cantidad.get()>BackController.getStock(cbTipo.getItemAt(cbTipo.getSelectedIndex()).toString())) {
                BackController.insertarMovimiento(cbTipo.getItemAt(cbTipo.getSelectedIndex()).toString(), cantidad.get());
                actualizar();
            }
            else {
                JOptionPane.showMessageDialog(null, "Por favor ingrese valores válidos", "Error", JOptionPane.ERROR_MESSAGE);
            }
            ventana.cerrar();
        });
        ventana.add(btConfirm);

        SButton btClose = new SButton(190, 232, 100, 32, "CERRAR");
        btClose.addActionListener( (e) -> ventana.cerrar());
        ventana.add(btClose);

        ventana.lanzar();
    }

    public static void init() {
        view = new View();
    }

}
