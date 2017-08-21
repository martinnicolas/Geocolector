package com.apps.martin.geocolector;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.FolderOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import modelo.DaoSession;
import modelo.RutaMedicion;
import utilidades.MapsUtilities;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ZonaMedicion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ZonaMedicion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZonaMedicion extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FolderOverlay roadNodeMarkers;

    public ZonaMedicion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ZonaMedicion.
     */
    // TODO: Rename and change types and number of parameters
    public static ZonaMedicion newInstance(String param1, String param2) {
        ZonaMedicion fragment = new ZonaMedicion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        File path = new File(getActivity().getApplicationContext().getExternalFilesDir(null), "Android/data/"+getContext().getPackageName());
        Configuration.getInstance().setOsmdroidTileCache(path);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ver_mapa, container, false);

        Context ctx = getActivity().getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        MapView map = (MapView) rootView.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        final IMapController mapController = map.getController();
        mapController.setZoom(MapsUtilities.DEFAULT_ZOOM);
        MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getActivity().getApplicationContext()),map);
        mLocationOverlay.enableMyLocation();
        mLocationOverlay.enableFollowLocation();
        roadNodeMarkers = new FolderOverlay();
        map.getOverlays().add(roadNodeMarkers);
        GeoPoint mi_ubicacion = MapsUtilities.getUbicacion(getActivity().getApplicationContext());
        if (mi_ubicacion == null){
            GeoPoint centerPoint = new GeoPoint(MapsUtilities.getCentroRawsonMapa());
            mapController.animateTo(centerPoint);
        }
        else{
            mapController.animateTo(mi_ubicacion);
        }
        map.getOverlays().add(mLocationOverlay);
        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(map);
        map.getOverlays().add(scaleBarOverlay);
        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(map);
        mRotationGestureOverlay.setEnabled(true);
        map.getOverlays().add(mRotationGestureOverlay);
        CompassOverlay mCompassOverlay = new CompassOverlay(getActivity().getApplicationContext(), new InternalCompassOrientationProvider(getActivity().getApplicationContext()), map);
        mCompassOverlay.enableCompass();
        map.getOverlays().add(mCompassOverlay);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        map.setTilesScaledToDpi(true);

        //Las tareas que hacen peticiones http deben ejecutarse en un hilo diferente
        new EnBackground().execute(map);

        //Agrego botón para centrar el mapa en la ubicación del usuario
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GeoPoint mi_ubicacion = MapsUtilities.getUbicacion(getActivity().getApplicationContext());
                if (mi_ubicacion != null){
                    mapController.animateTo(mi_ubicacion);
                }
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class EnBackground extends AsyncTask<MapView, Void, Road> {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Cargando la zona.\nPor favor espere un momento.");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Road doInBackground(MapView... params) {
            //Mapa, administrador de Rutas y Database Session
            final MapView map = params[0];
            RoadManager roadManager = new OSRMRoadManager(getActivity());
            DaoSession daoSession = ((MainActivity)getActivity()).getDaoSession();

            //Obtengo todos los medidores
            List<RutaMedicion> medidores = daoSession.getRutaMedicionDao().loadAll();
            ArrayList<GeoPoint> waypoints = new ArrayList<>();
            GeoPoint mi_ubicacion = MapsUtilities.getUbicacion(getActivity().getApplicationContext());
            if (mi_ubicacion != null)
                waypoints.add(mi_ubicacion);
            //Por cada medidor
            for (RutaMedicion m: medidores){
                //Obtengo ubicación del medidor y defino un punto
                GeoPoint punto = new GeoPoint(Double.parseDouble(m.getLatitud()), Double.parseDouble(m.getLongitud()));
                waypoints.add(punto);
            }
            //Obtengo la ruta en base a la lista de puntos
            Road road = roadManager.getRoad(waypoints);
            //Dibujo la ruta sólo si pude conectarme y obtenerla
            if (road.mStatus == Road.STATUS_OK)
            {
                //Seteo el tipo de linea para dibujar la ruta
                Polyline roadOverlay = RoadManager.buildRoadOverlay(road);
                roadOverlay.setWidth(10);
                //Dibujo la ruta y actualizo el mapa
                roadNodeMarkers.add(roadOverlay);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() { map.invalidate(); }
                });
            }

            List<RutaMedicion> usuarios = daoSession.getRutaMedicionDao().loadAll();
            //Por cada usuario
            for (RutaMedicion u: usuarios) {
                //Obtengo ubicación del usuario y defino un punto
                GeoPoint punto = new GeoPoint(Double.parseDouble(u.getLatitud()), Double.parseDouble(u.getLongitud()));
                //Creo un marcador con la ubicacion del medidor
                Marker marcador = new Marker(map);
                marcador.setPosition(punto);
                marcador.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                if (RutaMedicion.medidosYEnviados(daoSession, u.getUsuario())) //Medido y enviado
                    marcador.setIcon(getResources().getDrawable(R.drawable.greenmarker2));
                else
                if (RutaMedicion.medidosYNoEnviados(daoSession, u.getUsuario())) //Medido y no enviado
                    marcador.setIcon(getResources().getDrawable(R.drawable.yellowmarker2));
                else
                if (RutaMedicion.noMedidos(daoSession, u.getUsuario())) //No medido
                    marcador.setIcon(getResources().getDrawable(R.drawable.redmarker2));
                //Seteo titulo y data del bubble del marcador
                marcador.setTitle("Usuario N° "+u.getUsuario());
                //Obtengo medidores del usuario y agrego data al bubble
                List<RutaMedicion> medidores_de_usuario = RutaMedicion.obtMedUsuario(daoSession, u.getUsuario());
                String data_medidores = "";
                for (RutaMedicion mu : medidores_de_usuario) {
                    data_medidores = data_medidores+"Medidor N° "+mu.getNro_medidor()+" -- Orden: "+mu.getId()+"<br/>("+mu.obtenerEstadoMedicion()+", "+mu.obtenerEstadoEnvio()+")<br/>";
                }
                marcador.setSnippet(data_medidores+"<br/>Dom. serv.: "+u.getDomicilio());
                //Agrego el marcador con la data al mapa
                roadNodeMarkers.add(marcador);
            }
            return road;
        }

        @Override
        protected void onPostExecute(Road road) {
            //pDialog.hide();
            pDialog.dismiss();
            //Si no pude conectarme y obtener la ruta
            if (road.mStatus != Road.STATUS_OK)
                Toast.makeText(getActivity().getApplicationContext(), "Error en la conexión.\nVerifique su conexión a internet", Toast.LENGTH_SHORT).show();
        }
    }

}
