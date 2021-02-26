package app.gui;

import app.controllers.BackController;
import app.inventario.Cliente;
import app.inventario.Factura;
import app.inventario.FacturaProducto;
import app.inventario.Producto;
import lib.sRAD.gui.component.VentanaEmergente;
import lib.sRAD.gui.sComponent.*;

import javax.swing.*;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;

public class ViewAdmin extends View {

    protected ViewAdmin() throws SQLException, ParseException {
        super();

        for(Producto faltante : BackController.controller.getProductosFaltantes()) {
            avisar(faltante);
        }

        SScrollPane spEstadistica = new SScrollPane(216, 90, 864, 624, pEstadistica);

        btRemove = new SButton(1140, 300, new ImageIcon("resources/delete.png"));
        btRemove.addActionListener((e) -> {
            final int selected = tpTabs.getSelectedIndex();
            if (selected == 0 || selected == 2) {
                removerMovimiento();
            } else {
                removerItem();
            }
        });
        Controller.agregar(btRemove);

        tpTabs.addTab("Estadística", spEstadistica);

        actualizar();
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
                    BackController.deleteProducto(Integer.parseInt(tfID.getText()));
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
                    BackController.controller.deleteFactura(tfID.getText());
                    actualizar();
                } catch (SQLException throwables) {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el movimiento indicado, por favor verifique" +
                            " los datos ingresados", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (ParseException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
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

    public static void init() throws SQLException, ParseException {
        view = new ViewAdmin();
    }
}
