package modelo;

import android.database.Cursor;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

/**
 * Created by fede on 14/03/2017.
 */

@Entity
public class RutaMedicion {

    //Se controla por la Ley de Defensa del Consumidor  que el usuario no gaste un 70% mas que el promedio
    private static final int PORCENTAJE_EXCESO = 70;
    private static  final  int m3Base = 50;   //indica los metros cúbicos base de agua, son 25 por mes, como la medicion es bimensual son 50m3
    private static final int tipoMedEA = 2;
    private static final int tipoMedER = 3;
    private static final int tipoMedAgua = 1 ;

    //atributos para las estadisticas de medicion. Por defecto lo asignamos a 0
    public static int contMEANoLeidos= 0;
    public static int contMEALeidos= 0;
    public static int contMERNoLeidos= 0;
    public static int contMERLeidos= 0;
    public static int conMANoLeidos= 0;
    public static int conMALeidos= 0;
    public static int totMedNoLeidos= 0;
    public static int totMedLeidos= 0;
    public static int ultMedNoMed = 0;   //ver si vale la pena usarlo

    //Constantes para control lectura ruta
    public static int RUTA_OK = 1;
    public static int RUTA_VACIA = -1;
    public static int RUTA_COMPLETA = 0;

    @Id
    private Long id;

    private String domicilio;
    private String categoria;
    private int nro_medidor;
    private int medidor_id;
    private int usuario;
    private String latitud;
    private String longitud;
    private int estado_anterior;
    private int promedio;
    private double multiplicador;
    private int estado_actual;
    private Boolean medido;
    private Boolean ack;
    private Date fecha;
    private double demanda;
    private String observacion;
    //foto

    //declaracion de claves foraneas
    private Long toma_estadoId;
    private Long tipo_medidorId;
    private Long zonaId;
    private long novedadId;

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
    @ToOne(joinProperty = "novedadId")
    private Novedad novedad;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 253730192)
    private transient RutaMedicionDao myDao;

    @Generated(hash = 146796908)
    public RutaMedicion(Long id, String domicilio, String categoria, int nro_medidor, int medidor_id, int usuario, String latitud, String longitud, int estado_anterior,
            int promedio, double multiplicador, int estado_actual, Boolean medido, Boolean ack, Date fecha, double demanda, String observacion, Long toma_estadoId,
            Long tipo_medidorId, Long zonaId, long novedadId) {
        this.id = id;
        this.domicilio = domicilio;
        this.categoria = categoria;
        this.nro_medidor = nro_medidor;
        this.medidor_id = medidor_id;
        this.usuario = usuario;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estado_anterior = estado_anterior;
        this.promedio = promedio;
        this.multiplicador = multiplicador;
        this.estado_actual = estado_actual;
        this.medido = medido;
        this.ack = ack;
        this.fecha = fecha;
        this.demanda = demanda;
        this.observacion = observacion;
        this.toma_estadoId = toma_estadoId;
        this.tipo_medidorId = tipo_medidorId;
        this.zonaId = zonaId;
        this.novedadId = novedadId;
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

    public double getMultiplicador() {
        return this.multiplicador;
    }

    public void setMultiplicador(double multiplicador) {
        this.multiplicador = multiplicador;
    }

    public int getEstado_actual() {
        return this.estado_actual;
    }

    public void setEstado_actual(int estado_actual) {
        this.estado_actual = estado_actual;
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

    public long getNovedadId() {
        return this.novedadId;
    }

    public void setNovedadId(long novedadId) {
        this.novedadId = novedadId;
    }

    public int getMedidor_id() {
        return this.medidor_id;
    }

    public void setMedidor_id(int medidor_id) {
        this.medidor_id = medidor_id;
    }

    public Boolean getAck() {
        return this.ack;
    }

    public void setAck(Boolean ack) {
        this.ack = ack;
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
    @Generated(hash = 1778317653)
    public Novedad getNovedad() {
        long __key = this.novedadId;
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
    @Generated(hash = 1055139031)
    public void setNovedad(@NotNull Novedad novedad) {
        if (novedad == null) {
            throw new DaoException(
                    "To-one property 'novedadId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.novedad = novedad;
            novedadId = novedad.getId();
            novedad__resolvedKey = novedadId;
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

    @Override
    public String toString(){
        return "Usuario: "+this.getUsuario()+" Domicilio: "+this.getDomicilio()+" Nro. Medidor:"+this.getNro_medidor();
    }


    /**
     *  Valida si el consumo de un medidor de agua superó el porcentaje de exceso y el consumo base de agua. Es necesario hacerlo por una Ley de Defensa al Consumidor
     * @return
     */
    public boolean altoConsumoAgua(){
        int consumo = this.calcularConsumo();
        int promedio = this.getPromedio();
        int porcentaje = promedio * PORCENTAJE_EXCESO / 100;
        return ( consumo > m3Base && (consumo > porcentaje) );
    }


    /**
     * Valida si el consumo superó el porcentaje de exceso, hay que validarlo por una Ley de Defensa al Consumidor
     * @return
     */
    public boolean altoConsumoEnergia() {
        int consumo = this.calcularConsumo();
        int promedio = this.getPromedio();
        int porcentaje = promedio * PORCENTAJE_EXCESO / 100;
        return (consumo > porcentaje);
    }

    /**
     * Calcula y devuelve el consumo de un medidor, tiene en cuenta si el medidor se dio vuelta
     * @return
     */
    public int calcularConsumo(){
        int consumo;

        //verificamos si el medidor volvio a 0 (se dio vuelta el contador)
        if ( estado_actual <= estado_anterior )
            consumo = calConsMedidorAgotado();
        else
            consumo = estado_actual - estado_anterior;

        return  consumo;
    }

    /**
     * Devuelve el consumo de un medidor cuando este se da vuelta
     * @return consumo del medidor
     */
    public int calConsMedidorAgotado()
    {
        if (this.getEstado_actual() == this.getEstado_anterior())//no hubo consumo
            return 0;

        int aux = Integer.toString(getEstado_anterior()).length();
        int cantDigMed = (int) Math.pow(10, aux);
        aux = cantDigMed + getEstado_actual();
        int consumo = aux - getEstado_anterior();

        return consumo;
    }

    /**
     * Devuelve verdadero si un medidor registra un consumo excesivo, falso en caso contrario
     * @return
     */
    public boolean consumoExcedido(){
        if( this.tipo_medidorId == tipoMedAgua )
            return altoConsumoAgua();
        else
            return altoConsumoEnergia();
    }

    /**
     * Devuelve una arreglo con todos los medidores compartidos
     * @return
     */
    public List<RutaMedicion> obtMedCompartidos(){
        RutaMedicionDao rutaMedicionDao = daoSession.getRutaMedicionDao();
        List<RutaMedicion> medCom = rutaMedicionDao.queryBuilder()
                .where(RutaMedicionDao.Properties.Nro_medidor.eq(getNro_medidor()), RutaMedicionDao.Properties.Tipo_medidorId.eq(getTipo_medidorId()))
                .orderAsc()
                .list();

        return medCom;
    }

    /**
     * Retorna el primer medidor no medido de la ruta de medición
     * @param daoSession
     * @return
     */
    public static RutaMedicion obtMedActual(DaoSession daoSession ){
        RutaMedicionDao rutaMedicionDao = daoSession.getRutaMedicionDao();
        List <RutaMedicion> medActual = rutaMedicionDao.queryBuilder()
                .where(RutaMedicionDao.Properties.Medido.eq(0))
                .orderAsc()
                .limit(1)
                .list();

        //validacion para evitar error en tiempo de ejecucion
        if(medActual.isEmpty())
            return null;

        return medActual.get(0);
    }

    /**
     *  Devuelve verdadero si la ruta fue medida completamente, falso caso contrario
     *
     * @param daoSession
     * @return
     */
    public static boolean esRutaMedida(DaoSession daoSession ){
        RutaMedicionDao rutaMedicionDao = daoSession.getRutaMedicionDao();
        List <RutaMedicion> medActual = rutaMedicionDao.queryBuilder()
                .where(RutaMedicionDao.Properties.Medido.eq(1))
                .list();

        if(medActual.isEmpty())
            System.out.println("hay medidores pendientes de medicion");
        else
            System.out.println("ruta medida por completa");

        return !medActual.isEmpty();
    }

    public static boolean NOhayMedCargados(DaoSession daoSession ){
        RutaMedicionDao rutaMedicionDao = daoSession.getRutaMedicionDao();
        List <RutaMedicion> medActual = rutaMedicionDao.loadAll();

        return medActual.isEmpty();
    }

    /**
     * Devuelve el próximo medidor no medido de la ruta medición, null en caso de que no queden medidores por medir
     * @param daoSession
     * @return
     */
    public RutaMedicion obtMedSgte(DaoSession daoSession ){
        //String consulta = "SELECT * FROM ruta_medicion WHERE medido = 0 and id_ruta > " + this.getId() + " order by id_ruta asc";

        RutaMedicionDao rutaMedicionDao = daoSession.getRutaMedicionDao();
        List <RutaMedicion> medSgte;
        medSgte = rutaMedicionDao.queryBuilder()
                .where(RutaMedicionDao.Properties.Medido.eq(0),
                        RutaMedicionDao.Properties.Id.ge(this.getId()))
                .orderAsc()
                .limit(1)
                .list();

        if( medSgte.isEmpty() )
            return  null;

        return medSgte.get(0);
    }

    /**
     * Retorna todos los datos de un medidor a partir del nro del mismo
     * @param nroMed
     * @param tipoMedidor
     * @return
     */
    public RutaMedicion obtPorNroMed(DaoSession daoSession, int nroMed, int tipoMedidor){
        RutaMedicionDao rutaMedicionDao = daoSession.getRutaMedicionDao();

        List <RutaMedicion> medidor = rutaMedicionDao.queryBuilder()
                .where(RutaMedicionDao.Properties.Nro_medidor.eq(nroMed),RutaMedicionDao.Properties.Tipo_medidorId.eq(tipoMedidor))
                .limit(1)
                .list();

        if (medidor.isEmpty()) //NO EXISTE el medidor
            return  null;

        return medidor.get(0);
    }

    /**
     * Devuelve una lista con los medidores que pertenece al usuario pasado por parámetro
     * @param nro_usr número de usuario que lo identifica unívocamente
     * @return
     */
    public static List<RutaMedicion> obtMedUsuario(DaoSession daoSession, int nro_usr){
        RutaMedicionDao rutaMedicionDao = daoSession.getRutaMedicionDao();
        List <RutaMedicion> medidor = rutaMedicionDao.queryBuilder()
                .where(RutaMedicionDao.Properties.Usuario.eq(nro_usr))
                .list();
        if (medidor.isEmpty()) //NO EXISTE el usuario
            return  null;
        return medidor;
    }

    /**
     * Verifica si todos los medidores del usuario fueron medidos y enviados
     * @param daoSession Database session
     * @param nro_usr Número de usuario
     * @return true si todos los medidores del usuario fueron medidos y enviados, false en caso contrario
     */
    public static boolean medidosYEnviados(DaoSession daoSession, int nro_usr){
        boolean medidos = false;
        List<RutaMedicion> medidores = obtMedUsuario(daoSession,nro_usr);
        for (RutaMedicion m:medidores) {
            if (m.getMedido() && m.getAck()){
                medidos = true;
            }
            else
            {
                medidos = false;
                break;
            }
        }
        return medidos;
    }

    /**
     * Verifica si alguno de los medidores del usuario fue medido pero no enviado
     * @param daoSession Database session
     * @param nro_usr Número de usuario
     * @return true si alguno de los medidores del usuario fue medido pero no enviado
     */
    public static boolean medidosYNoEnviados(DaoSession daoSession, int nro_usr){
        boolean medidos = false;
        List<RutaMedicion> medidores = obtMedUsuario(daoSession,nro_usr);
        for (RutaMedicion m:medidores) {
            if (m.getMedido() && !m.getAck()){
                medidos = true;
                break;
            }
        }
        return medidos;
    }

    /**
     * Verifica si alguno de los medidores del usuario no fue medido
     * @param daoSession Database session
     * @param nro_usr Número de usuario
     * @return true si alguno de los medidores del usuario no fue medido, false en caso contrario
     */
    public static boolean noMedidos(DaoSession daoSession, int nro_usr){
        boolean medidos = false;
        List<RutaMedicion> medidores = obtMedUsuario(daoSession,nro_usr);
        for (RutaMedicion m:medidores) {
            if (!m.getMedido()){
                medidos = true;
                break;
            }
        }
        return medidos;
    }

    /**
     * Obtiene una lista de usuarios de una ruta
     * @param daoSession Database session
     * @return Listado de usuarios de una ruta
     */
    public static List<RutaMedicion> obtenerUsuarios(DaoSession daoSession){
        String query = "SELECT distinct usuario FROM ruta_medicion";
        List<RutaMedicion> usuarios = new ArrayList<>();
        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        c.moveToFirst();
        do{
            usuarios.add(obtenerUsuario(daoSession, c.getInt(0)));
        }while(c.moveToNext());
        return usuarios;
    }

    /**
     * Obtiene una lista de usuarios de una ruta
     * @param daoSession Database session
     * @return Listado de cadenas representando usuario y numero
     */
    public static ArrayList<String> obtenerNumerosUsuarios(DaoSession daoSession){
        String query = "SELECT distinct usuario FROM ruta_medicion";
        ArrayList<String> usuarios = new ArrayList<>();
        Cursor c = daoSession.getDatabase().rawQuery(query, null);
        c.moveToFirst();
        do{
            usuarios.add("Usuario N° "+c.getInt(0));
        }while(c.moveToNext());
        return usuarios;
    }

    public String obtenerEstadoMedicion(){
        return (this.getMedido())? "Medido":"No medido";
    }

    public String obtenerEstadoEnvio(){
        return (this.getAck())? "Enviado":"No enviado";
    }

    /**
     * Obtiene los datos de un usuario a partir de su numero de usuario
     * @param daoSession Database session
     * @param nro_usuario Numero de usuario
     * @return Objeto RutaMedicion con los datos del usuario
     */
    public static RutaMedicion obtenerUsuario(DaoSession daoSession, int nro_usuario){
        List<RutaMedicion> usuarios = daoSession.getRutaMedicionDao().queryBuilder().where(RutaMedicionDao.Properties.Usuario.eq(nro_usuario)).limit(1).list();
        if (usuarios.isEmpty()) //NO EXISTE el usuario
            return  null;
        return usuarios.get(0);
    }

    /**
     * Setea todos los valores de los contadores del resumen de la medicion antes de comenzar el proceso de medición
     */
    public static void iniciarContadores(DaoSession daoSession){
        //List <RutaMedicion> ruta = RutaMedicion.obtRutaCompleta(daoSession);
        List <RutaMedicion> ruta = daoSession.getRutaMedicionDao().loadAll();

        //No está cargada la ruta de medición
        if ( ruta.size() ==  0)
            return;
        reiniciarContadores();
        //seteamos los medidores
        for(int i = 0;  i < ruta.size() ; i++)
            incrementarContadores( ruta.get(i).getTipo_medidorId(), ruta.get(i).getMedido(),false );
    }

    //incrementa los contadores de los medidores que fueron y NO leidos
    public static void incrementarContadores(Long tipoMedidor, Boolean leido,boolean midiendo) {

        switch ( tipoMedidor.intValue() )
        {
            case tipoMedEA: // Energía Activa
            {
                actContME(leido, midiendo);
                break;
            }
            case tipoMedER: //Energía Reactiva
            {
                actContMER(leido,midiendo);
                break;
            }
            case tipoMedAgua: //Agua
            {
                actContMA(leido,midiendo);
                break;
            }
        }
        actualizarTotales();
    }

    /**
     * Actualiza los contadores del resuemen de medicion correspondiente a los medidores de ENERGÍA ACTIVA
     * @param leido
     */
    public static void actContME(boolean leido, boolean midiendo){
        if (leido)
        {
            contMEALeidos++;
            if(midiendo)
                contMEANoLeidos--;
        }
        else//aca solo se accede en la carga inicial
            contMEANoLeidos++;
    }


    /**
     * Actualiza los contadores del resuemen de medicion correspondiente a los medidores de ENERGÍA REACTIVA
     * @param leido    indica si el medidor fue leído
     */
    public static void actContMER(boolean leido, boolean midiendo){
        if (leido)
        {
            contMERLeidos++;
            if(midiendo)
                contMERNoLeidos--;
        }
        else
            contMERNoLeidos++;
    }

    /**
     * Actualiza los contadores del resuemen de medicion correspondiente a los medidores de AGUA
     * @param leido indica si el medidor fue leído
     */
    public static void actContMA(boolean leido, boolean midiendo){
        if (leido)
        {
            conMALeidos++;
            if(midiendo)
                conMANoLeidos--;
        }
        else
            conMANoLeidos++;
    }


    /**
     * Actualiza los contadores de la cantidad total de medidores leídos y no leídos
     */
    public static void actualizarTotales(){
        totMedNoLeidos = contMEANoLeidos + contMERNoLeidos + conMANoLeidos;
        totMedLeidos = contMEALeidos + contMERLeidos + conMALeidos;
    }

    /**
     * Pone en 0 todos los contadores del resumen de medicion
     */
    public static void reiniciarContadores()
    {
        contMEANoLeidos= 0;
        contMEALeidos= 0;
        contMERNoLeidos= 0;
        contMERLeidos= 0;
        conMANoLeidos= 0;
        conMALeidos= 0;
        totMedNoLeidos= 0;
        totMedLeidos= 0;
        ultMedNoMed = 0;
    }


    /**
     * Setea los contadores y en base a eso informa al usuario si la ruta está medida o no cargada
     * @return
     */
    public int iniciarMedicion(DaoSession daoSession)
    {
        this.iniciarContadores(daoSession);       //seteamos los contadores de la ruta
        int totalMedidores = totMedLeidos + totMedNoLeidos;

        if( totalMedidores == 0 )   //la ruta está vacía
            return RUTA_VACIA;

        if( totMedNoLeidos == 0)    //RUTA medida completa
            return RUTA_COMPLETA;

        return RUTA_OK;   //quedan medidores por medir
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 102263451)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRutaMedicionDao() : null;
    }

}
