package PROYECTO_2.presentation.historico.verReceta;

import PROYECTO_2.presentation.historico.Controller_Historico;
import PROYECTO_2.presentation.historico.Model_Historico;

import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View_VerReceta extends JDialog  implements PropertyChangeListener {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel fechaRetiro;
    private JLabel nombreMedico;
    private JLabel nombrePaciente;
    private JLabel idPaciente;
    private JPanel datos;
    private JPanel Prescripciones;
    private JTable tablaPrescripciones;

    public View_VerReceta() {
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


    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    // MVC:

    private Model_Historico model;
    private Controller_Historico controller;

    public void setController(Controller_Historico controller) {
        this.controller = controller;
    }

    public void setModel(Model_Historico model) {
        this.model = model;
        //model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model_Historico.CURRENT_RECETA:

                fechaRetiro.setText(model.getCurrentReceta().getFechaDeRetiro().toString());
                nombreMedico.setText(model.getCurrentReceta().getMedico().getNombre());
                nombrePaciente.setText(model.getCurrentReceta().getPaciente().getNombre());
                idPaciente.setText(model.getCurrentReceta().getPaciente().getId());
                int[] cols = {PROYECTO_2.presentation.despacho.editarEstado.TableModel.MEDICAMENTO, PROYECTO_2.presentation.despacho.editarEstado.TableModel.PRESENTACION, PROYECTO_2.presentation.despacho.editarEstado.TableModel.INDICACION, PROYECTO_2.presentation.despacho.editarEstado.TableModel.CANTIDAD, PROYECTO_2.presentation.despacho.editarEstado.TableModel.DURACION};
                tablaPrescripciones.setModel(new TableModel(cols, model.getCurrentReceta().getPrescripciones()));
                break;
        }
        this.contentPane.revalidate();
    }
}


