package PROYECTO_2.presentation.pacientes;

import PROYECTO_2.logic.Service;
import pos.logic.Paciente;

import java.util.List;

public class Controller_Pacientes {
    View_Pacientes view;
    Model_Pacientes model;

    public Controller_Pacientes(View_Pacientes view, Model_Pacientes model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
        getPacientes();
    }

    public void create(Paciente e) throws Exception{
        Service.getInstance().create(e);
        model.setCurrent(new Paciente());
        model.setPacientes(Service.getInstance().getListaPacientes());
    }

    public void clear() {
        model.setCurrent(new Paciente());
    }

    public void getPacientes(){
        List<Paciente> list = Service.getInstance().getListaPacientes();
        model.setPacientes(list);
    }

    public void filtrarPacientes(String tipo, String texto){
        List<Paciente> list = Service.getInstance().filtrarPacientes(tipo, texto);
        model.setPacientes(list);
    }

    public void remove(Paciente e) throws Exception{
        Service.getInstance().removePaciente(e);
        model.setCurrent(new Paciente());
        model.setPacientes(Service.getInstance().getListaPacientes());
    }

    public void edit(Paciente n) throws Exception{
        Service.getInstance().update(n);
        model.setCurrent(new Paciente());
        model.setPacientes(Service.getInstance().getListaPacientes());

    }

    public void selectCurrent(Paciente e){
        model.setCurrent(e);
    }


}
