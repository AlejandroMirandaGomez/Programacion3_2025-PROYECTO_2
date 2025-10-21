package PROYECTO_2.presentation.farmaceutas;

import PROYECTO_2.presentation.AbstractTableModel;
import pos.logic.Farmaceuta;

import java.util.List;

public class TableModel extends AbstractTableModel<Farmaceuta> implements javax.swing.table.TableModel{
    public TableModel(int[] cols, List<Farmaceuta> rows){super(cols,rows);}

    public static final int ID = 0;
    public static final int NOMBRE = 1;

    @Override
    protected void initColNames(){
        colNames = new String[3];
        colNames[0] = "ID";
        colNames[1] = "NOMBRE";
    }

    @Override
    protected Object getPropetyAt(Farmaceuta e, int col){
        switch(cols[col]){
            case ID:
                return  e.getId();
            case  NOMBRE:
                return  e.getNombre();
            default:
                return "";
        }
    }
}
