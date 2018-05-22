package com.example.truong.apphealth.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.truong.apphealth.Instance;
import com.example.truong.apphealth.R;
import com.example.truong.apphealth.Server.ApiInterface;
import com.example.truong.apphealth.Server.Check;
import com.example.truong.apphealth.Server.Model.ListAccount;
import com.example.truong.apphealth.Server.RetrofitClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.btn_sign_up)
    Button mButtonSignUp;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Boolean check = false;
    private String email, password, name;
    protected ApiInterface mApiService = RetrofitClient.getApiClient();
    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        initialize();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        mButtonSignUp.setOnClickListener(this);


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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_up:
                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();
                name = inputName.getText().toString();
                if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                    Toast.makeText(this, "Bạn Nhập Chưa Đủ Thông Tin", Toast.LENGTH_SHORT).show();

                } else {
                    apiregisterAccout(email, password, name);

                }
                break;
        }
    }

    private void apiregisterAccout(final String email, final String password, String name) {
        Call<Check> call = mApiService.register(email, password, name);
        call.enqueue(new Callback<Check>() {
            @Override
            public void onResponse(Response<Check> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    check = response.body().result;
                    if (check) {
                        Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        apiLogin(email, password);
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void apiLogin(String email, String password) {
        progressDialog.show();
        Call<ListAccount> call = mApiService.login(email, password);
        call.enqueue(new Callback<ListAccount>() {
            @Override
            public void onResponse(Response<ListAccount> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Instance.profile = response.body().result;
                    if (Instance.profile.size() != 0) {
                        apigetProfile(Instance.profile.get(0).ID);
                    } else {
                    }

                } else {
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

    private void apigetProfile(String AccountID) {
        Call<ListAccount> call = mApiService.getProfile(AccountID);
        call.enqueue(new Callback<ListAccount>() {
            @Override
            public void onResponse(Response<ListAccount> response, Retrofit retrofit) {
                Instance.getProfile = response.body().result;
                startActivity(new Intent(SignUpActivity.this, RegisterActivity.class));

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
