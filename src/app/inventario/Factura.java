package app.inventario;

import java.util.Date;

public class Factura extends General {

    private String idfactura;
    private Date fecha;
    private Cliente cliente;
    private FacturaProducto[] items;

    public Factura(String idfactura, Date fecha, Cliente cliente, FacturaProducto[] items) {
        this.idfactura = idfactura;
        this.fecha = fecha;
        this.cliente = cliente;
        this.items = items;
    }

    public String getIdfactura() {
        return idfactura;
    }
    public void setIdfactura(String idfactura) {
        this.idfactura = idfactura;
    }

    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public FacturaProducto[] getItems() {
        return items;
    }
    public void setItems(FacturaProducto[] items) {
        this.items = items;
    }

    public static String[] toArrayAtributes() {

        return toArrayAtributes(Factura.class.getDeclaredFields());

    }

    public Object[] toArray() {

        Object[] objects = { fecha, cliente, items, idfactura };

        return objects;

    }

}