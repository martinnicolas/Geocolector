package modelo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;

/**
 * Created by fede on 09/03/2017.
 */

@Entity
public class Novedad {

    @Id
    private Long id;

    @NotNull
    private String descripcion;
    private Boolean repiteEstado;
    private String codServicio;


    @Generated(hash = 1278650675)
    public Novedad(Long id, @NotNull String descripcion, Boolean repiteEstado,
            String codServicio) {
        this.id = id;
        this.descripcion = descripcion;
        this.repiteEstado = repiteEstado;
        this.codServicio = codServicio;
    }

    @Generated(hash = 1909275415)
    public Novedad() {
    }

    @Override
    public String toString(){
        return this.getCodServicio()+" - "+this.getDescripcion();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getRepiteEstado() {
        return this.repiteEstado;
    }

    public void setRepiteEstado(Boolean repiteEstado) {
        this.repiteEstado = repiteEstado;
    }

    public String getCodServicio() {
        return this.codServicio;
    }

    public void setCodServicio(String codServicio) {
        this.codServicio = codServicio;
    }
}
