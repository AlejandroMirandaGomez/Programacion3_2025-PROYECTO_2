package PROYECTO_2.presentation.medicamentos;

import PROYECTO_2.logic.Service;
import pos.logic.Medicamento;

import java.util.List;

public class Controller_Medicamentos {
    View_Medicamentos view;
    Model_Medicamentos model;

    public Controller_Medicamentos(View_Medicamentos view, Model_Medicamentos model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
        getMedicamentos();
    }

    public void create(Medicamento e) throws Exception{
        Service.getInstance().create(e);
        model.setCurrent(new Medicamento());
        model.setMedicamentos(Service.getInstance().findAllMedicamentos());
    }


    public void clear() {
        model.setCurrent(new Medicamento());
    }

    public void getMedicamentos(){
        List<Medicamento> list = Service.getInstance().findAllMedicamentos();
        model.setMedicamentos(list);
    }

    public void filtrarMedicamentos(String tipo, String texto){
        List<Medicamento> list = Service.getInstance().filtrarMedicamentos(tipo, texto);
        model.setMedicamentos(list);
    }

    public void remove(Medicamento e) throws Exception{
        Service.getInstance().removeMedicamento(e);
        model.setCurrent(new Medicamento());
        model.setMedicamentos(Service.getInstance().findAllMedicamentos());
    }

    public void edit(Medicamento n) throws Exception{
        Service.getInstance().update(n);
        model.setCurrent(new Medicamento());
        model.setMedicamentos(Service.getInstance().findAllMedicamentos());

    }

    public void selectCurrent(Medicamento n){
        model.setCurrent(n);
    }
}
