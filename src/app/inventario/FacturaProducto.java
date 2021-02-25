package app.inventario;

public class FacturaProducto extends General {

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

    public static String[] toArrayAtributes() {

        return toArrayAtributes(FacturaProducto.class.getDeclaredFields());

    }

    @Override
    public Object[] toArray() {

        Object[] objects = { valor, cantidad, producto };

        return objects;

    }

}