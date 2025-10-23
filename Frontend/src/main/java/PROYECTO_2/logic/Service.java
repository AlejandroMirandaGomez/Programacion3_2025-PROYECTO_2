package PROYECTO_2.logic;

import pos.logic.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Service {
    private static Service Instance;
    public static Service getInstance(){
        if(Instance==null){
            Instance = new Service();
        }
        return Instance;
    }

    Socket s;
    ObjectOutputStream os;
    ObjectInputStream is;

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
        os.writeInt(Protocol.MEDICO_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.STATUS_NO_ERROR){}
        else throw new Exception("Medico ya existe");
    }

    public Medico read(Medico e) throws Exception {
        os.writeInt(Protocol.MEDICO_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.STATUS_NO_ERROR){
            return (Medico) is.readObject();
        }
        else throw new Exception("Medico no existe");
    }

    public void update(Medico e) throws Exception{
        os.writeInt(Protocol.MEDICO_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.STATUS_NO_ERROR){}
        else throw new Exception("Medico no existe");
    }

    public void removeMedico(Medico e) throws Exception{
        os.writeInt(Protocol.MEDICO_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.STATUS_NO_ERROR){}
        else throw new Exception("Medico no existe");
    }

    public List<Medico> findAllMedicos(){
        try{
            os.writeInt(Protocol.MEDICO_FIND_ALL);
            os.flush();
            if(is.readInt() == Protocol.STATUS_NO_ERROR){
                return (List<Medico>) is.readObject();
            }
            else return List.of();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<Medico> filtrarMedicos(String tipo, String texto) {
        try{
            os.writeInt(Protocol.MEDICO_FILTER);
            os.writeObject(tipo);
            os.writeObject(texto);
            os.flush();
            if(is.readInt() == Protocol.STATUS_NO_ERROR){
                return (List<Medico>) is.readObject();
            }
            else return List.of();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    // ================= Farmaceuta =================

    public void create(Farmaceuta e) throws Exception {
        os.writeInt(Protocol.FARMACEUTA_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.STATUS_NO_ERROR) {
            throw new Exception("Farmaceuta ya existe");
        }
    }

    public void update(Farmaceuta e) throws Exception {
        os.writeInt(Protocol.FARMACEUTA_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.STATUS_NO_ERROR) {
            throw new Exception("Farmaceuta no existe");
        }
    }

    public void removeFarmaceuta(Farmaceuta e) throws Exception {
        os.writeInt(Protocol.FARMACEUTA_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.STATUS_NO_ERROR) {
            throw new Exception("Farmaceuta no existe");
        }
    }

    public List<Farmaceuta> findAllFarmaceutas() {
        try {
            os.writeInt(Protocol.FARMACEUTA_FIND_ALL);
            os.flush();
            if (is.readInt() == Protocol.STATUS_NO_ERROR) {
                return (List<Farmaceuta>) is.readObject();
            } else {
                return List.of();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Farmaceuta> filtrarFarmaceutas(String tipo, String texto) {
        try {
            os.writeInt(Protocol.FARMACEUTA_FILTER);
            os.writeObject(tipo);
            os.writeObject(texto);
            os.flush();
            if (is.readInt() == Protocol.STATUS_NO_ERROR) {
                return (List<Farmaceuta>) is.readObject();
            } else {
                return List.of();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ================= Pacientes =================

    public void create(Paciente e) throws Exception {
        os.writeInt(Protocol.PACIENTE_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.STATUS_NO_ERROR) {
            throw new Exception("Paciente ya existe");
        }
    }

    public void update(Paciente e) throws Exception {
        os.writeInt(Protocol.PACIENTE_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.STATUS_NO_ERROR) {
            throw new Exception("Paciente no existe");
        }
    }

    public void removePaciente(Paciente e) throws Exception {
        os.writeInt(Protocol.PACIENTE_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.STATUS_NO_ERROR) {
            throw new Exception("Paciente no existe");
        }
    }

    public List<Paciente> findAllPacientes() {
        try {
            os.writeInt(Protocol.PACIENTE_FIND_ALL);
            os.flush();
            if (is.readInt() == Protocol.STATUS_NO_ERROR) {
                return (List<Paciente>) is.readObject();
            } else {
                return List.of();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Paciente> filtrarPacientes(String tipo, String texto) {
        try {
            os.writeInt(Protocol.PACIENTE_FILTER);
            os.writeObject(tipo);
            os.writeObject(texto);
            os.flush();
            if (is.readInt() == Protocol.STATUS_NO_ERROR) {
                return (List<Paciente>) is.readObject();
            } else {
                return List.of();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ================= Medicamento =================

    public void create(Medicamento e) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.STATUS_NO_ERROR) {
            throw new Exception("Medicamento ya existe");
        }
    }

    public void update(Medicamento e) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.STATUS_NO_ERROR) {
            throw new Exception("Medicamento no existe");
        }
    }

    public void removeMedicamento(Medicamento e) throws Exception {
        os.writeInt(Protocol.MEDICAMENTO_DELETE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.STATUS_NO_ERROR) {
            throw new Exception("Medicamento no existe");
        }
    }

    public List<Medicamento> findAllMedicamentos() {
        try {
            os.writeInt(Protocol.MEDICAMENTO_FIND_ALL);
            os.flush();
            if (is.readInt() == Protocol.STATUS_NO_ERROR) {
                return (List<Medicamento>) is.readObject();
            } else {
                return List.of();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Medicamento> filtrarMedicamentos(String tipo, String texto) {
        try {
            os.writeInt(Protocol.MEDICAMENTO_FILTER);
            os.writeObject(tipo);
            os.writeObject(texto);
            os.flush();
            if (is.readInt() == Protocol.STATUS_NO_ERROR) {
                return (List<Medicamento>) is.readObject();
            } else {
                return List.of();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ================= Recetas =================

    public void create(Receta e) throws Exception {
        os.writeInt(Protocol.RECETA_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.STATUS_NO_ERROR) {
            throw new Exception("Receta ya existe");
        }
    }

    public void update(Receta e) throws Exception {
        os.writeInt(Protocol.RECETA_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.STATUS_NO_ERROR) {
            throw new Exception("Error al actualizar la receta");
        }
    }

    public List<Receta> findAllRecetas() {
        try {
            os.writeInt(Protocol.RECETA_FIND_ALL);
            os.flush();
            if (is.readInt() == Protocol.STATUS_NO_ERROR) {
                return (List<Receta>) is.readObject();
            } else {
                return List.of();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Receta> filtrarRecetas(String tipo, String texto) {
        try {
            os.writeInt(Protocol.RECETA_FILTER);
            os.writeObject(tipo);
            os.writeObject(texto);
            os.flush();
            if (is.readInt() == Protocol.STATUS_NO_ERROR) {
                return (List<Receta>) is.readObject();
            } else {
                return List.of();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Receta> filtrarRecetas(String tipo, String texto1, String texto2) {
        try {
            os.writeInt(Protocol.RECETA_FILTER_2); // si tienes un opcode específico, cámbialo aquí
            os.writeObject(tipo);
            os.writeObject(texto1);
            os.writeObject(texto2);
            os.flush();
            if (is.readInt() == Protocol.STATUS_NO_ERROR) {
                return (List<Receta>) is.readObject();
            } else {
                return List.of();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ================= Prescripciones =================

    public void create(Prescripcion e) throws Exception {
        os.writeInt(Protocol.PRESCRIPCION_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.STATUS_NO_ERROR) {
            throw new Exception("No se pudo crear prescripción");
        }
    }

    // ================= Usuario =================

    public Medico getUsuario() throws Exception {
//        os.writeInt(Protocol.USUARIO_GET_CURRENT);
//        os.flush();
//        if (is.readInt() == Protocol.STATUS_NO_ERROR) {
//            return (Medico) is.readObject();
//        } else {
//            throw new Exception("Usuario no existe");
//        }
        String id = Sesion.getUsuario().getId();
        Medico e = new Medico();
        e.setId(id);
        return Service.getInstance().read(e);
    }

    public void create(Usuario e) throws Exception {
        os.writeInt(Protocol.USUARIO_CREATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.STATUS_NO_ERROR) {
            throw new Exception("Usuario ya existe");
        }
    }

    public Usuario read(Usuario e) throws Exception {
        os.writeInt(Protocol.USUARIO_READ);
        os.writeObject(e);
        os.flush();
        if (is.readInt() == Protocol.STATUS_NO_ERROR) {
            return (Usuario) is.readObject();
        } else {
            throw new Exception("Usuario no existe");
        }
    }

    public void update(Usuario e) throws Exception {
        os.writeInt(Protocol.USUARIO_UPDATE);
        os.writeObject(e);
        os.flush();
        if (is.readInt() != Protocol.STATUS_NO_ERROR) {
            throw new Exception("Usuario no existe");
        }
    }

    public List<Usuario> findAllUsuarios() {
        try {
            os.writeInt(Protocol.USUARIO_FIND_ALL);
            os.flush();
            if (is.readInt() == Protocol.STATUS_NO_ERROR) {
                return (List<Usuario>) is.readObject();
            } else {
                return List.of();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
