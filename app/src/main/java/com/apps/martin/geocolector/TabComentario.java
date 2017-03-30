package com.apps.martin.geocolector;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import modelo.DaoSession;
import modelo.Novedad;
import modelo.RutaMedicion;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabComentario.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabComentario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabComentario extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TabComentario() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabComentario.
     */
    // TODO: Rename and change types and number of parameters
    public static TabComentario newInstance(String param1, String param2) {
        TabComentario fragment = new TabComentario();
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
        View rootView = inflater.inflate(R.layout.fragment_tab_comentario, container, false);

        final DaoSession daoSession = ((MainActivity)getActivity()).getDaoSession();

        final EditText comentario = (EditText) rootView.findViewById(R.id.comentario);

        //Manejo accion del boton guardar
        Button btnGuardar = (Button) rootView.findViewById(R.id.button);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RutaMedicion rutaMedicion = MedirZona.medidor_actual();
                rutaMedicion.setObservacion(comentario.getText().toString());
                daoSession.getRutaMedicionDao().update(rutaMedicion);
                Toast.makeText(getActivity().getApplicationContext(), "Se ha añadido el comentario!", Toast.LENGTH_SHORT).show();
            }
        });

        //Manejo accion del boton borrar
        Button btnBorrar = (Button) rootView.findViewById(R.id.button4);
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                comentario.setText("");
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
}
