package PROYECTO_2.presentation.login;

import PROYECTO_2.presentation.login.changePassword.View_ChangePassword;
import pos.logic.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class View_Login extends JDialog implements PropertyChangeListener{
    private JPanel panel;
    private JTextField idFld;
    private JTextField claveFld;
    private JLabel idLab;
    private JLabel claveLab;
    private JButton LoginButton;
    private JButton loginButton;
    private JButton claveButton;

    private boolean authenticated = false;

    private View_ChangePassword view;

    public View_Login() {
        setContentPane(panel);
        setModal(true);

        setLocationRelativeTo(null);
        setTitle("Login");
        setSize(600, 300);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        view = new View_ChangePassword();

        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Usuario usuario = take();
                    controller.login(usuario);
                    model.setCurrent(usuario);
                    authenticated = true;
                    dispose();
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idFld.setText("");
                claveFld.setText("");
            }
        });
        claveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.setVisible(true);
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    //MVC
    Controller_Login controller;
    Model_Login model;

    public void setController(Controller_Login controller) {

        this.controller = controller;
        view.setController(controller);
    }
    public void setModel(Model_Login model) {
        this.model = model;
        view.setModel(model);
        model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model_Login.CURRENT:
                //JOptionPane.showMessageDialog(panel, "Loggeado con exito", "Informacion", JOptionPane.PLAIN_MESSAGE);
                break;
        }
    }

    public Usuario take(){
        Usuario usuario = new Usuario();
        usuario.setId(this.idFld.getText());
        usuario.setPassword(this.claveFld.getText());
        return usuario;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }


}
