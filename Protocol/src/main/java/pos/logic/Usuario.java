package pos.logic;

import java.io.Serializable;
import java.util.Objects;

public class Usuario implements Serializable {
    private String id;
    private String password;
    private String rol;

    public Usuario(String id, String password, String rol) {
        this.id = id;
        this.password = password;
        this.rol = rol;
    }

    public Usuario(){
        this.id = "";
        this.password = "";
        this.rol = "";
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario e = (Usuario) o;
        return Objects.equals(id, e.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
