package com.apps.martin.geocolector;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ver_mapa, container, false);

        //Context ctx = getActivity().getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        //Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        MapView map = (MapView) rootView.findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        IMapController mapController = map.getController();
        mapController.setZoom(15);
        GeoPoint centerPoint = new GeoPoint(-43.296344, -65.091966);
        mapController.setCenter(centerPoint);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        map.setTilesScaledToDpi(true);

        new EnBackground().execute(map);

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
        @Override
        protected Road doInBackground(MapView... params) {
            final MapView map = params[0];
            RoadManager roadManager = new OSRMRoadManager(getActivity());
            GeoPoint startPoint = new GeoPoint(-43.291362, -65.094455);
            final Marker startMarker = new Marker(map);
            startMarker.setPosition(startPoint);
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {map.getOverlays().add(startMarker);
                }
            });

            ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
            waypoints.add(startPoint);
            GeoPoint endPoint = new GeoPoint(-43.294682, -65.082539);
            final Marker endMarker = new Marker(map);
            endMarker.setPosition(endPoint);
            endMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {map.getOverlays().add(endMarker);
                }
            });

            waypoints.add(endPoint);

            GeoPoint endPoint2 = new GeoPoint(-43.291572, -65.086545);
            final Marker endMarker2 = new Marker(map);
            endMarker2.setPosition(endPoint2);
            endMarker2.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {map.getOverlays().add(endMarker2);
                }
            });

            waypoints.add(endPoint2);

            Road road = roadManager.getRoad(waypoints);
            final Polyline roadOverlay = RoadManager.buildRoadOverlay(road);
            roadOverlay.setWidth(10);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {map.getOverlays().add(roadOverlay);
                }
            });

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    map.invalidate();
                }
            });

            return road;
        }

        @Override
        protected void onPostExecute(Road road) {

        }
    }

}
