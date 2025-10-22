package pos.logic;

import java.io.Serializable;

public class Sesion implements Serializable {

    private static Usuario usuario;

    public static Usuario getUsuario() {
        return usuario;
    }
    public static void setUsuario(Usuario usuario) {
        Sesion.usuario = usuario;
    }
    public static void logout() {
        Sesion.usuario = null;
    }
    public static boolean isLoggedIn(){
        return usuario != null;
    }
}
