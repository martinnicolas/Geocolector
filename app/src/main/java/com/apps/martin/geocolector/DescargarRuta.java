package com.apps.martin.geocolector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.DaoSession;
import modelo.RutaMedicion;
import utilidades.Session;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DescargarRuta.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DescargarRuta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DescargarRuta extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private DaoSession daoSession;
    private View rootView;

    public DescargarRuta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DescargarRuta.
     */
    // TODO: Rename and change types and number of parameters
    public static DescargarRuta newInstance(String param1, String param2) {
        DescargarRuta fragment = new DescargarRuta();
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
        daoSession = ((MainActivity)getActivity()).getDaoSession();
        rootView = inflater.inflate(R.layout.fragment_descargar_ruta, container, false);

        //Manejo el evento del boton guardar en la medición
        Button btnDescargar= (Button) rootView.findViewById(R.id.boton_descargar_ruta);
        btnDescargar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                descargarRuta();
            }
        });

        List<RutaMedicion> ruta = daoSession.getRutaMedicionDao().loadAll();
        if (!ruta.isEmpty())
            mostrar_ruta();

        return rootView;
    }

    public void descargarRuta(){
        SharedPreferences prefs = getActivity().getSharedPreferences("Configuracion", Context.MODE_PRIVATE);
        String ip_server = prefs.getString("ip_server", "");
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest getRequest = new JsonArrayRequest("http://"+ip_server+"/restful/descargar_ruta",
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        procesarRespuesta(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        procesarRespuestaErronea();
                    }
                }
        );
        queue.add(getRequest);
    }

    /**
     * Procesa la respuesta del servidor
     *
     * @param response
     */
    private void procesarRespuesta(JSONArray response){
        ArrayList<JSONObject> medidores = new ArrayList<>();
        for (int i=0;i<response.length();i++){
            try {
                medidores.add(response.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        guardarRuta(medidores);
    }

    /**
     * Procesa una respuseta erronea por parte del servidor
     */
    private void procesarRespuestaErronea(){
        Toast.makeText(getActivity().getApplicationContext(), "No se pudo establecer la conexion \nVerifique la configuracion.", Toast.LENGTH_SHORT).show();
    }

    /**
     * Guarda la ruta descargada
     *
     * @param medidores
     */
    private void guardarRuta(ArrayList<JSONObject> medidores){
        try{
            for (JSONObject m: medidores) {
                RutaMedicion rutaMedicion = new RutaMedicion();
                rutaMedicion.setDomicilio(m.getString("domicilio_postal"));
                rutaMedicion.setCategoria(m.getString("categoria"));
                rutaMedicion.setNro_medidor(m.getInt("numero_medidor"));
                rutaMedicion.setUsuario(m.getInt("numero"));
                rutaMedicion.setLatitud(m.getString("latitud"));
                rutaMedicion.setLongitud(m.getString("longitud"));
                rutaMedicion.setEstado_anterior(0);//Chequear
                rutaMedicion.setPromedio(0);//Chequear
                rutaMedicion.setMultiplicador(0);
                rutaMedicion.setEstado_actual(0);//Chequear
                rutaMedicion.setMedido(false);//Chequear
                rutaMedicion.setFecha(new Date());//Chequear
                rutaMedicion.setDemanda(0);//Chequear
                rutaMedicion.setObservacion("");
                rutaMedicion.setTomaEstado(Session.getSession().getTomaEstado());
                rutaMedicion.setTipo_medidorId((long)m.getInt("tipo_medidor_id"));
                rutaMedicion.setZonaId((long)m.getInt("zona_id"));
                rutaMedicion.setNovedadId(0l);
                daoSession.getRutaMedicionDao().insert(rutaMedicion);
            }
            mostrar_ruta();
            Toast.makeText(getActivity().getApplicationContext(), "La ruta se descargo con exito!.", Toast.LENGTH_SHORT).show();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    /**
     * Muestra la ruta descargada
     */
    private void mostrar_ruta(){
        ListView ruta = (ListView) rootView.findViewById(R.id.ruta);
        List<RutaMedicion> medidores = daoSession.getRutaMedicionDao().loadAll();
        ArrayAdapter<RutaMedicion> adapter = new ArrayAdapter<RutaMedicion>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                medidores
        );
        ruta.setAdapter(adapter);
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
