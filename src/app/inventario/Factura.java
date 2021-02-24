package app.inventario;

import java.util.Date;

public class Factura {

    private String idfactura;
    private Date fecha;
    private Cliente cliente;
    private FacturaProducto[] productos;

    public Factura(String idfactura, Date fecha, Cliente cliente, FacturaProducto[] productos) {
        this.idfactura = idfactura;
        this.fecha = fecha;
        this.cliente = cliente;
        this.productos = productos;
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

    public FacturaProducto[] getProductos() {
        return productos;
    }
    public void setProductos(FacturaProducto[] productos) {
        this.productos = productos;
    }

}

class FacturaProducto {

    private String valor;
    private int cantidad;
    private Producto producto;

    public FacturaProducto(String valor, int cantidad, Producto producto) {
        this.valor = valor;
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

}