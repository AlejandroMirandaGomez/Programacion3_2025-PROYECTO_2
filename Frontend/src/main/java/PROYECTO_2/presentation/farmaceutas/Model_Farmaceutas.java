package PROYECTO_2.presentation.farmaceutas;

import PROYECTO_2.presentation.AbstractModel;
import pos.logic.Farmaceuta;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model_Farmaceutas extends AbstractModel{
    Farmaceuta current;
    List<Farmaceuta> farmaceutas;

    public static final String CURRENT = "current";
    public static final String FARMACEUTAS = "farmaceutas";

    public Model_Farmaceutas(){
        current = new Farmaceuta();
        farmaceutas = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(FARMACEUTAS);
    }

    public Farmaceuta getCurrent() {
        return current;
    }

    public void setCurrent(Farmaceuta current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<Farmaceuta> getFarmaceutas() {
        return farmaceutas;
    }
    public void setFarmaceutas(List<Farmaceuta> medicos) {
        this.farmaceutas = medicos;
        firePropertyChange(FARMACEUTAS);
    }
}
