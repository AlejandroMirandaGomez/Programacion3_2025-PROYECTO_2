package PROYECTO_2.presentation.prescribir.buscarPaciente;

import PROYECTO_2.presentation.prescribir.Controller_Prescribir;
import PROYECTO_2.presentation.prescribir.Model_Prescribir;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View_BuscarPaciente extends JDialog  implements PropertyChangeListener {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField busqueda;
    private JTable tablaPacientes;
    private JComboBox tipo;

    private Model_Prescribir model;
    private Controller_Prescribir controller;

    public View_BuscarPaciente() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setLocationRelativeTo(null);
        setTitle("Buscar pacientes");
        setSize(400, 250);

        busqueda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto=busqueda.getText();
                if (texto.isEmpty()) {
                    controller.getPacientes();
                }else{
                    String tipoElegido = tipo.getSelectedItem().toString();
                    controller.filtrarPacientes(tipoElegido, texto);
                }

            }
        });

        tablaPacientes.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPacientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tablaPacientes.getSelectedRow();
                if (row >= 0) {

                    controller.setPaciente(row);
                }

            }
        });

        buttonOK.addActionListener(e -> {
            int row = tablaPacientes.getSelectedRow();
            if (row >= 0) {
                controller.setPaciente(row);
            }
            dispose();
        });

        buttonCancel.addActionListener(e -> dispose());
    }
    public void setController(Controller_Prescribir controller) {
        this.controller = controller;
    }

    public void setModel(Model_Prescribir model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model_Prescribir.PACIENTES:
                int[] cols = {TableModel.ID, TableModel.NOMBRE, TableModel.TELEFONO, TableModel.FECHANACIMIENTO};
                tablaPacientes.setModel(new TableModel(cols, model.getPacientes()));
                break;

        }
    }
}
