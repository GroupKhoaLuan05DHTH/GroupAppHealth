package com.example.truong.apphealth.Server;

import com.example.truong.apphealth.Server.Model.ListAccount;
import com.example.truong.apphealth.Server.Model.ListHabit;
import com.example.truong.apphealth.Server.Model.ListHistory;
import com.example.truong.apphealth.Server.Model.ListQuestion;
import com.example.truong.apphealth.Server.Model.ResultHistory;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Truong on 5/15/2018.
 */

public interface ApiInterface {
    @FormUrlEncoded
    @POST("admin/login")
    Call<ListAccount> login(@Field("Email") String email,
                            @Field("Password") String password);

    @FormUrlEncoded
    @POST("admin/register")
    Call<Check> register(@Field("Email") String email,
                         @Field("Password") String password,
                         @Field("Name") String name);

    @FormUrlEncoded
    @POST("admin/updateProfile")
    Call<Check> updateProfile(@Field("Name") String name,
                              @Field("PhoneNumber") String phoneNumber,
                              @Field("Weight") String weight,
                              @Field("Height") String height,
                              @Field("Gender") String gender,
                              @Field("Address") String address,
                              @Field("DoB") String DoB,
                              @Field("AccountID") String accountID,
                              @Field("Job") String Job,
                              @Field("Marital_Status") String Marital_Status,
                              @Field("Latitude") String Latitude,
                              @Field("Longtitude") String Longtitude,
                              @Field("Year_Income") String Year_Income);

    @GET("admin/getProfile")
    Call<ListAccount> getProfile(@Query("AccountID") String AccountID);

    @GET("admin/getListQuestion")
    Call<ListQuestion> getListQuestion();

    @FormUrlEncoded
    @POST("admin/CreateHistory")
    Call<ResultHistory> createHistory(@Field("UserID") String UserID);

    @FormUrlEncoded
    @POST("admin/AnswerQuestion")
    Call<Check> answerQuestion(@Field("HistoryID") String historyID,
                               @Field("ListQuestionOptionID") String listQuestionOptionID);

    @FormUrlEncoded
    @POST("admin/algorithm")
    Call<Check> algorithm(@Field("HistoryID") String historyID);

    @GET("admin/GetHistory")
    Call<ListHistory> getHistory(@Query("AccountID") String accountID);

    @GET("admin/GetHabitNote")
    Call<ListHabit> getHabitNote(@Query("historyID") String historyID);
}
