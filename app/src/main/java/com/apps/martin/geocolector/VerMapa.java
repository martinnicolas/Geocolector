package com.apps.martin.geocolector;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.bonuspack.routing.RoadNode;
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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import modelo.DaoSession;
import modelo.RutaMedicion;
import utilidades.MapsUtilities;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VerMapa.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VerMapa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerMapa extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private DaoSession daoSession;
    private MapView map;
    private static ArrayList<String> mResults;
    private FolderOverlay roadNodeMarkers;

    public VerMapa() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerMapa.
     */
    // TODO: Rename and change types and number of parameters
    public static VerMapa newInstance(String param1, String param2) {
        VerMapa fragment = new VerMapa();
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
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_ver_mapa, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu){
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setSuggestionsAdapter(new SearchSuggestionsAdapter(getActivity().getApplicationContext(), daoSession));
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener()
        {

            @Override
            public boolean onSuggestionSelect(int position) {
                searchView.clearFocus();
                String numero_usuario = mResults.get(position).replaceAll("\\D","");
                searchView.setQuery("Usuario N° "+numero_usuario,false);
                int numero = Integer.parseInt(numero_usuario);
                RutaMedicion u = RutaMedicion.obtenerUsuario(daoSession,numero);
                //Las tareas que hacen peticiones http deben ejecutarse en un hilo diferente
                new EnBackground().execute(u);
                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                searchView.clearFocus();
                String numero_usuario = mResults.get(position).replaceAll("\\D","");
                searchView.setQuery("Usuario N° "+numero_usuario,false);
                int numero = Integer.parseInt(numero_usuario);
                RutaMedicion u = RutaMedicion.obtenerUsuario(daoSession,numero);
                //Las tareas que hacen peticiones http deben ejecutarse en un hilo diferente
                new EnBackground().execute(u);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                searchView.clearFocus();
                String numero_usuario = query.replaceAll("\\D","");
                searchView.setQuery("Usuario N° "+numero_usuario,false);
                int numero = Integer.parseInt(numero_usuario);
                RutaMedicion u = RutaMedicion.obtenerUsuario(daoSession,numero);
                if (u != null)
                    new EnBackground().execute(u);
                else
                    Toast.makeText(getActivity().getApplicationContext(), "El Usuario no existe en la zona de medición asignada. Verifique la zona de medición", Toast.LENGTH_SHORT).show();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.search) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        File path = new File(getActivity().getApplicationContext().getExternalFilesDir(null), "Android/data/"+getContext().getPackageName());
        Configuration.getInstance().setOsmdroidTileCache(path);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ver_mapa, container, false);
        daoSession = ((MainActivity)getActivity()).getDaoSession();

        Context ctx = getActivity().getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        map = (MapView) rootView.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        final IMapController mapController = map.getController();
        mapController.setZoom(MapsUtilities.DEFAULT_ZOOM);
        MyLocationNewOverlay mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getActivity().getApplicationContext()),map);
        mLocationOverlay.enableMyLocation();
        mLocationOverlay.enableFollowLocation();
        //Agrego repositorio de marcadores
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
        map.setKeepScreenOn(true);

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

    class EnBackground extends AsyncTask<RutaMedicion, Void, Road> {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Calculando la ruta.\nPor favor espere un momento.");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Road doInBackground(RutaMedicion... params) {
            //Usuario
            RutaMedicion u = params[0];

            //Elimino la ruta y marcadores que se habían dibujado previamente
            roadNodeMarkers.getItems().clear();

            ArrayList<GeoPoint> waypoints = new ArrayList<>();
            //Obtengo ultima ubicación del tomaestado
            GeoPoint mi_ubicacion = MapsUtilities.getUbicacion(getActivity().getApplicationContext());
            if (mi_ubicacion != null)
                waypoints.add(mi_ubicacion);

            //Obtengo ubicación del usuario y defino un punto
            GeoPoint punto = new GeoPoint(Double.parseDouble(u.getLatitud()), Double.parseDouble(u.getLongitud()));
            //Creo un marcador con la ubicacion del usuario
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
            //Agrego el marcador con la data al respositorio que los mostrara en el mapa
            roadNodeMarkers.add(marcador);
            //Agrego ubicacion del usuario a lista de puntos
            waypoints.add(punto);
            //Obtengo la ruta en base a la lista de puntos
            RoadManager roadManager = new OSRMRoadManager(getActivity());
            Road road = roadManager.getRoad(waypoints);
            //Dibujo la ruta sólo si pude conectarme y obtenerla
            if (road.mStatus == Road.STATUS_OK)
            {
                //Seteo el tipo de linea para dibujar la ruta
                Polyline roadOverlay = RoadManager.buildRoadOverlay(road);
                roadOverlay.setWidth(10);
                //Dibujo la ruta y la agrego al repositorio que la muestra en el mapa
                roadNodeMarkers.add(roadOverlay);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() { map.invalidate(); }
                });
            }

            Drawable nodeIcon = getResources().getDrawable(R.drawable.marker_node);
            //Por cada maniobra en la ruta existe un nodo, creo un marcador por cada nodo, con data de la maniobra
            for (int i=0; i<road.mNodes.size(); i++){
                RoadNode node = road.mNodes.get(i);
                Marker nodeMarker = new Marker(map);
                nodeMarker.setPosition(node.mLocation);
                nodeMarker.setIcon(nodeIcon);
                nodeMarker.setTitle("Paso "+(i+1));
                nodeMarker.setSnippet(node.mInstructions);
                TypedArray iconIds = getActivity().getApplicationContext().getResources().obtainTypedArray(R.array.direction_icons);
                int iconId = iconIds.getResourceId(node.mManeuverType, R.drawable.ic_empty);
                Drawable icon = getActivity().getApplicationContext().getResources().getDrawable(iconId);
                nodeMarker.setSubDescription(Road.getLengthDurationText(getActivity().getApplicationContext(),node.mLength,node.mDuration));
                nodeMarker.setImage(icon);
                //Agrego los nodos de la ruta al repositorio para que los muestre en el mapa
                roadNodeMarkers.add(nodeMarker);
            }

            return road;
        }

        @Override
        protected void onPostExecute(Road road) {
            pDialog.hide();
            //Si no pude conectarme y obtener la ruta
            if (road.mStatus != Road.STATUS_OK){
                //Obtengo ultima ubicación del tomaestado
                GeoPoint mi_ubicacion = MapsUtilities.getUbicacion(getActivity().getApplicationContext());
                if (mi_ubicacion != null)
                    Toast.makeText(getActivity().getApplicationContext(), "Error en la conexión.\nVerifique su conexión a internet", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity().getApplicationContext(), "Debe activar la ubicación por GPS.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public static class SearchSuggestionsAdapter extends SimpleCursorAdapter
    {
        private static final String[] mFields  = { "_id", "result" };
        private static final String[] mVisible = { "result" };
        private static final int[]    mViewIds = { android.R.id.text1 };
        private static DaoSession daoSession = null;


        public SearchSuggestionsAdapter(Context context, DaoSession dataBaseSession)
        {
            super(context, android.R.layout.simple_list_item_1, null, mVisible, mViewIds, 0);
            daoSession = dataBaseSession;
        }

        @Override
        public Cursor runQueryOnBackgroundThread(CharSequence constraint)
        {
            return new SuggestionsCursor(constraint);
        }

        private static class SuggestionsCursor extends AbstractCursor
        {

            public SuggestionsCursor(CharSequence constraint)
            {
                mResults = RutaMedicion.obtenerNumerosUsuarios(daoSession);
                if(!TextUtils.isEmpty(constraint)){
                    String constraintString = constraint.toString().toLowerCase(Locale.ROOT);
                    Iterator<String> iter = mResults.iterator();
                    while(iter.hasNext()){
                        if(!iter.next().toLowerCase(Locale.ROOT).contains(constraintString))
                        {
                            iter.remove();
                        }
                    }
                }
            }

            @Override
            public int getCount()
            {
                return mResults.size();
            }

            @Override
            public String[] getColumnNames()
            {
                return mFields;
            }

            @Override
            public long getLong(int column)
            {
                if(column == 0){
                    return mPos;
                }
                throw new UnsupportedOperationException("unimplemented");
            }

            @Override
            public String getString(int column)
            {
                if(column == 1){
                    return mResults.get(mPos);
                }
                throw new UnsupportedOperationException("unimplemented");
            }

            @Override
            public short getShort(int column)
            {
                throw new UnsupportedOperationException("unimplemented");
            }

            @Override
            public int getInt(int column)
            {
                throw new UnsupportedOperationException("unimplemented");
            }

            @Override
            public float getFloat(int column)
            {
                throw new UnsupportedOperationException("unimplemented");
            }

            @Override
            public double getDouble(int column)
            {
                throw new UnsupportedOperationException("unimplemented");
            }

            @Override
            public boolean isNull(int column)
            {
                return false;
            }
        }
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
}
