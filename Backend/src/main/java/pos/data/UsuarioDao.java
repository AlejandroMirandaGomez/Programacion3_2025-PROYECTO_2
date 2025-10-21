package pos.data;

import pos.logic.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    Database db;

    public UsuarioDao(){
        db= Database.instance();
    }

    public void create(Usuario n) throws Exception{
        String sql="insert into Usuario (id, clave, rol) "+
                "values(?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, n.getId());
        stm.setString(2, n.getPassword());
        stm.setString(3, n.getRol());

        int count=db.executeUpdate(stm);
        if (count==0){
            throw new Exception("Usuario ya existe");
        }
    }

    public List<Usuario> findAll(){
        List<Usuario> ds=new ArrayList<Usuario>();
        try {
            String sql="select * from Usuario n";
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs =  db.executeQuery(stm);
            while (rs.next()) {
                ds.add(from(rs,"n"));
            }
        } catch (SQLException ex) { }
        return ds;
    }

    public Usuario read(String codigo) throws Exception{
        String sql="select * from usuario e where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, codigo);
        ResultSet rs =  db.executeQuery(stm);
        if (rs.next()) {
            return from(rs,"e");
        }
        else{
            throw new Exception ("Usuario no Existe");
        }
    }

    public void remove(Usuario m) throws Exception{
        String sql="delete from Usuario where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, m.getId());
        int count=db.executeUpdate(stm);
        if (count==0){
            throw new Exception("Usuario no existe");
        }
    }

    public void update(Usuario m) throws Exception{
        String sql="update usuario set clave=?,rol=? "+
                "where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, m.getPassword());
        stm.setString(2, m.getRol());
        stm.setString(3, m.getId());

        int count=db.executeUpdate(stm);
        if (count==0){
            throw new Exception("Usuario no existe");
        }
    }

    public Usuario from(ResultSet rs, String alias){
        try {
            Usuario m= new Usuario();
            m.setId(rs.getString(alias + ".id"));
            m.setPassword(rs.getString(alias + ".clave"));
            m.setRol(rs.getString(alias + ".rol"));
            return m;
        } catch (SQLException ex) {
            return null;
        }
    }
}
