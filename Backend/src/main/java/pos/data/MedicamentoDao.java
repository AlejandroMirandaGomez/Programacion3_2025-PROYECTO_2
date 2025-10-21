package pos.data;

import pos.logic.Medicamento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDao {
    Database db;

    public MedicamentoDao(){
        db= Database.instance();
    }

    public void create(Medicamento p) throws Exception{
        String sql="insert into Medicamento (codigo, nombre, presentacion) "+
                "values(?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, p.getCodigo());
        stm.setString(2, p.getNombre());
        stm.setString(3, p.getPresentacion());

        int count=db.executeUpdate(stm);
        if (count==0){
            throw new Exception("Medicamento ya existe");
        }
    }
    public List<Medicamento> findAll(){
        List<Medicamento> ds=new ArrayList<Medicamento>();
        try {
            String sql="select * from Medicamento m";
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs =  db.executeQuery(stm);
            while (rs.next()) {
                ds.add(from(rs,"m"));
            }
        } catch (SQLException ex) { }
        return ds;
    }

    public List<Medicamento> searchById(String filtro){
        List<Medicamento> ds=new ArrayList<Medicamento>();
        try {
            String sql="select * from Medicamento m "+
                    "where m.codigo like ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%"+filtro+"%");
            ResultSet rs =  db.executeQuery(stm);
            while (rs.next()) {
                ds.add(from(rs,"m"));
            }
        } catch (SQLException ex) { }
        return ds;
    }
    public List<Medicamento> searchByName(String filtro){
        List<Medicamento> ds=new ArrayList<Medicamento>();
        try {
            String sql="select * from Medicamento m "+
                    "where m.nombre like ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%"+filtro+"%");
            ResultSet rs =  db.executeQuery(stm);
            while (rs.next()) {
                ds.add(from(rs,"m"));
            }
        } catch (SQLException ex) { }
        return ds;
    }

    public List<Medicamento> findByForm(String filtro){
        List<Medicamento> ds=new ArrayList<Medicamento>();
        try {
            String sql="select * from Medicamento m "+
                    "where m.presentacion like ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%"+filtro+"%");
            ResultSet rs =  db.executeQuery(stm);
            while (rs.next()) {
                ds.add(from(rs,"m"));
            }
        } catch (SQLException ex) { }
        return ds;
    }

    public void delete(Medicamento o) throws Exception{
        String sql="delete from Medicamento where codigo=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, o.getCodigo());
        int count=db.executeUpdate(stm);
        if (count==0){
            throw new Exception("Medicamento no existe");
        }
    }

    public void update(Medicamento p) throws Exception{
        String sql="update medicamento set nombre=?,presentacion=? "+
                "where codigo=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, p.getNombre());
        stm.setString(2, p.getPresentacion());
        stm.setString(3, p.getCodigo());

        int count=db.executeUpdate(stm);
        if (count==0){
            throw new Exception("Medicamento no existe");
        }
    }


    public Medicamento from(ResultSet rs, String alias){
        try {
            Medicamento p= new Medicamento();
            p.setCodigo(rs.getString(alias + ".codigo"));
            p.setNombre(rs.getString(alias + ".nombre"));
            p.setPresentacion(rs.getString(alias + ".presentacion"));
            return p;
        } catch (SQLException ex) {
            return null;
        }
    }

}
