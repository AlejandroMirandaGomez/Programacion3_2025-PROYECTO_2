package pos.data;

import pos.logic.MedicamentoResumen;
import pos.logic.Receta;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

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

    public Map<String, Integer> conteoPorEstado() {
        // Orden fijo: EN_PROCESO, LISTA, ENTREGADA, CONFECCIONADA
        Map<String, Integer> m = new LinkedHashMap<>();
        m.put("En Proceso", 0);
        m.put("Lista", 0);
        m.put("Entregada", 0);
        m.put("Confeccionada", 0);

        String sql = "SELECT estado, COUNT(*) AS c FROM Receta GROUP BY estado";
        try {
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs = db.executeQuery(stm);
            while (rs.next()) {
                String estado = rs.getString("estado");
                if (m.containsKey(estado)) {
                    m.put(estado, m.get(estado) + rs.getInt("c"));
                }
                // Si llega un estado no contemplado, simplemente se ignora
            }
        } catch (SQLException ex) {
        }
        return m;
    }

    public Map<String, Map<YearMonth, Integer>> agruparCantidadesPorMedicamentoYMes(List<MedicamentoResumen> medicamentosResumen) {
        Map<String, Map<YearMonth, Integer>> resultado = new LinkedHashMap<>();

        // Comportamiento: si la lista es null o vacía, devuelve mapa vacío
        if (medicamentosResumen == null || medicamentosResumen.isEmpty()) {
            return resultado;
        }

        // 1) Índice por nombre (case-insensitive) -> MedicamentoResumen
        Map<String, MedicamentoResumen> indexPorNombre = new LinkedHashMap<>();
        for (MedicamentoResumen mr : medicamentosResumen) {
            if (mr == null || mr.getNombre() == null) continue;

            String key = mr.getNombre().trim().toLowerCase(Locale.ROOT);
            indexPorNombre.put(key, mr);

            // 2) Pre-crear serie de ese medicamento (clave externa = nombre tal cual viene en el MR)
            Map<YearMonth, Integer> serie = new LinkedHashMap<>();

            LocalDate d = mr.getFechaDesde();
            LocalDate h = mr.getFechaHasta();
            // Pre-llenar con 0 si ambos límites existen (inclusive)
            if (d != null && h != null) {
                YearMonth start = YearMonth.from(d);
                YearMonth end   = YearMonth.from(h);
                for (YearMonth m = start; !m.isAfter(end); m = m.plusMonths(1)) {
                    serie.put(m, 0);
                }
            }
            resultado.putIfAbsent(mr.getNombre(), serie);
        }

        // 3) Consultar BD: Receta + Prescripcion + Medicamento (nombre) y sumar por YearMonth
        //    Ajusta los nombres de columnas/tablas si en tu esquema difieren.
        String sql =
                "SELECT r.fechaDeRetiro AS fecha, m.nombre AS med_nombre, pr.cantidad AS cantidad " +
                        "FROM Receta r " +
                        "JOIN Prescripcion pr ON pr.receta = r.id " +
                        "JOIN Medicamento  m  ON m.codigo = pr.medicamento";
        try {
            PreparedStatement stm = db.prepareStatement(sql);
            ResultSet rs = db.executeQuery(stm);

            while (rs.next()) {
                Date fechaSql = rs.getDate("fecha");
                if (fechaSql == null) continue;
                LocalDate fecha = fechaSql.toLocalDate();

                String nombre = rs.getString("med_nombre");
                if (nombre == null) continue;

                // Ubicar el MR correspondiente por nombre (case-insensitive)
                MedicamentoResumen mr = indexPorNombre.get(nombre.trim().toLowerCase(Locale.ROOT));
                if (mr == null) continue; // si no está en la lista seleccionada, se ignora

                // Filtro de rango del propio MR (inclusive)
                LocalDate desde = mr.getFechaDesde();
                LocalDate hasta = mr.getFechaHasta();
                if (desde != null && fecha.isBefore(desde)) continue;
                if (hasta != null && fecha.isAfter(hasta)) continue;

                YearMonth ym = YearMonth.from(fecha);

                // Serie por nombre EXACTO como viene en el MR
                Map<YearMonth, Integer> serie = resultado.computeIfAbsent(mr.getNombre(), k -> new LinkedHashMap<>());
                serie.putIfAbsent(ym, 0);

                int cantidad = rs.getInt("cantidad");
                serie.put(ym, serie.get(ym) + cantidad);
            }
        } catch (SQLException ex) {
            // Si hay error, se retorna lo que haya
        }
        return resultado;
    }
}
