package com.apps.martin.geocolector;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrarseActivity extends AppCompatActivity {

    private View mProgressView;
    private View signUpFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        signUpFormView = findViewById(R.id.sign_up_form);
        mProgressView = findViewById(R.id.login_progress);

        Button signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText mEmailView = (EditText) findViewById(R.id.email);
                EditText mPasswordView = (EditText) findViewById(R.id.password);
                EditText mConfirmPasswordView = (EditText) findViewById(R.id.confirm_password);

                // Reset errors.
                mEmailView.setError(null);
                mPasswordView.setError(null);

                // Store values at the time of the register attempt.
                final String email = mEmailView.getText().toString();
                final String password = mPasswordView.getText().toString();
                final String confirm_password = mConfirmPasswordView.getText().toString();

                boolean cancel = false;
                View focusView = null;

                // Check for a valid confirmed password.
                if (TextUtils.isEmpty(confirm_password)) {
                    mConfirmPasswordView.setError(getString(R.string.error_field_required));
                    focusView = mConfirmPasswordView;
                    cancel = true;
                } else if (!isPasswordValid(confirm_password)) {
                    mConfirmPasswordView.setError(getString(R.string.error_invalid_password));
                    focusView = mConfirmPasswordView;
                    cancel = true;
                } else if (!confirmedPassword(confirm_password)) {
                    mConfirmPasswordView.setError(getString(R.string.error_confirm_password));
                    focusView = mConfirmPasswordView;
                    cancel = true;
                }

                // Check for a valid password.
                if (TextUtils.isEmpty(password)) {
                    mPasswordView.setError(getString(R.string.error_field_required));
                    focusView = mPasswordView;
                    cancel = true;
                } else if (!isPasswordValid(password)) {
                    mPasswordView.setError(getString(R.string.error_invalid_password));
                    focusView = mPasswordView;
                    cancel = true;
                }

                // Check for a valid email address.
                if (TextUtils.isEmpty(email)) {
                    mEmailView.setError(getString(R.string.error_field_required));
                    focusView = mEmailView;
                    cancel = true;
                } else if (!isEmailValid(email)) {
                    mEmailView.setError(getString(R.string.error_invalid_email));
                    focusView = mEmailView;
                    cancel = true;
                }

                if (cancel) {
                    // There was an error; don't attempt sign up and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                } else {
                    showProgress(true);
                    SharedPreferences prefs = getSharedPreferences("Configuracion", Context.MODE_PRIVATE);
                    String ip_server = prefs.getString("ip_server", "");
                    RequestQueue queue = Volley.newRequestQueue(RegistrarseActivity.this);
                    StringRequest postRequest = new StringRequest(Request.Method.POST, "http://"+ip_server+"/restful/log_up",
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("1")){
                                        showProgress(false);
                                        finish();
                                        Toast.makeText(getApplicationContext(), "Se ha registrado con exito", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        showProgress(false);
                                        Toast.makeText(getApplicationContext(), "Ocurrio un error durante la creacion \n contactese con el administrador", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    showProgress(false);
                                    Toast.makeText(getApplicationContext(), "No se pudo establecer la conexion \n Verifique la configuracion.", Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("nombre", email);
                            params.put("contrasenia", password);

                            return params;
                        }
                    };
                    queue.add(postRequest);
                }
            }
        });
    }

    private boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private boolean confirmedPassword(String confirm_password) {
        EditText password = (EditText) findViewById(R.id.password);
        String password_string = password.getText().toString();
        return password_string.equals(confirm_password);
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

            signUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            signUpFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    signUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            signUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
