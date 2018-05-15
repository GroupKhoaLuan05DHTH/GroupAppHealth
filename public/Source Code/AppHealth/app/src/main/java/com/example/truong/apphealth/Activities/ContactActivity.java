package com.example.truong.apphealth.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.truong.apphealth.Config;
import com.example.truong.apphealth.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @BindView(R.id.text_support_address)
    TextView mTextSupportAddress;
    @BindView(R.id.text_support_phone)
    TextView mTextSupportPhone;
    @BindView(R.id.layout_support_phone)
    LinearLayout mLayoutSupportPhone;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        setUpToolBar();
        mLayoutSupportPhone.setOnClickListener(this);

        setUpMap();
        bindContactInfo();
    }

    private void setUpToolBar() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        //set the back arrow in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    private void callSupport() {

        String uri = "tel:" + mTextSupportPhone.getText();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        if (ActivityCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions();
        } else {
            startActivity(intent);
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_support_phone:
                callSupport();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            default:
                break;
            case 1:
                callSupport();
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng address = new LatLng(Config.latitudeLocation, Config.longitudeLocation);
        mMap.addMarker(new MarkerOptions().position(address).title(getString(R.string.title_marker_contact)));
        CameraUpdate centerCamera = CameraUpdateFactory.newLatLng(address);
        googleMap.animateCamera(centerCamera);
        CameraUpdate zoomUpdate = CameraUpdateFactory.newLatLngZoom(address, Config.GOOGLE_MAP_ZOOM);
        googleMap.animateCamera(zoomUpdate);

    }

    private void setUpMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_contact_support);
        mapFragment.getMapAsync(this);
    }

    private void bindContactInfo() {
        mTextSupportAddress.setText(Config.HOME_ADDRESS);
        mTextSupportPhone.setText(Config.HOME_PHONE);
    }
}
