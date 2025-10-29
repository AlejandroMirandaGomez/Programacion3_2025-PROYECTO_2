package PROYECTO_2.presentation.usuarios.enviarMensaje;

import PROYECTO_2.presentation.prescribir.Controller_Prescribir;
import PROYECTO_2.presentation.prescribir.Model_Prescribir;
import PROYECTO_2.presentation.usuarios.Controller_Usuarios;
import PROYECTO_2.presentation.usuarios.Model_Usuarios;
import PROYECTO_2.presentation.usuarios.TableModel;
import pos.logic.Prescripcion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View_EnviarMensaje extends JDialog implements PropertyChangeListener {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea mensaje;
    private JLabel destino;

    private Controller_Usuarios controller;
    private Model_Usuarios model;

    public View_EnviarMensaje() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


        setLocationRelativeTo(null);
        setTitle("Ventana de mensajes");
        setSize(400, 250);

        ImageIcon icon = new ImageIcon(getClass().getResource("/Icons/comentario.png"));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validar()) {
                    try {
                        controller.enviarMensaje(mensaje.getText());
                        mensaje.setText("");

                        JOptionPane.showMessageDialog(null, "MENSAJE ENVIADO", "", JOptionPane.INFORMATION_MESSAGE, icon);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    onOK();
                }

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
        this.setVisible(false);
    }

    private void onCancel() {
        // add your code here if necessary
        this.setVisible(false);
    }

    public void setController(Controller_Usuarios controller) {
        this.controller = controller;
    }

    public void setModel(Model_Usuarios model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model_Usuarios.SELECCIONADO:
                destino.setText(model.getUsuarioSeleccionado().getUsuario().getId());

                break;
        }
    }
    private boolean validar() {
        boolean valid = true;
        if (mensaje.getText() == null || "".equals(mensaje.getText())) {
            valid = false;
            mensaje.setBackground(Color.RED);
            mensaje.setToolTipText("Texto requerido");
        } else {
            mensaje.setBackground(null);
            mensaje.setToolTipText(null);
        }

        return valid;
    }
}
