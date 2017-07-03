package com.apps.martin.geocolector;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import modelo.DaoSession;
import modelo.RutaMedicion;

public class BuscarMedidorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_medidor);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            System.out.println("query: "+query);
            //use the query to search your data somehow
            int numero_usuario = Integer.parseInt(query);
            System.out.println("Numero de usuario: "+numero_usuario);
            //RutaMedicion.obtenerUsuario(daoSession, numero_usuario);
        }
    }
}
