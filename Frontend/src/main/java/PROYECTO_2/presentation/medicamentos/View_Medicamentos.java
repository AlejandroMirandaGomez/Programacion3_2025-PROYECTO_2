package PROYECTO_2.presentation.medicamentos;

import PROYECTO_2.presentation.prescribir.buscarMedicamento.TableModel;
import pos.logic.Medicamento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

public class View_Medicamentos implements PropertyChangeListener {
    private JPanel panel;
    private JPanel Medicamentos;
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
    private JTable medicamentos;
    private JTextField presentacionFld;


    public View_Medicamentos(){


        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validate()){
                    Medicamento n = take();
                    try{
                        if(Objects.equals(model.getCurrent().getCodigo(), "")){
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

        medicamentos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int viewRow = medicamentos.getSelectedRow();
                if (viewRow != -1) {
                    int modelRow = medicamentos.convertRowIndexToModel(viewRow);
                    Medicamento n = model.getMedicamentos().get(modelRow);
                    controller.selectCurrent(n);
                }
            }
        });

        FiltrarFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = FiltrarFld.getText();
                if (texto.isEmpty()) {
                    controller.getMedicamentos();
                }else{
                    String tipoFiltrado = filtrar.getSelectedItem().toString();
                    controller.filtrarMedicamentos(tipoFiltrado, texto);
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
                controller.getMedicamentos();
            }
        });

        medicamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }


    public JPanel getPanel() {
        return panel;
    }

    Controller_Medicamentos controller;
    Model_Medicamentos model;

    public void setController(Controller_Medicamentos controller) {
        this.controller = controller;
    }

    public void setModel(Model_Medicamentos model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model_Medicamentos.MEDICAMENTOS:
                int [] cols = {TableModel.CODIGO, TableModel.NOMBRE, TableModel.PRESENTACION};
                medicamentos.setModel(new TableModel(cols, model.getMedicamentos()));
                break;
            case Model_Medicamentos.CURRENT:
                IdFld.setText(model.getCurrent().getCodigo());
                NombreFld.setText(model.getCurrent().getNombre());
                presentacionFld.setText(model.getCurrent().getPresentacion());

                IdFld.setEnabled(model.getCurrent().getCodigo().isEmpty());

                IdFld.setBackground(null);
                IdFld.setToolTipText(null);
                NombreFld.setBackground(null);
                NombreFld.setToolTipText(null);

        }
        this.panel.revalidate();
    }

    public Medicamento take(){
        Medicamento e = new Medicamento();
        e.setCodigo(this.IdFld.getText());
        e.setNombre(this.NombreFld.getText());
        e.setPresentacion(this.presentacionFld.getText());

        return e;

    }

    private boolean validate(){
        boolean valid = true;
        if (this.IdFld.getText().isEmpty()){
            valid = false;
            IdFld.setBackground(Color.RED);
            IdFld.setToolTipText("Codigo requerido");
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
