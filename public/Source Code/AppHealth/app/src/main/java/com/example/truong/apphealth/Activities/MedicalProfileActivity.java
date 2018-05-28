package com.example.truong.apphealth.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truong.apphealth.Instance;
import com.example.truong.apphealth.R;
import com.example.truong.apphealth.Server.ApiInterface;
import com.example.truong.apphealth.Server.Check;
import com.example.truong.apphealth.Server.Model.ResultHistory;
import com.example.truong.apphealth.Server.RetrofitClient;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MedicalProfileActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_info)
    LinearLayout linearLayout;
    @BindView(R.id.layout_question)
    LinearLayout linearLayoutQuestion;
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
    @BindView(R.id.progressbar_index)
    ProgressBar mProgressbarInder;
    @BindView(R.id.text_date)
    TextView mTextDate;
    @BindView(R.id.text_index)
    TextView mTextIndex;
    @BindView(R.id.text_total_index)
    TextView mTextTotalIndex;
    @BindView(R.id.text_title)
    TextView mtextTitle;
    @BindView(R.id.text_question)
    TextView mtextQuestion;
    @BindView(R.id.fab_back)
    FloatingActionButton mFABBack;
    @BindView(R.id.fab_next)
    FloatingActionButton mFABNext;
    private int i = 0, position = 1;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_start)
    Button mButtonStart;
    private RecyclerView.LayoutManager layoutManager;
    protected ApiInterface mApiService = RetrofitClient.getApiClient();
    private String answerQuestion = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_profile);
        ButterKnife.bind(this);
        initialize();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date today = Calendar.getInstance().getTime();
        mTextDate.setText(dateFormat.format(today));
        setUpdata();
        setUpRecyclerViewAnswer();
        mProgressbarInder.setMax(Instance.questionList.size());
    }

    private void setUpRecyclerViewAnswer() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void setUpdata() {
        mTextIndex.setText("1");
        mFABBack.setVisibility(View.INVISIBLE);
        mProgressbarInder.setProgress(1);
        mTextTotalIndex.setText(String.valueOf("/" + Instance.questionList.size()));
        mtextTitle.setText(Instance.questionList.get(0).ChuDe);
        mtextQuestion.setText(Instance.questionList.get(0).CauHoi);
        RecyclerViewAnswerAdapter adapter = new RecyclerViewAnswerAdapter(this, Instance.questionList.get(0).answer);
        recyclerView.setAdapter(adapter);
        if (Instance.QuestionOptionID != "") {
            answerQuestion = Instance.QuestionOptionID;
        }
        mFABNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                if (i < Instance.questionList.size()) {
                    linearLayout.setVisibility(View.GONE);
                    linearLayoutQuestion.setVisibility(View.VISIBLE);
                    mtextTitle.setText(Instance.questionList.get(i).ChuDe);
                    mtextQuestion.setText(Instance.questionList.get(i).CauHoi);
                    RecyclerViewAnswerAdapter adapter = new RecyclerViewAnswerAdapter(MedicalProfileActivity.this, Instance.questionList.get(i).answer);
                    recyclerView.setAdapter(adapter);
                    if (answerQuestion.equals("")) {
                        answerQuestion = Instance.QuestionOptionID;
                    } else {
                        answerQuestion = answerQuestion + "," + Instance.QuestionOptionID;
                    }
                    position = i + 1;
                    mTextIndex.setText(String.valueOf(position));
                    if (i < 0) {
                        mFABBack.setVisibility(View.INVISIBLE);
                    } else {
                        mFABBack.setVisibility(View.VISIBLE);
                    }

                    if (i == Instance.questionList.size() - 1) {
                        apiCreateHistory(Instance.profile.get(0).ID);
                        mFABNext.setVisibility(View.INVISIBLE);
                        mButtonStart.setVisibility(View.VISIBLE);
                    } else {
                        mFABNext.setVisibility(View.VISIBLE);
                        mButtonStart.setVisibility(View.INVISIBLE);

                    }
                    mProgressbarInder.setProgress(position);

                } else {
                    Toast.makeText(MedicalProfileActivity.this, "Da het cau hoi", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mFABBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = i - 1;
                if (i >= 0) {
                    mtextTitle.setText(Instance.questionList.get(i).ChuDe);
                    mtextQuestion.setText(Instance.questionList.get(i).CauHoi);
                    RecyclerViewAnswerAdapter adapter = new RecyclerViewAnswerAdapter(MedicalProfileActivity.this, Instance.questionList.get(i).answer);
                    recyclerView.setAdapter(adapter);
                    position = i + 1;
                    mTextIndex.setText(String.valueOf(position));
                    if (i <= 0) {
                        mFABBack.setVisibility(View.INVISIBLE);
                    } else {
                        mFABBack.setVisibility(View.VISIBLE);
                    }
                    if (i == Instance.questionList.size() - 1) {
                        mFABNext.setVisibility(View.INVISIBLE);
                        mButtonStart.setVisibility(View.VISIBLE);

                    } else {
                        mButtonStart.setVisibility(View.INVISIBLE);
                        mFABNext.setVisibility(View.VISIBLE);
                    }
                    mProgressbarInder.setProgress(position);
                } else {
                    mProgressbarInder.setProgress(1);
                    mTextIndex.setText("1");
                    mFABBack.setVisibility(View.INVISIBLE);
//                    linearLayoutQuestion.setVisibility(View.GONE);
//                    linearLayout.setVisibility(View.VISIBLE);
//                    setUpInfo();
//                    mtextTitle.setText("Cơ Bản");
                }
            }
        });
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerQuestion = answerQuestion + "," + Instance.QuestionOptionID;
                Log.d("BBB", "answerQuestion: " + answerQuestion);
                apiAnswerQuestion( Instance.historyId, answerQuestion);
            }
        });

    }

    private void apiAnswerQuestion(final String historyId, String arraylist) {

        Call<Check> call = mApiService.answerQuestion(historyId, arraylist);
        call.enqueue(new Callback<Check>() {
            @Override
            public void onResponse(Response<Check> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    boolean check = response.body().result;
                    apiAlgorithm(historyId);
                    Log.d("BBB", "đúng " + check);

                } else {
                    Log.d("BBB", "Sai api answer question");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("BBB", "Lỗi answer question " + t.getMessage());
            }
        });
    }

    private void apiAlgorithm(String historyId) {
        Call<Check> call = mApiService.algorithm(historyId);
        call.enqueue(new Callback<Check>() {
            @Override
            public void onResponse(Response<Check> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    boolean check = response.body().result;
                    Log.d("BBB", "onResponse: " + check);
                    if (check) {
                        startActivity(new Intent(MedicalProfileActivity.this, ResultActivity.class));
                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Throwable t) {

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

    private void apiCreateHistory(String id) {
        Call<ResultHistory> call = mApiService.createHistory(id);
        call.enqueue(new Callback<ResultHistory>() {
            @Override
            public void onResponse(Response<ResultHistory> response, Retrofit retrofit) {
                if (response.isSuccess()) {
               Instance.historyId = response.body().result.historyId;
                    Log.d("BBB", "History id " +  Instance.historyId);
                } else {
                    Log.d("BBB", "sai history");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("BBB", "lỗi history " + t.getMessage());

            }
        });
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
