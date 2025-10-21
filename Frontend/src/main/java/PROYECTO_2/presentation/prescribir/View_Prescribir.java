package PROYECTO_2.presentation.prescribir;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import PROYECTO_2.presentation.prescribir.buscarMedicamento.View_buscarMedicamento;
import PROYECTO_2.presentation.prescribir.buscarPaciente.View_BuscarPaciente;
import PROYECTO_2.presentation.prescribir.guardarMedicamento.View_GuardarMedicamento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View_Prescribir implements PropertyChangeListener {
    private JPanel panel;
    private JPanel control;
    private JButton buscarPacienteBtn;
    private JButton agregarMedicamentoBtn;
    private DatePicker fechaRetiro;
    private JLabel nombrePaciente;
    private JTable tablaPrescripciones;
    private JPanel ajustar;
    private JButton guardar;
    private JButton limpiar;
    private JButton descartar;
    private JButton detalles;

    private View_BuscarPaciente buscarPacienteView;
    private View_buscarMedicamento buscarMedicamentoView;
    private View_GuardarMedicamento guardarMedicamentoView;

    Controller_Prescribir controller;
    Model_Prescribir model;



    public View_Prescribir(){
        buscarPacienteView = new  View_BuscarPaciente();
        buscarMedicamentoView = new View_buscarMedicamento();
        guardarMedicamentoView = new View_GuardarMedicamento();

        descartar.setEnabled(false);
        detalles.setEnabled(false);


        buscarPacienteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                buscarPacienteView.setVisible(true);
            }
        });
        agregarMedicamentoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarMedicamentoView.setVisible(true);
            }
        });

        tablaPrescripciones.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPrescripciones.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tablaPrescripciones.getSelectedRow();
                if (row >= 0) {
                    descartar.setEnabled(true);
                    detalles.setEnabled(true);
                }

            }
        });

        descartar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tablaPrescripciones.getSelectedRow();
                if (row >= 0) {
                    controller.borrarPrescripcion(row);
                    descartar.setEnabled(false);
                    detalles.setEnabled(false);
                }
            }
        });

        detalles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tablaPrescripciones.getSelectedRow();
                if (row >= 0) {
                    controller.seleccionarPrescripcionDetalle(row);
                    descartar.setEnabled(false);
                    detalles.setEnabled(false);
                }
            }
        });
        limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
            }
        });

        fechaRetiro.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent event) {
                controller.actualizarFecha(event.getNewDate());
            }
        });

        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validar()){
                    try {
                        controller.create();
                        JOptionPane.showMessageDialog(panel, "RECETA APLICADO", "Registro", JOptionPane.INFORMATION_MESSAGE);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }


    public void setController(Controller_Prescribir controller) {
        this.controller = controller;
        buscarPacienteView.setController(controller);
        buscarMedicamentoView.setController(controller);
        guardarMedicamentoView.setController(controller);
    }

    public void setModel(Model_Prescribir model) {
        this.model = model;
        model.addPropertyChangeListener(this);

        buscarMedicamentoView.setModel(model);
        model.addPropertyChangeListener(buscarMedicamentoView);

        buscarPacienteView.setModel(model);
        model.addPropertyChangeListener(buscarPacienteView);

        guardarMedicamentoView.setModel(model);
        model.addPropertyChangeListener(guardarMedicamentoView);


    }
    public void abrirGuardarMedicamento() {
        guardarMedicamentoView.setParaDetalle(false);
        guardarMedicamentoView.setLocationRelativeTo(panel);
        guardarMedicamentoView.setVisible(true);
    }
    public void abrirDetalle(int row) {
        guardarMedicamentoView.setParaDetalle(true);
        guardarMedicamentoView.setRowDetalle(row);
        guardarMedicamentoView.setLocationRelativeTo(panel);
        guardarMedicamentoView.setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model_Prescribir.PRESCRIPCIONES:
                int[] cols = {TableModel.MEDICAMENTO, TableModel.PRESENTACION, TableModel.INDICACION, TableModel.CANTIDAD, TableModel.DURACION};
                tablaPrescripciones.setModel(new TableModel(cols, model.getPrescripciones()));
                break;
            case Model_Prescribir.CURRENT:
                if (model.getCurrentReceta().getPaciente() == null) {
                    nombrePaciente.setText("No hay paciente");
                }else{
                    nombrePaciente.setText(model.getCurrentReceta().getPaciente().getNombre());
                }
                if(model.getCurrentReceta().getFechaDeRetiro() == null) {
                    fechaRetiro.clear();
                }
                int[] col = {TableModel.MEDICAMENTO, TableModel.PRESENTACION, TableModel.INDICACION, TableModel.CANTIDAD, TableModel.DURACION};
                tablaPrescripciones.setModel(new TableModel(col, model.getCurrentReceta().getPrescripciones()));


        }

    }
    private boolean validar() {
        boolean valid = true;
        if (model.getCurrentReceta().getPaciente() == null) {
            valid = false;
            buscarPacienteBtn.setBackground(Color.RED);
            buscarPacienteBtn.setToolTipText("paciente requerido");
        } else {
            buscarPacienteBtn.setBackground(null);
            buscarPacienteBtn.setToolTipText(null);
        }
        if (model.getPrescripciones().isEmpty()) {
            valid = false;
            agregarMedicamentoBtn.setBackground(Color.RED);
            agregarMedicamentoBtn.setToolTipText("medicamento requerido");
        } else {
            agregarMedicamentoBtn.setBackground(null);
            agregarMedicamentoBtn.setToolTipText(null);
        }
        if (fechaRetiro.getDate()==null) {
            valid = false;
            fechaRetiro.setBackground(Color.RED);
            fechaRetiro.setToolTipText("fecha requerida");

        } else {
            fechaRetiro.setBackground(null);
            fechaRetiro.setToolTipText(null);

        }

        return valid;
    }
}
