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
    private Long id;

    @NotNull
    private int nro_legajo;
    private String nombre;
    private String password;
    private Boolean administrador;
    @Generated(hash = 186624813)
    public TomaEstado(Long id, int nro_legajo, String nombre, String password,
            Boolean administrador) {
        this.id = id;
        this.nro_legajo = nro_legajo;
        this.nombre = nombre;
        this.password = password;
        this.administrador = administrador;
    }
    @Generated(hash = 268110501)
    public TomaEstado() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
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
