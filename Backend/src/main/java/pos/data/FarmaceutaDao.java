package pos.data;

import pos.logic.Farmaceuta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FarmaceutaDao {
    Database db;

    public FarmaceutaDao(){
        db= Database.instance();
    }
    public void create(Farmaceuta f) throws Exception{
        String sql="insert into Farmaceuta (id, nombre) "+
                "values(?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, f.getId());
        stm.setString(2, f.getNombre());

        int count=db.executeUpdate(stm);
        if (count==0){
            throw new Exception("Ya existe farmaceuta con el id "+f.getId());
        }
    }
    public List<Farmaceuta> findAll(){
        List<Farmaceuta> ds=new ArrayList<Farmaceuta>();
        try {
            String sql="select * from Farmaceuta f";
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs =  db.executeQuery(stm);
            while (rs.next()) {
                ds.add(from(rs,"f"));
            }
        } catch (SQLException ex) { }
        return ds;
    }

    public List<Farmaceuta> searchById(String filtro){
        List<Farmaceuta> ds=new ArrayList<Farmaceuta>();
        try {
            String sql="select * from Farmaceuta f "+
                    "where f.id like ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%"+filtro+"%");
            ResultSet rs =  db.executeQuery(stm);
            while (rs.next()) {
                ds.add(from(rs,"f"));
            }
        } catch (SQLException ex) { }
        return ds;
    }
    public List<Farmaceuta> searchByName(String filtro){
        List<Farmaceuta> ds=new ArrayList<Farmaceuta>();
        try {
            String sql="select * from Farmaceuta f "+
                    "where f.nombre like ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%"+filtro+"%");
            ResultSet rs =  db.executeQuery(stm);
            while (rs.next()) {
                ds.add(from(rs,"f"));
            }
        } catch (SQLException ex) { }
        return ds;
    }

    public void delete(Farmaceuta o) throws Exception{
        String sql="delete from Farmaceuta where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, o.getId());
        int count=db.executeUpdate(stm);
        if (count==0){
            throw new Exception("Farmaceuta no existe");
        }
    }

    public void update(Farmaceuta p) throws Exception{
        String sql="update farmaceuta set nombre=? "+
                "where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, p.getNombre());
        stm.setString(2, p.getId());

        int count=db.executeUpdate(stm);
        if (count==0){
            throw new Exception("Farmaceuta no existe");
        }
    }


    public Farmaceuta from(ResultSet rs, String alias){
        try {
            Farmaceuta p= new Farmaceuta();
            p.setId(rs.getString(alias + ".id"));
            p.setNombre(rs.getString(alias + ".nombre"));

            return p;
        } catch (SQLException ex) {
            return null;
        }
    }

}
