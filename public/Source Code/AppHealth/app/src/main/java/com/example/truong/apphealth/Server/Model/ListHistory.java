package com.example.truong.apphealth.Server.Model;

import com.example.truong.apphealth.Server.Result;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Truong on 5/23/2018.
 */

public class ListHistory {
    @SerializedName("result")
    @Expose
    public List<Result> result = null;

}
