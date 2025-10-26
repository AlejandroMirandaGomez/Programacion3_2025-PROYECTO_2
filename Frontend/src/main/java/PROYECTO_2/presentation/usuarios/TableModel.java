package PROYECTO_2.presentation.usuarios;

import PROYECTO_2.presentation.AbstractTableModel;
import pos.logic.UsuarioChat;

import java.util.List;

public class TableModel extends AbstractTableModel<UsuarioChat> implements javax.swing.table.TableModel {
    public TableModel(int[] cols, List<UsuarioChat> rows) {
        super(cols, rows);
    }

    public static final int ID = 0;
    public static final int MENSAJES = 1;

    @Override
    protected void initColNames() {
        colNames = new String[2];
        colNames[ID] = "id";
        colNames[MENSAJES] = "Mensajes??";
    }
    @Override
    protected Object getPropetyAt(UsuarioChat e, int col) {
        switch (cols[col]) {
            case ID:
                return e.getNombre();
            case MENSAJES:
                return e.mensajesPendientes();
            default:
                return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int colIndex) {
        if (cols[colIndex] == MENSAJES)
            return Boolean.class; // muestra un checkbox
        return String.class;
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
