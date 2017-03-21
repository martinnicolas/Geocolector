package utilidades;

import org.osmdroid.util.GeoPoint;

/**
 * Created by martin on 20/03/2017.
 */

public class MapsUtilities {

    /**
     * Latitud y Longitud correspondientes al punto céntrico de Rawson;
     */
    public static final double CENTRO_MAPA_LATITUD = -43.291362;

    public static final double CENTRO_MAPA_LONGITUD = -65.094455;

    /**
     * Devuelve un punto ubicado en el centro de Rawson
     *
     * @return
     */
    public static final GeoPoint getCentroMapa(){
        return new GeoPoint(CENTRO_MAPA_LATITUD,CENTRO_MAPA_LONGITUD);
    }
}
