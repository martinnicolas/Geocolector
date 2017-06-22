package com.apps.martin.geocolector;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import modelo.DaoSession;
import modelo.RutaMedicion;
import modelo.RutaMedicionDao;
import utilidades.Session;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CargarZona.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CargarZona#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CargarZona extends Fragment {
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
    private View mDCargarZonaForm;
    private View btnCargarZona;

    public CargarZona() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CargarZona.
     */
    // TODO: Rename and change types and number of parameters
    public static CargarZona newInstance(String param1, String param2) {
        CargarZona fragment = new CargarZona();
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
        rootView = inflater.inflate(R.layout.fragment_cargar_zona, container, false);
        mDCargarZonaForm = rootView.findViewById(R.id.cargar_zona_form);
        mProgressView = rootView.findViewById(R.id.download_progress);
        btnCargarZona = rootView.findViewById(R.id.boton_cargar_zona);

        if (!medidoresPendientes().isEmpty())
        {
            btnCargarZona.setEnabled(true);
            mostrarRuta();
        }
        else
        {
            btnCargarZona.setEnabled(false);
        }
        //Manejo el evento del boton cargar zona
        btnCargarZona.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    enviarMedicionesPendientes(medidoresPendientes());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return rootView;
    }

    /**
     * Envía todas las mediciones pendientes al servidor
     */
    private void enviarMedicionesPendientes(List<RutaMedicion> medidores) throws JSONException {
        for (RutaMedicion m:medidores) {
            enviarMedicion(m);
        }
    }

    /**
     * Envia los datos de la medición al servidor
     * @param ruta
     * @throws JSONException
     */
    public void enviarMedicion(final RutaMedicion ruta) throws JSONException {
        showProgress(true);
        SharedPreferences prefs = getActivity().getSharedPreferences("Configuracion", Context.MODE_PRIVATE);
        String ip_server = prefs.getString("ip_server", "");
        JSONObject credencials_and_data = new JSONObject();
        credencials_and_data.put("nombre", Session.getSession().getUsuario());
        credencials_and_data.put("user_token",Session.getSession().getUser_token());
        credencials_and_data.put("medidor_id", ruta.getMedidor_id());
        credencials_and_data.put("novedad_id",ruta.getNovedadId());
        credencials_and_data.put("estado_actual",ruta.getEstado_actual());
        credencials_and_data.put("estado_anterior",ruta.getEstado_anterior());
        credencials_and_data.put("promedio",ruta.getPromedio());
        credencials_and_data.put("demanda",ruta.getDemanda());
        credencials_and_data.put("observacion",ruta.getObservacion());
        credencials_and_data.put("fecha_medicion",ruta.getFecha());
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest getRequest = new JsonObjectRequest("http://"+ip_server+"/restful/guardar_medicion",
                credencials_and_data,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            procesarRespuesta(response,ruta);
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
    public void procesarRespuesta(JSONObject response, RutaMedicion ruta) throws JSONException {
        if (response.has("errors")){
            Toast.makeText(getActivity().getApplicationContext(), response.getString("errors"), Toast.LENGTH_SHORT).show();
        }
        else{
            ruta.setAck(true);
            ruta.update();
        }
        if (medidoresPendientes().isEmpty())
        {
            ocultarRuta();
            btnCargarZona.setEnabled(false);
        }
        showProgress(false);
    }

    /**
     * Procesa una respuseta erronea por parte del servidor
     */
    public void procesarRespuestaErronea(){
        Toast.makeText(getActivity().getApplicationContext(), "No se pudo establecer la conexion \nVerifique la configuracion.", Toast.LENGTH_SHORT).show();
        showProgress(false);
    }

    /**
     * Obtiene una lista de medidores pendientes de envio
     * @return Lista de medidores pendientes de envio
     */
    private List<RutaMedicion> medidoresPendientes(){
        //Tomo medidores cuyas mediciones no fueron recibidos por el servidor y los muestro
        RutaMedicionDao rutaMedicionDao = daoSession.getRutaMedicionDao();
        List<RutaMedicion> medidores = rutaMedicionDao.queryBuilder().where(
                RutaMedicionDao.Properties.Ack.eq(false),
                RutaMedicionDao.Properties.Medido.eq(true)).orderAsc().list();
        return medidores;
    }

    /**
     * Muestra los medidores cuyas mediciones no fueron recibidas por el servidor
     */
    private void mostrarRuta(){
        ListView ruta = (ListView) rootView.findViewById(R.id.ruta);
        List<RutaMedicion> medidores = medidoresPendientes();
        ArrayAdapter<RutaMedicion> adapter = new ArrayAdapter<RutaMedicion>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                medidores
        );
        ruta.setAdapter(adapter);
    }

    /**
     * Oculta la ruta de medidores pendientes
     */
    private void ocultarRuta(){
        ListView ruta = (ListView) rootView.findViewById(R.id.ruta);
        ruta.setVisibility(View.GONE);
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

            mDCargarZonaForm.setVisibility(show ? View.GONE : View.VISIBLE);
            mDCargarZonaForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mDCargarZonaForm.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mDCargarZonaForm.setVisibility(show ? View.GONE : View.VISIBLE);
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
