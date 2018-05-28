package com.example.truong.apphealth.Server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Truong on 5/23/2018.
 */

public class QuestionOption {
    @SerializedName("CauHoi")
    @Expose
    public String cauHoi;
    @SerializedName("TraLoi")
    @Expose
    public String traLoi;

}
