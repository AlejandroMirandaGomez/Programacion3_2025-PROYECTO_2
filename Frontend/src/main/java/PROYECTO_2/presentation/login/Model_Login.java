package PROYECTO_2.presentation.login;

import PROYECTO_2.presentation.AbstractModel;
import pos.logic.Usuario;

import java.beans.PropertyChangeListener;

public class Model_Login extends AbstractModel{
    Usuario current;

    public static final String CURRENT = "current";

    public Model_Login(){ current = new Usuario() ;}

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        //firePropertyChange(CURRENT);
    }

    public Usuario getCurrent() {
        return current;
    }

    public void setCurrent(Usuario current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }
}
