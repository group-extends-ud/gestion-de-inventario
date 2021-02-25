package app.inventario;

import java.lang.reflect.Field;

public abstract class General {

    protected static String[] toArrayAtributes(Field[] fields) {

        String[] objects = new String[fields.length];

        for(int i = 0; i < fields.length; ++i) {
            objects[i] = fields[i].getName();
        }

        return objects;

    }

    public abstract Object[] toArray();
    
}
