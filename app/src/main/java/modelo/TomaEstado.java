package modelo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by fede on 10/03/2017.
 */
@Entity
public class TomaEstado {

    @Id
    private int nro_legajo;

    @NotNull
    private String nombre;
    private String password;
    private Boolean administrador;
    @Generated(hash = 364512082)
    public TomaEstado(int nro_legajo, @NotNull String nombre, String password,
            Boolean administrador) {
        this.nro_legajo = nro_legajo;
        this.nombre = nombre;
        this.password = password;
        this.administrador = administrador;
    }
    @Generated(hash = 268110501)
    public TomaEstado() {
    }
    public int getNro_legajo() {
        return this.nro_legajo;
    }
    public void setNro_legajo(int nro_legajo) {
        this.nro_legajo = nro_legajo;
    }
    public String getNombre() {
        return this.nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Boolean getAdministrador() {
        return this.administrador;
    }
    public void setAdministrador(Boolean administrador) {
        this.administrador = administrador;
    }

}
