package com.apps.martin.geocolector;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MedirZona.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MedirZona#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedirZona extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TabHost TbH;

    private OnFragmentInteractionListener mListener;

    public MedirZona() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MedirZona.
     */
    // TODO: Rename and change types and number of parameters
    public static MedirZona newInstance(String param1, String param2) {
        MedirZona fragment = new MedirZona();
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
       View rootView = inflater.inflate(R.layout.fragment_medir_zona, container, false);

        //codigo introducido para manipular el tabHost
        TbH = (TabHost) rootView.findViewById(R.id.tabHost);
        TbH.setup();                                                         //lo activamos

        TabHost.TabSpec tabMedir = TbH.newTabSpec("tabMedir");  //aspectos de cada Tab (pestaña)
        TabHost.TabSpec tabComentario = TbH.newTabSpec("tabComentario");
        TabHost.TabSpec tabFoto = TbH.newTabSpec("tabFoto");

        tabMedir.setIndicator("Medir");    //qué queremos que aparezca en las pestañas
        tabMedir.setContent(R.id.tabMedir); //definimos el id de cada Tab (pestaña)
        tabMedir.setIndicator("Medir",getResources().getDrawable(R.drawable.ic_menu_exit));

        tabComentario.setIndicator("Comentario");
        tabComentario.setContent(R.id.tabMedir);

        tabFoto.setIndicator("Fotografiar");
        tabFoto.setContent(R.id.tabFoto);

        TbH.addTab(tabMedir); //añadimos los tabs ya programados
        TbH.addTab(tabComentario);
        TbH.addTab(tabFoto);

        //fin codigo para tabHost
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
