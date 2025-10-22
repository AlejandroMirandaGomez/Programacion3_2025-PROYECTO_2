package pos.logic;

import java.io.Serializable;
import java.util.Objects;

public class Prescripcion implements Serializable {
    // --- Atributos ---
    private int id;
    private String indicaciones;
    private int duracion; // en días
    private Medicamento medicamento;
    private int cantidad;
    private Receta receta;

    // --- Constructores ---
    public Prescripcion() {};

    public Prescripcion(String indicaciones, int duracion, Medicamento medicamento, int cantidad) {
        this.id = 0;
        this.indicaciones = indicaciones;
        this.duracion = duracion;
        this.medicamento = medicamento;
        this.cantidad = cantidad;
        this.receta = null;
    }

    public Prescripcion(int id, String indicaciones, int duracion, Medicamento medicamento, int cantidad,  Receta receta) {
        this.id = id;
        this.indicaciones = indicaciones;
        this.duracion = duracion;
        this.medicamento = medicamento;
        this.cantidad = cantidad;
        this.receta = receta;
    }

// --- Getters y Setters ---
    public int getId() {
    return id;
}

    public void setId(int id) {
        this.id = id;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public Medicamento getMedicamento() { return medicamento; }

    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Receta getReceta() { return receta; }

    public void setReceta(Receta receta) { this.receta = receta; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescripcion e = (Prescripcion) o;
        return Objects.equals(id, e.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return indicaciones;
    }
}