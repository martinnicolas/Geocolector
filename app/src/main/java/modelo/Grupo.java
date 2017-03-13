package modelo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by fede on 10/03/2017.
 */

@Entity
public class Grupo {
    @Id
    private String letra;

    @NotNull
    private int codigo;
    private String nombre;
    @Generated(hash = 1587847872)
    public Grupo(String letra, int codigo, String nombre) {
        this.letra = letra;
        this.codigo = codigo;
        this.nombre = nombre;
    }
    @Generated(hash = 668265261)
    public Grupo() {
    }
    public String getLetra() {
        return this.letra;
    }
    public void setLetra(String letra) {
        this.letra = letra;
    }
    public int getCodigo() {
        return this.codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getNombre() {
        return this.nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
