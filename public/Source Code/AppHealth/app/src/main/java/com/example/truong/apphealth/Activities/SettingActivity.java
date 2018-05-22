package com.example.truong.apphealth.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truong.apphealth.Instance;
import com.example.truong.apphealth.R;
import com.example.truong.apphealth.Server.ApiInterface;
import com.example.truong.apphealth.Server.Check;
import com.example.truong.apphealth.Server.Model.ListAccount;
import com.example.truong.apphealth.Server.RetrofitClient;
import com.facebook.login.widget.ProfilePictureView;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.input_mobile)
    EditText inputMobile;
    @BindView(R.id.input_height)
    EditText inputHeight;
    @BindView(R.id.input_weight)
    EditText inputWeight;
    @BindView(R.id.input_address)
    EditText inputAddress;

    @BindView(R.id.text_DoB)
    TextView mTextDoB;
    @BindView(R.id.profile_image)
    ProfilePictureView imgProfile;
    @BindView(R.id.image_profile)
    ImageView imageView;
    @BindView(R.id.radioMale)
    RadioButton mRadioButtonMale;
    @BindView(R.id.radioFemale)
    RadioButton mRadioButtonFemale;
    @BindView(R.id.btn_save)
    Button mButtonSave;
    @BindView(R.id.group_radio)
    RadioGroup radioGroup;
    @BindView(R.id.imgCalendar)
    ImageView imgCalendar;
    private Boolean check = false;
    protected ApiInterface mApiService = RetrofitClient.getApiClient();
    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initialize();

        mButtonSave.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        mButtonSave.setOnClickListener(this);
        mRadioButtonMale.setOnClickListener(this);
        mRadioButtonFemale.setOnClickListener(this);
        imgCalendar.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        int checked = radioGroup.getCheckedRadioButtonId();
        switch (checked) {
            case R.id.radioMale:
                break;
            case R.id.radioFemale:
                break;
        }
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

        if (Instance.getProfile.size() != 0) {
            for (int i = 0; i < Instance.getProfile.size(); i++) {
                inputName.setText(Instance.getProfile.get(i).Name);
                inputAddress.setText(Instance.getProfile.get(i).Address);
                inputHeight.setText(Instance.getProfile.get(i).Height);
                inputWeight.setText(Instance.getProfile.get(i).Weight);
                inputMobile.setText(Instance.getProfile.get(i).PhoneNumber);
                mTextDoB.setText(Instance.getProfile.get(i).DoB);
                if (Instance.getProfile.get(i).Gender == null) {
                    mRadioButtonMale.setChecked(true);
                } else {
                    if (Instance.getProfile.get(i).Gender.equals("1"))
                        mRadioButtonMale.setChecked(true);

                    if (Instance.getProfile.get(i).Gender.equals("2"))
                        mRadioButtonFemale.setChecked(true);
                }
            }
        } else

        {
            inputName.setText(Instance.first_name);
        }
        if (!Instance.profile_id.equals("")) {
            imgProfile.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            imgProfile.setProfileId(Instance.profile_id);
        } else {
            imgProfile.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            Picasso.with(this).load(Instance.photoUrl).resize(80, 80).centerCrop().into(imageView);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                String name = inputName.getText().toString();
                String address = inputAddress.getText().toString();
                String Dob = mTextDoB.getText().toString();
                String weight = inputWeight.getText().toString();
                String height = inputHeight.getText().toString();
                String phone = inputMobile.getText().toString();
                apiUpdateProfile(name, phone, weight, height, Instance.gender, address, Dob, Instance.profile.get(0).ID);
                break;
            case R.id.radioMale:
                Instance.gender = "1";

                break;
            case R.id.radioFemale:
                Instance.gender = "2";

                break;
            case R.id.imgCalendar:
                setUpDoB();
                break;
        }
    }

    private void setUpDoB() {
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog mDatePicker = new DatePickerDialog(SettingActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                selectedmonth = selectedmonth + 1;
                mTextDoB.setText(selectedyear + "-" + selectedmonth + "-" + selectedday);
            }
        }, mYear, mMonth, mDay);
        mDatePicker.show();
    }

    private void apiUpdateProfile(String name, String phone, String weight, String hight, String gender, String address, String DoB, String id) {
        progressDialog.show();
        final Call<Check> call = mApiService.updateProfile(name, phone, weight, hight, gender, address, DoB, id, "", "", "", "", "");
        call.enqueue(new Callback<Check>() {
            @Override
            public void onResponse(Response<Check> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    check = response.body().result;
                    if (check) {
                        Toast.makeText(SettingActivity.this, "Cập Nhập frofile thành công", Toast.LENGTH_SHORT).show();
                        if (Instance.profile != null) {
                            apigetProfile(Instance.profile.get(0).ID);
                        } else {
                        }
                    } else {
                        Toast.makeText(SettingActivity.this, "Cập nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
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
                startActivity(new Intent(SettingActivity.this, HomeActivity.class));
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

}
