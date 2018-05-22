package com.example.truong.apphealth.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truong.apphealth.Instance;
import com.example.truong.apphealth.R;
import com.example.truong.apphealth.Server.ApiInterface;
import com.example.truong.apphealth.Server.Model.ListAccount;
import com.example.truong.apphealth.Server.Model.ListQuestion;
import com.example.truong.apphealth.Server.RetrofitClient;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.btn_sign_in)
    Button mButtonSignIn;
    @BindView(R.id.lb_forgot_my_password)
    TextView mTextviewForgotPassword;
    @BindView(R.id.login_button)
    LoginButton button;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sign_in_button)
    SignInButton signInButton;


    CallbackManager callbackManager;
    String name = "", id = "";
    public FirebaseAuth firebaseAuth;

    public GoogleApiClient googleApiClient;
    protected ApiInterface mApiService = RetrofitClient.getApiClient();

    protected ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initialize();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        setUpLoginFacebook();
        setUpLoginGoogle();
        mButtonSignIn.setOnClickListener(this);
    }

    private void setUpLoginGoogle() {
        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(LoginActivity.this)
                .enableAutoManage(LoginActivity.this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserSignInMethod();

            }
        });
    }

    private void setUpLoginFacebook() {
        callbackManager = CallbackManager.Factory.create();
        button.setReadPermissions(Arrays.asList("public_profile", "email"));
        button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("BBB", response.getJSONObject().toString());
                        try {
                            Instance.first_name = object.getString("name");
                            Instance.profile_id = Profile.getCurrentProfile().getId();
                            Log.d("BBB", "id face: " + Instance.profile_id);
                            apiGetQuestionListFB();
//                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//                            profilePictureView.setProfileId(Profile.getCurrentProfile().getId());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "name,email");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void UserSignInMethod() {
        Intent AuthIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);

        startActivityForResult(AuthIntent, 7);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7) {

            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (googleSignInResult.isSuccess()) {

                GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();

                FirebaseUserAuth(googleSignInAccount);
            }
        }
    }

    public void FirebaseUserAuth(GoogleSignInAccount googleSignInAccount) {

        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
        Toast.makeText(this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
        firebaseAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> AuthResultTask) {

                        if (AuthResultTask.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            Instance.first_name = firebaseUser.getDisplayName().toString();
                            Instance.email = firebaseUser.getEmail().toString();
                            Log.d("BBB", "id google " + firebaseUser.getUid());
                            Instance.photoUrl = firebaseUser.getPhotoUrl();
                            apiGetQuestionListFB();
//                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));


                        } else {
                            Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                        }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                checkLogin();
                break;


        }
    }

    private void apiLogin(String email, String password) {
        progressDialog.show();
        Call<ListAccount> call = mApiService.login(email, password);
        call.enqueue(new Callback<ListAccount>() {
            @Override
            public void onResponse(Response<ListAccount> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Instance.profile = response.body().result;
                    Log.d("BBB", "Size list" + Instance.profile.size());
                    if (Instance.profile.size() != 0) {
                        apigetProfile(Instance.profile.get(0).ID);
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

    private void apiGetQuestionList() {
        Call<ListQuestion> call = mApiService.getListQuestion();
        call.enqueue(new Callback<ListQuestion>() {
            @Override
            public void onResponse(Response<ListQuestion> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Instance.questionList = response.body().result;
                    if (Instance.profile.size() == 0) {
                        Toast.makeText(LoginActivity.this, "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login is Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    }
                    Log.d("BBB", "size list question" + Instance.questionList.size());
                } else {
                }
                progressDialog.dismiss();

            }


            @Override
            public void onFailure(Throwable t) {
                Log.d("BBB", "Loi " + t.getMessage());
            }
        });
    }

    private void apiGetQuestionListFB() {
        Call<ListQuestion> call = mApiService.getListQuestion();
        call.enqueue(new Callback<ListQuestion>() {
            @Override
            public void onResponse(Response<ListQuestion> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Instance.questionList = response.body().result;

                    Toast.makeText(LoginActivity.this, "Login is Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                    Log.d("BBB", "size list question" + Instance.questionList.size());
                } else {
                }
                progressDialog.dismiss();

            }


            @Override
            public void onFailure(Throwable t) {
                Log.d("BBB", "Loi " + t.getMessage());
            }
        });
    }

    private void apigetProfile(String AccountID) {
        Call<ListAccount> call = mApiService.getProfile(AccountID);
        call.enqueue(new Callback<ListAccount>() {
            @Override
            public void onResponse(Response<ListAccount> response, Retrofit retrofit) {
                Instance.getProfile = response.body().result;
                if (Instance.getProfile != null) {
                    String address = Instance.getProfile.get(0).Address.replace(' ', '+');
                    new getListAsysncTask().execute("http://maps.google.com/maps/api/geocode/json?address=" + address);

                } else {
                    Log.d("BBB", "null");
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private class getListAsysncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                Log.d("BBB", "json array" + jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String geometry = object.getString("geometry");
                    JSONObject object1 = new JSONObject(geometry);
                    JSONObject object2 = object1.getJSONObject("location");
                    Instance.lat = object2.getString("lat");
                    Instance.lng = object2.getString("lng");

                    Log.d("BBB", "lat " + Instance.lat + "  lng " + Instance.lng);


                }
                if (!Instance.lat.equals("") || !Instance.lng.equals("")) {
                    apiGetQuestionList();
                } else {
                    String address = Instance.getProfile.get(0).Address.replace(' ', '+');
                    new getListAsysncTask().execute("http://maps.google.com/maps/api/geocode/json?address=" + address);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkLogin() {
        if (inputEmail.getText().length() == 0 || inputPassword.getText().length() == 0) {
            Toast.makeText(this, "Bạn cần nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            apiLogin(inputEmail.getText().toString(), inputPassword.getText().toString());
        }
    }

    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }
}
