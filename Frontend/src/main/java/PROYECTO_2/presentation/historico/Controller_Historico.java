package PROYECTO_2.presentation.historico;

import PROYECTO_2.logic.Service;
import pos.logic.Paciente;
import pos.logic.Receta;

import java.util.ArrayList;
import java.util.List;

public class Controller_Historico {
    View_Historico view;
    Model_Historico model;

    public Controller_Historico(View_Historico view, Model_Historico model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
        getPacientes();
        getRecetas();
    }

    //--Buscar Recetas--
    public void getRecetas() {
        List<Receta> recetas = Service.getInstance().getListaRecetas();
        model.setRecetas(recetas);
    }
    public void filtrarRecetas(String tipo, String texto) {
        List<Receta> result = Service.getInstance().filtrarRecetas(tipo, texto);
        model.setRecetas(result);
    }
    public void filtrarRecetas(String tipo, String texto1,  String texto2) {
        List<Receta> result = Service.getInstance().filtrarRecetas(tipo, texto1, texto2);
        model.setRecetas(result);
    }
    public void limpiarRecetas() {
        List<Receta> recetas = new ArrayList<Receta>();
        model.setRecetas(recetas);
    }

    //--Buscar Paciente--
    public void getPacientes() {
        List<Paciente> pacientes= Service.getInstance().getListaPacientes();
        model.setPacientes(pacientes);
    }
    public void filtrarPacientes(String tipo, String texto) {
        List<Paciente> result =Service.getInstance().filtrarPacientes(tipo, texto);
        model.setPacientes(result);
    }

    // Atributos
    public void setCurrentEstado(String s){
        model.setCurrentEstado(s);
    }

    public void setCurrentPaciente(Paciente p){
        model.setCurrentaciente(p);
    }

    public void setCurrentReceta(Receta r){ model.setCurrentReceta(r); }

}
