package PROYECTO_2.presentation.despacho.editarEstado;

import PROYECTO_2.presentation.despacho.Controller_Despacho;
import PROYECTO_2.presentation.despacho.Model_Despacho;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class View_EditarEstado extends JDialog  implements PropertyChangeListener {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox seleccionEstado;
    private JLabel fechaRetiro;
    private JLabel nombreMedico;
    private JLabel nombrePaciente;
    private JLabel idPaciente;
    private JPanel datos;
    private JPanel Prescripciones;
    private JTable tablaPrescripciones;
    private JLabel msjAdvertencia;

    public View_EditarEstado() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);
        setTitle("Editar Estado de Receta");
        setSize(700,500);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        seleccionEstado.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String newEstado = seleccionEstado.getSelectedItem().toString();
                controller.setEstado_of_CurrentReceta(newEstado);
            }
        });
    }

    private void onOK() {
        // add your code here
        try {
            controller.saveState();
        }catch (Exception ex){
            JOptionPane.showMessageDialog(contentPane, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        controller.getRecetas();
        dispose();
    }

    // MVC:

    private Model_Despacho model;
    private Controller_Despacho controller;

    public void setController(Controller_Despacho controller) {
        this.controller = controller;
    }

    public void setModel(Model_Despacho model) {
        this.model = model;
        //model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model_Despacho.CURRENT_RECETA:
                fechaRetiro.setText(model.getCurrentReceta().getFechaDeRetiro().toString());
                nombreMedico.setText(model.getCurrentReceta().getMedico().getNombre());
                nombrePaciente.setText(model.getCurrentReceta().getPaciente().getNombre());
                idPaciente.setText(model.getCurrentReceta().getPaciente().getId());
                seleccionEstado.setSelectedItem(model.getCurrentReceta().getEstado());
                int[] cols = {TableModel.MEDICAMENTO, TableModel.PRESENTACION, TableModel.INDICACION, TableModel.CANTIDAD, TableModel.DURACION};
                tablaPrescripciones.setModel(new TableModel(cols, model.getCurrentReceta().getPrescripciones()));
                LocalDate fechaRetiro = model.getCurrentReceta().getFechaDeRetiro();
                LocalDate hoy = LocalDate.now();
                long diferencia = ChronoUnit.DAYS.between(hoy, fechaRetiro);

                //Validacion:
                if (Objects.equals(model.getCurrentReceta().getEstado(), "Confeccionada")
                        && (diferencia < -3 || diferencia > 3)
                ) {
                    seleccionEstado.setEnabled(false);
                    msjAdvertencia.setText("← La fecha de retiro NO está dentro de +/- 3 días desde hoy");
                    msjAdvertencia.setForeground(Color.RED);
                } else{
                    seleccionEstado.setEnabled(true);
                    msjAdvertencia.setText("← Puedes modificar la receta");
                    msjAdvertencia.setForeground(Color.decode("#00731E"));
                }
                break;
        }
        this.contentPane.revalidate();
    }
}


