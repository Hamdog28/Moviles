package com.example.anthony_pc.pocketrecipe.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.answers.Answers;
import com.example.anthony_pc.pocketrecipe.Globals;
import com.example.anthony_pc.pocketrecipe.Ingrediente;
import com.example.anthony_pc.pocketrecipe.R;
import com.example.anthony_pc.pocketrecipe.Receta;
import com.example.anthony_pc.pocketrecipe.Usuario;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity {
    ImageView image;
    private RequestQueue mQueue;
    LoginButton loginButton;
    TextView titulo;
    CallbackManager callbackManager;
    ProgressDialog progressDialog;
    Button crearCuenta;
    TextInputEditText emailTxt, passwordTxt;
    private Globals instance= Globals.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Answers());
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        image = (ImageView)findViewById(R.id.image);
        callbackManager = CallbackManager.Factory.create();
        crearCuenta = (Button) findViewById(R.id.crearCuenta_button);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        emailTxt = (TextInputEditText) findViewById(R.id.correoTxt);
        passwordTxt = (TextInputEditText) findViewById(R.id.passwordTxt);

        loginButton.setReadPermissions(Arrays.asList("public_profile","email","user_birthday","user_friends"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Profile profile = Profile.getCurrentProfile();
                        instance.setProfile(profile);
                        displayInfo(object,profile);
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields","first_name, last_name, email, id");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Error no se por que",Toast.LENGTH_SHORT).show();
            }
        });


        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/greatvibes_regular.ttf");
        titulo = (TextView) findViewById(R.id.titulo);
        titulo.setTypeface(custom_font);


        mQueue = Volley.newRequestQueue(this);

        getAllUsers("https://moviles-backoffice.herokuapp.com/persona/?format=json");
        getAllIngredients("https://moviles-backoffice.herokuapp.com/ingrediente/?format=json");
        getAllRecipes("https://moviles-backoffice.herokuapp.com/receta/?format=json");


    }

    public void displayInfo(JSONObject object,Profile profile ){
        String first_name , last_name, email;
        email = "";
        last_name = "";
        first_name = "";
        if(profile != null) {
            try {
                first_name = object.getString("first_name");
                last_name = object.getString("last_name");
                email = object.getString("email");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            TextInputEditText correoTxt = (TextInputEditText) findViewById(R.id.correoTxt);
            correoTxt.setText(email);
            Intent intent = new Intent(this, RegistroActivity.class);
            intent.putExtra("first_name", first_name);
            intent.putExtra("last_name", last_name);
            intent.putExtra("email", email);

            intent.putExtra("persona", profile.getProfilePictureUri(100, 100).toString());

            startActivity(intent);
        }else{
            Toast.makeText(this,"Perfil de facebook no existe",Toast.LENGTH_SHORT).show();
        }
    }

    public void getAllIngredients(String url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i  = 0; i <response.length(); i++){
                                //Log.e("foto",String.valueOf(i));
                                int id = Integer.parseInt(response.getJSONObject(i).getString("id"));
                                String nombre = response.getJSONObject(i).getString("nombre");
                                int recetaID = Integer.parseInt(response.getJSONObject(i).getString("receta"));
                                int cantidad = Integer.parseInt(response.getJSONObject(i).getString("url"));

                                Ingrediente ingrediente = new Ingrediente(id,recetaID,nombre,cantidad);
                                instance.addIngrediente(ingrediente);
                            }
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

    public void getAllUsers(String url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i  = 0; i <response.length(); i++){
                                //Log.e("foto",String.valueOf(i));
                                int id = Integer.parseInt(response.getJSONObject(i).getString("id"));
                                String nombre = response.getJSONObject(i).getString("nombre");
                                String correo = response.getJSONObject(i).getString("correo");
                                String url = response.getJSONObject(i).getString("url");
                                String descripcion = response.getJSONObject(i).getString("descripcion");
                                Bitmap foto = null;


                                if(!url.equals("")) {
                                    DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(image);
                                    foto = downloadTask.execute(url).get();
                                    Log.e("foto",String.valueOf(foto));

                                }else{
                                    Log.e("else","else");
                                }

                                Usuario usuario = new Usuario(id,nombre,foto,correo,descripcion);
                                instance.addUser(usuario);
                                instance.setActualUser(usuario);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
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

    public void getAllRecipes(String url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i  = 0; i <response.length(); i++){

                                int id = Integer.parseInt(response.getJSONObject(i).getString("id"));
                                int autor = Integer.parseInt(response.getJSONObject(i).getString("autor"));
                                String nombre = response.getJSONObject(i).getString("nombre");
                                String duracion = response.getJSONObject(i).getString("duracion");
                                String url = response.getJSONObject(i).getString("url");
                                String procedimiento = response.getJSONObject(i).getString("procedimiento");
                                String notas = response.getJSONObject(i).getString("notas");
                                String dificultad = response.getJSONObject(i).getString("dificultad");
                                int porciones = Integer.parseInt(response.getJSONObject(i).getString("porciones"));
                                Boolean publico = Boolean.valueOf(response.getJSONObject(i).getString("publico"));
                                String publicacion = response.getJSONObject(i).getString("publicacion");
                                String costo = response.getJSONObject(i).getString("costo");
                                Float calificacion = Float.parseFloat(response.getJSONObject(i).getString("calificacion"));
                                int cantidad_calificaciones = Integer.parseInt(response.getJSONObject(i).getString("cantidad_calificaciones"));
                                Bitmap foto = null;


                                if(!url.equals("")) {
                                    DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(image);
                                    foto = downloadTask.execute(url).get();
                                    Log.e("fotoREceta",String.valueOf(foto));

                                }else{
                                    Log.e("else","else");
                                }

                                Receta receta = new Receta(id,nombre,duracion,procedimiento,dificultad,porciones,foto,costo,calificacion,cantidad_calificaciones,
                                        publico,notas,listaIngredientes(id),autor,publicacion);
                                instance.addRecipe(receta);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
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

    public ArrayList<String> listaIngredientes(int id){
        ArrayList<String> listaIngredientes = new ArrayList<>();
        for(Ingrediente i : instance.getIngredienteList()){
            if(i.getRecetaID() == id){
                listaIngredientes.add(i.getNombre());
            }
        }
        return listaIngredientes;
    }


    private class DownloadImageWithURLTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;


        public DownloadImageWithURLTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String pathToFile = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(pathToFile).openStream();
                bitmap = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            //bmImage.setImageBitmap(result);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void crearCuenta(View view){
        Intent intent = new Intent(this, RegistroActivity.class);
        intent.putExtra("first_name", "");
        intent.putExtra("last_name", "");
        intent.putExtra("email", "");
        startActivity(intent);
    }

    public void ingresar(View view){
        Intent intent = new Intent(this, InicioActivity.class);
        //String email = emailTxt.getText().toString();
       // String password = passwordTxt.getText().toString();
        startActivity(intent);
    }


}
