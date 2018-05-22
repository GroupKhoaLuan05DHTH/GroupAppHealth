package com.example.truong.apphealth.Server.Model;

import com.example.truong.apphealth.Server.History;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Truong on 5/22/2018.
 */

public class ResultHistory {
    @SerializedName("result")
    @Expose
    public History result;

}
