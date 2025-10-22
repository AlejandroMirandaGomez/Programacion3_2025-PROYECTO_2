package pos.logic;

import pos.logic.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Worker {
    Server srv;
    Socket s;
    Service service;
    ObjectOutputStream os;
    ObjectInputStream is;

    public Worker(Server srv, Socket s, Service service) {
        try{
            this.srv=srv;
            this.s=s;
            os = new ObjectOutputStream(s.getOutputStream());
            is = new ObjectInputStream(s.getInputStream());
            this.service=service;
        } catch (IOException ex) { System.out.println(ex); }
    }

    boolean continuar;
    public void start(){
        try {
            System.out.println("Worker atendiendo peticiones...");
            Thread t = new Thread(new Runnable(){
                public void run(){
                    listen();
                }
            });
            continuar = true;
            t.start();
        } catch (Exception ex) { }
    }
    
    public void stop(){
        continuar=false;
        System.out.println("Conexion cerrada...");
    }
    
    public void listen(){
        int method;
        while (continuar) {
            try {
                method = is.readInt();
                System.out.println("Operacion: " + method);
                switch (method) {

                    // ================= Medicos =================

                    case Protocol.MEDICO_CREATE:
                        try{
                            Medico e = (Medico) is.readObject();
                            service.create(e);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                        }catch (Exception ex){
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_READ:
                        try{
                            Medico e = (Medico) is.readObject();
                            Medico data = service.read(e);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                            os.writeObject(data);
                        }catch (Exception ex){
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_UPDATE:
                        try {
                            Medico e = (Medico) is.readObject();
                            service.update(e);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_DELETE:
                        try {
                            Medico e = (Medico) is.readObject();
                            service.removeMedico(e);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_FIND_ALL:
                        try {
                            List<Medico> data = service.findAllMedicos();
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                            os.writeObject(data);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.MEDICO_FILTER:
                        try {
                            String tipo = is.readUTF();
                            String texto = is.readUTF();
                            List<Medico> data = service.filtrarMedicos(tipo, texto);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                            os.writeObject(data);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;

                    // ================= Farmaceuta =================

                    case Protocol.FARMACEUTA_CREATE:
                        try{
                            Farmaceuta e = (Farmaceuta) is.readObject();
                            service.create(e);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                        }catch (Exception ex){
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTA_UPDATE:
                        try {
                            Farmaceuta e = (Farmaceuta) is.readObject();
                            service.update(e);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTA_DELETE:
                        try {
                            Farmaceuta e = (Farmaceuta) is.readObject();
                            service.removeFarmaceuta(e);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTA_FIND_ALL:
                        try {
                            List<Farmaceuta> data = service.findAllFarmaceutas();
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                            os.writeObject(data);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.FARMACEUTA_FILTER:
                        try {
                            String tipo = is.readUTF();
                            String texto = is.readUTF();
                            List<Farmaceuta> data = service.filtrarFarmaceutas(tipo, texto);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                            os.writeObject(data);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;

                    // ================= Pacientes =================

                    case Protocol.PACIENTE_CREATE:
                        try{
                            Paciente e = (Paciente) is.readObject();
                            service.create(e);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                        }catch (Exception ex){
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_UPDATE:
                        try {
                            Paciente e = (Paciente) is.readObject();
                            service.update(e);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_DELETE:
                        try {
                            Paciente e = (Paciente) is.readObject();
                            service.removePaciente(e);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_FIND_ALL:
                        try {
                            List<Paciente> data = service.findAllPacientes();
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                            os.writeObject(data);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.PACIENTE_FILTER:
                        try {
                            String tipo = is.readUTF();
                            String texto = is.readUTF();
                            List<Paciente> data = service.filtrarPacientes(tipo, texto);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                            os.writeObject(data);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;

                    // ================= Medicamento =================

                    case Protocol.MEDICAMENTO_CREATE:
                        try{
                            Medicamento e = (Medicamento) is.readObject();
                            service.create(e);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                        }catch (Exception ex){
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_UPDATE:
                        try {
                            Medicamento e = (Medicamento) is.readObject();
                            service.update(e);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_DELETE:
                        try {
                            Medicamento e = (Medicamento) is.readObject();
                            service.removeMedicamento(e);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_FIND_ALL:
                        try {
                            List<Medicamento> data = service.findAllMedicamentos();
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                            os.writeObject(data);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.MEDICAMENTO_FILTER:
                        try {
                            String tipo = is.readUTF();
                            String texto = is.readUTF();
                            List<Medicamento> data = service.filtrarMedicamentos(tipo, texto);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                            os.writeObject(data);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;

                    // ================= Recetas =================

                    case Protocol.RECETA_CREATE:
                        try{
                            Receta e = (Receta) is.readObject();
                            service.create(e);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                        }catch (Exception ex){
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.RECETA_UPDATE:
                        try {
                            Receta e = (Receta) is.readObject();
                            service.update(e);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.RECETA_FIND_ALL:
                        try {
                            List<Receta> data = service.findAllRecetas();
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                            os.writeObject(data);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.RECETA_FILTER:
                        try {
                            String tipo = is.readUTF();
                            String texto = is.readUTF();
                            List<Receta> data = service.filtrarRecetas(tipo, texto);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                            os.writeObject(data);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;

                    // ================= Prescripciones =================

                    case Protocol.PRESCRIPCION_CREATE:
                        try{
                            Prescripcion e = (Prescripcion) is.readObject();
                            service.create(e);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                        }catch (Exception ex){
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;

                    // ================= Usuario =================

                    case Protocol.USUARIO_CREATE:
                        try{
                            Usuario e = (Usuario) is.readObject();
                            service.create(e);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                        }catch (Exception ex){
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.USUARIO_READ:
                        try{
                            Usuario e = (Usuario) is.readObject();
                            Usuario data = service.read(e);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                            os.writeObject(data);
                        }catch (Exception ex){
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.USUARIO_UPDATE:
                        try {
                            Usuario e = (Usuario) is.readObject();
                            service.update(e);
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.USUARIO_FIND_ALL:
                        try {
                            List<Usuario> data = service.findAllUsuarios();
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                            os.writeObject(data);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                    case Protocol.USUARIO_GET_CURRENT:
                        try {
                            Medico data = service.getUsuario();
                            os.writeInt(Protocol.STATUS_NO_ERROR);
                            os.writeObject(data);
                        } catch (Exception ex) {
                            os.writeInt(Protocol.STATUS_ERROR);
                        }
                        break;
                }
                os.flush();
            } catch (IOException e) {
                stop();
            }
        }
    }
}
