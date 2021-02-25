package app.inventario;

import java.math.BigDecimal;

public class Producto extends General {

    private String idproducto, nombre;
    private BigDecimal precio;
    private int stock, stockminimo;

    public Producto(String idproducto, String nombre, BigDecimal precio, int stock, int stockminimo) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.stockminimo = stockminimo;
    }

    public String getIdproducto() {
        return idproducto;
    }
    public void setIdproducto(String idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
		this.stock = stock;
    }

    public int getStockMinimo() {
        return stockminimo;
    }
    public void setStockMinimo(int stockminimo) {
        this.stockminimo = stockminimo;
    }

    public static String[] toArrayAtributes() {

        return toArrayAtributes(Producto.class.getDeclaredFields());

    }

    @Override
    public Object[] toArray() {

        Object[] objects = { idproducto, nombre, precio, stock, stockminimo };

        return objects;

    }

}