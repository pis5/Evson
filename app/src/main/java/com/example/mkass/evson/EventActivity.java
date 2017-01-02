package com.example.mkass.evson;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import entities.Evenement;
import entities.Personne;

import static com.example.mkass.evson.R.id.map;

public class EventActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private MapFragment mMapFragment;
    private Evenement evenement;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        evenement = (Evenement) getIntent().getSerializableExtra("evenement");

        mMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map, mMapFragment);
        fragmentTransaction.commit();

        mMapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(evenement.getTitre());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final Personne pers = (Personne) getIntent().getSerializableExtra("personne");

        // Profil utilisateur dans le NavHeader
        ImageView navImageProfile = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.navImageProfile);
        TextView navNameProfile = (TextView) navigationView.getHeaderView(0).findViewById(R.id.navNameProfile);
        TextView navEmailProfile = (TextView) navigationView.getHeaderView(0).findViewById(R.id.navEmailProfile);


        navNameProfile.setText(pers.getPrenom() + " " + pers.getNom());
        navEmailProfile.setText(pers.getEmail());
        if (pers.getPhoto() != null) {
            Bitmap bMap = BitmapFactory.decodeByteArray(pers.getPhoto(), 0, pers.getPhoto().length);
            navImageProfile.setImageBitmap(bMap);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(EventActivity.this, NewEventActivity.class);
                it.putExtra("evenement",evenement);
                startActivity(it);
            }
        });

        final RecyclerView rv  = (RecyclerView)findViewById(R.id.list);
        mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);

        final PostsAdapter adapter = new PostsAdapter();
        adapter.invokeWS(evenement,this);
        rv.setAdapter(adapter);

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
        getMenuInflater().inflate(R.menu.event, menu);
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
            final Personne pers = (Personne) getIntent().getSerializableExtra("personne");
            Intent it = new Intent(EventActivity.this, HomeActivity.class);
            it.putExtra("personne", pers);
            startActivity(it);

        } else if (id == R.id.nav_friends) {
            final Personne pers = (Personne) getIntent().getSerializableExtra("personne");
            Intent it = new Intent(EventActivity.this, FriendsActivity.class);
            it.putExtra("personne", pers);
            startActivity(it);
        } else if (id == R.id.nav_profile) {
            final Personne pers = (Personne) getIntent().getSerializableExtra("personne");
            Intent it = new Intent(EventActivity.this, MyProfileActivity.class);
            it.putExtra("personne", pers);
            startActivity(it);
        } else if (id == R.id.nav_myEvents) {
            final Personne pers = (Personne) getIntent().getSerializableExtra("personne");
            Intent it = new Intent(EventActivity.this, MyEventsActivity.class);
            it.putExtra("personne", pers);
            startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.getUiSettings()
                .setZoomControlsEnabled(true);
        LatLng evLocation = new LatLng(evenement.getLieu().getLatitude(), evenement.getLieu().getLongitude());
        //map.animateCamera(CameraUpdateFactory.newLatLngZoom(evLocation, 13));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(evLocation)      // Sets the center of the map to location user
                .zoom(14)                   // Sets the zoom
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.addMarker(new MarkerOptions().position(evLocation).title(evenement.getTitre()));
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
