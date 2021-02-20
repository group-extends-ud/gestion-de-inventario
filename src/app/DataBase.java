package app;

public class DataBase {

    public static final int ADMINISTRADOR = 2;
    public static final int USUARIO = 1;
    public static final int INVALIDO = 0;

    /**
     * Valida si los datos corresponden a alguna cuenta registrada válida, para permitir acceso a personal autorizado.
     * @param nombre nombre del usuario.
     * @param password contrasenia del usuario.
     * @return ADMINISTRADOR si los datos corresponden a una cuenta de administrador.
     * USUARIO si los datos corresponden a una cuenta de cajero.
     * INVALIDO si no corresponde a ninguna cuenta.
     */
    public static int validarIngreso(String nombre, String password) {
        return ADMINISTRADOR; //falta implementar
    }

}
