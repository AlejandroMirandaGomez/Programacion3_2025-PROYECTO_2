package pos.data;

import pos.logic.Prescripcion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrescripcionDao {
    Database db;

    public PrescripcionDao() {
        db = Database.instance();
    }

    public void create(Prescripcion p) throws Exception {
        String sql = "insert into Prescripcion " +
                "(indicaciones, duracion, cantidad, medicamento, receta) " +
                "values (?,?,?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, p.getIndicaciones());
        stm.setInt(2, p.getDuracion());
        stm.setInt(3, p.getCantidad());
        stm.setString(4, p.getMedicamento().getCodigo());
        stm.setInt(5, p.getReceta().getId());

        int count = db.executeUpdate(stm);
        if (count == 0) throw new Exception("Prescripcion no creada");

        PreparedStatement keyStm = db.prepareStatement("SELECT LAST_INSERT_ID()");
        ResultSet rs = db.executeQuery(keyStm);
        if (rs.next()) p.setId(rs.getInt(1));
    }


    public Prescripcion read(int id) throws Exception {
        String sql = "select * from Prescripcion p " +
                "inner join Medicamento m on p.medicamento=m.codigo " +
                "inner join Receta r on p.receta=r.id " +
                "where p.id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet rs = db.executeQuery(stm);

        Prescripcion p;
        MedicamentoDao mDao = new MedicamentoDao();
        RecetaDao rDao = new RecetaDao();

        if (rs.next()) {
            p = from(rs, "p");
            p.setMedicamento(mDao.from(rs, "m"));
            p.setReceta(rDao.from(rs, "r"));
            return p;
        } else{
            throw new Exception("Prescripcion no existe");
        }
    }

    public void update(Prescripcion p) throws Exception {
        String sql = "update Prescripcion set indicaciones=?, duracion=?, cantidad=?, medicamento=?, receta=? where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, p.getIndicaciones());
        stm.setInt(2, p.getDuracion());
        stm.setInt(3, p.getCantidad());
        stm.setString(4, p.getMedicamento().getCodigo());
        stm.setInt(5, p.getReceta().getId());
        stm.setInt(6, p.getId());
        int count = db.executeUpdate(stm);
        if (count == 0) throw new Exception("Prescripcion no existe");
    }

    public void delete(Prescripcion p) throws Exception {
        String sql = "delete from Prescripcion where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setInt(1, p.getId());
        int count = db.executeUpdate(stm);
        if (count == 0) throw new Exception("Prescripcion no existe");
    }

    public List<Prescripcion> readAll() throws Exception {
        List<Prescripcion> resultado = new ArrayList<>();
        try {
            String sql = "select * from Prescripcion p " +
                    "inner join Medicamento m on m.codigo=p.medicamento " +
                    "inner join Receta r on r.id=p.receta";
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs = db.executeQuery(stm);

            Prescripcion p;
            MedicamentoDao mDao = new MedicamentoDao();
            RecetaDao rDao = new RecetaDao();

            while (rs.next()) {
                p = from(rs, "p");
                p.setMedicamento(mDao.from(rs, "m"));
                p.setReceta(rDao.from(rs, "r"));
                resultado.add(p);
            }
        } catch (SQLException ex) {}
        return resultado;
    }

    public Prescripcion from(ResultSet rs, String alias) {
        try {
            Prescripcion p = new Prescripcion();
            p.setId(rs.getInt(alias + ".id"));
            p.setIndicaciones(rs.getString(alias + ".indicaciones"));
            p.setDuracion(rs.getInt(alias + ".duracion"));
            p.setCantidad(rs.getInt(alias + ".cantidad"));
            return p;
        } catch (SQLException ex) {
            return null;
        }
    }

    public List<Prescripcion> searchByReceta(Integer id) {
        List<Prescripcion> resultado = new ArrayList<>();
        try {
            String sql = "select * from Prescripcion p " +
                    "inner join Medicamento m on m.codigo=p.medicamento " +
                    "inner join Receta r on r.id=p.receta " +
                    "where p.receta = ?";
            PreparedStatement stm = db.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = db.executeQuery(stm);
            Prescripcion p;
            MedicamentoDao mDao = new MedicamentoDao();
            RecetaDao rDao = new RecetaDao();

            while (rs.next()) {
                p = from(rs, "p");
                p.setMedicamento(mDao.from(rs, "m"));
                p.setReceta(rDao.from(rs, "r"));
                resultado.add(p);
            }
        } catch (SQLException ex) {}
        return resultado;
    }
}
