package PROYECTO_2.presentation;

import pos.logic.Usuario;

public interface ThreadListener {
    void deliver_message(Usuario usuarioEmisor, String message);
    void loggedIn(Usuario usuario);
    void loggedOut(Usuario usuario);
}