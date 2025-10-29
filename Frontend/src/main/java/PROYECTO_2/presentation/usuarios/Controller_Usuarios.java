package PROYECTO_2.presentation.usuarios;

import PROYECTO_2.logic.Service;
import PROYECTO_2.presentation.ThreadListener;
import pos.logic.Usuario;
import pos.logic.UsuarioChat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        Service.getInstance().enviarMensaje(model.getUsuarioSeleccionado().getUsuario(),mensaje);
    }
    public String getMensajes(){
        UsuarioChat n = model.getUsuarioSeleccionado();
        String msg = n.getMensaje();
        List<UsuarioChat> nu = new ArrayList<>(model.getUsuarios());
        model.setUsuarios(nu);
        return msg;
    }

    @Override
    public void deliver_message(Usuario usuario, String message) {
        List<UsuarioChat> n = new ArrayList<>(model.getUsuarios());
        for (UsuarioChat u : n){
            if(Objects.equals(u.getUsuario().getId(), usuario.getId())){
                u.addMensaje(message);
            }
        }

        model.setUsuarios(n);
    }

    @Override
    public void loggedIn(Usuario usuario) {
        UsuarioChat nuevo = new UsuarioChat(usuario);
        List<UsuarioChat> n = new ArrayList<>(model.getUsuarios());
        n.add(nuevo);
        model.setUsuarios(n);
        System.out.println(n.size());

    }

    @Override
    public void loggedOut(Usuario usuario) {
        List<UsuarioChat> n = new ArrayList<>(model.getUsuarios());
        n.removeIf(u -> u.getUsuario().getId().equals(usuario.getId()));
        model.setUsuarios(n);

    }
}
