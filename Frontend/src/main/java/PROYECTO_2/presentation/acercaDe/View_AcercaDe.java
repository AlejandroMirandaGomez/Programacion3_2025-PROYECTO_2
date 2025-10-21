package PROYECTO_2.presentation.acercaDe;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View_AcercaDe implements PropertyChangeListener {
    private JPanel panel;

    public JPanel getPanel() {
        return panel;
    }

    //MVC
    Controller_AcercaDe controller;
    Model_AcercaDe model;

    public void setController(Controller_AcercaDe controller) {
        this.controller = controller;
    }
    public void setModel(Model_AcercaDe model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
