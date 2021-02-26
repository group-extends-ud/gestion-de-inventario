package app.inventario;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Factura extends General {

    private Integer idfactura;
    private Date fecha;
    private Cliente idcliente;
    private ArrayList<FacturaProducto> items;

    public Factura(Integer idfactura, Date fecha, Cliente cliente, ArrayList<FacturaProducto> items) {
        if(idfactura != 1)
            this.idfactura = idfactura;
        else this.idfactura = null;
        this.fecha = fecha;
        this.idcliente = cliente;
        this.items = items;

        for(FacturaProducto item : items) {
            item.setFactura(this.idfactura);
        }
    }

    public Integer getIdfactura() {
        return idfactura;
    }
    public void setIdfactura(Integer idfactura) {
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

    public int getCantidad() {
        return items.size();
    }

    public long getCostoTotal() {
        long costoTotal = 0;
        for(FacturaProducto item : items) {
            costoTotal += item.getValor().longValue();
        }

        return costoTotal;
    }

    public Object[] toArray() {

        Object[] objects = { idfactura, null, (idcliente != null)? idcliente.getIdcliente(): "" };

        return objects;

    }

}