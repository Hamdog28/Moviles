package com.example.anthony_pc.pocketrecipe.Activites;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anthony_pc.pocketrecipe.Globals;
import com.example.anthony_pc.pocketrecipe.R;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class EditarPerfilActivity extends AppCompatActivity {

    EditText nombreTxt, contrasenaTxt, descripcionTxt;
    ImageButton photoImg;
    String url = "https://moviles-backoffice.herokuapp.com/persona/";
    private Globals instance= Globals.getInstance();

    private static final int RESULT_LOAD_IMAGE = 0;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Editar Perfil");
        url = url + String.valueOf(instance.getActualUser().getId())+"/";

        nombreTxt = (EditText)findViewById(R.id.nombre);
        contrasenaTxt = (EditText) findViewById(R.id.contrasena);
        descripcionTxt = (EditText) findViewById(R.id.descripcionTV);
        photoImg = (ImageButton) findViewById(R.id.photoImg);

        nombreTxt.setText(instance.getActualUser().getNombre());
        descripcionTxt.setText(instance.getActualUser().getDescripcion());
        photoImg.setImageBitmap(instance.getActualUser().getFoto());

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public void agarrarImagenGaleria(View view){
        Intent galeriaIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galeriaIntent,RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_LOAD_IMAGE:
                if(resultCode == RESULT_OK){
                    Uri imagenSeleccionada = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(imagenSeleccionada,filePath,null,null,null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePath[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    photoImg.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    //photoImg.setBackground(new BitmapDrawable(picturePath));

                    break;
                }
        }
    }



    public void editarUsuairo(View view){
        String id = String.valueOf(instance.getActualUser().getId());
        String nombre = nombreTxt.getText().toString();
        String contrasena = contrasenaTxt.getText().toString();
        String descripcion = descripcionTxt.getText().toString();
        String correo = instance.getActualUser().getCorreo();

        Bitmap image2 = ((BitmapDrawable)photoImg.getDrawable()).getBitmap();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        uploadUser(nombre,contrasena,descripcion,id,imageString,correo);
    }

    public void uploadUser(final String nombre, final String contrasena, final String descripcion, final String id,final String imagen,final String correo){
        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"Usuario modificado exitosamente", Toast.LENGTH_SHORT).show();
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error al subir datos", Toast.LENGTH_SHORT).show();
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {

            /*@Override
            public Map<String, String> getHeaders()
            {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                //or try with this:
                //headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                return headers;
            }*/
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("nombre", nombre);
                params.put("contrasena", contrasena);
                params.put("descripcion", descripcion);
                params.put("foto", "data:image/JPEG;base64,"+ imagen);
                params.put("correo", correo);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(putRequest);
    }




    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
