package pos.logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
    ServerSocket ss;
    List<Worker> workers;
    public Server() {
        try {
            ss = new ServerSocket(Protocol.PORT);
            workers = Collections.synchronizedList(new ArrayList<Worker>());
            System.out.println("Servidor iniciado...");
        } catch (IOException ex) { System.out.println(ex);}
    }

    public void run() {
        Service service = Service.getInstance();
        boolean continuar = true;
        Socket s;
        Worker worker;
        String sid; //Session Id
        while (continuar) {
            try {
                s = ss.accept();
                System.out.println("Conexion Establecida...");
                ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(s.getInputStream());
                int type= is.readInt();
                switch (type) {
                    case Protocol.SYNC:
                        sid=s.getRemoteSocketAddress().toString();
                        System.out.println("SYNCH: "+sid);
                        worker = new Worker(this,s,os,is,sid,Service.getInstance());
                        workers.add(worker);
                        System.out.println("Quedan: "+ workers.size());
                        worker.start();
                        os.writeObject(sid);
                        break;
                    case Protocol.ASYNC:
                        sid=(String)is.readObject();
                        System.out.println("SYNCH: "+sid);
                        join(s,os,is,sid);
                        break;

                }

            } catch (Exception ex) { System.out.println(ex);}
        }
    }
    public void join(Socket as, ObjectOutputStream aos, ObjectInputStream ais, String sid) {
        for (Worker w:workers){
            if(w.sid.equals(sid)){
                w.setAs(as, aos, ais);
                verQuienesEstan(w);
                break;
            }
        }
    }
    public void verQuienesEstan(Worker from){
        int i=1;
        for (Worker w : workers) {
            if (w != from) {
                String mensaje = i + "," + w.sid;
                from.sendNotification(Protocol.LOGGEDIN, mensaje);
                i++;

            }
        }
    }
    //METODO PARA PROBAR. SI SIRVE PERO EL PROBLEMA ES QUE NO TENGO EL NOMBRE
    public void remove(Worker w) {
        workers.remove(w);
        System.out.println("Quedan: " +workers.size());
    }
    public void notifyUserConnected(Worker from, String nombre, String sid) {
        String mensaje = nombre + "," + sid;
        System.out.println("Usuario conectado: " + mensaje);
        for (Worker w : workers) {
            if (w != from) {
                w.sendNotification(Protocol.LOGGEDIN, mensaje);

            }
        }
    }

    public void notifyUserDisconnected(Worker from) {
        String mensaje = from.sid;
        workers.remove(from);
        System.out.println("Usuario desconectado: " + mensaje);
        for (Worker w : workers) {
            w.sendNotification(Protocol.LOGGEDOUT, mensaje);
        }
    }
    public void sendMessage(Worker from, String destinoId, String texto) {

        for (Worker w : workers) {
            if (w.sid.equals(destinoId)) {
                String mensaje = from.sid + "," + texto;
                w.sendNotification(Protocol.DELIVER_MESSAGE, mensaje);
                break;
            }
        }
    }
}