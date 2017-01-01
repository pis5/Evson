package com.example.mkass.evson;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import entities.Personne;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount,firstVisibleItem;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Personne pers = (Personne)getIntent().getSerializableExtra("personne");

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(HomeActivity.this, NewEventActivity.class);
                it.putExtra("personne",pers);
                startActivity(it);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // Profil utilisateur dans le NavHeader
        ImageView navImageProfile = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.navImageProfile);
        TextView navNameProfile = (TextView) navigationView.getHeaderView(0).findViewById(R.id.navNameProfile);
        TextView navEmailProfile = (TextView)navigationView.getHeaderView(0).findViewById(R.id.navEmailProfile);


        navNameProfile.setText(pers.getPrenom() + " "+ pers.getNom());
        navEmailProfile.setText(pers.getEmail());
        if( pers.getPhoto() != null){
            Bitmap bMap = BitmapFactory.decodeByteArray(pers.getPhoto(),0,pers.getPhoto().length);
            navImageProfile.setImageBitmap(bMap);
        }


        final RecyclerView rv  = (RecyclerView)findViewById(R.id.list);
        mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);

        final EvenementAdapter adapter = new EvenementAdapter(pers);
        adapter.invokeWS(pers, 10,false,this);
        rv.setAdapter(adapter);

        final int[] previousTotal = {0};
        final int visibleThreshold = 5;

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

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



                        adapter.invokeWS(pers,  3,true,getBaseContext());
                        loading = true;
                    }

                    }

            }
        });

        final ProgressBar progress = (ProgressBar) findViewById(R.id.progress);
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
            Intent it = new Intent(HomeActivity.this, HomeActivity.class);
            it.putExtra("personne",pers);
            startActivity(it);

        }
        else if (id == R.id.nav_friends){
            final Personne pers = (Personne)getIntent().getSerializableExtra("personne");
            Intent it = new Intent(HomeActivity.this, FriendsActivity.class);
            it.putExtra("personne",pers);
            startActivity(it);
        }
        else if (id == R.id.nav_profile) {
            final Personne pers = (Personne)getIntent().getSerializableExtra("personne");
            Intent it = new Intent(HomeActivity.this, MyProfileActivity.class);
            it.putExtra("personne",pers);
            startActivity(it);
        }
        else if (id == R.id.nav_myEvents) {
            final Personne pers = (Personne)getIntent().getSerializableExtra("personne");
            Intent it = new Intent(HomeActivity.this, MyEventsActivity.class);
            it.putExtra("personne",pers);
            startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
