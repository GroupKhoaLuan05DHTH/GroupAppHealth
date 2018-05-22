package com.example.truong.apphealth.Activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.truong.apphealth.Config;
import com.example.truong.apphealth.Instance;
import com.example.truong.apphealth.R;
import com.example.truong.apphealth.Server.Clinics;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListClinicsActivity extends AppCompatActivity implements OnMapReadyCallback {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ProgressDialog mProgressDialog;
    private List<Clinics> clinicsList;
    private GoogleMap mMap;
    @BindView(R.id.recyclerViewClinics)
    RecyclerView recyclerView;
    private Clinics clinics;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewListClinicsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clinics);
        ButterKnife.bind(this);
        initialize();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        clinicsList = new ArrayList<>();
//        clinics = new Clinics();
//        clinics.name = "Phòng Chuẩn Trị Đông Y Hằng Sanh Đường";
//        clinics.vicinity = "11A/L3, Hà Thị Khiêm, Tổ 27, Phường Trung Mỹ Tây, Quận 12, Thành Phố Hồ Chí Minh, Thành Phố Hồ Chí Minh";
//        clinics.lat = 10.8542981;
//        clinics.lng = 106.6278696;
//        clinicsList.add(clinics);
//        Log.d("BBB", "size clinics list: " + clinicsList.size());
        new getListAsysncTask().execute("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + Instance.lat + "," + Instance.lng + "&radius=1000&types=hospital&key=AIzaSyB4zVpRrFZeJvNb9XyI5I-_QufiwvOnPEQ");

    }

    private void initialize() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setUpMap();
    }

    private void setUpMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_contact_support);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng address = new LatLng(Double.parseDouble(Instance.lat), Double.parseDouble(Instance.lng));
        mMap.addMarker(new MarkerOptions().position(address).title(getString(R.string.title_marker_contact)));
        CameraUpdate centerCamera = CameraUpdateFactory.newLatLng(address);
        googleMap.animateCamera(centerCamera);
        CameraUpdate zoomUpdate = CameraUpdateFactory.newLatLngZoom(address, Config.GOOGLE_MAP_ZOOM);
        googleMap.animateCamera(zoomUpdate);
    }

    private class getListAsysncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    clinics = new Clinics();
                    JSONObject object = jsonArray.getJSONObject(i);
                    String name = object.getString("name");
                    clinics.name = object.getString("name");
                    String vicinity = object.getString("vicinity");
                    clinics.vicinity = object.getString("vicinity");
                    String geometry = object.getString("geometry");
                    JSONObject object1 = new JSONObject(geometry);
                    JSONObject object2 = object1.getJSONObject("location");
                    String lat = object2.getString("lat");
                    clinics.lat = Double.parseDouble(object2.getString("lat"));
                    String lng = object2.getString("lng");
                    clinics.lng = Double.parseDouble(object2.getString("lng"));
                    Log.d("BBB", "name " + name + "///" + vicinity + "///" + lat);

                    clinicsList.add(clinics);
                    Log.d("BBB", "size clinics " + clinicsList.size());

                }
                for (int i = 0; i < clinicsList.size(); i++) {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(clinicsList.get(i).lat, clinicsList.get(i).lng)).title(clinicsList.get(i).name));

                }
                adapter = new RecyclerViewListClinicsAdapter(ListClinicsActivity.this, clinicsList, mMap);
                recyclerView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
