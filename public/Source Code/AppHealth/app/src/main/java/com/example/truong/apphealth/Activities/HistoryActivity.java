package com.example.truong.apphealth.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.truong.apphealth.Instance;
import com.example.truong.apphealth.R;
import com.example.truong.apphealth.Server.ApiInterface;
import com.example.truong.apphealth.Server.RetrofitClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView_history)
    RecyclerView recyclerView;
    protected ApiInterface mApiService = RetrofitClient.getApiClient();
    String TAG = "BBB";
    private RecyclerView.LayoutManager layoutManager;
    RecyclerViewHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        initialize();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewHistoryAdapter(this, Instance.resultList);
        recyclerView.setAdapter(adapter);
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
