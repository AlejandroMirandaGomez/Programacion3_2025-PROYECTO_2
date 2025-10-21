package PROYECTO_2.presentation.historico.verReceta;

import PROYECTO_2.presentation.AbstractTableModel;
import pos.logic.Prescripcion;

import java.util.List;

public class TableModel extends AbstractTableModel<Prescripcion> implements javax.swing.table.TableModel {
    public TableModel(int[] cols, List<Prescripcion> rows) {
        super(cols, rows);
    }

    public static final int MEDICAMENTO = 0;
    public static final int PRESENTACION = 1;
    public static final int INDICACION = 2;
    public static final int CANTIDAD = 3;
    public static final int DURACION = 4;

    @Override
    protected void initColNames() {
        colNames = new String[5];
        colNames[MEDICAMENTO] = "Medicamento";
        colNames[PRESENTACION] = "Presentación";
        colNames[CANTIDAD] = "Cantidad";
        colNames[INDICACION] = "Indicaciones";
        colNames[DURACION] = "Duracion";
    }
    @Override
    protected Object getPropetyAt(Prescripcion e, int col) {
        switch (cols[col]) {
            case MEDICAMENTO:
                return e.getMedicamento().getNombre();
            case PRESENTACION:
                return e.getMedicamento().getPresentacion();
            case INDICACION:
                return e.getIndicaciones();
            case CANTIDAD:
                return e.getCantidad();
            case DURACION:
                return e.getDuracion();
            default:
                return "";
        }
    }
}
