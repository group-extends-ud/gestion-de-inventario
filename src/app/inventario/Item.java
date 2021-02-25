package app.inventario;

public class Item {
    
    private String nombre;
    private int id;
    private int stockDisponible;
    private int stockMinimo;
    private int stockFaltante;

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public int getStockFaltante() {
        return stockFaltante;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStockDisponible(int stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public void setStockFaltante(int stockFaltante) {
        this.stockFaltante = stockFaltante;
    }
    
    
}
