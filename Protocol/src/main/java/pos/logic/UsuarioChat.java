package pos.logic;

import java.util.ArrayList;
import java.util.List;

public class UsuarioChat {
    Usuario usuario;
    String nombre;
    List<String> mensajes;

    public UsuarioChat(Usuario  usuario) {
        this.usuario = usuario;
        this.nombre = usuario.getId()+" (0)";
        this.mensajes = new ArrayList<String>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public String getMensaje() {
        String msg =mensajes.get(0);
        mensajes.remove(0);
        setNombre(usuario.getId()+" ("+sizeMensajes()+")");
        return msg;
    }
    public void addMensaje(String mensaje) {
        mensajes.add(mensaje);
        setNombre(usuario.getId()+" ("+sizeMensajes()+")");
    }

    public void setMensjes(List<String> mensajes) {
        this.mensajes = mensajes;
    }
    public int sizeMensajes(){
        return mensajes.size();
    }

    public boolean mensajesPendientes(){
        return !mensajes.isEmpty();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
