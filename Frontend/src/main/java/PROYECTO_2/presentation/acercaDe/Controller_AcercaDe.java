package PROYECTO_2.presentation.acercaDe;

public class Controller_AcercaDe {
    View_AcercaDe view;
    Model_AcercaDe model;

    public Controller_AcercaDe(View_AcercaDe view, Model_AcercaDe model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }
}
