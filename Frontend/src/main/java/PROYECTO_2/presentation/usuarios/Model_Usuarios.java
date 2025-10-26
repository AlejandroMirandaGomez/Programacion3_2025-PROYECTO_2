package PROYECTO_2.presentation.usuarios;

import PROYECTO_2.presentation.AbstractModel;
import pos.logic.Usuario;
import pos.logic.UsuarioChat;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model_Usuarios extends AbstractModel {

    List<UsuarioChat> usuarios;
    UsuarioChat usuarioSeleccionado;

    //public static final String CURRENT = "current";
    public static final String USUARIOS = "usuarios";
    public static final String SELECCIONADO = "seleccionado";

    public Model_Usuarios(){

        usuarios = new ArrayList<>();
        usuarioSeleccionado = new UsuarioChat("x","y");
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(USUARIOS);
        firePropertyChange(SELECCIONADO);
    }
/*
    public Usuario getCurrent() {
        return current;
    }

    public void setCurrent(Usuario current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

 */

    public UsuarioChat getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(UsuarioChat usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
        firePropertyChange(SELECCIONADO);
    }

    public List<UsuarioChat> getUsuarios() {
        return usuarios;
    }
    public void setUsuarios(List<UsuarioChat> usuarios) {
        this.usuarios = usuarios;
        firePropertyChange(USUARIOS);
    }
}
