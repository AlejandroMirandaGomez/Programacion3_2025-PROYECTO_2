package pos.logic;

import pos.data.*;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private static Service Instance;

    public static Service getInstance(){
        if(Instance==null){
            Instance = new Service();
        }
        return Instance;
    }

    private PacienteDao pacienteDao;
    private MedicamentoDao medicamentoDao;
    private MedicoDao medicoDao;
    private UsuarioDao usuarioDao;
    private FarmaceutaDao farmaceutaDao;
    private RecetaDao recetaDao;
    private PrescripcionDao prescripcionDao;

    private Service(){
        try{
            pacienteDao = new PacienteDao();
            medicamentoDao = new MedicamentoDao();
            medicoDao = new MedicoDao();
            usuarioDao = new UsuarioDao();
            farmaceutaDao = new FarmaceutaDao();
            recetaDao = new RecetaDao();
            prescripcionDao = new PrescripcionDao();
        } catch (Exception ex) {}
    }

    public void stop(){
        try {
            Database.instance().close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // ================= Medicos =================

    public void create(Medico e) throws Exception {
        medicoDao.create(e);
    }

    public Medico read(Medico e) throws Exception {
        return medicoDao.read(e.getId());
    }

    public Medico read(String e) throws Exception {
        return medicoDao.read(e);
    }

    public List<Medico> findAllMedicos(){
        return medicoDao.findAll();
    }

    public List<Medico> filtrarMedicos(String tipo, String texto) {
        List<Medico> list = new ArrayList<>();

        switch (tipo) {
            case "Id":
                list = medicoDao.searchById(texto);
                break;
            case "Nombre":
                list = medicoDao.searchByName(texto);
                break;
        }
        return list;
    }

    public void removeMedico(Medico e) throws Exception{
        medicoDao.remove(e);
    }

    public void update(Medico e)throws Exception{
        medicoDao.update(e);
    }

    // ================= Farmaceuta =================

    public void create(Farmaceuta e) throws Exception {
        farmaceutaDao.create(e);
    }

    public List<Farmaceuta> findAllFarmaceutas(){
        return farmaceutaDao.findAll();
    }

    public List<Farmaceuta> filtrarFarmaceutas(String tipo, String texto) {
        List<Farmaceuta> list = new ArrayList<>();
        switch (tipo) {
            case "Id":
                list = farmaceutaDao.searchById(texto);
                break;
            case "Nombre":
                list = farmaceutaDao.searchByName(texto);
                break;
        }
        return list;
    }

    public void update(Farmaceuta e) throws Exception {
        farmaceutaDao.update(e);
    }

    public void removeFarmaceuta(Farmaceuta e) throws Exception {
        farmaceutaDao.delete(e);
    }

    // ================= Pacientes =================

    public void create(Paciente e) throws Exception {
        pacienteDao.create(e);
    }

    public List<Paciente> findAllPacientes() {
        return pacienteDao.findAll();
    }

    public void update(Paciente e) throws Exception {
        pacienteDao.update(e);
    }

    public List<Paciente> filtrarPacientes(String tipo, String texto) {
        List<Paciente> result=new ArrayList<Paciente>();

        switch (tipo){
            case "Id":
                result = pacienteDao.searchById(texto);
                break;
            case "Nombre":
                result = pacienteDao.searchByName(texto);
                break;
            case "Telefono":
                result = pacienteDao.findByPhone(texto);
                break;
        }

        return result;
    }

    public void removePaciente(Paciente e) throws Exception {
        pacienteDao.delete(e);
    }

    // ================= Medicamento =================

    public void create(Medicamento e) throws Exception {
        medicamentoDao.create(e);
    }

    public void update(Medicamento e) throws Exception {
        medicamentoDao.update(e);
    }

    public List<Medicamento> findAllMedicamentos() {
        return medicamentoDao.findAll();
    }

    public List<Medicamento> filtrarMedicamentos(String tipo, String texto) {
        List<Medicamento> result=new ArrayList<Medicamento>();
        switch (tipo){
            case "Codigo":
                result = medicamentoDao.searchById(texto);
                break;
            case "Nombre":
                result = medicamentoDao.searchByName(texto);
                break;
            case "Presentacion":
                result = medicamentoDao.findByForm(texto);
                break;
        }

        return result;
    }

    public void removeMedicamento(Medicamento e) throws Exception {
        medicamentoDao.delete(e);
    }

    // ================= Recetas =================

    public List<Receta> findAllRecetas() {
        return recetaDao.findAll();
    }

    public List<Receta> filtrarRecetas(String tipo, String texto) {
        List<Receta> result=new ArrayList<Receta>();
        switch (tipo){
            case "ID_PACIENTE":
                result = recetaDao.searchByPaciente(texto);
                break;
            case "ESTADO":
                result = recetaDao.searchByEstado(texto);
                break;
        }
        return result;
    }

    public List<Receta> filtrarRecetas(String tipo, String texto1, String texto2){
        List<Receta> result = new ArrayList<Receta>();
        switch (tipo) {
            case "ID_PACIENTE_Y_ESTADO":
                result = recetaDao.searchByPaciente_Estado(texto1, texto2);
                break;
        }
        return result;
    }

    public void create(Receta e) throws Exception {
        recetaDao.create(e);
        for(Prescripcion p : e.getPrescripciones()){
            create(p);
        }
    }

    public void update(Receta e) throws Exception {
        recetaDao.update(e);
    }

    // ================= Prescripciones =================

    public void create(Prescripcion e) throws Exception {
        prescripcionDao.create(e);
    }

    // ================= Usuario =================

    public Medico getUsuario() throws Exception{
        String id = Sesion.getUsuario().getId();
        return Service.getInstance().read(id);
    }

    public Usuario read(Usuario e) throws Exception {
        return usuarioDao.read(e.getId());
    }

    public void create(Usuario e) throws Exception {
        usuarioDao.create(e);
    }

    public List<Usuario> findAllUsuarios() {
        return usuarioDao.findAll();
    }

    public void update(Usuario e) throws Exception {
        usuarioDao.update(e);
    }
}
