package PROYECTO_2.presentation;

public interface ThreadListener {
    void deliver_message(String idServerEmisor, String message);
    void loggedIn(String nombre, String idServer);
    void loggedOut(String idServer);
}