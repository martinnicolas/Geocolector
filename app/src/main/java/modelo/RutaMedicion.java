package modelo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import org.greenrobot.greendao.DaoException;

/**
 * Created by fede on 14/03/2017.
 */

@Entity
public class RutaMedicion {

    @Id
    private Long id;

    @NotNull
    private String domicilio;
    private String categoria;
    private int nro_medidor;
    private int usuario;
    private String latitud;
    private String longitud;
    private int estado_anterior;
    private int promedio;
    private double multipliacdor;
    private int getEstado_actual;
    private Boolean medido;
    private Date fecha;
    private double demanda;
    private String observacion;
    //foto

    //declaracion de claves foraneas
    private Long toma_estadoId;
    private Long tipo_medidorId;
    private Long zonaId;
    private long cod_novedadId;

    //definimos la clave foranea para el toma estado
    @ToOne(joinProperty = "toma_estadoId")
    private TomaEstado tomaEstado;

    //definimos la clave foranea para el tipo medidor
    @ToOne(joinProperty = "tipo_medidorId")
    private TipoMedidor tipoMedidor;

    //definimos la clave foranea a la zona
    @ToOne(joinProperty = "zonaId")
    private Zona zona;

    //definimos la clave foranea a la novedad
    @ToOne(joinProperty = "cod_novedadId")
    private Novedad novedad;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 253730192)
    private transient RutaMedicionDao myDao;

    @Generated(hash = 1103576339)
    public RutaMedicion(Long id, @NotNull String domicilio, String categoria,
            int nro_medidor, int usuario, String latitud, String longitud,
            int estado_anterior, int promedio, double multipliacdor,
            int getEstado_actual, Boolean medido, Date fecha, double demanda,
            String observacion, Long toma_estadoId, Long tipo_medidorId,
            Long zonaId, long cod_novedadId) {
        this.id = id;
        this.domicilio = domicilio;
        this.categoria = categoria;
        this.nro_medidor = nro_medidor;
        this.usuario = usuario;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estado_anterior = estado_anterior;
        this.promedio = promedio;
        this.multipliacdor = multipliacdor;
        this.getEstado_actual = getEstado_actual;
        this.medido = medido;
        this.fecha = fecha;
        this.demanda = demanda;
        this.observacion = observacion;
        this.toma_estadoId = toma_estadoId;
        this.tipo_medidorId = tipo_medidorId;
        this.zonaId = zonaId;
        this.cod_novedadId = cod_novedadId;
    }

    @Generated(hash = 1731830566)
    public RutaMedicion() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomicilio() {
        return this.domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getNro_medidor() {
        return this.nro_medidor;
    }

    public void setNro_medidor(int nro_medidor) {
        this.nro_medidor = nro_medidor;
    }

    public int getUsuario() {
        return this.usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public String getLatitud() {
        return this.latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return this.longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public int getEstado_anterior() {
        return this.estado_anterior;
    }

    public void setEstado_anterior(int estado_anterior) {
        this.estado_anterior = estado_anterior;
    }

    public int getPromedio() {
        return this.promedio;
    }

    public void setPromedio(int promedio) {
        this.promedio = promedio;
    }

    public double getMultipliacdor() {
        return this.multipliacdor;
    }

    public void setMultipliacdor(double multipliacdor) {
        this.multipliacdor = multipliacdor;
    }

    public int getGetEstado_actual() {
        return this.getEstado_actual;
    }

    public void setGetEstado_actual(int getEstado_actual) {
        this.getEstado_actual = getEstado_actual;
    }

    public Boolean getMedido() {
        return this.medido;
    }

    public void setMedido(Boolean medido) {
        this.medido = medido;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getDemanda() {
        return this.demanda;
    }

    public void setDemanda(double demanda) {
        this.demanda = demanda;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Long getToma_estadoId() {
        return this.toma_estadoId;
    }

    public void setToma_estadoId(Long toma_estadoId) {
        this.toma_estadoId = toma_estadoId;
    }

    public Long getTipo_medidorId() {
        return this.tipo_medidorId;
    }

    public void setTipo_medidorId(Long tipo_medidorId) {
        this.tipo_medidorId = tipo_medidorId;
    }

    public Long getZonaId() {
        return this.zonaId;
    }

    public void setZonaId(Long zonaId) {
        this.zonaId = zonaId;
    }

    public long getCod_novedadId() {
        return this.cod_novedadId;
    }

    public void setCod_novedadId(long cod_novedadId) {
        this.cod_novedadId = cod_novedadId;
    }

    @Generated(hash = 1361011882)
    private transient Long tomaEstado__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 101716511)
    public TomaEstado getTomaEstado() {
        Long __key = this.toma_estadoId;
        if (tomaEstado__resolvedKey == null
                || !tomaEstado__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TomaEstadoDao targetDao = daoSession.getTomaEstadoDao();
            TomaEstado tomaEstadoNew = targetDao.load(__key);
            synchronized (this) {
                tomaEstado = tomaEstadoNew;
                tomaEstado__resolvedKey = __key;
            }
        }
        return tomaEstado;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1101756409)
    public void setTomaEstado(TomaEstado tomaEstado) {
        synchronized (this) {
            this.tomaEstado = tomaEstado;
            toma_estadoId = tomaEstado == null ? null : tomaEstado.getId();
            tomaEstado__resolvedKey = toma_estadoId;
        }
    }

    @Generated(hash = 314399599)
    private transient Long tipoMedidor__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 976109909)
    public TipoMedidor getTipoMedidor() {
        Long __key = this.tipo_medidorId;
        if (tipoMedidor__resolvedKey == null
                || !tipoMedidor__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TipoMedidorDao targetDao = daoSession.getTipoMedidorDao();
            TipoMedidor tipoMedidorNew = targetDao.load(__key);
            synchronized (this) {
                tipoMedidor = tipoMedidorNew;
                tipoMedidor__resolvedKey = __key;
            }
        }
        return tipoMedidor;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1195830103)
    public void setTipoMedidor(TipoMedidor tipoMedidor) {
        synchronized (this) {
            this.tipoMedidor = tipoMedidor;
            tipo_medidorId = tipoMedidor == null ? null : tipoMedidor.getId();
            tipoMedidor__resolvedKey = tipo_medidorId;
        }
    }

    @Generated(hash = 808118032)
    private transient Long zona__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 66837197)
    public Zona getZona() {
        Long __key = this.zonaId;
        if (zona__resolvedKey == null || !zona__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ZonaDao targetDao = daoSession.getZonaDao();
            Zona zonaNew = targetDao.load(__key);
            synchronized (this) {
                zona = zonaNew;
                zona__resolvedKey = __key;
            }
        }
        return zona;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 458062340)
    public void setZona(Zona zona) {
        synchronized (this) {
            this.zona = zona;
            zonaId = zona == null ? null : zona.getId();
            zona__resolvedKey = zonaId;
        }
    }

    @Generated(hash = 413368274)
    private transient Long novedad__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1811429371)
    public Novedad getNovedad() {
        long __key = this.cod_novedadId;
        if (novedad__resolvedKey == null || !novedad__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            NovedadDao targetDao = daoSession.getNovedadDao();
            Novedad novedadNew = targetDao.load(__key);
            synchronized (this) {
                novedad = novedadNew;
                novedad__resolvedKey = __key;
            }
        }
        return novedad;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1150905274)
    public void setNovedad(@NotNull Novedad novedad) {
        if (novedad == null) {
            throw new DaoException(
                    "To-one property 'cod_novedadId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.novedad = novedad;
            cod_novedadId = novedad.getId();
            novedad__resolvedKey = cod_novedadId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 102263451)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRutaMedicionDao() : null;
    }


}
