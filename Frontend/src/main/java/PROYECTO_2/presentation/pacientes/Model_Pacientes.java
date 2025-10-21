package PROYECTO_2.presentation.pacientes;

import PROYECTO_2.presentation.AbstractModel;
import pos.logic.Paciente;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;


public class Model_Pacientes extends AbstractModel{
    Paciente current;
    List<Paciente> pacientes;

    public static final String CURRENT = "current";
    public static final String PACIENTES = "pacientes";

    public Model_Pacientes(){
        current = new Paciente();
        pacientes = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(PACIENTES);
    }

    public Paciente getCurrent() {
        return current;
    }

    public void setCurrent(Paciente current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }
    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
        firePropertyChange(PACIENTES);
    }
}
