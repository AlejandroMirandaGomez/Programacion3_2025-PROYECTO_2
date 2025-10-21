package pos.data;

import pos.logic.Medico;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoDao {

    Database db;

    public MedicoDao(){
        db= Database.instance();
    }

    public void create(Medico m) throws Exception{
        String sql="insert into Medico (id, nombre, especialidad) "+
                "values(?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, m.getId());
        stm.setString(2, m.getNombre());
        stm.setString(3, m.getEspecialidad());

        int count=db.executeUpdate(stm);
        if (count==0){
            throw new Exception("Medico ya existe");
        }
    }

    public Medico read(String id) throws Exception{
        String sql="select * from Medico m where m.id=? ";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();

        Medico m;
        if(rs.next()){
            m = from(rs, "m");
            return m;
        } else {
            throw new Exception("Medico no existe");
        }
    }

    public List<Medico> findAll(){
        List<Medico> ds = new ArrayList<Medico>();
        try {
            String sql="select * from Medico m";
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs =  db.executeQuery(stm);
            while (rs.next()) {
                ds.add(from(rs,"m"));
            }
        } catch (SQLException ex) { }
        return ds;
    }

    public List<Medico> searchById(String filtro){
        List<Medico> ds=new ArrayList<Medico>();
        try {
            String sql="select * from Medico m "+
                    "where m.id like ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%"+filtro+"%");
            ResultSet rs =  db.executeQuery(stm);
            while (rs.next()) {
                ds.add(from(rs,"m"));
            }
        } catch (SQLException ex) { }
        return ds;
    }
    public List<Medico> searchByName(String filtro){
        List<Medico> ds=new ArrayList<Medico>();
        try {
            String sql="select * from Medico m "+
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

    public void remove(Medico m) throws Exception{
        String sql="delete from Medico where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, m.getId());
        int count=db.executeUpdate(stm);
        if (count==0){
            throw new Exception("Medico no existe");
        }
    }

    public void update(Medico m) throws Exception{
        String sql="update medico set nombre=?,especialidad=? "+
                "where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, m.getNombre());
        stm.setString(2, m.getEspecialidad());
        stm.setString(3, m.getId());

        int count=db.executeUpdate(stm);
        if (count==0){
            throw new Exception("Medico no existe");
        }
    }


    public Medico from(ResultSet rs, String alias){
        try {
            Medico m= new Medico();
            m.setId(rs.getString(alias + ".id"));
            m.setNombre(rs.getString(alias + ".nombre"));
            m.setEspecialidad(rs.getString(alias + ".especialidad"));
            return m;
        } catch (SQLException ex) {
            return null;
        }
    }
}
