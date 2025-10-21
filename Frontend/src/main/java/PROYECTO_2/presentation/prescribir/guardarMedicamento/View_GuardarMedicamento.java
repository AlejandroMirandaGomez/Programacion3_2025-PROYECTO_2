package PROYECTO_2.presentation.prescribir.guardarMedicamento;

import PROYECTO_2.presentation.prescribir.Controller_Prescribir;
import PROYECTO_2.presentation.prescribir.Model_Prescribir;
import pos.logic.Prescripcion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View_GuardarMedicamento extends JDialog  implements PropertyChangeListener {
    private JPanel contentPane;
    private JSpinner cantidad;
    private JSpinner duracion;
    private JTextArea instrucciones;
    private JButton guardar;
    private JButton cancelar;
    private JLabel cantidadTxt;
    private JLabel duracionTxt;
    private JLabel instruccionesTxt;
    private JButton buttonOK;

    boolean paraDetalle;
    int rowDetalle;



    private Model_Prescribir model;
    private Controller_Prescribir controller;

    public View_GuardarMedicamento() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setLocationRelativeTo(null);
        setTitle("Detalles medicamento");
        setSize(400, 250);

        paraDetalle=false;
        rowDetalle=0;

        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(paraDetalle){
                    if(validar()){
                        Prescripcion n = take();
                        controller.actualizarPrescripcion(rowDetalle,n);
                        dispose();
                    }
                }else{
                    if (validar()) {
                        Prescripcion n = take();
                        try {
                            controller.crearPrescripcion(n);

                            JOptionPane.showMessageDialog(null, "REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        dispose();
                    }
                }


            }
        });

        cancelar.addActionListener(e -> dispose());

    }
    public void setController(Controller_Prescribir controller) {
        this.controller = controller;
    }

    public void setModel(Model_Prescribir model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public boolean isParaDetalle() {
        return paraDetalle;
    }

    public void setRowDetalle(int rowDetalle) {
        this.rowDetalle = rowDetalle;
    }

    public void setParaDetalle(boolean paraDetalle) {
        this.paraDetalle = paraDetalle;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model_Prescribir.CURRENTDETALLE:
                    cantidad.setValue(model.getCurrentDetalle().getCantidad());
                    duracion.setValue(model.getCurrentDetalle().getDuracion());
                    instrucciones.setText(model.getCurrentDetalle().getIndicaciones());
                break;

        }
    }


    public Prescripcion take(){
        Prescripcion prescripcion = new Prescripcion();
        prescripcion.setMedicamento(model.getCurrentDetalle().getMedicamento());
        prescripcion.setCantidad((Integer)cantidad.getValue());
        prescripcion.setDuracion((Integer)duracion.getValue());
        prescripcion.setIndicaciones((String)instrucciones.getText());
        prescripcion.setReceta(model.getCurrentReceta());
        return prescripcion;
    }

    private boolean validar() {
        boolean valid = true;
        if ((Integer)cantidad.getValue()<=0) {
            valid = false;
            cantidadTxt.setBackground(Color.RED);
            cantidadTxt.setToolTipText("cantidad requerida");
        } else {
            cantidadTxt.setBackground(null);
            cantidadTxt.setToolTipText(null);
        }
        if ((Integer)duracion.getValue()<=0) {
            valid = false;
            duracionTxt.setBackground(Color.RED);
            duracionTxt.setToolTipText("cantidad requerida");
        } else {
            duracionTxt.setBackground(null);
            duracionTxt.setToolTipText(null);
        }
        if (instrucciones.getText().isEmpty()) {
            valid = false;
            instrucciones.setBackground(Color.RED);
            instrucciones.setToolTipText("cantidad requerida");

            instruccionesTxt.setBackground(Color.RED);
            instruccionesTxt.setToolTipText("cantidad requerida");
        } else {
            instrucciones.setBackground(null);
            instrucciones.setToolTipText(null);

            instruccionesTxt.setBackground(null);
            instruccionesTxt.setToolTipText(null);
        }

        return valid;
    }
}
