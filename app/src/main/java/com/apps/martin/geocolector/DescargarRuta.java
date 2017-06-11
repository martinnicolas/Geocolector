package com.apps.martin.geocolector;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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
import com.android.volley.toolbox.JsonObjectRequest;
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

    private View mProgressView;
    private View mDescargarRutaForm;

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

        final List<RutaMedicion> ruta = daoSession.getRutaMedicionDao().loadAll();

        mDescargarRutaForm = rootView.findViewById(R.id.descargar_ruta_form);
        mProgressView = rootView.findViewById(R.id.download_progress);
        //Manejo el evento del boton descargar
        Button btnDescargar= (Button) rootView.findViewById(R.id.boton_descargar_ruta);
        btnDescargar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!ruta.isEmpty())
                    confirmarEliminarYDescargar("Quiere eliminar la ruta actual y descargar nuevamente?");
                else
                {
                    try {
                        descargarRuta();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        if (!ruta.isEmpty())
            mostrar_ruta();

        return rootView;
    }

    public void confirmarEliminarYDescargar(String mje){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Atención!");
        builder.setMessage(mje);
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    daoSession.getRutaMedicionDao().deleteAll();
                    descargarRuta();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    public void descargarRuta() throws JSONException {
        showProgress(true);
        SharedPreferences prefs = getActivity().getSharedPreferences("Configuracion", Context.MODE_PRIVATE);
        String ip_server = prefs.getString("ip_server", "");
        JSONObject credencials = new JSONObject();
        credencials.put("nombre",Session.getSession().getUsuario());
        credencials.put("user_token",Session.getSession().getUser_token());
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest getRequest = new JsonObjectRequest("http://"+ip_server+"/restful/descargar_ruta",
                credencials,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            procesarRespuesta(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
    private void procesarRespuesta(JSONObject response) throws JSONException {
        if (response.has("errors")){
            Toast.makeText(getActivity().getApplicationContext(), response.getString("errors"), Toast.LENGTH_SHORT).show();
        }
        else{
            JSONArray ruta = response.getJSONArray("ruta");
            ArrayList<JSONObject> medidores = new ArrayList<>();
            for (int i=0;i<ruta.length();i++){
                try {
                    medidores.add(ruta.getJSONObject(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            guardarRuta(medidores);
        }
        showProgress(false);
    }

    /**
     * Procesa una respuseta erronea por parte del servidor
     */
    private void procesarRespuestaErronea(){
        Toast.makeText(getActivity().getApplicationContext(), "No se pudo establecer la conexion \nVerifique la configuracion.", Toast.LENGTH_SHORT).show();
        showProgress(false);
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
                rutaMedicion.setMedidor_id(m.getInt("id_medidor"));
                rutaMedicion.setUsuario(m.getInt("numero"));
                rutaMedicion.setLatitud(m.getString("latitud"));
                rutaMedicion.setLongitud(m.getString("longitud"));
                rutaMedicion.setEstado_anterior(m.getInt("estado_anterior"));
                rutaMedicion.setPromedio(0);
                rutaMedicion.setMultiplicador(m.getInt("multiplicador"));
                rutaMedicion.setEstado_actual(0);//Chequear
                rutaMedicion.setMedido(false);//Chequear
                rutaMedicion.setAck(false);
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

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mDescargarRutaForm.setVisibility(show ? View.GONE : View.VISIBLE);
            mDescargarRutaForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mDescargarRutaForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mDescargarRutaForm.setVisibility(show ? View.GONE : View.VISIBLE);
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
