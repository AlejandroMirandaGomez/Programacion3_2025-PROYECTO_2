package PROYECTO_2.presentation.login.changePassword;

import PROYECTO_2.presentation.login.Controller_Login;
import PROYECTO_2.presentation.login.Model_Login;
import pos.logic.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View_ChangePassword extends JDialog{
    private JPanel panel;
    private JTextField usuarioFld;
    private JPasswordField passwordFld;
    private JPasswordField nuevaFld;
    private JPasswordField nuevaOtraFld;
    private JButton aceptarButton;
    private JButton cancelarButton;


    private Model_Login model;
    private Controller_Login  controller;

    public View_ChangePassword() {
        setContentPane(panel);
        setModal(true);
        getRootPane().setDefaultButton(aceptarButton);

        setLocationRelativeTo(null);
        setTitle("Change Password");
        setSize(400, 250);


        aceptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validar()){
                    Usuario usuario = take();
                    try {
                        controller.changePassword(usuario, nuevaFld.getText());

                        JOptionPane.showMessageDialog(null, "REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    dispose();
                }
            }
        });

        cancelarButton.addActionListener(e -> dispose());
    }

    public void setController(Controller_Login controller) {
        this.controller = controller;
    }

    public void setModel(Model_Login model) {
        this.model = model;
    }

    public Usuario take(){
        Usuario usuario = new Usuario();
        usuario.setId(usuarioFld.getText());
        usuario.setPassword(passwordFld.getText());

        return usuario;
    }

    private boolean validar(){
        boolean valid = true;

        if(usuarioFld.getText().equals("")){
            valid = false;
            usuarioFld.setBackground(Color.RED);
            usuarioFld.setToolTipText("Usuario requerido");

        }else{
            usuarioFld.setBackground(null);
            usuarioFld.setToolTipText(null);
        }
        if(passwordFld.getText().equals("")){
            valid = false;
            passwordFld.setBackground(Color.RED);
            passwordFld.setToolTipText("Contrasena requerida");
        }else{
            passwordFld.setBackground(null);
            passwordFld.setToolTipText(null);
        }
        if(nuevaFld.getText().equals("")){
            valid = false;
            nuevaFld.setBackground(Color.RED);
        }else {
            nuevaFld.setBackground(null);
        }
        if(nuevaOtraFld.getText().equals("")){
            valid = false;
            nuevaOtraFld.setBackground(Color.RED);
        }else{
            nuevaOtraFld.setBackground(null);
        }
        if(!nuevaFld.getText().equals(nuevaOtraFld.getText())){
            nuevaFld.setBackground(Color.RED);
            nuevaOtraFld.setBackground(Color.RED);
            valid = false;
            JOptionPane.showMessageDialog(panel, "Las contrasenas deben ser iguales", "Change Password", JOptionPane.ERROR_MESSAGE);
        }


        return valid;
    }

}
