package com.example.mkass.evson;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

import customfonts.MyEditText;
import customfonts.MyTextView;
import entities.Evenement;
import entities.Personne;

public class MyProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Personne e=new Personne();
    private static MyEditText Nom;
    private static MyEditText Prenom;
    private static MyEditText DateNaissance;
    private static MyEditText LieuNaissance;
    private static MyEditText Habitat;
    private static MyEditText Phone;


    private static NavigationView navigationView;
    String monimage;
    private static MyTextView errorView;

    Personne pers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Create New Event");


        pers = (Personne)getIntent().getSerializableExtra("personne");
        e.setId(pers.getId());
        e.setEmail(pers.getEmail());
        e.setMotDePasse(pers.getMotDePasse());
        e.setNom(pers.getNom());
        e.setPrenom(pers.getPrenom());
        if (pers.getDateDEnregistrement() != null) {
            e.setDateDEnregistrement(pers.getDateDEnregistrement());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Nom = (MyEditText)findViewById(R.id.NomProfile);
        Nom.setText( pers.getNom());

        Prenom = (MyEditText)findViewById(R.id.PrenomProfile);
        Prenom.setText( pers.getPrenom());

        DateNaissance = (MyEditText)findViewById(R.id.dateDeNaissance);
        if (pers.getDateDeNaissance() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/M/yyyy");

                DateNaissance.setText(formatter.format(pers.getDateDeNaissance()));


                e.setDateDeNaissance(pers.getDateDeNaissance());

        }
        LieuNaissance= (MyEditText)findViewById(R.id.lieuNaissanceProfile);
        if (pers.getLieuDeNaissance() != null) {
        LieuNaissance.setText(pers.getLieuDeNaissance());
            e.setLieuDeNaissance(pers.getLieuDeNaissance());
        }
        Habitat = (MyEditText)findViewById(R.id.HabitatProfile);
        if (pers.getHabite() != null) {
        Habitat.setText(pers.getHabite());
            e.setHabite(pers.getHabite());
        }
        Phone=(MyEditText)findViewById(R.id.TelephoneProfile);
        if (pers.getTelephone() != null) {
        Phone.setText(pers.getTelephone());
        e.setTelephone(pers.getTelephone());
        }


        DateNaissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new MyProfileActivity.DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        errorView = (MyTextView)findViewById(R.id.errorMsgFriendLookUp);





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
        getMenuInflater().inflate(R.menu.new_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            DateNaissance.setText(day+"/"+(month+1)+"/"+year);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Intent it = new Intent(MyProfileActivity.this, HomeActivity.class);
            it.putExtra("personne",pers);
            startActivity(it);

        }
        else if (id == R.id.nav_friends){

            Intent it = new Intent(MyProfileActivity.this, FriendsActivity.class);
            it.putExtra("personne",pers);
            startActivity(it);
        }
        else
        if (id == R.id.nav_profile) {

            Intent it = new Intent(MyProfileActivity.this, MyProfileActivity.class);
            it.putExtra("personne",pers);
            startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }






    public void finichActivity(View view){
        finish();
    }

    private boolean verifyForm(){
        if(!Utility.isNotNull(Nom.getText().toString()) || !Utility.isNotNull(Prenom.getText().toString()))
            return false;
        return true;
    }

    public void modify(View view){
        if(verifyForm() == false){
            errorView.setText("Please fill first and last name");
            errorView.setVisibility(View.VISIBLE);
        }
        else{
            SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");
            try {
                e.setDateDeNaissance(formatter.parse(DateNaissance.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            e.setTelephone(Phone.getText().toString());
            e.setHabite(Habitat.getText().toString());
            e.setNom(Nom.getText().toString());
            e.setPrenom(Prenom.getText().toString());
            e.setLieuDeNaissance(LieuNaissance.getText().toString());

            invokeWS(e);



           pers=e;



        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void invokeWS(final Personne e){
        final RequestParams params = new RequestParams();
        Gson gson =new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("MMM d, yyyy HH:mm:ss")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();


        params.put("personne", gson.toJson(e));


        final AsyncHttpClient client = new AsyncHttpClient();
        final String ip = this.getString(R.string.ipAdress);
        AsyncHttpResponseHandler RH=new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // JSON Object
                Gson gson = new Gson();
                // When the JSON response has status boolean value assigned with true

                if(response.equals("updated")){
                    //

                    pers=e;
                    Toast.makeText(getBaseContext(), "Updated", Toast.LENGTH_LONG);

                }
                // Else display error message
                else{

                }
            }
            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {


                // Hide Progress Dialog
                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getBaseContext(), "ERROR 404", Toast.LENGTH_LONG);

                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getBaseContext(), "ERROR 500", Toast.LENGTH_LONG);

                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getBaseContext(), "ERROR", Toast.LENGTH_LONG);

                }
            }

        };
        client.get( ip +"/profile/update",params ,RH);







    }

}