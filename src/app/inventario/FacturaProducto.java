package app.inventario;

import java.math.BigDecimal;

public class FacturaProducto extends General {

    private int cantidad;
    private double valor;
    private Integer idfactura;
    private Producto idproducto;

    public FacturaProducto(int cantidad, Integer idfactura, Producto idproducto) {
        this.cantidad = cantidad;
        this.idfactura = idfactura;
        this.idproducto = idproducto;
        this.valor = idproducto.getPrecio() * cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getValor() {
        return valor;
    }

    public Integer getFactura() {
        return idfactura;
    }
    public void setFactura(Integer idfactura) {
        this.idfactura = idfactura;
    }

    public Producto getProducto() {
        return idproducto;
    }
    public void setProducto(Producto idproducto) {
        this.idproducto = idproducto;
    }

    public static String[] toArrayAtributes() {

        return toArrayAtributes(FacturaProducto.class.getDeclaredFields());

    }

    @Override
    public Object[] toArray() {

        Object[] objects = { cantidad, valor, idfactura, idproducto.getIdproducto() };

        return objects;

    }

}