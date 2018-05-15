package com.example.truong.apphealth.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.truong.apphealth.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedicalProfileActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressbar_index)
    ProgressBar mProgressbarInder;
    @BindView(R.id.text_date)
    TextView mTextDate;
    @BindView(R.id.text_index)
    TextView mTextIndex;
    @BindView(R.id.text_total_index)
    TextView mTextTotalIndex;
    @BindView(R.id.fab_back)
    FloatingActionButton mFABBack;
    @BindView(R.id.fab_next)
    FloatingActionButton mFABNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_profile);
        ButterKnife.bind(this);
        initialize();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = Calendar.getInstance().getTime();
        mTextDate.setText(dateFormat.format(today));
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
    }
}
