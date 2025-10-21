package PROYECTO_2.presentation.historico;

import PROYECTO_2.presentation.AbstractTableModel;
import pos.logic.Receta;

import java.util.List;

public class TableModel extends AbstractTableModel<Receta> implements javax.swing.table.TableModel  {
    public TableModel(int[] cols, List<Receta> rows) {
        super(cols, rows);
    }

    public static final int ID_PACIENTE = 0;
    public static final int NOMBRE_PACIENTE = 1;
    public static final int FECHA_RETIRO = 2;
    public static final int NOMBRE_MEDICO = 3;
    public static final int ESTADO = 4;

    @Override
    protected Object getPropetyAt(Receta receta, int col) {
        switch (cols[col]) {
            case ID_PACIENTE:
                return receta.getPaciente().getId();
            case NOMBRE_PACIENTE:
                return receta.getPaciente().getNombre();
            case FECHA_RETIRO:
                return receta.getFechaDeRetiro();
            case NOMBRE_MEDICO:
                 return receta.getMedico().getNombre();
            case ESTADO:
                return receta.getEstado();
            default:
                return "";
        }
    }

    @Override
    protected void initColNames() {
        colNames = new String[5];
        colNames[ID_PACIENTE] = "Id del Paciente";
        colNames[NOMBRE_PACIENTE] = "Nombre del Paciente";
        colNames[FECHA_RETIRO] = "Fecha de retiro";
        colNames[NOMBRE_MEDICO] = "Nombre del medico";
        colNames[ESTADO] = "Estado";
    }
}
