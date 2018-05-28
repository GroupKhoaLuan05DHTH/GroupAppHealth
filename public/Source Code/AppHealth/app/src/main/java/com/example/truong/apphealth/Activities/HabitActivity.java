package com.example.truong.apphealth.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.truong.apphealth.Instance;
import com.example.truong.apphealth.R;
import com.example.truong.apphealth.Server.ApiInterface;
import com.example.truong.apphealth.Server.Habit;
import com.example.truong.apphealth.Server.Model.ListHabit;
import com.example.truong.apphealth.Server.RetrofitClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class HabitActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView_habit)
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    protected ApiInterface mApiService = RetrofitClient.getApiClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);
        ButterKnife.bind(this);
        initialize();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getHabitNote(Instance.currentHistoryId);
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

    private void getHabitNote(String historyId) {
        Call<ListHabit> call = mApiService.getHabitNote(historyId);
        call.enqueue(new Callback<ListHabit>() {
            @Override
            public void onResponse(Response<ListHabit> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    List<Habit> habits = response.body().result;
                    RecyclerViewHabitAdapter adapter = new RecyclerViewHabitAdapter(HabitActivity.this, habits);
                    recyclerView.setAdapter(adapter);
                    Log.d("BBB", "onResponse: habit " + habits.size());
                } else {

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
