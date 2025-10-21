package PROYECTO_2.presentation.pacientes;

import com.github.lgooddatepicker.components.DatePicker;
import PROYECTO_2.presentation.prescribir.buscarPaciente.TableModel;
import pos.logic.Paciente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

public class View_Pacientes implements PropertyChangeListener {


    private JPanel panel;
    private JPanel Farmaceutas;
    private JLabel IdLab;
    private JTextField IdFld;
    private JTextField NombreFld;
    private JButton limpiarButton;
    private JButton guardarButton;
    private JButton borrarButton;
    private JLabel NombreLab;
    private JLabel FiltrarLab;
    private JButton buscarButton;
    private JTextField FiltrarFld;
    private JComboBox filtrar;
    private JButton limpiarBusqueda;
    private JTable pacientes;
    private DatePicker datePicker;
    private JTextField telefonoFld;


    public View_Pacientes(){
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validate()){
                    Paciente n = take();
                    try{
                        if(Objects.equals(model.getCurrent().getId(), "")){
                            controller.create(n);
                            JOptionPane.showMessageDialog(panel, "REGISTRO APLICADO", "Registro", JOptionPane.INFORMATION_MESSAGE);
                        } else{
                            controller.edit(n);
                            FiltrarFld.postActionEvent();
                            JOptionPane.showMessageDialog(panel, "EDICION APLICADA", "Registro", JOptionPane.INFORMATION_MESSAGE);
                        }

                    }catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
            }
        });

        pacientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int viewRow = pacientes.getSelectedRow();
                if (viewRow != -1) {
                    int modelRow = pacientes.convertRowIndexToModel(viewRow);
                    Paciente n = model.getPacientes().get(modelRow);
                    controller.selectCurrent(n);
                }
            }
        });

        FiltrarFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = FiltrarFld.getText();
                if (texto.isEmpty()) {
                    controller.getPacientes();
                }else{
                    String tipoFiltrado = filtrar.getSelectedItem().toString();
                    controller.filtrarPacientes(tipoFiltrado, texto);
                }
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int respuesta = JOptionPane.showConfirmDialog(
                        panel,
                        "¿Está seguro de que desea eliminar este elemento?",
                        "Confirmar borrado",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (respuesta == JOptionPane.YES_OPTION) {
                    try {
                        controller.remove(model.getCurrent());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(
                                panel,
                                ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FiltrarFld.postActionEvent();
            }
        });
        limpiarBusqueda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FiltrarFld.setText("");
                controller.getPacientes();
            }
        });

        pacientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }


    public JPanel getPanel() {
        return panel;
    }

    Controller_Pacientes controller;
    Model_Pacientes model;

    public void setController(Controller_Pacientes controller) {
        this.controller = controller;
    }

    public void setModel(Model_Pacientes model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model_Pacientes.PACIENTES:
                int [] cols = {TableModel.ID, TableModel.NOMBRE, TableModel.TELEFONO, TableModel.FECHANACIMIENTO};
                pacientes.setModel(new TableModel(cols, model.getPacientes()));
                break;
            case Model_Pacientes.CURRENT:
                IdFld.setText(model.getCurrent().getId());
                NombreFld.setText(model.getCurrent().getNombre());
                datePicker.setDate(model.getCurrent().getFechaNacimiento());
                telefonoFld.setText(model.getCurrent().getTelefono());

                if(model.getCurrent().getId().isEmpty()){
                    IdFld.setEnabled(true);
                }else {
                    IdFld.setEnabled(false);
                }

                IdFld.setBackground(null);
                IdFld.setToolTipText(null);
                NombreFld.setBackground(null);
                NombreFld.setToolTipText(null);

        }
        this.panel.revalidate();
    }

    public Paciente take(){
        Paciente e = new Paciente();
        e.setId(this.IdFld.getText());
        e.setNombre(this.NombreFld.getText());
        e.setFechaNacimiento(this.datePicker.getDate());
        e.setTelefono(this.telefonoFld.getText());

        return e;

    }

    private boolean validate(){
        boolean valid = true;
        if (this.IdFld.getText().isEmpty()){
            valid = false;
            IdFld.setBackground(Color.RED);
            IdFld.setToolTipText("Id requerido");
        } else{
            IdFld.setBackground(null);
            IdFld.setToolTipText(null);
        }
        if (this.NombreFld.getText().isEmpty()){
            valid = false;
            NombreFld.setBackground(Color.RED);
            NombreFld.setToolTipText("Nombre requerido");
        }  else{
            NombreFld.setBackground(null);
            NombreFld.setToolTipText(null);
        }
        return valid;
    }

}



