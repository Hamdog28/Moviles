package com.example.anthony_pc.pocketrecipe;

import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    TextInputEditText editTextName, editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        editTextEmail = (TextInputEditText) findViewById(R.id.editTextEmail);
        editTextName = (TextInputEditText) findViewById(R.id.editTextName);
        editTextPassword = (TextInputEditText) findViewById(R.id.editTextPassword);
        String name =  getIntent().getExtras().getString("first_name")+" "+getIntent().getExtras().getString("last_name");
        String email = getIntent().getExtras().getString("email");
        editTextName.setText(name);
        editTextEmail.setText(email);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/greatvibes_regular.ttf");
        TextView titulo = (TextView) findViewById(R.id.titulo);
        titulo.setTypeface(custom_font);

        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        TextView atras =(TextView)  findViewById(R.id.atras);
        atras.setTypeface(font);

        insertUser("");

    }

    public void registerClick(View view){
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
    }

    public void insertUser(String url){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "EERRORRRRRR", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String nombre = "tony alfaro";
                String correo = "tony@gmail.com";
                String contrasena = "123456";
                String descripcion = "no idea";

                params.put("nombre",nombre);
                params.put("correo",correo);
                params.put("contrasena",contrasena);
                params.put("descripcion",descripcion);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
