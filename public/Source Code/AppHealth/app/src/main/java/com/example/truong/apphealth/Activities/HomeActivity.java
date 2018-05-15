package com.example.truong.apphealth.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.truong.apphealth.Activities.HeartRate.HeartRateMainActivity;
import com.example.truong.apphealth.Instance;
import com.example.truong.apphealth.R;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.layout_medical_profile)
    LinearLayout mLayoutMedicalProfile;
    @BindView(R.id.layout_list_clinics)
    LinearLayout mLayoutListClinics;
    @BindView(R.id.layout_medical_knowledge)
    LinearLayout mLayoutMedicalKnowledge;
    @BindView(R.id.layout_history)
    LinearLayout mLayoutHistory;
    NavigationView navigationView;
    public FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        initialize();
        mLayoutMedicalProfile.setOnClickListener(this);
        mLayoutListClinics.setOnClickListener(this);
        mLayoutMedicalKnowledge.setOnClickListener(this);
        mLayoutHistory.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }

    private void initialize() {
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setUpNavView();
        bindDataNavUserInfo();
    }

    private void setUpNavView() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.setBackgroundResource(R.color.default_background_color);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.nav_file:
                startActivity(new Intent(this, MedicalProfileActivity.class));
                break;
            case R.id.nav_sign_out:
                confirmSignoutDialog();
                break;
            case R.id.nav_profile:
                break;
            case R.id.nav_list_clinic:
                break;
            case R.id.nav_medical_knowledge:
                startActivity(new Intent(this, MedicalKnowledgeActivity.class));
                break;
            case R.id.nav_history:
                break;
            case R.id.nav_contact:
                startActivity(new Intent(this, ContactActivity.class));
                break;
            case R.id.nav_support:
                break;
            case R.id.nav_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.nav_heartReat:
                startActivity(new Intent(this, HeartRateMainActivity.class));

        }
        return false;
    }

    private void confirmSignoutDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(getString(R.string.confirm_logout));

        alert.setPositiveButton(getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                onSignOut();
            }
        });

        alert.setNegativeButton(getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }

    private void onSignOut() {
        Intent i = new Intent(this, WellcomeActivity.class);
        LoginManager.getInstance().logOut();
        firebaseAuth.signOut();
        startActivity(i);
        finish();
    }

    private void bindDataNavUserInfo() {
        View headerView = mNavView.getHeaderView(0);
        ProfilePictureView profilePictureView = headerView.findViewById(R.id.image_profile);
        ImageView imageView = headerView.findViewById(R.id.image_google);
        if (Instance.accounts.size() != 0) {
            ((TextView) headerView.findViewById(R.id.text_name)).setText(Instance.accounts.get(0).first_name);
        } else {
            ((TextView) headerView.findViewById(R.id.text_name)).setText(Instance.first_name);
            if (!Instance.profile_id.equals("")) {
                profilePictureView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                profilePictureView.setProfileId(Instance.profile_id);
            } else {
                profilePictureView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                Picasso.with(this).load(Instance.photoUrl).resize(70, 70).centerCrop().into(imageView);
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_medical_profile:
                startActivity(new Intent(this, MedicalProfileActivity.class));
                break;
            case R.id.layout_list_clinics:
                break;
            case R.id.layout_medical_knowledge:
                startActivity(new Intent(this, MedicalKnowledgeActivity.class));
                break;
            case R.id.layout_history:
                break;
        }
    }
}
