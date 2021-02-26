package app.inventario;

import java.math.BigDecimal;

public class FacturaProducto extends General {

    private int cantidad;
    private BigDecimal valor;
    private Integer idfactura;
    private Producto idproducto;

    public FacturaProducto(int cantidad, BigDecimal valor, Integer idfactura, Producto idproducto) {
        this.cantidad = cantidad;
        this.valor = valor;
        this.idfactura = idfactura;
        this.idproducto = idproducto;

        boolean noCoinciden = false;
        if(valor != null)
            noCoinciden = valor.compareTo(BigDecimal.valueOf(idproducto.getPrecio()).multiply(new BigDecimal(cantidad))) != 0;

        if(!noCoinciden) {
            this.valor = BigDecimal.valueOf(idproducto.getPrecio()).multiply(new BigDecimal(cantidad));
        }

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