package PROYECTO_2.presentation.despacho;

import PROYECTO_2.presentation.despacho.buscarPaciente.View_BuscarPaciente;
import PROYECTO_2.presentation.despacho.editarEstado.View_EditarEstado;
import pos.logic.Paciente;
import pos.logic.Receta;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View_Despacho implements PropertyChangeListener {
    Controller_Despacho controller;
    Model_Despacho model;
    private JPanel panel;
    private JTable tablaListaRecetas;
    private JRadioButton verTodoRadioBtn;
    private JPanel control;
    private JButton pacienteBtn;
    private JButton limpiarBtn;
    private JLabel filtroActualLabel;
    private JComboBox estadoComboBox;

    private View_BuscarPaciente buscarPaciente;
    private View_EditarEstado editarEstado;
    private String filtroBusqueda;

    public View_Despacho() {
        buscarPaciente = new View_BuscarPaciente();
        editarEstado = new View_EditarEstado();

        pacienteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPaciente.setVisible(true);
            }
        });

        tablaListaRecetas.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaListaRecetas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tablaListaRecetas.getSelectedRow();
                if (row >= 0) {
                    Receta r = model.getRecetas().get(row);
                    controller.setCurrentReceta(r);
                    editarEstado.setVisible(true);
                }
            }
        });

        verTodoRadioBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(verTodoRadioBtn.isSelected()) {
                    showAllFunction();
                } else{
                    cleanFunction();
                }
            }
        });

        estadoComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String nuevoEstado = (String) e.getItem();
                controller.setCurrentEstado(nuevoEstado);
            }
        });
        limpiarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cleanFunction();
            }
        });
    }

    private void showAllFunction() {
        if(!verTodoRadioBtn.isSelected()) {
            verTodoRadioBtn.setSelected(true);
        }

        controller.setCurrentPaciente(new Paciente());
        estadoComboBox.setSelectedIndex(0);
        controller.getRecetas();
        filtroActualLabel.setText("Mostrando todas las recetas");
    }

    private void cleanFunction() {
        if(verTodoRadioBtn.isSelected()) {
            verTodoRadioBtn.setSelected(false);
        }

        controller.setCurrentPaciente(new Paciente());
        estadoComboBox.setSelectedIndex(0);
        controller.limpiarRecetas();
        filtroActualLabel.setText("Aún no se han aplicado filtros");
    }

    public void setController(Controller_Despacho controller) {
        this.controller = controller;
        this.buscarPaciente.setController(controller);
        this.editarEstado.setController(controller);
    }
    public void setModel(Model_Despacho model) {
        this.model = model;
        model.addPropertyChangeListener(this);

        this.buscarPaciente.setModel(model);
        model.addPropertyChangeListener(buscarPaciente);

        this.editarEstado.setModel(model);
        model.addPropertyChangeListener(editarEstado);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model_Despacho.RECETAS:
                int[] cols = {TableModel.ID_PACIENTE, TableModel.NOMBRE_PACIENTE, TableModel.FECHA_RETIRO, TableModel.NOMBRE_MEDICO, TableModel.ESTADO};
                tablaListaRecetas.setModel(new TableModel(cols, model.getRecetas()));
                break;
            case Model_Despacho.CURRENT_PACIENTE:
                if(model.getCurrentPaciente() != null && !model.getCurrentPaciente().equals(new Paciente())){
                    filtroBusqueda = "Recetas del paciente: " + model.getCurrentPaciente().getNombre();
                    filtroActualLabel.setText(filtroBusqueda);
                    controller.filtrarRecetas("ID_PACIENTE",  model.getCurrentPaciente().getId());
                    controller.setCurrentEstado(model.getCurrentEstado());
                }
                break;
            case Model_Despacho.CURRENT_ESTADO:
                if (estadoComboBox.getSelectedIndex() == 0 && (model.getCurrentPaciente() == null || model.getCurrentPaciente().equals(new Paciente()))){
                    controller.limpiarRecetas();
                    filtroActualLabel.setText("Aún no se han aplicado filtros");
                } else if(estadoComboBox.getSelectedIndex() == 0){
                    filtroBusqueda = "Recetas del paciente: " + model.getCurrentPaciente().getNombre();
                    filtroActualLabel.setText(filtroBusqueda);
                    controller.filtrarRecetas("ID_PACIENTE",  model.getCurrentPaciente().getId());
                }
                else if(model.getCurrentPaciente() == null || model.getCurrentPaciente().equals(new Paciente())){
                    filtroBusqueda = "Recetas con estado: " + estadoComboBox.getSelectedItem().toString();
                    filtroActualLabel.setText(filtroBusqueda);
                    controller.filtrarRecetas("ESTADO", model.getCurrentEstado());

                } else {
                    filtroBusqueda = "Recetas del paciente: " + model.getCurrentPaciente().getNombre() + " · Estado: " + estadoComboBox.getSelectedItem().toString();
                    filtroActualLabel.setText(filtroBusqueda);
                    controller.filtrarRecetas("ID_PACIENTE_Y_ESTADO",  model.getCurrentPaciente().getId(), model.getCurrentEstado());
                }
                break;
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}

