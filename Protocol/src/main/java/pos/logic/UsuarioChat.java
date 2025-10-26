package pos.logic;

import java.util.ArrayList;
import java.util.List;

public class UsuarioChat {
    String idServer;
    String id;
    String nombre;
    List<String> mensajes;

    public UsuarioChat(String idServer, String id) {
        this.idServer = idServer;
        this.id = id;
        this.nombre = id+" (0)";
        this.mensajes = new ArrayList<String>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdServer() {
        return idServer;
    }

    public void setIdServer(String idServer) {
        this.idServer = idServer;
    }

    public String getMensaje() {
        String msg =mensajes.get(0);
        mensajes.remove(0);
        setNombre(id+" ("+sizeMensajes()+")");
        return msg;
    }
    public void addMensaje(String mensaje) {
        mensajes.add(mensaje);
        setNombre(id+" ("+sizeMensajes()+")");
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
