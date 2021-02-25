package app.inventario;

import java.math.BigDecimal;

public class FacturaProducto extends General {

    private int cantidad;
    private BigDecimal valor;
    private Factura idfactura;
    private Producto idproducto;

    public FacturaProducto(int cantidad, BigDecimal valor, Factura idfactura, Producto idproducto) {
        this.cantidad = cantidad;
        this.valor = valor;
        this.idfactura = idfactura;
        this.idproducto = idproducto;
    }

    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getValor() {
        return valor;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Factura getFactura() {
        return idfactura;
    }
    public void setFactura(Factura idfactura) {
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

        Object[] objects = { cantidad, valor, Integer.parseInt(idfactura.getIdfactura()), Integer.parseInt(idproducto.getIdproducto()) };

        return objects;

    }

}