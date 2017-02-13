package com.apps.martin.geocolector;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfiguracionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences prefs = getSharedPreferences("Configuracion", Context.MODE_PRIVATE);
        String ip_server = prefs.getString("ip_server", "");
        if (!ip_server.isEmpty()){
            EditText ip = (EditText) findViewById(R.id.ip_server);
            ip.setText(ip_server);
        }

        Button guardarConfigButton = (Button) findViewById(R.id.action_save);
        guardarConfigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ip = (EditText) findViewById(R.id.ip_server);
                SharedPreferences prefs = getSharedPreferences("Configuracion", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("ip_server", ip.getText().toString());
                editor.commit();
                Toast.makeText(getApplicationContext(), "Datos guardados", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
