package com.example.truong.apphealth.Server.Model;

import com.example.truong.apphealth.Server.Habit;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Truong on 5/24/2018.
 */

public class ListHabit {
    @SerializedName("result")
    @Expose
    public List<Habit> result = null;

}
