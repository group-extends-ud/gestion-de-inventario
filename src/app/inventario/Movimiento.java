package app.inventario;

public class Movimiento {
   
    
    private int id;
    private String nombreProducto;
    private int idProducto;
    private int cantidad;
    private int costoUnitario;
    private int costoTotal;
    private String descripcion;

    public int getId() {
        return id;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getCostoUnitario() {
        return costoUnitario;
    }

    public int getCostoTotal() {
        return costoTotal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setCostoUnitario(int costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public void setCostoTotal(int costoTotal) {
        this.costoTotal = costoTotal;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
