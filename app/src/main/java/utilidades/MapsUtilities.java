package utilidades;

import org.osmdroid.util.GeoPoint;

/**
 * Created by martin on 20/03/2017.
 */

public class MapsUtilities {

    /**
     * Latitud y Longitud correspondientes al punto céntrico de Rawson;
     */
    public static final double CENTRO_MAPA_RW_LATITUD = -43.291362;

    public static final double CENTRO_MAPA_RW_LONGITUD = -65.094455;

    /**
     * Devuelve un punto ubicado en el centro de Rawson
     *
     * @return Punto geográfico ubicado en el centro de Rawson
     */
    public static final GeoPoint getCentroRawsonMapa(){
        return new GeoPoint(CENTRO_MAPA_RW_LATITUD,CENTRO_MAPA_RW_LONGITUD);
    }
}
