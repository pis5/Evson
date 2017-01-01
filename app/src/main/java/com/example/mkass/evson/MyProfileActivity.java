package com.example.mkass.evson;


import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import customfonts.MyEditText;
import customfonts.MyTextView;
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
    ImageView image;
    private static NavigationView navigationView;
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

        ImageView navImageProfile = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.navImageProfile);
        TextView navNameProfile = (TextView) navigationView.getHeaderView(0).findViewById(R.id.navNameProfile);
        TextView navEmailProfile = (TextView)navigationView.getHeaderView(0).findViewById(R.id.navEmailProfile);


        navNameProfile.setText(pers.getPrenom() + " "+ pers.getNom());
        navEmailProfile.setText(pers.getEmail());
        if( pers.getPhoto() != null){
            Bitmap bMap = BitmapFactory.decodeByteArray(pers.getPhoto(),0,pers.getPhoto().length);
            navImageProfile.setImageBitmap(bMap);
        }


        Nom = (MyEditText)findViewById(R.id.NomProfile);
        Nom.setText( pers.getNom());

        Prenom = (MyEditText)findViewById(R.id.PrenomProfile);
        Prenom.setText( pers.getPrenom());

        DateNaissance = (MyEditText)findViewById(R.id.dateDeNaissance);
        if (pers.getDateDeNaissance() != null) {
        DateNaissance.setText(pers.getDateDeNaissance().toString());
            e.setDateDeNaissance(pers.getDateDeNaissance());
        }
        LieuNaissance= (MyEditText)findViewById(R.id.lieuNaissanceProfile);
        if (pers.getLieuDeNaissance() != null) {
        LieuNaissance.setText(pers.getLieuDeNaissance());
            e.setLieuDeNaissance(pers.getLieuDeNaissance());
        }
        Habitat = (MyEditText)findViewById(R.id.HabitatProfile);
        Habitat.setText(pers.getHabite());
        Phone=(MyEditText)findViewById(R.id.TelephoneProfile);
        Phone.setText(pers.getTelephone());

        if (pers.getPhoto() != null) {
            Bitmap bMap = BitmapFactory.decodeByteArray(pers.getPhoto(),0, pers.getPhoto().length);
            image.setImageBitmap(bMap);
        }

        DateNaissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new NewEventActivity.DatePickerFragment();
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
            final Personne pers = (Personne)getIntent().getSerializableExtra("personne");
            Intent it = new Intent(MyProfileActivity.this, HomeActivity.class);
            it.putExtra("personne",pers);
            startActivity(it);

        }
        else if (id == R.id.nav_friends){
            final Personne pers = (Personne)getIntent().getSerializableExtra("personne");
            Intent it = new Intent(MyProfileActivity.this, FriendsActivity.class);
            it.putExtra("personne",pers);
            startActivity(it);
        }
        else
        if (id == R.id.nav_profile) {
            final Personne pers = (Personne)getIntent().getSerializableExtra("personne");
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


            String nom = Nom.getText().toString();
            String prenom = Prenom.getText().toString();
            Intent it = new Intent(MyProfileActivity.this, MyProfileActivity.class);
            it.putExtra("personne",pers);
            it.putExtra("nom", nom);
            it.putExtra("prenom", prenom);
            startActivity(it);


        }
    }


}