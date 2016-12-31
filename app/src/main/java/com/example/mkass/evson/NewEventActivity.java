package com.example.mkass.evson;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.gson.Gson;
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

public class NewEventActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int year, month, day;
    private DatePicker datePicker;
    private static MyEditText dateEvent;
    private static TextView locationView;
    private static MyEditText title;
    private static MyEditText description;
    private static NavigationView navigationView;
    private static MyTextView errorView;
    int status;
    private Place selectedPlace;
    private Spinner spinner;

    Personne pers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Create New Event");

        pers = (Personne)getIntent().getSerializableExtra("personne");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dateEvent = (MyEditText)findViewById(R.id.dateEvent);
        locationView = (MyTextView)findViewById(R.id.location);
        title = (MyEditText)findViewById(R.id.title);
        description = (MyEditText)findViewById(R.id.description);
        errorView = (MyTextView)findViewById(R.id.errorMsg);
        spinner = (Spinner)findViewById(R.id.spinner);

        dateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        // Check if Google Play Services are available. If not, then show the dialog that will prompt the user to upgrade
        status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
                GooglePlayServicesUtil.getErrorDialog(status, this,
                        100).show();
            }
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            dateEvent.setText(day+"/"+(month+1)+"/"+year);
        }
    }

    public void launchPlacePicker(View v){
        if (status == ConnectionResult.SUCCESS) {
            int PLACE_PICKER_REQUEST = 199;
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Context context = getBaseContext();
            try {
                startActivityForResult(builder.build(NewEventActivity.this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100){
            status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        }
        if (requestCode == 199){

            //process Intent......
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                selectedPlace = place;
                locationView.setText(place.getName());
            }

        }
    }

    public void finichActivity(View view){
        finish();
    }

    private boolean verifyForm(){
        if(!Utility.isNotNull(title.getText().toString()) || !Utility.isNotNull(description.getText().toString()) || !Utility.isNotNull(dateEvent.getText().toString()) || !Utility.isNotNull(locationView.getText().toString()) )
            return false;
        return true;
    }

    public void addEvent(View view){
        if(verifyForm() == false){
            errorView.setText("Please fill in all fields");
            errorView.setVisibility(View.VISIBLE);
        }
        else{
            SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");
            Evenement ev = new Evenement();
            try {
                ev.setDateEvenement(formatter.parse(dateEvent.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            ev.setOrganisateur(pers);
            ev.setTitre(title.getText().toString());
            ev.setText(description.getText().toString());

            Lieu lieu = new Lieu();
            lieu.setNom(selectedPlace.getName().toString());
            lieu.setAdresse(selectedPlace.getAddress().toString());
            lieu.setLongitude(selectedPlace.getLatLng().longitude);
            lieu.setLatitude(selectedPlace.getLatLng().latitude);
            ev.setLieu(lieu);

            ev.setGenre(spinner.getSelectedItem().toString());

            invokeAddEvent(ev);
        }
    }

    private void invokeAddEvent(Evenement ev){
        //Request parameters
        final RequestParams params = new RequestParams();
        Gson gson = new Gson();
        params.put("evenement", gson.toJson(ev));

        // Show Progress Dialog
        // Make RESTful webservice call using AsyncHttpClient object
        final AsyncHttpClient client = new AsyncHttpClient();
        final String ip = getBaseContext().getString(R.string.ipAdress);
        AsyncHttpResponseHandler RH=new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // JSON Object
                Gson gson = new Gson();
                // When the JSON response has status boolean value assigned with true

                if(response.equals("created")){

                    finish();
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
        client.get( ip +"/mesevenements/createEvent",params ,RH);
    }
}