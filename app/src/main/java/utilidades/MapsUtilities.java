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

    public static Drawable getDirectionIcon(Context context, int instruction){
        Drawable icon = null;
        switch (instruction){
            case 0: icon = context.getResources().getDrawable(R.drawable.ic_empty);
                break;
            case 1: icon = context.getResources().getDrawable(R.drawable.ic_continue);
                break;
            case 2: icon = context.getResources().getDrawable(R.drawable.ic_continue);
                break;
            case 3: icon = context.getResources().getDrawable(R.drawable.ic_slight_left);
                break;
            case 4: icon = context.getResources().getDrawable(R.drawable.ic_turn_left);
                break;
            case 5: icon = context.getResources().getDrawable(R.drawable.ic_sharp_left);
                break;
            case 6: icon = context.getResources().getDrawable(R.drawable.ic_slight_right);
                break;
            case 7: icon = context.getResources().getDrawable(R.drawable.ic_turn_right);
                break;
            case 8: icon = context.getResources().getDrawable(R.drawable.ic_sharp_right);
                break;
            case 9: icon = context.getResources().getDrawable(R.drawable.ic_empty);
                break;
            case 10: icon = context.getResources().getDrawable(R.drawable.ic_empty);
                break;
            case 11: icon = context.getResources().getDrawable(R.drawable.ic_continue);
                break;
            case 12: icon = context.getResources().getDrawable(R.drawable.ic_u_turn);
                break;
            case 13: icon = context.getResources().getDrawable(R.drawable.ic_u_turn);
                break;
            case 14: icon = context.getResources().getDrawable(R.drawable.ic_u_turn);
                break;
            case 15: icon = context.getResources().getDrawable(R.drawable.ic_slight_left);
                break;
            case 16: icon = context.getResources().getDrawable(R.drawable.ic_slight_right);
                break;
            case 17: icon = context.getResources().getDrawable(R.drawable.ic_slight_left);
                break;
            case 18: icon = context.getResources().getDrawable(R.drawable.ic_slight_right);
                break;
            case 19: icon = context.getResources().getDrawable(R.drawable.ic_continue);
                break;
            case 20: icon = context.getResources().getDrawable(R.drawable.ic_empty);
                break;
            case 21: icon = context.getResources().getDrawable(R.drawable.ic_empty);
                break;
            case 22: icon = context.getResources().getDrawable(R.drawable.ic_empty);
                break;
            case 23: icon = context.getResources().getDrawable(R.drawable.ic_empty);
                break;
            case 24: icon = context.getResources().getDrawable(R.drawable.ic_arrived);
                break;
            case 25: icon = context.getResources().getDrawable(R.drawable.ic_arrived);
                break;
            case 26: icon = context.getResources().getDrawable(R.drawable.ic_arrived);
                break;
            case 27: icon = context.getResources().getDrawable(R.drawable.ic_roundabout);
                break;
            case 28: icon = context.getResources().getDrawable(R.drawable.ic_roundabout);
                break;
            case 29: icon = context.getResources().getDrawable(R.drawable.ic_roundabout);
                break;
            case 30: icon = context.getResources().getDrawable(R.drawable.ic_roundabout);
                break;
            case 31: icon = context.getResources().getDrawable(R.drawable.ic_roundabout);
                break;
            case 32: icon = context.getResources().getDrawable(R.drawable.ic_roundabout);
                break;
            case 33: icon = context.getResources().getDrawable(R.drawable.ic_roundabout);
                break;
            case 34: icon = context.getResources().getDrawable(R.drawable.ic_roundabout);
                break;
            case 35: icon = context.getResources().getDrawable(R.drawable.ic_empty);
                break;
            case 36: icon = context.getResources().getDrawable(R.drawable.ic_empty);
                break;
            case 37: icon = context.getResources().getDrawable(R.drawable.ic_empty);
                break;
            case 38: icon = context.getResources().getDrawable(R.drawable.ic_empty);
                break;
            case 39: icon = context.getResources().getDrawable(R.drawable.ic_empty);
                break;
        }
        return icon;
    }
}
