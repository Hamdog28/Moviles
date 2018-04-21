package com.example.anthony_pc.pocketrecipe.Activites;

import android.app.TabActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.net.Uri;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;


import com.example.anthony_pc.pocketrecipe.Globals;
import com.example.anthony_pc.pocketrecipe.R;
import com.example.anthony_pc.pocketrecipe.Receta;
import com.example.anthony_pc.pocketrecipe.fragments.carrito.CarritoFragment;
import com.example.anthony_pc.pocketrecipe.fragments.fav.FavoritosFragment;
import com.example.anthony_pc.pocketrecipe.fragments.inicio.Inicio_Fragment;
import com.example.anthony_pc.pocketrecipe.fragments.perfil.PerfilFragment;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class InicioActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Inicio_Fragment.OnFragmentInteractionListener,
        FavoritosFragment.OnFragmentInteractionListener,PerfilFragment.OnFragmentInteractionListener,
        CarritoFragment.OnFragmentInteractionListener{

    Fragment fragment;
    FragmentManager fragmentManager = getSupportFragmentManager();


    private Globals instance= Globals.getInstance();
    CircleImageView profile_image;
    TextView nombreTxt,correoTxt;
    int aux = 0;

    String mensaje, pantalla = "";


    NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_inicio);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SpannableString s = new SpannableString("Pocket Recipe");
        s.setSpan(new TypefaceSpan("greatvibes_regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(s);



        //getSupportFragmentManager().beginTransaction().add(R.id.container,fragment);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // Do whatever you want here
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                //navigationView.setNavigationItemSelectedListener();
                if (aux == 0)
                    navigationView.getMenu().getItem(0).setChecked(true);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        try {
            mensaje = getIntent().getStringExtra("mensaje");
            pantalla = getIntent().getStringExtra("pantalla");

            if(pantalla.equals("perfil")){
                Bundle bundle = new Bundle();
                Bundle bundle1 = new Bundle();
                Log.e("MENSAJE",mensaje);
                bundle.putString("mensaje", mensaje);
                bundle.putString("orientacion", "grid");
                bundle1.putString("mensaje",mensaje);
                fragment = new PerfilFragment();
                fragmentManager.popBackStack();
                Fragment fragment1 = new FavoritosFragment();
                fragment1.setArguments(bundle);
                fragment.setArguments(bundle1);


                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();

                fragmentManager.beginTransaction()
                        .replace(R.id.container2, fragment1)
                        .commit();
                //fragment = new PerfilFragment();
                //fragmentManager.beginTransaction().add(R.id.container, fragment).commit();


                navigationView.getMenu().getItem(1).setChecked(true);
            }
        }catch (NullPointerException e){
            fragment = new Inicio_Fragment();
            fragmentManager.beginTransaction().add(R.id.container, fragment).commit();


            navigationView.getMenu().getItem(0).setChecked(true);
        }



        View viewNav = navigationView.getHeaderView(0);
        profile_image = viewNav.findViewById(R.id.profile_image);
        nombreTxt = viewNav.findViewById(R.id.nombreTxt);
        correoTxt = viewNav.findViewById(R.id.correoTxt);

        try {
            nombreTxt.setText(instance.getActualUser().getNombre());
            correoTxt.setText(instance.getActualUser().getCorreo());

            profile_image.setImageBitmap(instance.getActualUser().getFoto());

        } catch (NullPointerException e) {

        }
    }

    public void imprimirDatos(){
        Log.e("cantidad REcetas",String.valueOf(instance.getRecipeList().size()));
        for(Receta i : instance.getRecipeList()){
            Log.e("NOMBRE RECETA",i.getNombre());
            Log.e("","-----------------------");
            for(String j : i.getListaIngredientes()){
                Log.e("Ingrediente",j);
            }
            Log.e("","-----------------------");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        View viewNav = navigationView.getHeaderView(0);
        profile_image = viewNav.findViewById(R.id.profile_image);
        nombreTxt = viewNav.findViewById(R.id.nombreTxt);
        correoTxt = viewNav.findViewById(R.id.correoTxt);

        nombreTxt.setText(instance.getActualUser().getNombre());
        correoTxt.setText(instance.getActualUser().getCorreo());

        profile_image.setImageBitmap(instance.getActualUser().getFoto());
        profile_image.setImageBitmap(instance.getActualUser().getFoto());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.inicio, menu);
        //setMenuBackground();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        aux = 1;


        Bundle bundle = new Bundle();




        if (id == R.id.nav_home) {
            // Handle the camera action
            bundle.putString("mensaje", "");
            bundle.putString("pantalla", "inicio");
            setActionBarTitle("Pocket Recipe");
            aux = 0;
            fragment = new Inicio_Fragment();
            fragmentManager.popBackStack();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();

        } else if (id == R.id.nav_account) {
            setActionBarTitle("Perfil");

            bundle.putString("mensaje", String.valueOf(instance.getActualUser().getId()));
            bundle.putString("orientacion", "grid");
            Log.e("mensaje",String.valueOf(instance.getActualUser().getId()));

            fragment = new PerfilFragment();
            fragment.setArguments(bundle);
            fragmentManager.popBackStack();

            Fragment fragment1 = new FavoritosFragment();
            fragment1.setArguments(bundle);

            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();

            fragmentManager.beginTransaction()
                    .replace(R.id.container2, fragment1)
                    .commit();

        } else if (id == R.id.nav_favorite) {
            setActionBarTitle("Favoritos");

            bundle.putString("mensaje", "favorito");
            bundle.putString("orientacion", "grid");

            fragment = new FavoritosFragment();
            fragmentManager.popBackStack();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();

        } else if (id == R.id.nav_cart) {
            setActionBarTitle("Carrito");
            fragment = new CarritoFragment();
            //fragmentManager.beginTransaction()
              //      .replace(R.id.container, fragment)
                //    .commit();


        } else if (id == R.id.nav_create) {
            aux = 0;
            Intent intent = new Intent(this, CURecetaActivity.class);
            intent.putExtra("mensaje","crear");
            startActivity(intent);

        } else if (id == R.id.nav_settings) {
            setActionBarTitle("Ajustes");


        }
        else if (id == R.id.nav_logout) {
            LoginManager.getInstance().logOut();
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
    protected void setMenuBackground(){
        // Log.d(TAG, "Enterting setMenuBackGround");
        getLayoutInflater().setFactory( new LayoutInflater.Factory() {
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                if ( name.equalsIgnoreCase( "com.android.internal.view.menu.IconMenuItemView" ) ) {
                    try { // Ask our inflater to create the view
                        LayoutInflater f = getLayoutInflater();
                        final View view = f.createView( name, null, attrs );
                        /* The background gets refreshed each time a new item is added the options menu.
                        * So each time Android applies the default background we need to set our own
                        * background. This is done using a thread giving the background change as runnable
                        * object */
                        new Handler().post(new Runnable() {
                            public void run () {
                                // sets the background color
                                view.setBackgroundResource( R.color.colorPrimary);
                                // sets the text color
                                ((TextView) view).setTextColor(Color.WHITE);
                                // sets the text size
                                ((TextView) view).setTextSize(18);
                            }
                        } );
                        return view;
                    }
                    catch ( InflateException e ) {}
                    catch ( ClassNotFoundException e ) {}
                }
                return null;
            }});
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

