package app.gui;

import app.controllers.BackController;
import app.inventario.Movimiento;
import app.inventario.Producto;
import lib.sRAD.gui.component.VentanaEmergente;
import lib.sRAD.gui.sComponent.*;

import javax.swing.*;

import java.sql.SQLException;

public class ViewAdmin extends View{

    protected ViewAdmin() {
        super();

        pInventario = new SPanel(SPanel.INTERNO, 0, 0, 848, 608);
        SScrollPane spInventario = new SScrollPane(216, 90, 864, 624, pInventario);

        pEstadistica = new SPanel(SPanel.INTERNO, 0, 0, 848, 608);
        SScrollPane spEstadistica = new SScrollPane(216, 90, 864, 624, pEstadistica);

        btRemove = new SButton(1140, 300, new ImageIcon("resources/delete.png"));
        btRemove.addActionListener( (e) -> {
            final int selected = tpTabs.getSelectedIndex();
            if (selected == 0 || selected == 2) {
                removerMovimiento();
            }
            else {
                removerItem();
            }
        });
        Controller.agregar(btRemove);

        btSetting = new SButton(1140, 400, new ImageIcon("resources/ajustar.png"));
        btSetting.addActionListener( (e) -> {
            final int selected = tpTabs.getSelectedIndex();
            if (selected == 0 || selected == 2) {
                ajustarMovimiento();
            }
            else {
                ajustarItem();
            }
        });
        Controller.agregar(btSetting);

        tpTabs.addTab("Inventario", spInventario);
        tpTabs.addTab("Estadística", spEstadistica);

        actualizar();
    }

    public void actualizar() {
        super.actualizar();
    }

    private void ajustarItem() {
        VentanaEmergente ventana = new VentanaEmergente(Controller.controller, 340, 340);

        SLabel lInsertar = new SLabel(32, 32, 200, 28, "Modificar un producto");
        ventana.add(lInsertar);

        SLabel lID = new SLabel(64, 64, 168, 28, "ID:");
        ventana.add(lID);

        STextField tfID = new STextField(200, 62, 100, 32);
        ventana.add(tfID);

        SLabel lNombre = new SLabel(64, 104, 168, 28, "Nombre:");
        ventana.add(lNombre);

        STextField tfNombre = new STextField(200, 102, 100, 32);
        ventana.add(tfNombre);

        SLabel lStock = new SLabel(64, 144, 168, 28, "Stock:");
        ventana.add(lStock);

        STextField tfStock = new STextField(200, 142, 100, 32);
        ventana.add(tfStock);

        SLabel lStockMin = new SLabel(64, 184, 168, 28, "Stock Mínimo:");
        ventana.add(lStockMin);

        STextField tfStockMin = new STextField(200, 182, 100, 32);
        ventana.add(tfStockMin);

        SLabel lPrecio = new SLabel(64, 224, 168, 28, "Precio:");
        ventana.add(lPrecio);

        STextField tfPrecio = new STextField(200, 222, 100, 32);
        ventana.add(tfPrecio);

        SButton btConfirm = new SButton(50, 272, 100, 32, "MODIFICAR");
        btConfirm.addActionListener( (e) -> {
            if (!tfID.getText().isEmpty() && !tfNombre.getText().isEmpty() && !tfPrecio.getText().isEmpty() && !tfStock.getText().isEmpty()
                    && !tfStockMin.getText().isEmpty()) {
                try {
                    BackController.updateProducto(new Producto(tfID.getText(), tfNombre.getText(), tfPrecio.getText(),
                            Integer.parseInt(tfStock.getText()), Integer.parseInt(tfStockMin.getText())));
                } catch (Exception throwables) {
                    JOptionPane.showMessageDialog(null, "No se pudo modificar el producto indicado, por favor verifique" +
                            " los datos ingresados", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Por favor ingrese valores válidos", "Error", JOptionPane.ERROR_MESSAGE);
            }
            ventana.cerrar();
        });
        ventana.add(btConfirm);

        SButton btClose = new SButton(190, 272, 100, 32, "CERRAR");
        btClose.addActionListener( (e) -> ventana.cerrar());
        ventana.add(btClose);

        ventana.lanzar();
    }

    private void ajustarMovimiento() {
        VentanaEmergente ventana = new VentanaEmergente(Controller.controller, 340, 340);

        SLabel lInsertar = new SLabel(32, 32, 200, 28, "Modificar un movimiento");
        ventana.add(lInsertar);

        SLabel lID = new SLabel(64, 64, 168, 28, "ID:");
        ventana.add(lID);

        STextField tfID = new STextField(200, 62, 100, 32);
        ventana.add(tfID);

        SLabel lProducto = new SLabel(64, 104, 168, 28, "ID producto:");
        ventana.add(lProducto);

        STextField tfProducto = new STextField(200, 102, 100, 32);
        ventana.add(tfProducto);

        SLabel lCantidad = new SLabel(64, 144, 168, 28, "Cantidad:");
        ventana.add(lCantidad);

        STextField tfCantidad = new STextField(200, 142, 100, 32);
        ventana.add(tfCantidad);

        SLabel lCostoUnitario = new SLabel(64, 184, 168, 28, "Costo unitario:");
        ventana.add(lCostoUnitario);

        STextField tfCostoUnitario = new STextField(200, 182, 100, 32);
        ventana.add(tfCostoUnitario);

        SLabel lCostoTotal = new SLabel(64, 224, 168, 28, "Costo total:");
        ventana.add(lCostoTotal);

        STextField tfCostoTotal = new STextField(200, 222, 100, 32);
        ventana.add(tfCostoTotal);

        SButton btConfirm = new SButton(50, 272, 100, 32, "MODIFICAR");
        btConfirm.addActionListener( (e) -> {
            if (!tfID.getText().isEmpty() && !tfProducto.getText().isEmpty() && !tfCantidad.getText().isEmpty() && !tfCostoUnitario.getText().isEmpty()
                    && !tfCostoTotal.getText().isEmpty()) {
                try {
                    BackController.updateMovimiento(new Movimiento(Integer.parseInt(tfID.getText()), Integer.parseInt(tfProducto.getText()),
                            Integer.parseInt(tfCantidad.getText()), Integer.parseInt(tfCostoUnitario.getText()), Integer.parseInt(tfCostoTotal.getText())));
                    actualizar();
                } catch (Exception throwables) {
                    JOptionPane.showMessageDialog(null, "No se pudo modificar el movimiento indicado, por favor verifique" +
                            " los datos ingresados", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Por favor ingrese valores válidos", "Error", JOptionPane.ERROR_MESSAGE);
            }
            ventana.cerrar();
        });
        ventana.add(btConfirm);

        SButton btClose = new SButton(190, 272, 100, 32, "CERRAR");
        btClose.addActionListener( (e) -> ventana.cerrar());
        ventana.add(btClose);

        ventana.lanzar();
    }

    public void removerItem() {
        VentanaEmergente ventana = new VentanaEmergente(Controller.controller, 340, 180);

        SLabel lInsertar = new SLabel(32, 32, 200, 28, "Eliminar un producto");
        ventana.add(lInsertar);

        SLabel lID = new SLabel(64, 64, 168, 28, "ID:");
        ventana.add(lID);

        STextField tfID = new STextField(200, 62, 100, 32);
        ventana.add(tfID);

        SButton btConfirm = new SButton(50, 112, 100, 32, "ELIMINAR");
        btConfirm.addActionListener( (e) -> {
            if (!tfID.getText().isEmpty()) {
                try {
                    BackController.deleteProducto(tfID.getText());
                } catch (SQLException throwables) {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el producto indicado, por favor verifique" +
                            " los datos ingresados", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Por favor ingrese valores válidos", "Error", JOptionPane.ERROR_MESSAGE);
            }
            ventana.cerrar();
        });
        ventana.add(btConfirm);

        SButton btClose = new SButton(190, 112, 100, 32, "CERRAR");
        btClose.addActionListener( (e) -> ventana.cerrar());
        ventana.add(btClose);

        ventana.lanzar();
    }

    public void removerMovimiento() {
        VentanaEmergente ventana = new VentanaEmergente(Controller.controller, 340, 180);

        SLabel lInsertar = new SLabel(32, 32, 200, 28, "Eliminar un movimiento");
        ventana.add(lInsertar);

        SLabel lID = new SLabel(64, 64, 168, 28, "ID:");
        ventana.add(lID);

        STextField tfID = new STextField(200, 62, 100, 32);
        ventana.add(tfID);

        SButton btConfirm = new SButton(50, 112, 100, 32, "ELIMINAR");
        btConfirm.addActionListener( (e) -> {
            if (!tfID.getText().isEmpty()) {
                try {
                    BackController.deleteMovimiento(tfID.getText());
                    actualizar();
                } catch (SQLException throwables) {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el movimiento indicado, por favor verifique" +
                            " los datos ingresados", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Por favor ingrese valores válidos", "Error", JOptionPane.ERROR_MESSAGE);
            }
            ventana.cerrar();
        });
        ventana.add(btConfirm);

        SButton btClose = new SButton(190, 112, 100, 32, "CERRAR");
        btClose.addActionListener( (e) -> ventana.cerrar());
        ventana.add(btClose);

        ventana.lanzar();
    }

    public static void init() {
        view = new ViewAdmin();
    }
}
