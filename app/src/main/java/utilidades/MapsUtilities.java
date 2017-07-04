package utilidades;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;

import com.apps.martin.geocolector.R;

import org.osmdroid.util.GeoPoint;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by martin on 20/03/2017.
 */

public class MapsUtilities {
    /**
     * Zoom por default
     */
    public static final int DEFAULT_ZOOM = 15;
    /**
     * Latitud y Longitud correspondientes al punto céntrico de Rawson
     */
    public static final double CENTRO_MAPA_RW_LATITUD = -43.291362;

    public static final double CENTRO_MAPA_RW_LONGITUD = -65.094455;

    /**
     * Devuelve un punto ubicado en el centro de Rawson
     *
     * @return Punto geográfico ubicado en el centro de Rawson
     */
    public static final GeoPoint getCentroRawsonMapa() {
        return new GeoPoint(CENTRO_MAPA_RW_LATITUD, CENTRO_MAPA_RW_LONGITUD);
    }

    public static GeoPoint getUbicacion(Context context) {
        LocationManager mLocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null){
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()){
                //Encuentro el mejor proveedor para la última ubicacion conocida
                bestLocation = l;
            }
        }
        return new GeoPoint(bestLocation);
    }


    public static LocationProvider getBestProvider(Context context){
        LocationManager mLocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        Criteria crit = new Criteria();
        crit.setAccuracy(Criteria.ACCURACY_FINE);
        crit.setPowerRequirement(Criteria.POWER_LOW);
        String provider = mLocationManager.getBestProvider(crit, true);
        return mLocationManager.getProvider(provider);
    }
}
