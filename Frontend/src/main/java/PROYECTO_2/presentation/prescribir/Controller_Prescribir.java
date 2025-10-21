package PROYECTO_2.presentation.prescribir;

import PROYECTO_2.logic.Service;

import pos.logic.Medicamento;
import pos.logic.Prescripcion;
import pos.logic.Receta;
import pos.logic.Paciente;
import pos.logic.Medico;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller_Prescribir {
    Model_Prescribir model;
    View_Prescribir view;
    public Controller_Prescribir(View_Prescribir view, Model_Prescribir model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);

        getPacientes();
        getMedicamentos();
    }
    //--Recetas--
    public void clear(){
        model.setCurrentReceta(new Receta());
        model.setMedicamentos(new ArrayList<>());
        model.setPacientes(new ArrayList<>());
        model.setPrescripciones(new ArrayList<>());
        model.setCurrentDetalle(new Prescripcion());
    }
    public void create() throws Exception{
        Receta receta = model.getCurrentReceta();
        receta.setMedico(getMedico());

        Service.getInstance().create(receta);

        model.setCurrentReceta(new Receta());
        model.setPrescripciones(new ArrayList<>());
    }
    public Medico getMedico() throws Exception{
        return Service.getInstance().getUsuario();
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
    public void setPaciente(int row){
        Paciente paciente = model.getPacientes().get(row);
        model.marcarPaciente(paciente);
    }

    //--Buscar Medicamentos--
    public void getMedicamentos(){
        List<Medicamento> medicamentos= Service.getInstance().getListaMedicamentos();
        model.setMedicamentos(medicamentos);
    }
    public void filtrarMedicamentos(String tipo, String texto) {
        List<Medicamento> medicamentos= Service.getInstance().filtrarMedicamentos(tipo, texto);
        model.setMedicamentos(medicamentos);
    }

    //--Borrar prescripcion--
    public void borrarPrescripcion(int row){
        model.borrarPrescripcion(row);
    }

    //--Guardar prescripcion--
    public void crearPrescripcion(Prescripcion prescripcion){
        model.agregarPrescripcion(prescripcion);
    }

    public void seleccionarMedicamentoParaPrescripcion(int row) {
        Medicamento m = model.getMedicamentos().get(row);
        Prescripcion p = new Prescripcion();
        p.setMedicamento(m);
        model.setCurrentDetalle(p);
        view.abrirGuardarMedicamento();
    }

    public void seleccionarPrescripcionDetalle(int row) {
        Prescripcion p= model.getPrescripciones().get(row);

        model.setCurrentDetalle(p);
        view.abrirDetalle(row);
    }

    //--Detalle--
    public void actualizarPrescripcion(int row,Prescripcion prescripcion){
        model.actualizarPrescripcion(row,prescripcion);
    }

    //--Fecha--
    public void actualizarFecha(LocalDate fecha){
        Receta receta = model.getCurrentReceta();
        receta.setFechaDeRetiro(fecha);
        model.setCurrentReceta(receta);
    }

}
