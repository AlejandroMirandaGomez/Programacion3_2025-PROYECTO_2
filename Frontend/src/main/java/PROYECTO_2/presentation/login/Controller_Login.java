package PROYECTO_2.presentation.login;

import PROYECTO_2.logic.Service;
import pos.logic.Sesion;
import pos.logic.Usuario;

public class Controller_Login {
    View_Login view;
    Model_Login model;

    public Controller_Login(View_Login view, Model_Login model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
        Service.getInstance();
    }

    public void login(Usuario usuario) throws Exception{
        Usuario logged = Service.getInstance().read(usuario);
        if(!logged.getPassword().equals(usuario.getPassword())){
            throw new Exception("Usuario o clave incorrectos");
        }
        Sesion.setUsuario(logged);
        Service.getInstance().loggedIn(logged);
    }

    public void changePassword(Usuario usuario, String newPassword)throws  Exception{
        Usuario logged = Service.getInstance().read(usuario);
        if(logged.getPassword().equals(usuario.getPassword())){
            //logged.setPassword(newPassword);
            logged.setPassword(newPassword);
            Service.getInstance().update(logged);
        }
    }
}
