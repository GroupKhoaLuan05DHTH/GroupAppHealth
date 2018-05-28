package com.example.truong.apphealth;

import android.net.Uri;

import com.example.truong.apphealth.Server.Account;
import com.example.truong.apphealth.Server.Question;
import com.example.truong.apphealth.Server.QuestionOption;
import com.example.truong.apphealth.Server.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Truong on 5/10/2018.
 */

public class Instance {
    public static List<Account> profile = new ArrayList<>();
    public static List<Account> getProfile = new ArrayList<>();
    public static String heartRate = "0";
    public static String first_name = "";
    public static String profile_id = "";
    public static String email = "";
    public static Uri photoUrl;
    public static String gender = "1";
    public static List<Question> questionList = new ArrayList<>();
    public static String lat = "";
    public static String lng = "";
    public static String QuestionOptionID = "";
    public static List<Result> resultList = new ArrayList<>();
    public static List<QuestionOption> questionOptions=new ArrayList<>();
    public static String historyId="";
    public static String currentHistoryId="";

}
