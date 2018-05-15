package com.example.truong.apphealth.Activities.HeartRate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.truong.apphealth.Instance;
import com.example.truong.apphealth.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeartRateMainActivity extends AppCompatActivity {
    @BindView(R.id.btn_start)
    Button mButtonStart;
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate_main);
        ButterKnife.bind(this);
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HeartRateMainActivity.this, HeartRateActivity.class);
                startActivity(intent);

            }
        });
        textView.setText("Nhịp tim của bạn là: " + Instance.heartRate);
    }
}
