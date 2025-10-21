package PROYECTO_2.presentation.dashboard;

import PROYECTO_2.presentation.AbstractModel;
import pos.logic.Medicamento;
import pos.logic.MedicamentoResumen;
import pos.logic.Prescripcion;
import pos.logic.Receta;

import java.util.*;

import java.time.LocalDate;
import java.time.YearMonth;


import static PROYECTO_2.presentation.dashboard.ChartsFactory.*;

public class Model_Dashboard extends AbstractModel {
    public static final String ALLRECETAS = "allRecetas";
    public static final String ALLMEDICAMENTOS = "allMedicamentos";
    public static final String MEDICAMENTOSRESUMEN = "MedicamentosResumen";
    public static final String CURRENTMEDICAMENTO = "currentMedicamento";

    List<Receta> allRecetas;
    List<Medicamento> allMedicamentos;
    List<MedicamentoResumen> medicamentosResumen;
    private Medicamento currentMedicamento;
    private MedicamentoResumen currentMedicamentoResumen;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;

    public Model_Dashboard() {
        allRecetas = new ArrayList<>();
        allMedicamentos = new ArrayList<>();
        medicamentosResumen = new ArrayList<>();
        fechaDesde = null;
        fechaHasta = null;
    }

    public List<Receta> getAllRecetas() { return allRecetas; }

    public List<MedicamentoResumen> getMedicamentosResumen() { return medicamentosResumen; }
    public void setMedicamentosResumen(List<MedicamentoResumen> medicamentos) {
        this.medicamentosResumen = medicamentos;
        firePropertyChange(MEDICAMENTOSRESUMEN);
    }

    public LocalDate getFechaDesde() { return fechaDesde; }
    public LocalDate getFechaHasta() { return fechaHasta; }

    public void setFechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }
    public void setFechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public void setCurrentMedicamentoResumen(MedicamentoResumen m) {
        this.currentMedicamentoResumen = m;
    }
    public MedicamentoResumen getCurrentMedicamentoResumen() {
        return currentMedicamentoResumen;
    }

    public void setAllRecetas(List<Receta> recetas) {
        this.allRecetas = recetas;
        firePropertyChange(ALLRECETAS);
    }

    public List<Medicamento> getAllMedicamentos() {
        return allMedicamentos;
    }
    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.allMedicamentos = medicamentos;
        firePropertyChange(ALLMEDICAMENTOS);
    }

    public void setCurrentMedicamento(Medicamento m) {
        this.currentMedicamento = m;
        firePropertyChange(CURRENTMEDICAMENTO);
    }
    public Medicamento getCurrentMedicamento() { return currentMedicamento; }

    // Devuelve un mapa con los conteos por estado en ORDEN fijo
    public Map<String, Integer> conteoPorEstado() {
        Map<String, Integer> m = new LinkedHashMap<>();
        m.put(EN_PROCESO, 0);
        m.put(LISTA, 0);
        m.put(ENTREGADA, 0);
        m.put(CONFECCIONADA, 0);

        if (allRecetas == null) return m;
        for (Receta r : allRecetas) {
            String e = r.getEstado();
            m.put(e, m.get(e) + 1);
        }

        return m;
    }

    /**
     * Agrega cantidades por medicamento y mes.
     * - Usa enteros (no hay fracciones).
     * - Si un MedicamentoResumen tiene fechaDesde y fechaHasta, pre-llena
     *   la serie con 0 para todos los YearMonth en ese rango (inclusive).
     * - Suma todas las prescripciones que caen en el mismo YearMonth.
     *
     * @return Map<nombreMedicamento, Map<YearMonth, Integer>>
     */
    public Map<String, Map<YearMonth, Integer>> agruparCantidadesPorMedicamentoYMes() {
        Map<String, Map<YearMonth, Integer>> resultado = new LinkedHashMap<>();

        if (medicamentosResumen == null || allRecetas == null) return resultado;

        // Índice por nombre (case-insensitive) -> MedicamentoResumen
        Map<String, MedicamentoResumen> indexPorNombre = new LinkedHashMap<>();
        for (MedicamentoResumen mr : medicamentosResumen) {
            if (mr == null || mr.getNombre() == null) continue;

            String key = mr.getNombre().trim().toLowerCase(Locale.ROOT);
            indexPorNombre.put(key, mr);

            // Pre-crea serie y rellena con 0 los meses del rango si ambos límites existen
            Map<YearMonth, Integer> serie = new LinkedHashMap<>();
            LocalDate d = mr.getFechaDesde();
            LocalDate h = mr.getFechaHasta();
            if (d != null && h != null) {
                YearMonth start = YearMonth.from(d);
                YearMonth end   = YearMonth.from(h);
                for (YearMonth m = start; !m.isAfter(end); m = m.plusMonths(1)) {
                    serie.put(m, 0);  // cero para meses sin ventas
                }
            }
            resultado.putIfAbsent(mr.getNombre(), serie);
        }

        // Recorre recetas y prescripciones, suma por YearMonth
        for (Receta receta : allRecetas) {
            if (receta == null) continue;
            LocalDate fecha = receta.getFechaDeRetiro();
            if (fecha == null) continue;

            List<Prescripcion> prescripciones = receta.getPrescripciones();
            if (prescripciones == null) continue;

            for (Prescripcion p : prescripciones) {
                if (p == null || p.getMedicamento() == null) continue;

                String nombre = p.getMedicamento().getNombre();
                if (nombre == null) continue;

                MedicamentoResumen mr = indexPorNombre.get(nombre.trim().toLowerCase(Locale.ROOT));
                if (mr == null) continue; // no está en la lista seleccionada

                // Filtra por rango del MedicamentoResumen (inclusive)
                LocalDate desde = mr.getFechaDesde();
                LocalDate hasta = mr.getFechaHasta();
                if (desde != null && fecha.isBefore(desde)) continue;
                if (hasta != null && fecha.isAfter(hasta)) continue;

                YearMonth ym = YearMonth.from(fecha);
                Map<YearMonth, Integer> serie = resultado.computeIfAbsent(mr.getNombre(),
                        k -> new LinkedHashMap<>());

                // Asegura presencia del mes (0 si no estaba)
                serie.putIfAbsent(ym, 0);

                int cantidad = p.getCantidad();
                serie.put(ym, serie.get(ym) + cantidad);
            }
        }

        return resultado;
    }

    public int getCantidadOf(Medicamento m, LocalDate fechaDesde, LocalDate fechaHasta) {
        int cantidadTotal = 0;

        if (m == null || m.getCodigo() == null || allRecetas == null) return 0;
        final String codigoBuscado = m.getCodigo();

        for (Receta receta : allRecetas) {
            if (receta == null) continue;

            LocalDate fecha = receta.getFechaDeRetiro();
            if (fecha == null) continue;

            // Filtro de fecha: estrictamente mayor que fechaDesde y estrictamente menor que fechaHasta
            if (fechaDesde != null && !fecha.isAfter(fechaDesde)) continue;
            if (fechaHasta != null && !fecha.isBefore(fechaHasta)) continue;

            List<Prescripcion> prescripciones = receta.getPrescripciones();
            if (prescripciones == null) continue;

            for (Prescripcion p : prescripciones) {
                if (p == null) continue;
                Medicamento med = p.getMedicamento();
                if (med == null || med.getCodigo() == null) continue;

                if (codigoBuscado.equals(med.getCodigo())) {
                    cantidadTotal += p.getCantidad();
                }
            }
        }
        return cantidadTotal;
    }

}
