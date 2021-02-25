package app.inventario;

import java.util.ArrayList;

public class Registro {
    
    ArrayList<Item> items = new ArrayList<Item>();
    ArrayList<Movimiento> movimientos = new ArrayList<Movimiento>();
    
    public void additem(Item item){
        items.add(item);
    }
    
    public void deleteitem(int id){
        items.remove(id);
    }
    
    public void addMovimiento(Movimiento moviento){
        movimientos.add(moviento);
    }
    
    public void deleteMovimiento(int id){
        movimientos.remove(id);
    }

    public ArrayList<Item> getItems(int id) {
        return items;
    }

    public ArrayList<Movimiento> getMovimientos(int id) {
        return movimientos;
    }
    
}
