package pos.logic;

import java.time.LocalDate;
import java.io.Serializable;

public class MedicamentoResumen implements Serializable {
    private Medicamento medicamento;       // o puedes guardar el objeto Medicamento
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private int cantidad;

    public MedicamentoResumen(Medicamento medicamento, LocalDate fechaDesde, LocalDate fechaHasta, int cantidad) {
        this.medicamento = medicamento;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.cantidad = cantidad;
    }

    public String getNombre() { return medicamento.getNombre(); }
    public LocalDate getFechaDesde() { return fechaDesde; }
    public LocalDate getFechaHasta() { return fechaHasta; }
    public int getCantidad() { return cantidad; }
    public String getCodigo() { return medicamento.getCodigo(); }
}
