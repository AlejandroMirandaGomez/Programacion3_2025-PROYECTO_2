package PROYECTO_2.presentation.medicamentos;

import PROYECTO_2.presentation.AbstractModel;
import pos.logic.Medicamento;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;


public class Model_Medicamentos extends AbstractModel{
    Medicamento current;
    List<Medicamento> medicamentos;

    public static final String CURRENT = "current";
    public static final String MEDICAMENTOS = "medicamentos";

    public Model_Medicamentos(){
        current = new Medicamento();
        medicamentos = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(MEDICAMENTOS);
    }

    public Medicamento getCurrent() {
        return current;
    }

    public void setCurrent(Medicamento current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }
    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
        firePropertyChange(MEDICAMENTOS);
    }



}
