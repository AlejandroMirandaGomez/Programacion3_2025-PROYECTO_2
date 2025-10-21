package PROYECTO_2.logic;

import pos.logic.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private static Service Instance;
    Socket s;
    ObjectOutputStream os;
    ObjectInputStream is;

    public static Service getInstance(){
        if(Instance==null){
            Instance = new Service();
        }
        return Instance;
    }

    public Service() {
        try {
            s = new Socket(Protocol.SERVER, Protocol.PORT);
            os = new ObjectOutputStream(s.getOutputStream());
            is = new ObjectInputStream(s.getInputStream());
        } catch (Exception e) { System.exit(-1);}
    }

    private void disconnect() throws Exception {
        os.writeInt(Protocol.DISCONNECT);
        os.flush();
        s.shutdownOutput();
        s.close();
    }

    public void stop() {
        try {
            disconnect();
        } catch (Exception e) {
            System.exit(-1);
        }
    }

    // ================= Medicos =================

    public void create(Medico e) throws Exception {
        // Implementar
    }

    public Medico read(Medico e) throws Exception {
        return null; // Implementar
    }

    public Medico read(String e) throws Exception {
        return null; // Implementar
    }

    public List<Medico> findAll(){
        return new ArrayList<Medico>(); // Implementar
    }

    public List<Medico> filtrarMedicos(String tipo, String texto) {
        return new ArrayList<Medico>(); // Implementar
    }

    public void removeMedico(Medico e) throws Exception{
        // Implementar
    }

    public void update(Medico e) throws Exception{
        // Implementar
    }

    // ================= Farmaceuta =================

    public void create(Farmaceuta e) throws Exception {
        // Implementar
    }

    public List<Farmaceuta> findAllFarmaceutas(){
        return new ArrayList<Farmaceuta>(); // Implementar
    }

    public List<Farmaceuta> filtrarFarmaceutas(String tipo, String texto) {
        return new ArrayList<Farmaceuta>(); // Implementar
    }

    public void update(Farmaceuta e) throws Exception {
        // Implementar
    }

    public void removeFarmaceuta(Farmaceuta e) throws Exception {
        // Implementar
    }

    // ================= Pacientes =================

    public void create(Paciente e) throws Exception {
        // Implementar
    }

    public List<Paciente> getListaPacientes() {
        return new ArrayList<Paciente>(); // Implementar
    }

    public void update(Paciente e) throws Exception {
        // Implementar
    }

    public List<Paciente> filtrarPacientes(String tipo, String texto) {
        return new ArrayList<Paciente>(); // Implementar
    }

    public void removePaciente(Paciente e) throws Exception {
        // Implementar
    }

    // ================= Medicamento =================

    public void create(Medicamento e) throws Exception {
        // Implementar
    }

    public void update(Medicamento e) throws Exception {
        // Implementar
    }

    public List<Medicamento> getListaMedicamentos() {
        return new ArrayList<Medicamento>(); // Implementar
    }

    public List<Medicamento> filtrarMedicamentos(String tipo, String texto) {
        return new ArrayList<Medicamento>(); // Implementar
    }

    public void removeMedicamento(Medicamento e) throws Exception {
        // Implementar
    }

    // ================= Recetas =================

    public List<Receta> getListaRecetas() {
        return new ArrayList<Receta>(); // Implementar
    }

    public List<Receta> filtrarRecetas(String tipo, String texto) {
        return new ArrayList<Receta>(); // Implementar
    }

    public List<Receta> filtrarRecetas(String tipo, String texto1, String texto2){
        return new ArrayList<Receta>(); // Implementar
    }

    public void create(Receta e) throws Exception {
        // Implementar
    }

    public void update(Receta e) throws Exception {
        // Implementar
    }

    public Medico getUsuario() throws Exception{
        return null; // Implementar
    }


    // ================= Prescripciones =================

    public void create(Prescripcion e) throws Exception {
        // Implementar
    }

    // ================= Usuario =================

    public Usuario read(Usuario e) throws Exception {
        return null; // Implementar
    }

    public void create(Usuario e) throws Exception {
        // Implementar
    }

    public List<Usuario> getListaUsuarios() {
        return new ArrayList<Usuario>(); // Implementar
    }

    public void update(Usuario e) throws Exception {
        // Implementar
    }
}
