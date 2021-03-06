package com.example.anthony_pc.pocketrecipe.Activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import com.example.anthony_pc.pocketrecipe.Globals;
import com.example.anthony_pc.pocketrecipe.R;
import com.example.anthony_pc.pocketrecipe.Usuario;
import com.facebook.FacebookSdk;


public class RegistroActivity extends AppCompatActivity {

    TextInputEditText editTextName, editTextEmail, editTextPassword;
    int PICK_IMAGE_REQUEST =111;
    Bitmap bitmap;
    private Globals instance= Globals.getInstance();
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        getSupportActionBar().hide();
        //Log.e("persona","ASDFasdfasdf");
        FacebookSdk.sdkInitialize(this);
        image = (ImageView)findViewById(R.id.image);
        image.setImageDrawable(getResources().getDrawable( R.drawable.person_icon ));

        editTextEmail = (TextInputEditText) findViewById(R.id.editTextEmail);
        editTextName = (TextInputEditText) findViewById(R.id.editTextName);
        editTextPassword = (TextInputEditText) findViewById(R.id.editTextPassword);

        String name =  getIntent().getExtras().getString("first_name")+" "+getIntent().getExtras().getString("last_name");
        String email = getIntent().getExtras().getString("email");

        if(!name.equals(" ")){
            editTextName.setText(name);
            editTextEmail.setText(email);
            new downloadImage((ImageView)findViewById(R.id.image)).execute(getIntent().getExtras().getString("persona"));
        }

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/greatvibes_regular.ttf");
        TextView titulo = (TextView) findViewById(R.id.titulo);
        titulo.setTypeface(custom_font);


    }



    public boolean Atras(View view){
        finish();
        return true;
    }

    public void registerClick(View view){
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        Bitmap image2 = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        if(name.equals("") || email.equals("") || password.equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Aviso")
                    .setMessage("Favor ingrese todos los datos")
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
        }else{
            insertUser("https://moviles-backoffice.herokuapp.com/persona/",name,email,password,imageString);
            Usuario user = new Usuario(instance.lastIdUser()+1,name,image2,email,"",password);
            Log.e("IDE USUARIO CREADO", String.valueOf(instance.lastIdUser()+1));
            instance.addUser(user);
        }

    }

    public void insertUser(String url, final String nombre, final String email, final String contrasena,final String imageString){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Usuario ingresado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("nombre",nombre);
                params.put("correo",email);
                params.put("contrasena",contrasena);
                params.put("descripcion","");
                params.put("foto","data:image/JPEG;base64,"+ imageString);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                //image.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class downloadImage extends AsyncTask<String, Void, Bitmap>{
        ImageView imageView;
        public downloadImage(ImageView profileImage){
            this.imageView = profileImage;
        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlDisplay = strings[0];
            Bitmap profile = null;
            try{
                InputStream inputStream = new java.net.URL(urlDisplay).openStream();
                profile = BitmapFactory.decodeStream(inputStream);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return profile;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }


}
