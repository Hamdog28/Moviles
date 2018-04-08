package com.example.anthony_pc.pocketrecipe;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
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

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;


public class RegistroActivity extends AppCompatActivity {

    TextInputEditText editTextName, editTextEmail, editTextPassword;
    int PICK_IMAGE_REQUEST =111;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //Log.e("persona","ASDFasdfasdf");
        FacebookSdk.sdkInitialize(this);
        editTextEmail = (TextInputEditText) findViewById(R.id.editTextEmail);
        editTextName = (TextInputEditText) findViewById(R.id.editTextName);
        editTextPassword = (TextInputEditText) findViewById(R.id.editTextPassword);

        String name =  getIntent().getExtras().getString("first_name")+" "+getIntent().getExtras().getString("last_name");
        String email = getIntent().getExtras().getString("email");

        editTextName.setText(name);
        editTextEmail.setText(email);
        Log.e("persona",editTextName.getText().toString());
        if(editTextName.getText().toString().equals(" ")) {
            editTextName.setText("");
        }

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/greatvibes_regular.ttf");
        TextView titulo = (TextView) findViewById(R.id.titulo);
        titulo.setTypeface(custom_font);

        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        TextView atras =(TextView)  findViewById(R.id.atras);
        atras.setTypeface(font);



        new downloadImage((ImageView)findViewById(R.id.image)).execute(getIntent().getExtras().getString("persona"));

    }

    public void registerClick(View view){
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        insertUser("https://moviles-backoffice.herokuapp.com/persona/",name,email,password);
    }

    public void insertUser(String url, final String nombre, final String email, final String contrasena){

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
                /*String nombre = "tony alfaro";
                String correo = "tony@gmail.com";
                String contrasena = "123456";
                String descripcion = "no idea";*/
                ImageView image = (ImageView) findViewById(R.id.image);
                Bitmap image2 = ((BitmapDrawable)image.getDrawable()).getBitmap();
                //Drawable imagen = getResources().getDrawable(android.R.drawable.presence_online);
                //Bitmap bm = BitmapFactory.decodeResource(Resources.getSystem(), android.R.drawable.star_big_on);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                image2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                Log.e("foto",imageString);
                params.put("nombre",nombre);
                params.put("correo",email);
                params.put("contrasena",contrasena);
                //params.put("descripcion",descripcion);
                params.put("foto","data:image/JPEG;base64,"+ imageString);

                //byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
                //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                //image.setImageBitmap(decodedByte);

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
