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

public class AddFriendsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount, firstVisibleItem;
    LinearLayoutManager mLayoutManager;
    String nom="";
    String prenom="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_to_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Friends");






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        final Personne pers = (Personne)getIntent().getSerializableExtra("personne");

        nom=(String) getIntent().getSerializableExtra("nom");
        prenom=(String) getIntent().getSerializableExtra("prenom");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(AddFriendsActivity.this,LookForFriendActivity.class);
                it.putExtra("personne",pers);
                startActivity(it);
            }
        });

        //boutons menu amis
        final ImageButton MesAmis = (ImageButton)findViewById(R.id.imageButtonMesAmis);
        MesAmis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(AddFriendsActivity.this, FriendsActivity.class);
                it.putExtra("personne",pers);
                startActivity(it);

            }
        });

        final ImageButton AjouterAmi = (ImageButton)findViewById(R.id.imageButtonAjouterAmis);
        AjouterAmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(AddFriendsActivity.this, AddFriendsActivity.class);
                it.putExtra("personne",pers);
                startActivity(it);

            }
        });


        final ImageButton Demandes = (ImageButton)findViewById(R.id.imageButtonDemandesAmis);
        Demandes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(AddFriendsActivity.this, InvitesFriendsActivity.class);
                it.putExtra("personne",pers);
                startActivity(it);

            }
        });

        Log.i("Attention!!!!!", "tests test remplis");
        final RecyclerView rv  = (RecyclerView)findViewById(R.id.listAmisToAdd);
        mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);

        final FriendsToAddAdapter adapter = new FriendsToAddAdapter(pers,this);
       // adapter.invokeWS(pers, this);


if(nom!=null && prenom!=null){
        if(!nom.equals("") || !prenom.equals("")){
            adapter.invokeWS(pers,2,  nom, prenom ,true,getBaseContext());

            rv.setAdapter(adapter);
        }}

        final int[] previousTotal = {0};
        final int visibleThreshold = 5;

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(!nom.equals("") || !prenom.equals("")){

                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = rv.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if (totalItemCount > previousTotal[0]) {
                            loading = false;
                            previousTotal[0] = totalItemCount;
                        }
                    }
                    if (!loading && (totalItemCount - visibleItemCount)
                            <= (firstVisibleItem + visibleThreshold)) {
                        // End has been reached



                        adapter.invokeWS(pers,3,  nom, prenom ,false,getBaseContext());
                        loading = true;
                    }


                }


                }
            }
        });





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
           Intent it = new Intent(AddFriendsActivity.this, HomeActivity.class);
            it.putExtra("personne",pers);
            startActivity(it);
        }
        else if (id == R.id.nav_friends){
            final Personne pers = (Personne)getIntent().getSerializableExtra("personne");
            Intent it = new Intent(AddFriendsActivity.this, FriendsActivity.class);
            it.putExtra("personne",pers);
            startActivity(it);
        }
        else if (id == R.id.nav_profile) {
            final Personne pers = (Personne)getIntent().getSerializableExtra("personne");
            Intent it = new Intent(AddFriendsActivity.this, MyProfileActivity.class);
            it.putExtra("personne",pers);
            startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }







}
