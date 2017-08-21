package com.apps.martin.geocolector;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import org.greenrobot.greendao.database.Database;
import java.io.File;
import modelo.DaoMaster;
import modelo.DaoSession;
import utilidades.DatabaseUtilities;
import utilidades.Session;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DescargarRuta.OnFragmentInteractionListener, VerMapa.OnFragmentInteractionListener, MedirZona.OnFragmentInteractionListener,
        ZonaMedicion.OnFragmentInteractionListener, TabMedir.OnFragmentInteractionListener, TabComentario.OnFragmentInteractionListener, TabFoto.OnFragmentInteractionListener, CargarZona.OnFragmentInteractionListener, MenuPrincipal.OnFragmentInteractionListener {

    //Variable que representa una sesion en la base de datos;
    private DaoSession daoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Para mostrar fragment principal
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new MenuPrincipal()).commit();
        }
        //Almaceno base de datos en /Android/data/com.apps.martin.geocolector
        File path = new File(getExternalFilesDir(null), "Android/data/"+getPackageName()+"/geocolectorDB");
        //Almaceno base de datos en /storage/emulated/0/geocolector
        //File path = new File(Environment.getExternalStorageDirectory(), "geocolector/geocolectorDB");
        path.getParentFile().mkdirs();

        //DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext(), path.getAbsolutePath());
        DaoMaster.DevOpenHelper helper = new DatabaseUtilities.DevOpenHelper(getApplicationContext(), path.getAbsolutePath());
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View v = navigationView.getHeaderView(0);
        TextView credenciales = (TextView ) v.findViewById(R.id.textView);
        String usuario = Session.getSession().getUsuario();
        credenciales.setText(usuario);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            salir();
        }
    }

    public void salir(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Salir de la aplicación");
        builder.setMessage("Está seguro que desea continuar?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences prefs = getSharedPreferences("Configuración", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.remove("usuario");
                editor.commit();
                finish();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        //No se usarán opciones en la actividad principal
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        if (id == R.id.nav_camera) {
            fragment = new MedirZona();
            setTitle(R.string.action_medir_zona);
        } else if (id == R.id.nav_gallery) {
            fragment = new DescargarRuta();
            setTitle(R.string.action_descargar_ruta);
        } else if (id == R.id.nav_share) {
            fragment = new CargarZona();
            setTitle(R.string.action_cargar_zona);
        } else if (id == R.id.nav_slideshow) {
            fragment = new ZonaMedicion();
            setTitle(R.string.action_zona_medicion);
        } else if (id == R.id.nav_map) {
            fragment = new VerMapa();
            setTitle(R.string.action_ver_mapa);
        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(MainActivity.this, AdministrarCuentaActivity.class));
        } else if (id == R.id.nav_exit) {
            salir();
        }

        if (fragment != null)
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.content_main, fragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }

        //Cierro el menu cada vez que selecciono un item
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    /**
     * Obtiene una instancia que hace referencia a la conexión creada con la base de datos
     *
     * @return daoSession
     */
    public DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
