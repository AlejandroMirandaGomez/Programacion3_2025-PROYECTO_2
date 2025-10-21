package PROYECTO_2.presentation.farmaceutas;

import pos.logic.Farmaceuta;
import pos.logic.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

public class View_Farmaceutas implements PropertyChangeListener {
    private JPanel panel;
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
    private JTable farmaceutas;
    private JPanel Farmaceutas;


    public View_Farmaceutas(){


        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validate()){
                    Farmaceuta n = take();
                    Usuario user = takeUser();
                    try{
                        if(Objects.equals(model.getCurrent().getId(), "")){
                            controller.createUser(user);
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

        farmaceutas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int viewRow = farmaceutas.getSelectedRow();
                if (viewRow != -1) {
                    int modelRow = farmaceutas.convertRowIndexToModel(viewRow);
                    Farmaceuta n = model.getFarmaceutas().get(modelRow);
                    controller.selectCurrent(n);
                }
            }
        });

        FiltrarFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = FiltrarFld.getText();
                if (texto.isEmpty()) {
                    controller.getFarmaceutas();
                }else{
                    String tipoFiltrado = filtrar.getSelectedItem().toString();
                    controller.filtrarFarmaceutas(tipoFiltrado, texto);
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
                controller.getFarmaceutas();
            }
        });

        farmaceutas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }


    public JPanel getPanel() {
        return panel;
    }

    Controller_Farmaceutas controller;
    Model_Farmaceutas model;

    public void setController(Controller_Farmaceutas controller) {
        this.controller = controller;
    }

    public void setModel(Model_Farmaceutas model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model_Farmaceutas.FARMACEUTAS:
                int [] cols = {TableModel.ID, TableModel.NOMBRE};
                farmaceutas.setModel(new TableModel(cols, model.getFarmaceutas()));
                break;
            case Model_Farmaceutas.CURRENT:
                IdFld.setText(model.getCurrent().getId());
                NombreFld.setText(model.getCurrent().getNombre());

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

    public Farmaceuta take(){
        Farmaceuta e = new Farmaceuta();
        e.setId(this.IdFld.getText());
        e.setNombre(this.NombreFld.getText());

        return e;

    }

    public Usuario takeUser(){
        Usuario user = new Usuario();
        user.setId(this.IdFld.getText());
        user.setPassword(this.IdFld.getText());
        user.setRol("FAR");

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
        return valid;
    }

}
