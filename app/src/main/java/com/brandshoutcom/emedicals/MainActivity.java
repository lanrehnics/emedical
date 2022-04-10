package com.brandshoutcom.emedicals;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.brandshoutcom.adapter.DoctorAdapter;
import com.brandshoutcom.pojo.Doctor;
import com.brandshoutcom.widget.ExpandableSearchView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, DoctorAdapter.ClickListener, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    private static final String TAG = "MainActivity";
    private RecyclerView recycDoc;
    private DoctorAdapter adapter;
    ArrayList<Doctor> listDoc;
    private BottomSheetBehavior mBottomSheetBehavior;
    private Toolbar toolbar;

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private ActionBarDrawerToggle toggle;
    private View navHeader;
    private CircleImageView circular_paddy;
    private TextView text_user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _init();
        _initToolbar();

        View bottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(400);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setPeekHeight(400);
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        recycDoc = (RecyclerView) findViewById(R.id.recycDoc);
        adapter = new DoctorAdapter();
        adapter.setClickListener(this);
        recycDoc.setAdapter(adapter);
        recycDoc.setLayoutManager(new LinearLayoutManager(getBaseContext()));


        pupolater();

//        ExpandableSearchView expandableSearchView = (ExpandableSearchView) findViewById(R.id.expandableSearchView);
//        expandableSearchView.setOnSearchActionListener(new ExpandableSearchView.OnSearchActionListener() {
//            @Override
//            public void onSearchAction(String text) {
//                if (text.length() > 0) {
//                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                } else {
//                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                    Snackbar.make(findViewById(R.id.drawerLayout), "Invalid Query", Snackbar.LENGTH_LONG).show();
//                }
//            }
//        });


    }

    private void pupolater() {
        Doctor doc = new Doctor();
        doc.setRating(4);
        doc.setName("Something Whatver");
        doc.setJob("Dentist");
        listDoc = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listDoc.add(doc);
        }
        adapter.setListDoc(listDoc);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    @Override
    public void itemClick(View v, int postion) {
        startActivity(new Intent(MainActivity.this, DocDetails.class));
    }

    private void _init() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navView = (NavigationView) findViewById(R.id.navView);
        navHeader = LayoutInflater.from(this).inflate(R.layout.nav_header, null);
        circular_paddy = (CircleImageView) navHeader.findViewById(R.id.circular_paddy);
        text_user_name = (TextView) navHeader.findViewById(R.id.text_user_name);

        navView.addHeaderView(navHeader);
        navView.setNavigationItemSelectedListener(this);


    }

    private void _initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("eMedical");

        toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
//        Intent intent = null;
        if (item.getItemId() == R.id.action_home) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }

        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }
}

