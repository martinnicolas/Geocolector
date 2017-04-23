package com.apps.martin.geocolector;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;
import java.util.List;
import modelo.DaoSession;
import modelo.Novedad;
import modelo.RutaMedicion;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabMedir.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabMedir#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabMedir extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private EditText estado_actual;
    private TextView consumo;
    private Spinner spinner;
    private View rootView;
    private boolean respuesta;
    private RutaMedicion rutaMedicion;

    public TabMedir() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabMedir.
     */
    // TODO: Rename and change types and number of parameters
    public static TabMedir newInstance(String param1, String param2) {
        TabMedir fragment = new TabMedir();
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
        rootView = inflater.inflate(R.layout.fragment_tab_medir, container, false);
        DaoSession daoSession = ((MainActivity)getActivity()).getDaoSession();
        List<Novedad> novedades = daoSession.getNovedadDao().loadAll();
        spinner = (Spinner) rootView.findViewById(R.id.spNov);
        ArrayAdapter<Novedad> adapter = new ArrayAdapter<Novedad>(getActivity(),android.R.layout.simple_spinner_item, novedades);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        estado_actual = (EditText) rootView.findViewById(R.id.edtEstAct);
        rutaMedicion = MedirZona.getMedidorActual();

        if (rutaMedicion ==  null){
            mostrarMje("Fallo al inicio","Verifique la ruta de medición");
            return rootView;
        }


        setearDatosUsuario();//Muestro los datos del usuario
        setearDatosMedidor();//Muestro los datos del medidor
        setearResumenMedicion(daoSession);//muestro los datos del resumen de la medición

        //validamos el consumo cuando el componente pierde el foco
        consumo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validar_medicion();
                }
            }
        });

        //Manejo el evento del boton guardar en la medición
        Button btnGuardar = (Button) rootView.findViewById(R.id.btnGuardar);
        /*btnGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {validarMedicion();}});*/
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {guardar();}});
        return rootView;
    }

    /***
     *
     * nuevos métodos para validacion y guardar la medicion
     *
     */

    /**
     * Registra la medición de un me
     */
    public void guardar(){
        if( validar_medicion()){
            registrarOperacion();
            obtenerMedSgte();
        }
    }

    public void registrarOperacion(){
        DaoSession daoSession = ((MainActivity)getActivity()).getDaoSession();
        //rutaMedicion.setEstado_actual(Integer.parseInt(estado_actual.getText().toString()));
        rutaMedicion.setMedido(true);
        rutaMedicion.setFecha(new Date());//estampamos la fecha y hora de la medicion
        rutaMedicion.setNovedad((Novedad)spinner.getSelectedItem());
        //rutaMedicion.setToma_estadoId();//registramos el toma estado que meidió
        //cargar comentario
        //cargar foto
        daoSession.getRutaMedicionDao().update(rutaMedicion);
        Toast.makeText(getActivity().getApplicationContext(), "Se ha guardado la medición!", Toast.LENGTH_SHORT).show();
    }

    /***
     * retorna verdadero si la medicion es correcta, falso en caso contrario
     */
    public boolean validar_medicion(){
        if( ! validar_parametros() )
            return false;

        if( ! validar_consumo() )
            return false;

        return true;
    }


    /**
     * Retorna verdadero si están ingresados la novedad de la medición y el estado del medidor, falso en caso contrario
     * @return
     */
    public boolean validar_parametros() {
        rutaMedicion.setNovedad((Novedad)spinner.getSelectedItem());

        //no hay cargadas ni la novedad ni el estado actual
        if (rutaMedicion.getNovedadId() == 0 && estado_actual.getText().toString().isEmpty() ) {
            mostrarMje("¡ERROR!", "No ingresó novedad ni estado actual");
            return false;
        }

        //no hay novedad
        if (rutaMedicion.getNovedadId() == 0) {
            mostrarMje("¡ERROR!", "No ingresó novedad");
            return false;
        }

        //no hay estado actual
        if(estado_actual.getText().toString().isEmpty()){
            mostrarMje("¡ERROR!","No ingresó el estado actual");
            return false;
        }

        rutaMedicion.setEstado_actual(Integer.parseInt(estado_actual.getText().toString()));
        consumo.setText(String.valueOf(rutaMedicion.calcularConsumo()));

        return true;
    }

    /**
     * Retorna verdadero  si la medicion es correcta, falso en caso contrario
     * @return
     */
    public boolean validar_consumo(){
        if( rutaMedicion.getEstado_actual() < rutaMedicion.getEstado_anterior())
            return confirmarConsumo("El estado actual es menor que el anterior.");

        if (rutaMedicion.consumoExcedido()) //Si el consumo se excede se necesita confirmar
            return confirmarConsumo("Alto consumo.");

        return true;
    }


    /**
     * Retorna verdadero si el usuario acepta el mensaje mostrado, falso caso contario
     * @param mje Mensaje que se mostrará al usuario
     * @return
     */
    public boolean confirmarConsumo(String mje){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Atención!");
        builder.setMessage(mje + "\nConsumo: "+rutaMedicion.calcularConsumo()+"\nDesea continuar?");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                respuesta = true;
                }
            });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    respuesta = false;
                    dialog.cancel();
                }
        });
        builder.create().show();
        return respuesta;
    }

    /**
     *
     * @param titulo Título de la ventana que se mostrará al usuario
     * @param mje mensjae que se mostrará al usuario
     */
    public void mostrarMje(String titulo, String mje){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(titulo);
        builder.setMessage(mje);
        builder.create().show();
    }

    public void obtenerMedSgte(){
        DaoSession daoSession = ((MainActivity)getActivity()).getDaoSession();
        MedirZona.setMedidorActual(RutaMedicion.obtMedActual(daoSession));//Obtengo el siguiente medidor

        if (MedirZona.getMedidorActual() ==  null){
            mostrarMje("Medición finalizada","No quedan medidores sin medir");
            return;
        }

        rutaMedicion = MedirZona.getMedidorActual();
        mostrarEnPantalla();
    }

    /**
     * Muestra toda la información correspondiente un medidor y su respectiva pantalla
     */
    public void mostrarEnPantalla(){
        limpiarForm();
        mostarDatosMedidor();
        mostrarDatosUsuario();
    }

    public void mostrarDatosUsuario(){
        TextView numero_usuario = (TextView) rootView.findViewById(R.id.txtNusr);
        TextView categoria_usuario = (TextView) rootView.findViewById(R.id.txtDescCat);
        TextView domicilio_usuario = (TextView) rootView.findViewById(R.id.txtDetDir);
        numero_usuario.setText(String.valueOf(rutaMedicion.getUsuario()));
        categoria_usuario.setText(rutaMedicion.getCategoria());
        domicilio_usuario.setText(rutaMedicion.getDomicilio());
    }

    public void mostarDatosMedidor(){
        TextView estado_anterior = (TextView) rootView.findViewById(R.id.txtDescEA);
        consumo = (TextView) rootView.findViewById(R.id.txtConsumo);
        TextView medidor = (TextView) rootView.findViewById(R.id.nro_medidor);
        estado_anterior.setText(String.valueOf(rutaMedicion.getEstado_anterior()));
        consumo.setText("");
        medidor.setText(String.valueOf(rutaMedicion.getNro_medidor()));
    }



    public void validarMedicion(){
        rutaMedicion.setEstado_actual(Integer.parseInt(estado_actual.getText().toString()));
        rutaMedicion.setMedido(true);
        rutaMedicion.setFecha(new Date());
        rutaMedicion.setNovedad((Novedad)spinner.getSelectedItem());

        if( rutaMedicion.getEstado_actual() < rutaMedicion.getEstado_anterior())
            confirmarYGuardar("El estado actual es menor que el anterior.");
        else
        {
            if (rutaMedicion.consumoExcedido()) //Si el consumo se excede se necesita confirmar
                confirmarYGuardar("Alto consumo.");
            else
                guardarMedicion();
        }
    }

    public void confirmarYGuardar(String mje){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Atención!");
        builder.setMessage(mje + "\nConsumo: "+rutaMedicion.calcularConsumo()+"\nDesea continuar?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                guardarMedicion();
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

    public void guardarMedicion(){
        DaoSession daoSession = ((MainActivity)getActivity()).getDaoSession();
        rutaMedicion.setFecha(new Date());//estampamos la fecha y hora de la medicion
        System.out.println( "hora:" + rutaMedicion.getFecha());
        daoSession.getRutaMedicionDao().update(rutaMedicion);
        Toast.makeText(getActivity().getApplicationContext(), "Se ha guardado la medición!", Toast.LENGTH_SHORT).show();
        MedirZona.setMedidorActual(RutaMedicion.obtMedActual(daoSession));//Obtengo el siguiente medidor
        setearDatosUsuario();//Muestro los datos del siguiente usuario
        setearDatosMedidor();//Muestro los datos del medidor
        setearResumenMedicion(daoSession);//muestro los datos del resumen de la medición
        limpiarForm();//Limpio form
    }



    /**
     * Setea los datos del Usuario en la vista de medición
     */
    public void setearDatosUsuario(){
        TextView numero_usuario = (TextView) rootView.findViewById(R.id.txtNusr);
        TextView categoria_usuario = (TextView) rootView.findViewById(R.id.txtDescCat);
        TextView domicilio_usuario = (TextView) rootView.findViewById(R.id.txtDetDir);
        numero_usuario.setText(String.valueOf(rutaMedicion.getUsuario()));
        categoria_usuario.setText(rutaMedicion.getCategoria());
        domicilio_usuario.setText(rutaMedicion.getDomicilio());
    }

    /**
     * Setea los datos del Medidor en la vista de medición
     */
    public void setearDatosMedidor() {
        TextView estado_anterior = (TextView) rootView.findViewById(R.id.txtDescEA);
        consumo = (TextView) rootView.findViewById(R.id.txtConsumo);
        TextView medidor = (TextView) rootView.findViewById(R.id.nro_medidor);
        estado_anterior.setText(String.valueOf(rutaMedicion.getEstado_anterior()));
        //consumo.setText(String.valueOf(rutaMedicion.calcularConsumo()));
        consumo.setText("");
        medidor.setText(String.valueOf(rutaMedicion.getNro_medidor()));
    }

    public void setearResumenMedicion(DaoSession daoSession){
        RutaMedicion.iniciarContadores(daoSession);

        //obtenemos los text del fragment
        TextView MA = (TextView) rootView.findViewById(R.id.txtMANL);
        TextView MAL = (TextView) rootView.findViewById(R.id.txtMAL);
        TextView MER = (TextView) rootView.findViewById(R.id.txtMERNL);
        TextView MERL = (TextView) rootView.findViewById(R.id.txtMERL);
        TextView MEA = (TextView) rootView.findViewById(R.id.txtMEANL);
        TextView MEAL = (TextView) rootView.findViewById(R.id.txtMEAL);
        TextView totalNL = (TextView) rootView.findViewById(R.id.txtTotalNL);
        TextView totalL = (TextView) rootView.findViewById(R.id.txtTotalL);

        //cargamos los valores de las variables
        MA.setText(String.valueOf(RutaMedicion.conMANoLeidos));
        MAL.setText(String.valueOf(RutaMedicion.conMALeidos));
        MER.setText(String.valueOf(RutaMedicion.contMERNoLeidos));
        MERL.setText(String.valueOf(RutaMedicion.contMERLeidos));
        MEA.setText(String.valueOf(RutaMedicion.contMEANoLeidos));
        MEAL.setText(String.valueOf(RutaMedicion.contMEALeidos));
        totalNL.setText(String.valueOf(RutaMedicion.totMedNoLeidos));
        totalL.setText(String.valueOf(RutaMedicion.totMedLeidos));
    }


    /**
     * Limpio los editText del formulario
     */
    public void limpiarForm(){
        EditText estado_actual = (EditText) rootView.findViewById(R.id.edtEstAct);
        estado_actual.setText("");
        spinner.setSelection(0);//apuntamos el spinner a la primer posición
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