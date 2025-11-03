package PROYECTO_2.presentation.dashboard;

import PROYECTO_2.logic.*;
import pos.logic.Medicamento;
import pos.logic.MedicamentoResumen;
import pos.logic.Receta;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller_Dashboard {
    View_Dashboard view;
    Model_Dashboard model;
    public Controller_Dashboard(View_Dashboard view, Model_Dashboard model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
        getAllRecetas();
        getAllMedicamentos();
        renderPieEstados();
        renderLineMedicamentosPorMes();
    }

    //--Buscar Recetas--
    public void getAllRecetas() {
        List<Receta> recetas = Service.getInstance().findAllRecetas();
        model.setAllRecetas(recetas);
    }

    public void filtrarRecetas(String tipo, String texto) {
        List<Receta> result = Service.getInstance().filtrarRecetas(tipo, texto);
        model.setAllRecetas(result);
    }

    //--Render Graficos--
    public void renderPieEstados() {
        // 2) convierte a conteos
        //Map<String, Integer> conteo = model.conteoPorEstado();
        Map<String, Integer> conteo = Service.getInstance().conteoRecetasPorEstado();

        // 3) construye el ChartPanel
        javax.swing.JPanel chartPanel = ChartsFactory.buildPieRecetasPorEstado(conteo);

        // 4) insértalo en el panel de la View
        view.setPieRecetasPanel(chartPanel);
    }

    public void renderLineMedicamentosPorMes() {
        Map<String, Map<YearMonth, Integer>> datos = model.agruparCantidadesPorMedicamentoYMes();
        //List<MedicamentoResumen> medicamentosResumen = model.getMedicamentosResumen();
        //Map<String, Map<YearMonth, Integer>> datos = Service.getInstance().agruparRecetas_CantidadesPorMedicamentoYMes(medicamentosResumen);

        javax.swing.JPanel chartPanel = ChartsFactory.buildLineMedicamentosPorMes(datos);

        view.setLineMedicamentos(chartPanel);
    }

    //--Fecha--
    public void setFechaDesde(LocalDate fecha){
        model.setFechaDesde(fecha);
    }
    public void setFechaHasta(LocalDate fecha){
        model.setFechaHasta(fecha);
    }

    //--Buscar Medicamentos--
    public void getAllMedicamentos(){
        List<Medicamento> medicamentos= Service.getInstance().findAllMedicamentos();
        model.setMedicamentos(medicamentos);
    }
    public void filtrarMedicamentos(String tipo, String texto) {
        List<Medicamento> medicamentos= Service.getInstance().filtrarMedicamentos(tipo, texto);
        model.setMedicamentos(medicamentos);
    }

    public void seleccionarMedicamento(int row) {
        Medicamento m = model.getAllMedicamentos().get(row);
        model.setCurrentMedicamento(m);
    }

    public void addMedicamentoResumen(Medicamento m, LocalDate fechaDesde, LocalDate fechaHasta) {
        if (m == null || m.getCodigo() == null) return;
        final String codigo = m.getCodigo();

        List<MedicamentoResumen> list = model.getMedicamentosResumen();

        list.removeIf(old -> old != null && codigo.equals(old.getCodigo()));

        int cantidad = model.getCantidadOf(m, fechaDesde, fechaHasta);

        MedicamentoResumen medResumen = new MedicamentoResumen(m, fechaDesde, fechaHasta, cantidad);
        list.add(medResumen);
        model.setMedicamentosResumen(list);
    }

    public void removeMedicamentoResumen(MedicamentoResumen m) {
        if (m == null || m.getCodigo() == null) return;

        List<MedicamentoResumen> list = model.getMedicamentosResumen();
        if (list == null) return;

        final String codigo = m.getCodigo();
        list.removeIf(mr -> mr != null && codigo.equals(mr.getCodigo()));

        model.setMedicamentosResumen(list);
    }

    public void clearMedicamentosResumen(){
        List<MedicamentoResumen> list = new ArrayList<>();
        model.setMedicamentosResumen(list);
    }
}
