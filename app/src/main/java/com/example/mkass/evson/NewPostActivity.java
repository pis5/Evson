package com.example.mkass.evson;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import customfonts.MyEditText;
import customfonts.MyTextView;
import entities.Evenement;
import entities.Lieu;
import entities.Personne;
import entities.Post;

public class NewPostActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static MyEditText description;
    private static NavigationView navigationView;
    private static MyTextView errorView;
    int status;
    private Place selectedPlace;
    private Spinner spinner;

    Personne pers;
    Evenement event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("New post");

        pers = (Personne)getIntent().getSerializableExtra("personne");
        event = (Evenement) getIntent().getSerializableExtra("evenement");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        ImageView navImageProfile = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.navImageProfile);
        TextView navNameProfile = (TextView) navigationView.getHeaderView(0).findViewById(R.id.navNameProfile);
        TextView navEmailProfile = (TextView)navigationView.getHeaderView(0).findViewById(R.id.navEmailProfile);


        navNameProfile.setText(pers.getPrenom() + " "+ pers.getNom());
        navEmailProfile.setText(pers.getEmail());
        if( pers.getPhoto() != null){
            Bitmap bMap = BitmapFactory.decodeByteArray(pers.getPhoto(),0,pers.getPhoto().length);
            navImageProfile.setImageBitmap(bMap);
        }


        description = (MyEditText)findViewById(R.id.description);
        errorView = (MyTextView)findViewById(R.id.errorMsg);



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
        if (id == R.id.nav_home) {
            final Personne pers = (Personne)getIntent().getSerializableExtra("personne");
            Intent it = new Intent(NewPostActivity.this, HomeActivity.class);
            it.putExtra("personne",pers);
            startActivity(it);

        }
        else if (id == R.id.nav_friends){
            final Personne pers = (Personne)getIntent().getSerializableExtra("personne");
            Intent it = new Intent(NewPostActivity.this, FriendsActivity.class);
            it.putExtra("personne",pers);
            startActivity(it);
        }
        else if (id == R.id.nav_profile) {
            final Personne pers = (Personne)getIntent().getSerializableExtra("personne");
            Intent it = new Intent(NewPostActivity.this, MyProfileActivity.class);
            it.putExtra("personne",pers);
            startActivity(it);
        }
        else if (id == R.id.nav_myEvents) {
            final Personne pers = (Personne)getIntent().getSerializableExtra("personne");
            Intent it = new Intent(NewPostActivity.this, MyEventsActivity.class);
            it.putExtra("personne",pers);
            startActivity(it);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_friends) {

        } else if (id == R.id.nav_profile) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }







    public void finichActivity(View view){
        finish();
    }

    private boolean verifyForm(){
        if(!Utility.isNotNull(description.getText().toString())  )
            return false;
        return true;
    }

    public void addPost(View view){
        if(verifyForm() == false){
            errorView.setText("Please fill the post");
            errorView.setVisibility(View.VISIBLE);
        }
        else{
            Post p= new Post();
            p.setCommentaire(description.getText().toString());
            p.setPersonneId(pers);
            p.setEvenementId(event);

            invokeAddPost(p);
        }
    }

    private void invokeAddPost(final Post ev){
        //Request parameters
        final RequestParams params = new RequestParams();
        Gson gson =new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("MMM d, yyyy HH:mm:ss")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        params.put("post", gson.toJson(ev));

        // Show Progress Dialog
        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        final String ip = getBaseContext().getString(R.string.ipAdress);
        AsyncHttpResponseHandler RH=new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // JSON Object
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .setDateFormat("MMM d, yyyy HH:mm:ss")
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create();
                // When the JSON response has status boolean value assigned with true

                if(response.equals("created")){

                    Intent it = new Intent(NewPostActivity.this, EventActivity.class);
                    it.putExtra("personne",pers);
                    it.putExtra("evenement",ev.getEvenementId());
                    Log.i("add  " , "daada");
                    startActivity(it);
                }
                // Else display error message
                else{
                    // Toast.makeText(context, "Nothing to show here!!", Toast.LENGTH_LONG).show();
                }
            }
            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                Log.i("l", "lil");

                // Hide Progress Dialog
                // When Http response code is '404'
                if(statusCode == 404){
                    Log.i("l", "Requested resource not found");
                    //Toast.makeText(context, "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Log.i("l", "Something went wrong at server end ");
                    //Toast.makeText(context, "Something went wrong at server end 2 ", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Log.i("l", "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]");
                    //Toast.makeText(context, "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        };
        client.get( ip +"/mesevenements/createPost",params ,RH);
    }
}