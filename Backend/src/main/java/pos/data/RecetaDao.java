package pos.data;

import pos.logic.Receta;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecetaDao {
    Database db;

    public RecetaDao() {
        db = Database.instance();
    }

    public void create(Receta r) throws Exception {
        String sql = "insert into Receta (fechaDeRetiro, estado, paciente, medico)"+
                " values (?, ?, ?, ?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setDate(1, Date.valueOf( r.getFechaDeRetiro() ));
        stm.setString(2, r.getEstado());
        stm.setString(3, r.getPaciente().getId());
        stm.setString(4, r.getMedico().getId());

        int count = db.executeUpdate(stm);
        if (count==0){
            throw new Exception("Error al insertar receta");
        }

        PreparedStatement keyStm = db.prepareStatement("SELECT LAST_INSERT_ID()");
        ResultSet rs = db.executeQuery(keyStm);
        if (rs.next()) r.setId(rs.getInt(1));
    }

    public Receta read(String id) throws Exception {
        String sql = "select * from Receta r " +
                "inner join Paciente p on p.id=r.paciente " +
                "inner join Medico m on m.id=r.medico " +
                "where r.id = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, id);
        ResultSet rs =  db.executeQuery(stm);

        Receta r;
        PacienteDao pDao = new PacienteDao();
        MedicoDao mDao = new MedicoDao();
        PrescripcionDao prescripcionDao = new PrescripcionDao();

        if (rs.next()){
            r = from(rs, "r");
            r.setPaciente(pDao.from(rs, "p"));
            r.setMedico(mDao.from(rs, "m"));
            r.setPrescripciones(prescripcionDao.searchByReceta(r.getId()));
            return r;
        } else {
            throw new Exception("No se encontro el receta");
        }
    }

    public void update(Receta r) throws Exception {
        String sql = "update receta set fechaDeRetiro=?, estado=?, paciente=?, medico=? " +
                "where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setDate(1, Date.valueOf( r.getFechaDeRetiro() ));
        stm.setString(2, r.getEstado());
        stm.setString(3, r.getPaciente().getId());
        stm.setString(4, r.getMedico().getId());
        stm.setInt(5, r.getId());
        int count = db.executeUpdate(stm);
        if (count==0){
            throw new Exception("Error al actualizar receta");
        }
    }

    public void delete(Receta r) throws Exception {
        String sql = "delete from Receta where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setInt(1, r.getId());
        int count = db.executeUpdate(stm);
        if (count==0){
            throw new Exception("Error al eliminar receta");
        }

    }

    public List<Receta> findAll()  {
        List<Receta> resultado = new ArrayList<>();
        try {
            String sql = "select * from Receta r " +
                    "inner join Paciente p on p.id=r.paciente " +
                    "inner join Medico m on m.id=r.medico";
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs =  db.executeQuery(stm);
            Receta r;
            PacienteDao pDao = new PacienteDao();
            MedicoDao mDao = new MedicoDao();
            PrescripcionDao prescripcionDao = new PrescripcionDao();
            while (rs.next()){
                r = from(rs, "r");
                r.setPaciente(pDao.from(rs, "p"));
                r.setMedico(mDao.from(rs, "m"));
                r.setPrescripciones(prescripcionDao.searchByReceta(r.getId()));
                resultado.add(r);
            }
        } catch (SQLException ex) {}
        return resultado;
    }

    public List<Receta> searchByPaciente(String id)   {
        List<Receta> resultado = new ArrayList<>();
        try {
            String sql = "select * from Receta r " +
                    "inner join Paciente p on p.id=r.paciente " +
                    "inner join Medico m on m.id=r.medico " +
                    "where r.paciente LIKE ?";

            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%"+id+"%");
            ResultSet rs =  db.executeQuery(stm);
            Receta r;
            PacienteDao pDao = new PacienteDao();
            MedicoDao mDao = new MedicoDao();
            PrescripcionDao prescripcionDao = new PrescripcionDao();
            while (rs.next()){
                r = from(rs, "r");
                r.setPaciente(pDao.from(rs, "p"));
                r.setMedico(mDao.from(rs, "m"));
                r.setPrescripciones(prescripcionDao.searchByReceta(r.getId()));
                resultado.add(r);
            }
        } catch (SQLException ex) {}
        return resultado;
    }

    public List<Receta> searchByEstado(String estado)  {
        List<Receta> resultado = new ArrayList<>();
        try {
            String sql = "select * from Receta r " +
                    "inner join Paciente p on p.id=r.paciente " +
                    "inner join Medico m on m.id=r.medico " +
                    "where r.estado LIKE ?";

            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%"+estado+"%");
            ResultSet rs =  db.executeQuery(stm);
            Receta r;
            PacienteDao pDao = new PacienteDao();
            MedicoDao mDao = new MedicoDao();
            PrescripcionDao prescripcionDao = new PrescripcionDao();
            while (rs.next()){
                r = from(rs, "r");
                r.setPaciente(pDao.from(rs, "p"));
                r.setMedico(mDao.from(rs, "m"));
                r.setPrescripciones(prescripcionDao.searchByReceta(r.getId()));
                resultado.add(r);
            }
        } catch (SQLException ex) {}
        return resultado;
    }

    public List<Receta> searchByPaciente_Estado(String paciente, String estado)  {
        List<Receta> resultado = new ArrayList<>();
        try {
            String sql = "select * from Receta r " +
                    "inner join Paciente p on p.id=r.paciente " +
                    "inner join Medico m on m.id=r.medico " +
                    "where r.estado LIKE ? and r.paciente LIKE ?";

            PreparedStatement stm = db.prepareStatement(sql);
            stm.setString(1, "%"+estado+"%");
            stm.setString(2, "%"+paciente+"%");
            ResultSet rs =  db.executeQuery(stm);
            Receta r;
            PacienteDao pDao = new PacienteDao();
            MedicoDao mDao = new MedicoDao();
            while (rs.next()){
                r = from(rs, "r");
                r.setPaciente(pDao.from(rs, "p"));
                r.setMedico(mDao.from(rs, "m"));
                resultado.add(r);
            }
        } catch (SQLException ex) {}
        return resultado;
    }

    public Receta from(ResultSet rs, String alias) {
        try {
            Receta r = new Receta();
            r.setId(rs.getInt(alias + ".id"));
            Date fecha = rs.getDate(alias + ".fechaDeRetiro");
            if (fecha != null) {
                r.setFechaDeRetiro(fecha.toLocalDate());
            }
            r.setEstado(rs.getString(alias + ".estado"));
            return r;
        } catch (SQLException ex) {
            return null;
        }
    }
}
