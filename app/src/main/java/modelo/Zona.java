package modelo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

/**
 * Created by fede on 10/03/2017.
 */

@Entity
public class Zona {
    @Id
    private int id;

    @NotNull
    private String nombre;
    private String grupoId;

    //definimos la clave foranea a Grupo
    @ToOne(joinProperty = "grupoId")
    private Grupo grupo;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1893743705)
    private transient ZonaDao myDao;

    @Generated(hash = 778736738)
    public Zona(int id, @NotNull String nombre, String grupoId) {
        this.id = id;
        this.nombre = nombre;
        this.grupoId = grupoId;
    }

    @Generated(hash = 1036844871)
    public Zona() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGrupoId() {
        return this.grupoId;
    }

    public void setGrupoId(String grupoId) {
        this.grupoId = grupoId;
    }

    @Generated(hash = 1952796897)
    private transient String grupo__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 360655879)
    public Grupo getGrupo() {
        String __key = this.grupoId;
        if (grupo__resolvedKey == null || grupo__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            GrupoDao targetDao = daoSession.getGrupoDao();
            Grupo grupoNew = targetDao.load(__key);
            synchronized (this) {
                grupo = grupoNew;
                grupo__resolvedKey = __key;
            }
        }
        return grupo;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1577487749)
    public void setGrupo(Grupo grupo) {
        synchronized (this) {
            this.grupo = grupo;
            grupoId = grupo == null ? null : grupo.getLetra();
            grupo__resolvedKey = grupoId;
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
    @Generated(hash = 1595471394)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getZonaDao() : null;
    }


}
