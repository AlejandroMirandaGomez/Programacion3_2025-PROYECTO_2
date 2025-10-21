package pos.data;

import pos.logic.Paciente;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteDao {
    Database db;

    public PacienteDao(){
        db= Database.instance();
    }
    public void create(Paciente p) throws Exception{
        String sql="insert into Paciente (id, nombre, telefono, fechaNacimiento) "+
                "values(?,?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, p.getId());
        stm.setString(2, p.getNombre());
        stm.setString(3, p.getTelefono());
        stm.setDate(4, Date.valueOf(p.getFechaNacimiento()));

        int count=db.executeUpdate(stm);
        if (count==0){
            throw new Exception("Persona ya existe");
        }
    }
    public List<Paciente> findAll(){
        List<Paciente> ds=new ArrayList<Paciente>();
        try {
            String sql="select * from Paciente p";
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs =  db.executeQuery(stm);
            while (rs.next()) {
                ds.add(from(rs,"p"));
            }
        } catch (SQLException ex) { }
        return ds;
    }

    public List<Paciente> searchById(String filtro){
        List<Paciente> ds=new ArrayList<Paciente>();
        try {
            String sql="select * from Paciente p "+
                    "where p.id like ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%"+filtro+"%");
            ResultSet rs =  db.executeQuery(stm);
            while (rs.next()) {
                ds.add(from(rs,"p"));
            }
        } catch (SQLException ex) { }
        return ds;
    }
    public List<Paciente> searchByName(String filtro){
        List<Paciente> ds=new ArrayList<Paciente>();
        try {
            String sql="select * from Paciente p "+
                    "where p.nombre like ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%"+filtro+"%");
            ResultSet rs =  db.executeQuery(stm);
            while (rs.next()) {
                ds.add(from(rs,"p"));
            }
        } catch (SQLException ex) { }
        return ds;
    }

    public List<Paciente> findByPhone(String filtro){
        List<Paciente> ds=new ArrayList<Paciente>();
        try {
            String sql="select * from Paciente p "+
                    "where p.telefono like ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%"+filtro+"%");
            ResultSet rs =  db.executeQuery(stm);
            while (rs.next()) {
                ds.add(from(rs,"p"));
            }
        } catch (SQLException ex) { }
        return ds;
    }

    public void delete(Paciente o) throws Exception{
        String sql="delete from Paciente where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, o.getId());
        int count=db.executeUpdate(stm);
        if (count==0){
            throw new Exception("Paciente no existe");
        }
    }

    public void update(Paciente p) throws Exception{
        String sql="update paciente set nombre=?,telefono=?,fechaNacimiento=? "+
                "where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, p.getNombre());
        stm.setString(2, p.getTelefono());
        stm.setDate(3, Date.valueOf(p.getFechaNacimiento()));
        stm.setString(4, p.getId());

        int count=db.executeUpdate(stm);
        if (count==0){
            throw new Exception("Paciente no existe");
        }
    }


    public Paciente from(ResultSet rs, String alias){
        try {
            Paciente p= new Paciente();
            p.setId(rs.getString(alias + ".id"));
            p.setNombre(rs.getString(alias + ".nombre"));
            p.setTelefono(rs.getString(alias + ".telefono"));
            Date fecha = rs.getDate(alias + ".fechaNacimiento");
            if (fecha != null) {
                p.setFechaNacimiento(fecha.toLocalDate());
            }
            return p;
        } catch (SQLException ex) {
            return null;
        }
    }


}
