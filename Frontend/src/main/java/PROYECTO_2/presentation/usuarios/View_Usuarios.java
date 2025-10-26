package PROYECTO_2.presentation.usuarios;

import PROYECTO_2.presentation.usuarios.Controller_Usuarios;
import PROYECTO_2.presentation.usuarios.Model_Usuarios;
import PROYECTO_2.presentation.usuarios.enviarMensaje.View_EnviarMensaje;
import pos.logic.UsuarioChat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View_Usuarios implements PropertyChangeListener {
    private JPanel panel;
    private JButton enviarButton;
    private JButton recibirButton;
    private JTable usuariosTable;

    private View_EnviarMensaje enviarMensajeView;

    Controller_Usuarios controller;
    Model_Usuarios model;

    public View_Usuarios() {
        enviarMensajeView = new View_EnviarMensaje();

        enviarButton.setEnabled(false);
        recibirButton.setEnabled(false);

        ImageIcon icon = new ImageIcon(getClass().getResource("/Icons/comentario.png"));

        usuariosTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usuariosTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = usuariosTable.getSelectedRow();
                if (row >= 0) {
                    enviarButton.setEnabled(true);
                    recibirButton.setEnabled(true);
                    controller.setSeleccionado(row);
                }

            }
        });

        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensajeView.setVisible(true);

            }
        });
        recibirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(null, controller.getMensajes(), "Mensajes" , JOptionPane.INFORMATION_MESSAGE, icon);
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setController(Controller_Usuarios controller) {

        this.controller = controller;
        enviarMensajeView.setController(controller);
    }

    public void setModel(Model_Usuarios model) {
        this.model = model;
        model.addPropertyChangeListener(this);

        enviarMensajeView.setModel(model);
        model.addPropertyChangeListener(enviarMensajeView);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model_Usuarios.USUARIOS:
                int [] cols = {TableModel.ID, TableModel.MENSAJES};
                usuariosTable.setModel(new TableModel(cols, model.getUsuarios()));

                break;
        }
        this.panel.revalidate();
    }
}
