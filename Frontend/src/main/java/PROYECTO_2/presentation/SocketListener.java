package PROYECTO_2.presentation;

import pos.logic.Protocol;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketListener {
    ThreadListener listener;
    String sid; // Session Id
    Socket as; // Asynchronous Socket
    ObjectOutputStream aos;
    ObjectInputStream ais;

    public SocketListener(ThreadListener listener, String sid) throws Exception {
        this.listener = listener;
        as = new Socket(Protocol.SERVER, Protocol.PORT);
        this.sid = sid;
        aos = new ObjectOutputStream(as.getOutputStream());
        ais = new ObjectInputStream(as.getInputStream());
        aos.writeInt(Protocol.ASYNC);
        aos.writeObject(sid);
        aos.flush();
    }

    boolean condition = true;
    private Thread t;

    public void start() {
        t = new Thread(new Runnable() {
            public void run() { listen(); }
        });

        condition = true;
        t.start();
    }

    public void stop() {
        condition = false;
    }

    public void listen() {
        int method;
        while (condition) {
            try {
                method = ais.readInt();
                switch (method) {
                    case Protocol.DELIVER_MESSAGE:
                        try {

                            String message = (String) ais.readObject();
                            String[] msg = message.split(",");
                            deliver(msg[0], msg[1]); // emisor, mensaje
                        } catch (ClassNotFoundException ex) {
                            break;
                        }
                        break;
                    case Protocol.LOGGEDIN:
                        try {
                            String message = (String) ais.readObject();
                            String[] conectado = message.split(",");
                            loggedIn(conectado[0], conectado[1]); // nombre, id
                        } catch (ClassNotFoundException ex) {
                            break;
                        }
                        break;
                    case Protocol.LOGGEDOUT:
                        try {
                            String idServer = (String) ais.readObject();
                            loggedOut(idServer);
                        } catch (ClassNotFoundException ex) {
                            break;
                        }
                        break;
                }
            } catch (IOException ex) { condition = false; }
        }
        try {
            as.shutdownOutput();
            as.close();
        } catch (IOException e) { }
    }

    private void deliver(final String idServerEmisor, final String message) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                listener.deliver_message(idServerEmisor, message);
            }
        });
    }
    private void loggedIn(final String nombre, final String idServer) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                listener.loggedIn(nombre,idServer);
            }
        });
    }
    private void loggedOut(final String idServer) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                listener.loggedOut(idServer);
            }
        });
    }
}