package PROYECTO_2.presentation.dashboard;

import PROYECTO_2.presentation.AbstractTableModel;
import pos.logic.MedicamentoResumen;

import java.util.List;

public class TableModel extends AbstractTableModel<MedicamentoResumen> implements javax.swing.table.TableModel {
    public TableModel(int[] cols, List<MedicamentoResumen> rows) {
        super(cols, rows);
    }

    public static final int NOMBRE = 0;
    public static final int FECHA_DESDE = 1;
    public static final int FECHA_HASTA = 2;
    public static final int CANTIDAD = 3;

    @Override
    protected Object getPropetyAt(MedicamentoResumen mr, int col) {
        switch (cols[col]) {
            case NOMBRE:
                return mr.getNombre();
            case FECHA_DESDE:
                return mr.getFechaDesde().toString();
            case FECHA_HASTA:
                return mr.getFechaHasta().toString();
            case CANTIDAD:
                return mr.getCantidad();
            default:
                return "";
        }
    }

    @Override
    protected void initColNames() {
        colNames = new String[4];
        colNames[NOMBRE] = "Medicamento";
        colNames[FECHA_DESDE] = "Fecha desde";
        colNames[FECHA_HASTA] = "Fecha hasta";
        colNames[CANTIDAD] = "Cantidad";
    }
}
