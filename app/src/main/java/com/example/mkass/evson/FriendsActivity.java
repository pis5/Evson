package com.example.mkass.evson;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import entities.Personne;

public class FriendsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Friends");






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        final Personne pers = (Personne)getIntent().getSerializableExtra("personne");

        //boutons menu amis
        final ImageButton MesAmis = (ImageButton)findViewById(R.id.imageButtonMesAmis);
        MesAmis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FriendsActivity.this, FriendsActivity.class);
                it.putExtra("personne",pers);
                startActivity(it);

            }
        });

        final ImageButton AjouterAmi = (ImageButton)findViewById(R.id.imageButtonAjouterAmis);
        AjouterAmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FriendsActivity.this, AddFriendsActivity.class);
                it.putExtra("personne",pers);
                startActivity(it);

            }
        });


        final ImageButton Demandes = (ImageButton)findViewById(R.id.imageButtonDemandesAmis);
        Demandes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FriendsActivity.this, InvitesFriendsActivity.class);
                it.putExtra("personne",pers);
                startActivity(it);

            }
        });

        Log.i("Attention!!!!!", "tests test remplis");
        final RecyclerView rv  = (RecyclerView)findViewById(R.id.listAmis);
        mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);

        final AmisAdapter adapter = new AmisAdapter(pers, this);
        adapter.invokeWS(pers, this);
        rv.setAdapter(adapter);





        final ProgressBar progress = (ProgressBar) findViewById(R.id.progressAmis);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                progress.setVisibility(View.GONE);
            }
        });
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
        getMenuInflater().inflate(R.menu.home, menu);
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
        if (id == R.id.nav_home) {
            final Personne pers = (Personne)getIntent().getSerializableExtra("personne");
           Intent it = new Intent(FriendsActivity.this, HomeActivity.class);
            it.putExtra("personne",pers);
            startActivity(it);
        }
        else if (id == R.id.nav_friends){
            final Personne pers = (Personne)getIntent().getSerializableExtra("personne");
            Intent it = new Intent(FriendsActivity.this, FriendsActivity.class);
            it.putExtra("personne",pers);
            startActivity(it);
        }
        else if (id == R.id.nav_profile) {
            // Handle the camera action
            final Personne pers = (Personne)getIntent().getSerializableExtra("personne");
            Intent it = new Intent(FriendsActivity.this, MyProfileActivity.class);
            it.putExtra("personne",pers);
            startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }







}
