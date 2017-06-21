package modelo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;

/**
 * Created by fede on 09/03/2017.
 */

@Entity
public class Novedad {

    @Id
    private Long id;

    @NotNull
    private int codigo;
    private String descripcion;
    private Boolean repiteEstado;
    private String codServicio;

    @Generated(hash = 1000526213)
    public Novedad(Long id, int codigo, String descripcion, Boolean repiteEstado,
            String codServicio) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.repiteEstado = repiteEstado;
        this.codServicio = codServicio;
    }
    @Generated(hash = 1909275415)
    public Novedad() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getCodigo() {
        return this.codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

    @Override
    public String toString(){
        return this.getCodigo()+" - "+this.getDescripcion();
    }

    //retorna las novedades que se aplican a un determinado tipo de medidor
    public List<Novedad> obtenerNovedades(DaoSession daoSession, int codigoMedidor){
        int agua = 0;
        NovedadDao novedadDao = daoSession.getNovedadDao();
        List <Novedad> novedades;

        if (codigoMedidor == agua){
            novedades = novedadDao.queryBuilder()
                    .where( NovedadDao.Properties.CodServicio.in('A','T'))
                    .orderAsc()
                    .list();
        }else
        {
            novedades = novedadDao.queryBuilder()
                    .where(NovedadDao.Properties.CodServicio.in('E','T'))
                    .orderAsc()
                    .list();
        }

        if( novedades.isEmpty() )
            return  null;

        return novedades;

    }

}
