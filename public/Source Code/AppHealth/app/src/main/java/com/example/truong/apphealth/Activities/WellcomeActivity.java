package com.example.truong.apphealth.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.truong.apphealth.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WellcomeActivity extends AppCompatActivity {
    @BindView(R.id.welcome_btn_sign_in)
    Button mButtonSignIn;
    @BindView(R.id.welcome_btn_sign_up)
    Button mButtonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);
        ButterKnife.bind(this);

        mButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLoginScreen();
            }
        });
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignUpScreen();
            }
        });
    }

    private void goToLoginScreen() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void goToSignUpScreen() {
        startActivity(new Intent(this, SignUpActivity.class));
    }

}
