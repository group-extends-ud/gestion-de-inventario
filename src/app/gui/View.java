package app.gui;

import app.controllers.BackController;
import app.inventario.Factura;
import app.inventario.FacturaProducto;
import app.inventario.Producto;
import lib.sRAD.gui.component.VentanaEmergente;
import lib.sRAD.gui.sComponent.*;

import javax.swing.*;

import java.awt.HeadlessException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;

import static lib.sRAD.logic.Extension.*;

public class View {
    public static View view;
    // componentes gráficos
    protected STabbedPane tpTabs;
    protected SPanel pFactura;
    protected SPanel pInventario;
    protected SPanel pEstadistica;
    protected SButton btAdd;
    protected SButton btClose;
    protected SButton btRemove;
    protected SButton btSetting;
    // componentes lógicos
    protected ArrayList<FacturaProducto> carrito;

    protected View() throws SQLException, ParseException {
        carrito = new ArrayList<>();

        pFactura = new SPanel(SPanel.INTERNO, 0, 0, 848, 598);
        pInventario = new SPanel(SPanel.INTERNO, 0, 0, 848, 598);
        pEstadistica = new SPanel(SPanel.INTERNO, 0, 0, 848, 598);

        SScrollPane spMovimiento = new SScrollPane(216, 90, 864, 624, pFactura);
        SScrollPane spInventario = new SScrollPane(216, 90, 864, 624, pInventario);

        tpTabs = new STabbedPane(STabbedPane.DECORADO, 216, 30, 866, 656);
        tpTabs.addTab("Movimientos", spMovimiento);
        tpTabs.addTab("Inventario", spInventario);

        btAdd = new SButton(1140, 200, new ImageIcon("resources/add.png"));
        btAdd.addActionListener((e) -> {
            int selected = tpTabs.getSelectedIndex();
            if (selected == 0 || selected == 2) {
                try {
                    addFactura();
                } catch (SQLException | ParseException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } else {
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

    public void actualizar() throws SQLException, ParseException {

        // pFactura --FALTA REVISAR--
        pFactura.removeAll();
        ArrayList<Factura> facturas = BackController.controller.Factura();
        if (facturas.get(0) != null && !facturas.isEmpty()) {
            SLabel lID = new SLabel(32, 32, 68, 28, "ID");
            SLabel lNombre = new SLabel(100, 32, 250, 28, "Cliente");
            SLabel lPrecio = new SLabel(350, 32, 150, 28, "Precio Total");
            SLabel lStock = new SLabel(550, 32, 100, 28, "Fecha");
            SLabel lStockMinimo = new SLabel(700, 32, 100, 28, "Detalles");
            pFactura.add(lID);
            pFactura.add(lNombre);
            pFactura.add(lPrecio);
            pFactura.add(lStock);
            pFactura.add(lStockMinimo);
            //contenido
            for (int i = 0; i < facturas.size(); i++) {

                // por cada producto
                Factura factura = facturas.get(i);

                SLabel lProducto = new SLabel(32, i * 32 + 64, 68, 28, factura.getIdfactura().toString());
                SLabel lProducto1 = new SLabel(100, i * 32 + 64, 250, 28,
                        ((factura.getCliente() != null) ? factura.getCliente().getNombre() : "") + " " +
                        ((factura.getCliente() != null) ? factura.getCliente().getApellido() : ""));
                SLabel lProducto2 = new SLabel(350, i * 32 + 64, 150, 28, toCOP(factura.getCostoTotal()), SLabel.RIGHT);
                SLabel lProducto3 = new SLabel(550, i * 32 + 64, 100, 28, factura.getFecha() + "", SLabel.RIGHT);
                SButton btDetail = new SButton(700, i * 32 + 64, 100, 28, "+");

                pInventario.add(lProducto);
                pInventario.add(lProducto1);
                pInventario.add(lProducto2);
                pInventario.add(lProducto3);
                pInventario.add(btDetail);
            }
        }
        if(facturas.size() > 17) {
            pFactura.setSize(pFactura.getWidth(), facturas.size()*32+128);
        }
        else {
            pFactura.setSize(pFactura.getWidth(), 598);
        }
        pFactura.repaint();

        // pInventario
        pInventario.removeAll();
        ArrayList<Producto> productos = BackController.controller.Producto();
        if (productos != null && !productos.isEmpty()) {
            //encabezado
            SLabel lID = new SLabel(32, 32, 68, 28, "ID");
            SLabel lNombre = new SLabel(100, 32, 250, 28, "Nombre");
            SLabel lPrecio = new SLabel(350, 32, 150, 28, "Precio");
            SLabel lStock = new SLabel(550, 32, 100, 28, "Stock");
            SLabel lStockMinimo = new SLabel(700, 32, 100, 28, "Stock Minimo");
            pInventario.add(lID);
            pInventario.add(lNombre);
            pInventario.add(lPrecio);
            pInventario.add(lStock);
            pInventario.add(lStockMinimo);
            //contenido
            for (int i = 0; i < productos.size(); i++) {
                // por cada producto
                Producto producto = productos.get(i);
                SLabel lProducto = new SLabel(32, i * 32 + 64, 68, 28, producto.getIdproducto().toString());
                SLabel lProducto1 = new SLabel(100, i * 32 + 64, 250, 28, producto.getNombre());
                SLabel lProducto2 = new SLabel(350, i * 32 + 64, 150, 28, toCOP(producto.getPrecio()), SLabel.RIGHT);
                SLabel lProducto3 = new SLabel(550, i * 32 + 64, 100, 28, producto.getStock()+"", SLabel.RIGHT);
                SLabel lProducto4 = new SLabel(700, i * 32 + 64, 100, 28, producto.getStockMinimo()+"", SLabel.RIGHT);

                pInventario.add(lProducto);
                pInventario.add(lProducto1);
                pInventario.add(lProducto2);
                pInventario.add(lProducto3);
                pInventario.add(lProducto4);
            }
        }
        if(productos.size() > 17) {
            pInventario.setSize(pInventario.getWidth(), productos.size()*32+128);
        }
        else {
            pInventario.setSize(pInventario.getWidth(), 598);
        }
        pInventario.repaint();

        //pEstadistica
        pEstadistica.removeAll();
        SButton prueba = new SButton(32, 32, 68, 28, "Holiwis");
        pEstadistica.add(prueba);
        pEstadistica.repaint();
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
        btConfirm.addActionListener((e) -> {
            if (!tfNombre.getText().isEmpty() && isDouble(tfPrecio.getText()) && isInt(tfStock.getText())
                    && isInt(tfStockMin.getText())) {
                try {
                    BackController.insertarProducto(tfNombre.getText(), Double.valueOf(tfPrecio.getText()),
                            Integer.parseInt(tfStock.getText()), Integer.parseInt(tfStockMin.getText()));
                    actualizar();
                } catch (NumberFormatException | SQLException | ParseException e1) {
                    e1.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor ingrese valores válidos", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            ventana.cerrar();
        });
        ventana.add(btConfirm);

        SButton btClose = new SButton(190, 232, 100, 32, "CERRAR");
        btClose.addActionListener((e) -> ventana.cerrar());
        ventana.add(btClose);

        ventana.lanzar();
    }

    public void avisar(Producto faltante) {

        VentanaEmergente ventana = new VentanaEmergente(Controller.controller, 340, 300);

        SLabel lInsertar = new SLabel(32, 32, 300, 40, "Quedan pocos en stock de:");
        ventana.add(lInsertar);

        SLabel lNombre = new SLabel(64, 64, 168, 28, "Nombre:");
        ventana.add(lNombre);

        SLabel tfNombre = new SLabel(200, 62, 100, 32, faltante.getNombre());
        ventana.add(tfNombre);

        SLabel lPrecio = new SLabel(64, 104, 168, 28, "Precio:");
        ventana.add(lPrecio);

        SLabel tfPrecio = new SLabel(200, 102, 100, 32, toCOP(faltante.getPrecio()));
        ventana.add(tfPrecio);

        SLabel lStock = new SLabel(64, 144, 168, 28, "Stock:");
        ventana.add(lStock);

        SLabel tfStock = new SLabel(200, 142, 100, 32, faltante.getStock() + "");
        ventana.add(tfStock);

        SLabel lStockMin = new SLabel(64, 184, 168, 28, "Stock mínimo:");
        ventana.add(lStockMin);

        SLabel tfStockMin = new SLabel(200, 182, 100, 32, faltante.getStockMinimo() + "");
        ventana.add(tfStockMin);

        SButton btConfirm = new SButton(50, 232, 100, 32, "ACEPTAR");
        btConfirm.addActionListener((e) -> {
            ventana.cerrar();
        });
        ventana.add(btConfirm);

        ventana.lanzar();

    }

    public void addFactura() throws SQLException, ParseException {
        VentanaEmergente ventana = new VentanaEmergente(Controller.controller, 340, 210);
        ArrayList<FacturaProducto> facturas = new ArrayList<>();

        SLabel lInsertar = new SLabel(32, 32, 200, 28, "Inserte un movimiento");
        ventana.add(lInsertar);

        SLabel lCliente = new SLabel(64, 64, 168, 28, "Cliente:");
        ventana.add(lCliente);

        STextField tfCliente = new STextField(200, 62, 100, 32);
        ventana.add(tfCliente);

        SLabel lPrecioText = new SLabel(64, 104, 168, 28, "Precio Total:");
        ventana.add(lPrecioText);

        double precioTotal = 0.0;
        for (int i = 0; i < carrito.size(); i++) {
            precioTotal += carrito.get(i).getValor();
        }
        SLabel lPrecioTotal = new SLabel(200, 102, 100, 32, toCOP(precioTotal));
        ventana.add(lPrecioTotal);

        SButton btConfirm = new SButton(50, 152, 100, 32, "INSERTAR");
        btConfirm.addActionListener((e) -> {
            try {
                BackController.insert(new Factura(null, Date.from(Instant.now()), BackController.getCliente(tfCliente.getText()), carrito));
                carrito.clear();
                actualizar();
            } catch (HeadlessException | SQLException | ParseException e1) {
                JOptionPane.showMessageDialog(null, "No se pudo registrar la factura", "Error", JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
            }
            ventana.cerrar();
        });
        ventana.add(btConfirm);

        SButton btClose = new SButton(190, 152, 100, 32, "CERRAR");
        btClose.addActionListener( (e) -> ventana.cerrar());
        ventana.add(btClose);

        ventana.lanzar();
    }

    public static void init() throws SQLException, ParseException {
        view = new View();
    }

}
