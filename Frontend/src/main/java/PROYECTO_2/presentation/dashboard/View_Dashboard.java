package PROYECTO_2.presentation.dashboard;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import PROYECTO_2.presentation.dashboard.buscarMedicamento.View_buscarMedicamento;
import pos.logic.MedicamentoResumen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View_Dashboard implements PropertyChangeListener {
    Controller_Dashboard controller;
    Model_Dashboard model;
    private JPanel panel;
    private JPanel pnlPieEstados;
    private JTable tablaMedicamentosResumen;
    private JButton borrarActualButton;
    private JButton borrarTodosButton;
    private JPanel pnlLineMedicamentos;
    private DatePicker fechaDesde;
    private DatePicker fechaHasta;
    private JButton agregarMedicamentoBtn;

    private View_buscarMedicamento buscarMedicamentoView;


    public View_Dashboard() {
        buscarMedicamentoView = new View_buscarMedicamento();

        fechaDesde.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent event) {
                controller.setFechaDesde(event.getNewDate());
            }
        });

        fechaHasta.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent event) {
                controller.setFechaHasta(event.getNewDate());
            }
        });

        agregarMedicamentoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarMedicamentoView.setVisible(true);
            }
        });

        tablaMedicamentosResumen.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaMedicamentosResumen.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tablaMedicamentosResumen.getSelectedRow();
                if (row >= 0) {
                    MedicamentoResumen m = model.getMedicamentosResumen().get(row);
                    model.setCurrentMedicamentoResumen(m);
                }
            }
        });
        borrarActualButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.removeMedicamentoResumen(model.getCurrentMedicamentoResumen());
            }
        });
        borrarTodosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clearMedicamentosResumen();
            }
        });
    }

    public void setController(Controller_Dashboard controller) {
        this.controller = controller;
        buscarMedicamentoView.setController(controller);
    }

    public void setModel(Model_Dashboard model) {
        this.model = model;
        model.addPropertyChangeListener((PropertyChangeListener) this);

        buscarMedicamentoView.setModel(model);
        model.addPropertyChangeListener(buscarMedicamentoView);
    }

    public JPanel getPanel() {
        return panel;
    }

    // Metodos helper para inyectar el ChartPanel
    public void setPieRecetasPanel(JPanel chartPanel) {
        pnlPieEstados.removeAll();
        pnlPieEstados.setLayout(new BorderLayout());
        pnlPieEstados.add(chartPanel, BorderLayout.CENTER);
        pnlPieEstados.revalidate();
        pnlPieEstados.repaint();
    }

    public void setLineMedicamentos(JPanel chartPanel) {
        pnlLineMedicamentos.removeAll();
        pnlLineMedicamentos.setLayout(new BorderLayout());
        pnlLineMedicamentos.add(chartPanel, BorderLayout.CENTER);
        pnlLineMedicamentos.revalidate();
        pnlLineMedicamentos.repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model_Dashboard.ALLRECETAS:
                controller.renderPieEstados();
                break;
            case Model_Dashboard.MEDICAMENTOSRESUMEN:
                controller.renderLineMedicamentosPorMes();
                int[] cols = {TableModel.NOMBRE, TableModel.FECHA_DESDE, TableModel.FECHA_HASTA, TableModel.CANTIDAD};
                tablaMedicamentosResumen.setModel(new TableModel(cols, model.getMedicamentosResumen()));
                break;
            case Model_Dashboard.CURRENTMEDICAMENTO:
                onCurrentMedicamentoChange();
                break;
        }
    }

    private void onCurrentMedicamentoChange() {
        if(model.getFechaDesde() != null && model.getFechaHasta() != null) {
            controller.addMedicamentoResumen(model.getCurrentMedicamento(), model.getFechaDesde(), model.getFechaHasta());
            fechaDesde.setBackground(null);
            fechaHasta.setBackground(null);
        } else {
            fechaDesde.setBackground(Color.RED);
            fechaHasta.setBackground(Color.RED);
        }
    }
}
