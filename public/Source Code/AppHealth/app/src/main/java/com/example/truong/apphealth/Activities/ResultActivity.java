package com.example.truong.apphealth.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.truong.apphealth.Instance;
import com.example.truong.apphealth.R;
import com.example.truong.apphealth.Server.ApiInterface;
import com.example.truong.apphealth.Server.Model.ListHistory;
import com.example.truong.apphealth.Server.RetrofitClient;

import java.text.DecimalFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_weight)
    EditText edtWeight;
    @BindView(R.id.text_height)
    EditText edtHeight;
    @BindView(R.id.text_age)
    EditText edtAge;
    @BindView(R.id.text_BMI)
    EditText edtBMI;
    @BindView(R.id.text)
    TextView mTextBMI;
    @BindView(R.id.text_health_result)
    TextView mTextHealthResult;
    @BindView(R.id.btn_habit)
    Button mButtonHabit;
    @BindView(R.id.btn_train)
    Button mButtonTrain;
    @BindView(R.id.btn_clinics)
    Button mButtonClinics;
    protected ApiInterface mApiService = RetrofitClient.getApiClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        initialize();
        apiGetHistory(Instance.profile.get(0).ID);
        setUpInfo();
        mButtonClinics.setOnClickListener(this);
        mButtonHabit.setOnClickListener(this);

    }

    private void initialize() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResultActivity.this, HomeActivity.class));
                finish();
            }
        });
    }

    private void apiGetHistory(String accoutId) {
        final Call<ListHistory> call = mApiService.getHistory(accoutId);
        call.enqueue(new Callback<ListHistory>() {
            @Override
            public void onResponse(Response<ListHistory> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Instance.resultList = response.body().result;
                    for (int i = 0; i < Instance.resultList.size(); i++) {
                        if (Instance.historyId.equals(Instance.resultList.get(i).historyID)) {
                            Instance.currentHistoryId = Instance.resultList.get(i).historyID;
                            String healthResult = Instance.resultList.get(i).healthResult;
                            switch (healthResult) {
                                case "1":
                                    mTextHealthResult.setText("Tình trạng sức khỏe rất tệ đạt 20%");
                                    break;
                                case "2":
                                    mTextHealthResult.setText("Tình trạng sức khỏe tệ đạt 40%");
                                    break;
                                case "3":
                                    mTextHealthResult.setText("Tình trạng sức khỏe bình thường đạt 60%");
                                    break;
                                case "4":
                                    mTextHealthResult.setText("Tình trạng sức khỏe tốt đạt 80%");

                                    break;
                                case "5":
                                    mTextHealthResult.setText("Tình trạng sức khỏe rất tốt đạt 100%");
                                    break;
                            }
                            Log.d("BBB", "health Result " + healthResult);
                        }

                    }
                    Log.d("BBB", "onResponse: " + Instance.resultList.size());
                } else {
                    Log.d("BBB", "sai get history: ");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("BBB", "onFailure: " + t.getMessage());
            }
        });

    }

    private void setUpInfo() {
        edtWeight.setText(Instance.getProfile.get(0).Weight);
        edtHeight.setText(Instance.getProfile.get(0).Height);
        double height = Double.parseDouble(Instance.getProfile.get(0).Height);
        double weight = Double.parseDouble(Instance.getProfile.get(0).Weight);
        DecimalFormat df = new DecimalFormat("0.00"); //định dạng lấy đến 2 con số
        double BMI = weight / Math.pow(height, 2) * 10000;
        edtBMI.setText(df.format(BMI));
        if (BMI < 18)
            mTextBMI.setText("Bạn hơi gầy");
        else if (18 <= BMI && BMI < 25)
            mTextBMI.setText("Bạn bình thường");
        else if (25 <= BMI && BMI < 30)
            mTextBMI.setText("Bạn béo phì độ 1");
        else if (30 <= BMI && BMI < 35)
            mTextBMI.setText("Bạn béo phì độ 2");
        else if (35 <= BMI)
            mTextBMI.setText("Bạn béo phì độ 3");
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        String str = Instance.getProfile.get(0).DoB;
        String[] tmp = str.split("-");
        int age = mYear - Integer.parseInt(tmp[0]);
        edtAge.setText(String.valueOf(age));
        Log.d("BBB", "chuổi cắt: " + tmp[0]);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_habit:
                startActivity(new Intent(ResultActivity.this, HabitActivity.class));
                break;
            case R.id.btn_train:

                break;
            case R.id.btn_clinics:
                startActivity(new Intent(ResultActivity.this, ListClinicsActivity.class));
                break;
        }

    }
}
