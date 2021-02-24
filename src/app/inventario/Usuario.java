package app.inventario;

public class Usuario extends General {

    private String userName;
    private boolean isAdmin;

    public Usuario(String userName, boolean isAdmin) {
        this.userName = userName;
        this.isAdmin = isAdmin;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public static String[] toArrayAtributes() {

        return toArrayAtributes(Usuario.class.getDeclaredFields());

    }

    @Override
    public Object[] toArray() {

        Object[] objects = { userName, isAdmin };

        return objects;

    }
    
}
