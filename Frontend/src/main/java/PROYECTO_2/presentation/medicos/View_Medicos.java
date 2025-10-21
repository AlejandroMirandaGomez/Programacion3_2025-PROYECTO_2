package PROYECTO_2.presentation.medicos;

import pos.logic.Medico;
import pos.logic.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

public class View_Medicos implements PropertyChangeListener{
    private JPanel panel;
    private JLabel IdLab;
    private JTextField IdFld;
    private JTextField NombreFld;
    private JLabel NombreLab;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton borrarButton;
    private JLabel EspecialidadLab;
    private JTextField EspecialidadFld;
    private JLabel FiltrarLab;
    private JButton buscarButton;
    private JTextField FiltrarFld;
    private JTable medicos;
    private JComboBox filtrar;
    private JButton limpiarBusqueda;
    private JPanel Listado;

    public View_Medicos(){


        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validate()){
                    Medico n = take();
                    Usuario user = takeUser();
                    try{
                        if(Objects.equals(model.getCurrent().getId(), "")){
                            controller.createUser(user);
                            controller.create(n);
                            JOptionPane.showMessageDialog(panel, "REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                        } else{
                            controller.edit(model.getCurrent(), n);
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

        medicos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int viewRow = medicos.getSelectedRow();
                if (viewRow != -1) {
                    int modelRow = medicos.convertRowIndexToModel(viewRow);
                    Medico n = model.getMedicos().get(modelRow);
                    controller.selectCurrent(n);
                }
            }
        });

        FiltrarFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = FiltrarFld.getText();
                if (texto.isEmpty()) {
                    controller.getMedicos();
                }else{
                    String tipoFiltrado = filtrar.getSelectedItem().toString();
                    controller.filtrarMedicos(tipoFiltrado, texto);
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
                controller.getMedicos();
            }
        });

        medicos.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }


    public JPanel getPanel() {
        return panel;
    }

    Controller_Medicos controller;
    Model_Medicos model;

    public void setController(Controller_Medicos controller) {
        this.controller = controller;
    }

    public void setModel(Model_Medicos model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model_Medicos.MEDICOS:
                int [] cols = {TableModel.ID, TableModel.NOMBRE, TableModel.ESPECIALIDAD};
                medicos.setModel(new TableModel(cols, model.getMedicos()));
                break;
            case Model_Medicos.CURRENT:
                IdFld.setText(model.getCurrent().getId());
                NombreFld.setText(model.getCurrent().getNombre());
                EspecialidadFld.setText(model.getCurrent().getEspecialidad());

                if(model.getCurrent().getId().isEmpty()){
                    IdFld.setEnabled(true);
                }else {
                    IdFld.setEnabled(false);
                }

                IdFld.setBackground(null);
                IdFld.setToolTipText(null);
                NombreFld.setBackground(null);
                NombreFld.setToolTipText(null);
                EspecialidadFld.setBackground(null);

        }
        this.panel.revalidate();
    }

    public Medico take(){
        Medico e = new Medico();
        e.setId(this.IdFld.getText());
        e.setNombre(this.NombreFld.getText());
        e.setEspecialidad(this.EspecialidadFld.getText());



        return e;

    }

    public Usuario takeUser(){
        Usuario user = new Usuario();
        user.setId(this.IdFld.getText());
        user.setPassword(this.IdFld.getText());
        user.setRol("MED");

        return user;

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
        if (this.EspecialidadFld.getText().isEmpty()){
            valid = false;
            EspecialidadFld.setBackground(Color.RED);
        }  else{
            EspecialidadFld.setBackground(null);
        }
        return valid;
    }

}
