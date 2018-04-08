package com.example.truong.apphealth.Activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.truong.apphealth.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_password)
    EditText inputPassword;

    @BindView(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;

    @BindView(R.id.btn_signin)
    Button btnSignIn;
//    @BindView(R.id.lb_forgot_my_password)
//    TextView mTextviewForgotPassword;
//    @BindView(R.id.lb_sign_up)
//    TextView mTextviewSignup;


//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
//    @BindView(R.id.drawer_layout)
//    DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

//        setSupportActionBar(toolbar);

        initialize();

    }

    private void initialize() {

    }
}
