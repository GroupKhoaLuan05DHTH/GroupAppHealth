package com.example.truong.apphealth.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.truong.apphealth.Instance;
import com.example.truong.apphealth.R;
import com.example.truong.apphealth.RecyclerViewHistoryDetailAdapter;
import com.example.truong.apphealth.Server.ApiInterface;
import com.example.truong.apphealth.Server.RetrofitClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryDetailActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView_history_detail)
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    protected ApiInterface mApiService = RetrofitClient.getApiClient();
    private RecyclerViewHistoryDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        ButterKnife.bind(this);
        initialize();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewHistoryDetailAdapter(this, Instance.questionOptions);
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
