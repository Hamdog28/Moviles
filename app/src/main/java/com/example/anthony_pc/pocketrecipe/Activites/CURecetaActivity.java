package com.example.anthony_pc.pocketrecipe.Activites;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anthony_pc.pocketrecipe.Globals;
import com.example.anthony_pc.pocketrecipe.R;
import com.example.anthony_pc.pocketrecipe.Receta;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Calendar;


public class CURecetaActivity extends AppCompatActivity {

    private String m_Text = "";
    List<String> ingredientes = new ArrayList<String>();
    final Context context = this;

    EditText nombreRecetaTxt, duracionHoras, duracionMinTxt, porcionesTxt, preparacionTxt, notasTxt,tagsTxt;
    Spinner spinnerDificultad, spinnerCosto;
    ImageButton imageBtn;
    RadioGroup radioGroupPublico;
    String url = "https://moviles-backoffice.herokuapp.com/receta/";
    String urlTag = "https://moviles-backoffice.herokuapp.com/tag/";
    String urlIngrediente = "https://moviles-backoffice.herokuapp.com/ingrediente/";
    private Globals instance= Globals.getInstance();

    private static final int RESULT_LOAD_IMAGE = 0;
    private static final int PERMISSION_REQUEST = 0;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    String mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cureceta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mensaje = getIntent().getStringExtra("mensaje");

        setTitle("Crear Receta");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
        TextView agregar =(TextView)  findViewById(R.id.agregarIngrediente_button);
        agregar.setTypeface(font);

        /*if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST);
        }
*/
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }



        //VINCULAR-----------------EditText-------------------
        nombreRecetaTxt = (EditText)findViewById(R.id.nombreRecetaTxt);
        duracionHoras = (EditText) findViewById(R.id.duracionHorasTxt);
        duracionMinTxt = (EditText) findViewById(R.id.duracionMinTxt);
        porcionesTxt =  (EditText)findViewById(R.id.porcionesTxt);
        preparacionTxt = (EditText) findViewById(R.id.preparacionTxt);
        notasTxt =  (EditText) findViewById(R.id.notasTxt);
        tagsTxt =  (EditText)findViewById(R.id.tagsTxt);
        //VINCULAR-----------------Spinner-------------------
        spinnerDificultad = (Spinner) findViewById(R.id.spinnerDificultad);
        spinnerCosto = (Spinner) findViewById(R.id.spinnerCosto);
        //VINCULAR-----------------ImageButton-------------------
        imageBtn = (ImageButton) findViewById(R.id.imageBtn);
        //VINCULAR-----------------RadioGroup-------------------
        radioGroupPublico = (RadioGroup) findViewById(R.id.radioGroup);

        if(mensaje.equals("editar")){
            nombreRecetaTxt.setText("Carne asada");
            duracionHoras.setText("2");
            duracionMinTxt.setText("0");
            porcionesTxt.setText("4");
            preparacionTxt.setText("Paso1\npaso2\npaso3\npaso4");
            notasTxt.setText("Perfecto para fines de semana");

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
                    imageBtn.setBackground(new BitmapDrawable(picturePath));
                    Log.e("imgpath",picturePath);
                    String fileNameSegments[] = picturePath.split("/");
                    Log.e("imgpath",Arrays.deepToString(fileNameSegments));
                    String fileName = fileNameSegments[fileNameSegments.length -1];
                    Log.e("imgpath",fileName);
                    break;
                }
        }
    }

    public void agarrarDatos(View view){

        Date currentTime = Calendar.getInstance().getTime();

        String nombreRecetaString = nombreRecetaTxt.getText().toString();
        String duracionHorasString = duracionHoras.getText().toString();
        String duracionMinTxtString = duracionMinTxt.getText().toString();
        String porcionesTxtString = porcionesTxt.getText().toString();
        String preparacionTxtString = preparacionTxt.getText().toString();
        String notasTxtString = notasTxt.getText().toString();
        ArrayList<String> tagsTxtString = leerHashtags(tagsTxt.getText().toString().replace(" ","").split("#"));

        String dificultadString = spinnerDificultad.getSelectedItem().toString();
        String costoString = spinnerCosto.getSelectedItem().toString();

        Bitmap image2 = ((BitmapDrawable)imageBtn.getBackground()).getBitmap();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        int privacidad = radioGroupPublico.indexOfChild(findViewById(radioGroupPublico.getCheckedRadioButtonId()));
        String privacidadString = String.valueOf(privacidad);

        if(nombreRecetaString.equals("") || duracionHorasString.equals("") || duracionMinTxtString.equals("")
                || porcionesTxtString.equals("") || preparacionTxtString.equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Aviso").setMessage("Complete los campos requeridos").show();
        }else{
            int lastID = instance.getRecipeList().get(instance.getRecipeList().size()-1).getId();
            Log.e("LASTID",String.valueOf(lastID));

            insertarReceta(nombreRecetaString,duracionHorasString,duracionMinTxtString,porcionesTxtString, preparacionTxtString, notasTxtString,
                    dificultadString, costoString, imageString, String.valueOf(privacidad),tagsTxtString,lastID);

            Receta receta = new Receta(lastID+1,nombreRecetaString,duracionHorasString,listaProcedimiento(preparacionTxtString.split("\n")),dificultadString,
                    Integer.parseInt(porcionesTxtString), image2,costoString,0f,0,
                    Boolean.parseBoolean(privacidadString),notasTxtString,(ArrayList)ingredientes,
                    instance.getActualUser().getId(),String.valueOf(currentTime),tagsTxtString);

            instance.addRecipe(receta);

        }

        //0 - > Si RadioGroup
        //1 - > No

    }
    public ArrayList<String> listaProcedimiento(String[] proc){
        ArrayList<String> listaProcedimiento = new ArrayList<>();
        for(String i :proc){
            listaProcedimiento.add(i);
        }
        return listaProcedimiento;
    }

    public void insertarReceta(final String nombre, final String duracionH, final String duracionM, final String porciones, final String preparacion
                            , final String notas, final String dificultad, final String costo, final String imagen,
                               final String privacidad, final ArrayList<String> tagsTxtString, final int lastID){

        StringRequest recetaRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Receta registrada", Toast.LENGTH_SHORT).show();
                String idReceta = String.valueOf(lastID+1);
                for(String i : tagsTxtString){
                    Log.e("id tag", i+ " - " + idReceta);
                    insertarTags(i,idReceta);
                }
                for(String j : ingredientes){
                    Log.e("id tag", j+ " - " + idReceta);
                    insertarIngredientes(j,idReceta);
                }
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
                Log.e("autor",String.valueOf(instance.getActualUser().getId()));
                Log.e("nombre",nombre);
                Log.e("duracion",duracionH + ":" + duracionM);
                Log.e("porciones",porciones);
                Log.e("procedimiento",preparacion);
                Log.e("notas",notas);
                Log.e("dificultad",dificultad);
                Log.e("costo",costo);
                Log.e("publico",privacidad);
                //Log.e("dificultad",dificultad);



                params.put("autor",String.valueOf(instance.getActualUser().getId()));
                params.put("nombre",nombre);
                params.put("duracion",duracionH + ":" + duracionM);
                params.put("porciones",porciones);
                params.put("procedimiento",preparacion);
                params.put("notas",notas);
                params.put("dificultad",dificultad);
                params.put("costo",costo);
                params.put("publico",privacidad);
                params.put("calificacion","0");
                params.put("cantidad_calificaciones","0");

                params.put("foto","data:image/JPEG;base64,"+ imagen);

                return params;
            }
        };
        recetaRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(recetaRequest);

    }


    public void insertarIngredientes(final String ingrediente, final String idReceta){

        StringRequest ingredienteRequest = new StringRequest(Request.Method.POST, urlIngrediente, new Response.Listener<String>() {
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
                params.put("receta",idReceta);
                params.put("nombre",ingrediente);
                params.put("cantidad","0");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(ingredienteRequest);

    }

    public void insertarTags(final String tag,final String id){

        StringRequest tagRequest = new StringRequest(Request.Method.POST, urlTag, new Response.Listener<String>() {
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
                params.put("receta",id);
                params.put("nombre",tag);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(tagRequest);
    }

    public ArrayList<String> leerHashtags(String[] hashtag){

        ArrayList<String> hashtags = new ArrayList<String>();
        for(String i : hashtag){
            if(!i.equals(""))
                hashtags.add(i);
        }
        return hashtags;
    }


    public void showstatesList() {

        LinearLayout.LayoutParams params = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        View linearLayout =  findViewById(R.id.lista);
        if(((LinearLayout) linearLayout).getChildCount() > 0)
            ((LinearLayout) linearLayout).removeAllViews();

        for(int i = 0; i<ingredientes.size(); i++){
            final TextView valueTV = new TextView(this);
            valueTV.setTextColor(getResources().getColor(R.color.black));
            //valueTV.setTextColor(Color.parseColor("#000000"));
            valueTV.setTextSize(18);
            valueTV.setId(((LinearLayout) linearLayout).getChildCount());
            params.setMargins(0,25,0,0);
            valueTV.setText("  " + ingredientes.get(i));
            valueTV.setBackgroundResource(R.color.colorPrimaryLight);
            //valueTV.setAlpha((float)0.2);
            valueTV.getBackground().setAlpha(80);
            valueTV.isClickable();

            valueTV.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("¿Desea eliminar el ingrediente?");


                    // Set up the buttons
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ingredientes.remove(valueTV.getId());
                            showstatesList();
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();

                }
            });
            valueTV.setLayoutParams(params);
            ((LinearLayout) linearLayout).addView(valueTV);
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    public void agregarIngrediente(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Añadir ingrediente");

        // Set up the input
        final EditText input = new EditText(this);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ( ! (input.getText().toString().equals(""))) {
                    //go on here and dismiss dialog
                    m_Text = input.getText().toString();
                    ingredientes.add(m_Text);
                    showstatesList();
                }

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }



}
