package com.example.truong.apphealth.Server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Truong on 5/23/2018.
 */

public class Result {
    @SerializedName("HealthResult")
    @Expose
    public String healthResult;
    @SerializedName("historyID")
    @Expose
    public String historyID;
    @SerializedName("ThoiGianTraLoi")
    @Expose
    public String thoiGianTraLoi;
    @SerializedName("QuestionOption")
    @Expose
    public List<QuestionOption> questionOption = null;
}
