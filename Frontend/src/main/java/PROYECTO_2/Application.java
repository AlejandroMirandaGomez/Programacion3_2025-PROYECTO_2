package PROYECTO_2;

import PROYECTO_2.logic.Service;
import PROYECTO_2.presentation.acercaDe.Controller_AcercaDe;
import PROYECTO_2.presentation.acercaDe.Model_AcercaDe;
import PROYECTO_2.presentation.acercaDe.View_AcercaDe;
import PROYECTO_2.presentation.dashboard.Controller_Dashboard;
import PROYECTO_2.presentation.dashboard.Model_Dashboard;
import PROYECTO_2.presentation.dashboard.View_Dashboard;
import PROYECTO_2.presentation.despacho.Controller_Despacho;
import PROYECTO_2.presentation.despacho.Model_Despacho;
import PROYECTO_2.presentation.despacho.View_Despacho;
import PROYECTO_2.presentation.farmaceutas.Controller_Farmaceutas;
import PROYECTO_2.presentation.farmaceutas.Model_Farmaceutas;
import PROYECTO_2.presentation.farmaceutas.View_Farmaceutas;
import PROYECTO_2.presentation.historico.Controller_Historico;
import PROYECTO_2.presentation.historico.Model_Historico;
import PROYECTO_2.presentation.historico.View_Historico;
import PROYECTO_2.presentation.login.Controller_Login;
import PROYECTO_2.presentation.login.Model_Login;
import PROYECTO_2.presentation.login.View_Login;
import PROYECTO_2.presentation.medicamentos.Controller_Medicamentos;
import PROYECTO_2.presentation.medicamentos.Model_Medicamentos;
import PROYECTO_2.presentation.medicamentos.View_Medicamentos;
import PROYECTO_2.presentation.medicos.Controller_Medicos;
import PROYECTO_2.presentation.medicos.Model_Medicos;
import PROYECTO_2.presentation.medicos.View_Medicos;
import PROYECTO_2.presentation.pacientes.Controller_Pacientes;
import PROYECTO_2.presentation.pacientes.Model_Pacientes;
import PROYECTO_2.presentation.pacientes.View_Pacientes;
import PROYECTO_2.presentation.prescribir.Controller_Prescribir;
import PROYECTO_2.presentation.prescribir.Model_Prescribir;
import PROYECTO_2.presentation.prescribir.View_Prescribir;
import PROYECTO_2.presentation.usuarios.View_Usuarios;
import pos.logic.Sesion;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Application {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");}
        catch (Exception ex) {};

        doLogin();
        if(Sesion.isLoggedIn()){
            doRun();
        }

    }
    private static void doLogin(){
        // Login MVC:
        View_Login view = new View_Login();
        view.setTitle("LOGIN");
        view.pack();
        view.setLocationRelativeTo(null);
        Model_Login model = new Model_Login();
        Controller_Login controller = new Controller_Login(view, model);
        view.setVisible(true);

        if (!view.isAuthenticated()) {
            System.exit(0);
        }
    }
    private static void doRun(){
        // AcercaDe MVC:
        View_AcercaDe view_acercaDe = new View_AcercaDe();
        Model_AcercaDe model_acercaDe = new Model_AcercaDe();
        Controller_AcercaDe controller_acercaDe = new Controller_AcercaDe(view_acercaDe, model_acercaDe);
        // *************************************************************************************************************

        //Prescribir MVC:
        View_Prescribir view_prescribir = new View_Prescribir();
        Model_Prescribir  model_prescribir = new Model_Prescribir();
        Controller_Prescribir controller_Prescribir = new Controller_Prescribir(view_prescribir, model_prescribir);
        // *************************************************************************************************************

        //Medicos MVC
        View_Medicos view_medicos = new View_Medicos();
        Model_Medicos model_medicos = new Model_Medicos();
        Controller_Medicos controller_medicos = new Controller_Medicos(view_medicos, model_medicos);
        // *************************************************************************************************************

        //Despacho MVC:
        View_Despacho view_despacho = new View_Despacho();
        Model_Despacho model_despacho = new Model_Despacho();
        Controller_Despacho controller_despacho = new Controller_Despacho(view_despacho, model_despacho);
        // *************************************************************************************************************

        //Farmaceutas MVC
        View_Farmaceutas view_farmaceutas = new View_Farmaceutas();
        Model_Farmaceutas model_farmaceutas = new Model_Farmaceutas();
        Controller_Farmaceutas controller_farmaceutas = new Controller_Farmaceutas(view_farmaceutas, model_farmaceutas);
        // *************************************************************************************************************

        //Pacientes MVC
        View_Pacientes view_pacientes = new View_Pacientes();
        Model_Pacientes model_pacientes = new Model_Pacientes();
        Controller_Pacientes controller_pacientes = new Controller_Pacientes(view_pacientes, model_pacientes);
        // *************************************************************************************************************

        //Medicamentos MVC
        View_Medicamentos view_medicamentos = new View_Medicamentos();
        Model_Medicamentos model_medicamentos = new Model_Medicamentos();
        Controller_Medicamentos controller_medicamentos = new Controller_Medicamentos(view_medicamentos, model_medicamentos);
        // *************************************************************************************************************

        //Historico MVC
        View_Historico view_historico = new View_Historico();
        Model_Historico model_historico = new Model_Historico();
        Controller_Historico controller_historico = new Controller_Historico(view_historico, model_historico);

        //Dashboard MVC
        View_Dashboard view_dashboard = new View_Dashboard();
        Model_Dashboard model_dashboard = new Model_Dashboard();
        Controller_Dashboard controller_dashboard = new Controller_Dashboard(view_dashboard, model_dashboard);

        //Usuarios MVC
        View_Usuarios view_usuarios = new View_Usuarios();

        // *************************************************************************************************************

        JFrame window = new JFrame();
        JTabbedPane tabbedPane = new JTabbedPane();


        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                tabbedPane,
                view_usuarios.getPanel()
        );
        //Con esta forma se puede mover el tamaño de la ventana con el cursor
        /*
        splitPane.setDividerLocation(1000);
        splitPane.setOneTouchExpandable(false);
        splitPane.setResizeWeight(0.75);
        */
        //Con esta forma no se puede mover el tamaño de la ventana con el cursor
        splitPane.setDividerLocation(950);
        splitPane.setEnabled(false);
        splitPane.setDividerSize(1);
        splitPane.setResizeWeight(1.0);

        window.setSize(1380,720);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("ALL TABS");
        window.setContentPane(splitPane);
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);

        window.setIconImage(new ImageIcon(Application.class.getResource("/Icons/edificioHospitalBlanco.png")).getImage());

        window.setTitle("Hospital - " + Sesion.getUsuario().getId() + " (" + Sesion.getUsuario().getRol() + ")");

        switch(Sesion.getUsuario().getRol()){
            case "ADM":
                tabbedPane.addTab("Medicos", new ImageIcon(Application.class.getResource("/Icons/doctor.png")),view_medicos.getPanel());
                tabbedPane.addTab("Farmaceutas",new ImageIcon(Application.class.getResource("/Icons/farmaceutico.png")), view_farmaceutas.getPanel());
                tabbedPane.addTab("Pacientes", new ImageIcon(Application.class.getResource("/Icons/paciente.png")), view_pacientes.getPanel());
                tabbedPane.addTab("Medicamentos",new ImageIcon(Application.class.getResource("/Icons/pastillas.png")), view_medicamentos.getPanel());
                tabbedPane.addTab("Historico", new ImageIcon(Application.class.getResource("/Icons/historico.png")), view_historico.getPanel());
                tabbedPane.addChangeListener(e->{
                    if(tabbedPane.getSelectedComponent() == view_historico.getPanel()) {
                        SwingUtilities.invokeLater(() -> {
                            controller_historico.getRecetas();
                        });
                    }
                });
                tabbedPane.addTab("Dashboard", new ImageIcon(Application.class.getResource("/Icons/grafico.png")),view_dashboard.getPanel());

                tabbedPane.addChangeListener(e->{
                    if(tabbedPane.getSelectedComponent() == view_dashboard.getPanel()) {
                        SwingUtilities.invokeLater(() -> {
                            controller_dashboard.getAllRecetas();
                        });
                    }
                });

                tabbedPane.addTab("Acerca De",new ImageIcon(Application.class.getResource("/Icons/acerca de.png")), view_acercaDe.getPanel());

                break;
            case "MED":
                tabbedPane.addTab("Prescribir",new ImageIcon(Application.class.getResource("/Icons/Prescribir.png")), view_prescribir.getPanel());
                tabbedPane.addChangeListener(e->{
                    if(tabbedPane.getSelectedComponent() == view_prescribir.getPanel()) {
                        SwingUtilities.invokeLater(() -> {
                            controller_Prescribir.getPacientes();
                        });
                    }
                });
                tabbedPane.addTab("Dashboard", new ImageIcon(Application.class.getResource("/Icons/grafico.png")),view_dashboard.getPanel());

                tabbedPane.addChangeListener(e->{
                    if(tabbedPane.getSelectedComponent() == view_dashboard.getPanel()) {
                        SwingUtilities.invokeLater(() -> {
                            controller_dashboard.getAllRecetas();
                        });
                    }
                });
                tabbedPane.addTab("Historico",new ImageIcon(Application.class.getResource("/Icons/historico.png")), view_historico.getPanel());
                tabbedPane.addChangeListener(e->{
                    if(tabbedPane.getSelectedComponent() == view_historico.getPanel()) {
                        SwingUtilities.invokeLater(() -> {
                            controller_historico.getRecetas();
                        });
                    }
                });
                tabbedPane.addTab("Acerca De",new ImageIcon(Application.class.getResource("/Icons/acerca de.png")), view_acercaDe.getPanel());
                break;
            case "FAR":
                tabbedPane.addTab("Despacho", new ImageIcon(Application.class.getResource("/Icons/Despacho.png")),view_despacho.getPanel());
                tabbedPane.addTab("Dashboard",new ImageIcon(Application.class.getResource("/Icons/grafico.png")), view_dashboard.getPanel());

                tabbedPane.addChangeListener(e->{
                    if(tabbedPane.getSelectedComponent() == view_dashboard.getPanel()) {
                        SwingUtilities.invokeLater(() -> {
                            controller_dashboard.getAllRecetas();
                        });
                    }
                });
                tabbedPane.addTab("Historico",new ImageIcon(Application.class.getResource("/Icons/historico.png")), view_historico.getPanel());
                tabbedPane.addChangeListener(e->{
                    if(tabbedPane.getSelectedComponent() == view_historico.getPanel()) {
                        SwingUtilities.invokeLater(() -> {
                            controller_historico.getRecetas();
                        });
                    }
                });
                tabbedPane.addTab("Acerca De", new ImageIcon(Application.class.getResource("/Icons/acerca de.png")),view_acercaDe.getPanel());

        }

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Service.getInstance().stop();
            }
        });

    }

}
