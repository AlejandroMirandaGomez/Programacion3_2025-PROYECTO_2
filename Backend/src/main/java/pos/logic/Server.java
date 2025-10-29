package pos.logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
                connectedUsers(w);
                break;
            }
        }
    }
    public void connectedUsers(Worker from){
        List<String> ids = new ArrayList<>();
        for (Worker w : workers) {
            if (!Objects.equals(w.getUsuario().getId(), from.getUsuario().getId()) && !ids.contains(w.getUsuario().getId())) {
                from.sendNotification(Protocol.LOGGEDIN, w.getUsuario());
                ids.add(w.getUsuario().getId());

            }
        }
    }

    public void remove(Worker w) {
        workers.remove(w);
        System.out.println("Quedan: " +workers.size());
    }
    public void notifyUserConnected(Worker from, Usuario user) {
        System.out.println("Usuario conectado: " + user.getId());

        boolean alreadyConnected = workers.stream()
                .filter(w -> w != from)
                .anyMatch(w -> w.getUsuario().getId().equals(user.getId()));

        if (!alreadyConnected) {
            for (Worker w : workers) {
                if (!Objects.equals(w.getUsuario().getId(), user.getId())) {
                    w.sendNotification(Protocol.LOGGEDIN, user);
                }
            }
        }
    }

    public void notifyUserDisconnected(Worker from) {
        workers.remove(from);
        System.out.println("Usuario desconectado: " +from.getUsuario().getId() );

        boolean stillConnected = workers.stream()
                .anyMatch(w -> w.getUsuario().equals(from.getUsuario()));

        if (!stillConnected) {
            for (Worker w : workers) {
                w.sendNotification(Protocol.LOGGEDOUT, from.getUsuario());
            }
        }

    }
    public void sendMessage(Usuario from, Usuario destino, String texto) {

        for (Worker w : workers) {
            if (w.getUsuario().getId().equals(destino.getId())) {
                w.sendMessage(Protocol.DELIVER_MESSAGE, from , texto);
            }
        }
    }
}