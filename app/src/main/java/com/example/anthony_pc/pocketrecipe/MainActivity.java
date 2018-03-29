package com.example.anthony_pc.pocketrecipe;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.answers.Answers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity {
    private RequestQueue mQueue;

    TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Answers());
        setContentView(R.layout.activity_main);


        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/greatvibes_regular.ttf");
        titulo = findViewById(R.id.titulo);
        titulo.setTypeface(custom_font);


        mQueue = Volley.newRequestQueue(this);
        parseJson("https://moviles-backoffice.herokuapp.com/persona/?format=json");
    }

    public void parseJson(String url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.e("parse",String.valueOf(response.getJSONObject(0).getString("id")));
                            Log.e("parse",String.valueOf(response.getJSONObject(0).getString("nombre")));
                            Log.e("parse",String.valueOf(response.getJSONObject(0).getString("correo")));
                            Log.e("parse",String.valueOf(response.getJSONObject(0).getString("contrasena")));
                            //String bla = response.getJSONObject("quotes").get("USDCRC").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(jsonArrayRequest);

    }
}
