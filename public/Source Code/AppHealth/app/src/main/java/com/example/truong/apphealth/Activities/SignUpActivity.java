package com.example.truong.apphealth.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.truong.apphealth.Instance;
import com.example.truong.apphealth.R;
import com.example.truong.apphealth.Server.Account;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    Account account;
    List<Account> accounts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        initialize();
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
                bindInfo();
                break;
        }
    }

    private void bindInfo() {
        if (inputEmail.getText().length() == 0 || inputName.getText().length() == 0 || inputPassword.getText().length() == 0) {
            Toast.makeText(this, "Bạn cần nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            account = new Account();
            account.email = inputEmail.getText().toString();
            account.first_name = inputName.getText().toString();
            account.password = inputPassword.getText().toString();
            accounts.add(account);
            Instance.accounts=accounts;
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            Log.d("BBB","size "+accounts.size());
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
