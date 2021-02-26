package app.gui;

import app.controllers.BackController;
import app.inventario.Cliente;
import app.inventario.Factura;
import app.inventario.FacturaProducto;
import app.inventario.Producto;
import lib.sRAD.gui.component.VentanaEmergente;
import lib.sRAD.gui.sComponent.*;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import static lib.sRAD.gui.component.Resource.LSS;
import static lib.sRAD.gui.component.Resource.WSFS;
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
    // otros
    private Boolean isAdmin;

    protected View(Boolean isAdmin) throws SQLException, ParseException {
        this.isAdmin = isAdmin;
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

        if(isAdmin){
            for(Producto faltante : BackController.controller.getProductosFaltantes()) {
                avisar(faltante);
            }

            SScrollPane spEstadistica = new SScrollPane(216, 90, 864, 624, pEstadistica);
            tpTabs.addTab("Estadística", spEstadistica);
        }

        actualizar();
    }

    public void actualizar() throws SQLException, ParseException {
        // pFactura
        pFactura.removeAll();
        ArrayList<Factura> facturas = BackController.controller.Factura();
        if (!facturas.isEmpty() && facturas.get(0)!=null) {
            SLabel lID = new SLabel(32, 32, 68, 28, "ID", LSS);
            SLabel lNombre = new SLabel(100, 32, 250, 28, "Cliente", LSS);
            SLabel lPrecio = new SLabel(350, 32, 150, 28, "Precio Total", LSS);
            SLabel lStock = new SLabel(550, 32, 100, 28, "Fecha", LSS);
            pFactura.add(lID);
            pFactura.add(lNombre);
            pFactura.add(lPrecio);
            pFactura.add(lStock);
            //contenido
            for (int i = 0; i < facturas.size(); i++) {

                // por cada producto
                Factura factura = facturas.get(i);
                SLabel lIDFactura = new SLabel(32, i * 32 + 64, 68, 28, factura.getIdfactura().toString());
                SLabel lProducto1 = new SLabel(100, i * 32 + 64, 250, 28,
                        ((factura.getCliente() != null) ? factura.getCliente().getNombre() : "") + " " +
                        ((factura.getCliente() != null) ? factura.getCliente().getApellido() : ""));
                SLabel lProducto2 = new SLabel(350, i * 32 + 64, 150, 28, toCOP(factura.getCostoTotal()), SLabel.RIGHT);
                SLabel lProducto3 = new SLabel(550, i * 32 + 64, 100, 28, factura.getFecha() + "", SLabel.RIGHT);
                SButton btDetail = new SButton(700, i * 32 + 64, 100, 28, "detalles");
                btDetail.addActionListener((e) -> abrirDetalles(factura));

                pFactura.add(lIDFactura);
                pFactura.add(lProducto1);
                pFactura.add(lProducto2);
                pFactura.add(lProducto3);
                pFactura.add(btDetail);
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
            SLabel lID = new SLabel(32, 32, 68, 28, "ID", LSS);
            SLabel lNombre = new SLabel(100, 32, 250, 28, "Nombre", LSS);
            SLabel lPrecio = new SLabel(350, 32, 100, 28, "Precio", LSS);
            SLabel lStock = new SLabel(500, 32, 70, 28, "Stock", LSS);
            SLabel lStockMinimo = new SLabel(630, 32, 200, 28, "Stock Minimo", LSS);
            pInventario.add(lID);
            pInventario.add(lNombre);
            pInventario.add(lPrecio);
            pInventario.add(lStock);
            pInventario.add(lStockMinimo);
            //contenido
            for (int i = 0; i < productos.size(); i++) {
                // por cada producto
                final Producto producto = productos.get(i);
                SLabel lProducto = new SLabel(32, i * 32 + 64, 68, 28, producto.getIdproducto().toString());
                SLabel lProducto1 = new SLabel(100, i * 32 + 64, 250, 28, producto.getNombre());
                SLabel lProducto2 = new SLabel(350, i * 32 + 64, 100, 28, toCOP(producto.getPrecio()), SLabel.RIGHT);
                SLabel lProducto3 = new SLabel(500, i * 32 + 64, 100, 28, producto.getStock()+"", SLabel.RIGHT);
                SLabel lProducto4 = new SLabel(640, i * 32 + 64, 100, 28, producto.getStockMinimo()+"", SLabel.RIGHT);

                Image iAdd = new ImageIcon("resources/add.png").getImage().getScaledInstance(28, 28, Image.SCALE_DEFAULT);
                SButton btAdd = new SButton(750, i * 32 + 64, new ImageIcon(iAdd));
                btAdd.addActionListener((e)-> {
                    if(!(producto.getStock() <= 0)) {
                        addToCarrito(producto);
                    }
                });

                pInventario.add(lProducto);
                pInventario.add(lProducto1);
                pInventario.add(lProducto2);
                pInventario.add(lProducto3);
                pInventario.add(lProducto4);
                pInventario.add(btAdd);

                if(isAdmin) {
                    Image iEliminar = new ImageIcon("resources/delete.png").getImage().getScaledInstance(28, 28, Image.SCALE_DEFAULT);
                    SButton btEliminar = new SButton(780, i * 32 + 64, new ImageIcon(iEliminar));
                    btEliminar.addActionListener((e)-> {
                        try {
                            BackController.deleteProducto(producto.getIdproducto());
                            actualizar();
                        } catch (SQLException | ParseException throwables) {
                            throwables.printStackTrace();
                        }
                    });

                    Image iAjustar = new ImageIcon("resources/ajustar.png").getImage().getScaledInstance(28, 28, Image.SCALE_DEFAULT);
                    SButton btAjustar = new SButton(810, i * 32 + 64, new ImageIcon(iAjustar));
                    btAjustar.addActionListener((e)-> {
                        ajustarItem(producto.getIdproducto());
                    });

                    pInventario.add(btEliminar);
                    pInventario.add(btAjustar);
                }
            }
        }
        if(productos.size() > 17) {
            pInventario.setSize(pInventario.getWidth(), productos.size()*32+128);
        }
        else {
            pInventario.setSize(pInventario.getWidth(), 598);
        }
        pInventario.repaint();

        Object[] mas = BackController.getProductoMasVendido();
        Object[] menos = BackController.getProductoMenosVendido();

        //pEstadistica
        pEstadistica.removeAll();
        SLabel masVendido = new SLabel(32, 32, 500, 28, "Producto más vendido " + ((Producto)mas[0]).getNombre() + " con un total de " + mas[1]);
        SLabel menosVendido = new SLabel(32, 60, 500, 28, "Producto menos vendido " + ((Producto)menos[0]).getNombre() + " con un total de " + menos[1]);

        SLabel jsons = new SLabel(32, 92, 300, 28, "¿Desea guardar datos en Json?");
        SButton btGuardar = new SButton(64, 130, 100, 32, "GUARDAR");
        btGuardar.addActionListener( (e) -> {
            try {
                BackController.controller.jsonAll();
                JOptionPane.showMessageDialog(null, "Guardado exitosamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException | ParseException | IOException throwables) {
                throwables.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al guardar", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        pEstadistica.add(masVendido);
        pEstadistica.add(menosVendido);
        pEstadistica.add(jsons);
        pEstadistica.add(btGuardar);
        pEstadistica.repaint();
    }

    private void abrirDetalles(Factura factura) {
        VentanaEmergente ventana = new VentanaEmergente(Controller.controller, 520, 300);

        SPanel pInterno = new SPanel(SPanel.INTERNO, 0, 0, 506, 300);
        SScrollPane scroll = new SScrollPane(0, 0, 516, 296, pInterno);
        ventana.add(scroll);

        SLabel lCarro = new SLabel(202, 32, 168, 28, "Detalles de factura");
        pInterno.add(lCarro);

        if (isAdmin) {
            SButton btConfirm = new SButton(164, 62, 100, 32, "ELIMINAR");
            btConfirm.addActionListener( (e) -> {
                try {
                    BackController.controller.deleteFactura(factura.getIdfactura());
                    actualizar();
                } catch (SQLException | ParseException e1) {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el movimiento indicado, por favor verifique" +
                            " los datos ingresados", "Error", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                }
                ventana.cerrar();
            });
            pInterno.add(btConfirm);
        }

        SButton btClose = new SButton(32, 62, 100, 32, "CERRAR");
        btClose.addActionListener( (e) -> ventana.cerrar());
        pInterno.add(btClose);

        for(int i=0; i<factura.getItems().size(); i++) {
            FacturaProducto f = factura.getItems().get(i);
            SLabel lNombre = new SLabel(32, 104+32*i, 700, 28, "Nombre: "+f.getProducto().getNombre()+
                    ", cantidad: "+f.getCantidad()+ ",Valor: "+toCOP(f.getValor()));
            pInterno.add(lNombre);
        }
        pInterno.setSize(pInterno.getWidth(), 184+32*factura.getItems().size());

        ventana.lanzar();
    }

    private void addToCarrito(Producto producto) {
        VentanaEmergente ventana = new VentanaEmergente(Controller.controller, 340, 180);

        SLabel lCarro = new SLabel(32, 32, 168, 28, "Insertar al carrito");
        ventana.add(lCarro);

        SLabel lCantidad = new SLabel(64, 64, 168, 28, "Ingrese cantidad: ");
        ventana.add(lCantidad);

        STextField tfCantidad = new STextField(200, 62, 100, 32);
        ventana.add(tfCantidad);

        SButton btConfirm = new SButton(50, 112, 100, 32, "AÑADIR");
        btConfirm.addActionListener( (e) -> {
            if (!tfCantidad.getText().isEmpty() && Integer.parseInt(tfCantidad.getText()) < producto.getStock()) {
                try {
                    carrito.add(new FacturaProducto(Integer.parseInt(tfCantidad.getText()), -1, producto));
                    actualizar();
                } catch (SQLException | ParseException throwables) {
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

    private void ajustarItem(int id) {
        VentanaEmergente ventana = new VentanaEmergente(Controller.controller, 340, 320);

        SLabel lInsertar = new SLabel(32, 32, 200, 28, "Modificar un producto");
        ventana.add(lInsertar);

        SLabel lNombre = new SLabel(64, 64, 168, 28, "Nombre:");
        ventana.add(lNombre);

        STextField tfNombre = new STextField(200, 62, 100, 32);
        ventana.add(tfNombre);

        SLabel lStock = new SLabel(64, 104, 168, 28, "Stock:");
        ventana.add(lStock);

        STextField tfStock = new STextField(200, 102, 100, 32);
        ventana.add(tfStock);

        SLabel lStockMin = new SLabel(64, 144, 168, 28, "Stock Mínimo:");
        ventana.add(lStockMin);

        STextField tfStockMin = new STextField(200, 142, 100, 32);
        ventana.add(tfStockMin);

        SLabel lPrecio = new SLabel(64, 184, 168, 28, "Precio:");
        ventana.add(lPrecio);

        STextField tfPrecio = new STextField(200, 182, 100, 32);
        ventana.add(tfPrecio);

        SButton btConfirm = new SButton(50, 232, 100, 32, "MODIFICAR");
        btConfirm.addActionListener( (e) -> {
            if (!tfNombre.getText().isEmpty() && !tfPrecio.getText().isEmpty() && !tfStock.getText().isEmpty()
                    && !tfStockMin.getText().isEmpty()) {
                try {
                    BackController.updateProducto(new Producto(id, tfNombre.getText(), Double.parseDouble(tfPrecio.getText()),
                            Integer.parseInt(tfStock.getText()), Integer.parseInt(tfStockMin.getText())));
                    actualizar();
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

        SButton btClose = new SButton(190, 232, 100, 32, "CERRAR");
        btClose.addActionListener( (e) -> ventana.cerrar());
        ventana.add(btClose);

        ventana.lanzar();
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

        VentanaEmergente ventana = new VentanaEmergente(Controller.controller, 450, 300);

        SLabel lInsertar = new SLabel(32, 32, 250, 40, "Quedan pocos en stock de:");
        ventana.add(lInsertar);

        SLabel lNombre = new SLabel(64, 64, 168, 28, "Nombre:");
        ventana.add(lNombre);

        SLabel tfNombre = new SLabel(200, 62, 400, 32, faltante.getNombre());
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

        SLabel lCliente = new SLabel(64, 64, 168, 28, "Id del cliente:");
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
        double finalPrecioTotal = precioTotal;//esto es porque java lo requiere :v
        btConfirm.addActionListener((e) -> {
            try {
                if(finalPrecioTotal >0.0) {
                    BackController.insert(new Factura(-1, null, BackController.controller.Cliente(Integer.parseInt(tfCliente.getText())), carrito));
                    carrito.clear();
                    actualizar();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Inserte al menos un producto en el carro", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
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

    public static void init(Boolean isAdmin) throws SQLException, ParseException {
        view = new View(isAdmin);
    }

}
