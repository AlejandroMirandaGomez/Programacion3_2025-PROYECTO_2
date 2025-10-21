package PROYECTO_2.presentation.despacho.buscarPaciente;

import PROYECTO_2.presentation.despacho.Controller_Despacho;
import PROYECTO_2.presentation.despacho.Model_Despacho;
import pos.logic.Paciente;

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

    public View_BuscarPaciente() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setLocationRelativeTo(null);
        setTitle("Buscar Paciente");
        setSize(600,400);

        busqueda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto=busqueda.getText();
                if (texto.isEmpty()) {
                    controller.getPacientes();
                } else{
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
                    Paciente p = model.getPacientes().get(row);
                    model.setCurrentaciente(p);
                }
                //dispose(); //Con solo tocar la fila, sale de la ventana y marca el nombre
            }
        });

        buttonOK.addActionListener(e -> {
            int row = tablaPacientes.getSelectedRow();
            if (row >= 0) {
                Paciente p = model.getPacientes().get(row);
                model.setCurrentaciente(p);
            }
            dispose();
        });
        buttonCancel.addActionListener(e -> dispose());
    }

    private Model_Despacho model;
    private Controller_Despacho controller;

    public void setController(Controller_Despacho controller) {
        this.controller = controller;
    }

    public void setModel(Model_Despacho model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model_Despacho.PACIENTES:
                int[] cols = {TableModel.ID, TableModel.NOMBRE, TableModel.TELEFONO, TableModel.FECHANACIMIENTO};
                tablaPacientes.setModel(new TableModel(cols, model.getPacientes()));
                break;
        }
        this.contentPane.revalidate();
    }
}
