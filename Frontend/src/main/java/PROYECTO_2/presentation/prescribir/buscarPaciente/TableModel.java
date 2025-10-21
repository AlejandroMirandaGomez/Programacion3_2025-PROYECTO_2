package PROYECTO_2.presentation.prescribir.buscarPaciente;

import PROYECTO_2.presentation.AbstractTableModel;
import pos.logic.Paciente;

import java.util.List;

public class TableModel extends AbstractTableModel<Paciente> implements javax.swing.table.TableModel {
    public TableModel(int[] cols, List<Paciente> rows) {
        super(cols, rows);
    }

    public static final int ID = 0;
    public static final int NOMBRE = 1;
    public static final int TELEFONO = 2;
    public static final int FECHANACIMIENTO = 3;

    @Override
    protected void initColNames() {
        colNames = new String[4];
        colNames[ID] = "Id";
        colNames[NOMBRE] = "Nombre";
        colNames[TELEFONO] = "Telefono";
        colNames[FECHANACIMIENTO] = "Fecha Nacimiento";
    }
    @Override
    protected Object getPropetyAt(Paciente e, int col) {
        switch (cols[col]) {
            case ID:
                return e.getId();
            case NOMBRE:
                return e.getNombre();
            case TELEFONO:
                return e.getTelefono();
            case FECHANACIMIENTO:
                return e.getFechaNacimiento();
            default:
                return "";
        }
    }

}
