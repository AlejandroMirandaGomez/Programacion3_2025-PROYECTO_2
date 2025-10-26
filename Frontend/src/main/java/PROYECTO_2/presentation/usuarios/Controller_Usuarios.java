package PROYECTO_2.presentation.usuarios;

import PROYECTO_2.logic.Service;
import PROYECTO_2.presentation.SocketListener;
import PROYECTO_2.presentation.ThreadListener;
import PROYECTO_2.presentation.usuarios.Model_Usuarios;
import PROYECTO_2.presentation.usuarios.View_Usuarios;
import pos.logic.UsuarioChat;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Controller_Usuarios implements ThreadListener {
    View_Usuarios view;
    Model_Usuarios model;

    SocketListener socketListener;

    public Controller_Usuarios(View_Usuarios view, Model_Usuarios model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);

        model.setUsuarios(new ArrayList<>());

        try{
            socketListener= new SocketListener(this,((Service) Service.getInstance()).getSid());
            socketListener.start();
        }catch(Exception e){}
    }
    public void setSeleccionado(int row){
        UsuarioChat n = model.getUsuarios().get(row);
        model.setUsuarioSeleccionado(n);
    }
    public void enviarMensaje(String mensaje) throws Exception{

        Service.getInstance().enviarMensaje(model.getUsuarioSeleccionado().getIdServer(),mensaje);
    }
    public String getMensajes(){
        UsuarioChat n = model.getUsuarioSeleccionado();
        String msg = n.getMensaje();
        List<UsuarioChat> nu = new ArrayList<>(model.getUsuarios());
        model.setUsuarios(nu);
        return msg;
    }

    @Override
    public void deliver_message(String idServerEmisor, String message) {
        List<UsuarioChat> n = new ArrayList<>(model.getUsuarios());
        for (UsuarioChat u : n){
            if(u.getIdServer().equals(idServerEmisor)){
                u.addMensaje(message);
            }
        }

        model.setUsuarios(n);
    }

    @Override
    public void loggedIn(String nombre, String idServer) {
        UsuarioChat nuevo = new UsuarioChat(idServer, nombre);
        List<UsuarioChat> n = new ArrayList<>(model.getUsuarios());
        n.add(nuevo);
        model.setUsuarios(n);
        System.out.println(n.size());

    }

    @Override
    public void loggedOut(String idServer) {
        List<UsuarioChat> n = new ArrayList<>(model.getUsuarios());
        n.removeIf(u -> u.getIdServer().equals(idServer));
        model.setUsuarios(n);

    }
}
