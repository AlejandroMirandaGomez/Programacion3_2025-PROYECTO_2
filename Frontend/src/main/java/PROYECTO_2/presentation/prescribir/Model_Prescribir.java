package PROYECTO_2.presentation.prescribir;

import PROYECTO_2.presentation.AbstractModel;
import pos.logic.Medicamento;
import pos.logic.Paciente;
import pos.logic.Prescripcion;
import pos.logic.Receta;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model_Prescribir extends AbstractModel{
    Receta currentReceta;
    List<Prescripcion> prescripciones;

    //--Buscar Paciente--
    List<Paciente> pacientes;

    //--Buscar Medicamentos--
    List<Medicamento> medicamentos;

    //--Detalle Medicamento--
    Prescripcion currentDetalle;


    public static final String CURRENT = "currentReceta";
    public static final String PRESCRIPCIONES = "prescripciones";
    public static final String PACIENTES = "pacientes";
    public static final String MEDICAMENTOS = "medicamentos";
    public static final String CURRENTDETALLE = "currentDetalle";

    public Model_Prescribir() {
        currentReceta = new Receta();
        prescripciones = new ArrayList<Prescripcion>();

        pacientes= new ArrayList<Paciente>();
        medicamentos=new ArrayList<Medicamento>();

        currentDetalle=new Prescripcion();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(PRESCRIPCIONES);

        firePropertyChange(PACIENTES);
        firePropertyChange(MEDICAMENTOS);

        firePropertyChange(CURRENTDETALLE);
    }

    public Receta getCurrentReceta() {
        return currentReceta;
    }

    public void setCurrentReceta(Receta currentReceta) {
        this.currentReceta = currentReceta;
        firePropertyChange(CURRENT);
    }

    public List<Prescripcion> getPrescripciones() {
        return prescripciones;
    }

    public void setPrescripciones(List<Prescripcion> prescripciones) {
        this.prescripciones = prescripciones;
        currentReceta.setPrescripciones(prescripciones);

        firePropertyChange(PRESCRIPCIONES);
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }
    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
        firePropertyChange(PACIENTES);
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
        firePropertyChange(MEDICAMENTOS);
    }

    public Prescripcion getCurrentDetalle() {
        return currentDetalle;
    }

    public void setCurrentDetalle(Prescripcion currentDetalle) {
        this.currentDetalle = currentDetalle;
        firePropertyChange(CURRENTDETALLE);
    }

    public void borrarPrescripcion(int row){
        prescripciones.remove(row);
        currentReceta.setPrescripciones(prescripciones);
        firePropertyChange(PRESCRIPCIONES);
    }

    public void agregarPrescripcion(Prescripcion prescripcion){
        prescripciones.add(prescripcion);
        currentReceta.setPrescripciones(prescripciones);
        firePropertyChange(PRESCRIPCIONES);
    }
    public void actualizarPrescripcion(int row,Prescripcion prescripcion){
        prescripciones.set(row,prescripcion);
        currentReceta.setPrescripciones(prescripciones);
        firePropertyChange(PRESCRIPCIONES);
    }

    public void marcarPaciente(Paciente paciente){
        currentReceta.setPaciente(paciente);
        firePropertyChange(CURRENT);
    }
}
