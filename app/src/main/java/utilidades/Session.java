package utilidades;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import modelo.TomaEstado;

/**
 * Created by martin on 21/04/2017.
 */

public class Session {

    private int id;
    private int nro_legajo;
    private String usuario;
    private TomaEstado tomaEstado;
    private static Session actual_session;

    public Session() {
    }

    public static void createSession(Context context, JSONObject session_data) throws JSONException {
        //Creo session
        Session session = new Session();
        session.setId(session_data.getInt("id"));
        session.setNro_legajo(session_data.getInt("nro_legajo"));
        session.setUsuario(session_data.getString("email"));
        session.storeSession(context);
        //Creo TomaEstado vinculado a la session
        TomaEstado tomaEstado = new TomaEstado();
        tomaEstado.setId((long)session.getId());
        tomaEstado.setNro_legajo(session.getNro_legajo());
        tomaEstado.setNombre(session.getUsuario());
        //tomaEstado.setPassword();
        tomaEstado.setAdministrador(false);
        session.setTomaEstado(tomaEstado);
        actual_session = session;
    }

    public static Session getSession(){
        return actual_session;
    }

    private void storeSession(Context context){
        //Guardo Datos del usuario en archivo de Session
        SharedPreferences prefs = context.getSharedPreferences("Session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("id", this.getId());
        editor.putInt("nro_legajo",this.getNro_legajo());
        editor.putString("usuario", this.getUsuario());
        editor.commit();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNro_legajo() {
        return nro_legajo;
    }

    public void setNro_legajo(int nro_legajo) {
        this.nro_legajo = nro_legajo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public TomaEstado getTomaEstado() {
        return tomaEstado;
    }

    public void setTomaEstado(TomaEstado tomaEstado) {
        this.tomaEstado = tomaEstado;
    }
}
