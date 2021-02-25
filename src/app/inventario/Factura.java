package app.inventario;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Factura extends General {

    private String idfactura;
    private Date fecha;
    private Cliente idcliente;
    private ArrayList<FacturaProducto> items;

    public Factura(String idfactura, Date fecha, Cliente cliente, ArrayList<FacturaProducto> items) {
        this.idfactura = idfactura;
        this.fecha = fecha;
        this.idcliente = cliente;
        this.items = items;

        for(FacturaProducto item : items) {
            item.setFactura(this);
        }
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
        return idcliente;
    }
    public void setCliente(Cliente cliente) {
        this.idcliente = cliente;
    }

    public ArrayList<FacturaProducto> getItems() {
        return items;
    }
    public void setItems(ArrayList<FacturaProducto> items) {
        this.items = items;
    }

    public void addItems(FacturaProducto item) {
        this.items.add(item);
    }

    public static String[] toArrayAtributes() {

        Field[] provisional = Factura.class.getDeclaredFields();

        Field[] fields = Arrays.copyOf(provisional, provisional.length - 1);

        return toArrayAtributes(fields);

    }

    public Object[] toArray() {

        Object[] objects = { idfactura, fecha, (idcliente != null)? idcliente.getIdcliente(): null };

        return objects;

    }

}