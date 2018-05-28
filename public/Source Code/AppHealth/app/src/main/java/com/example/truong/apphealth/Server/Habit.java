package com.example.truong.apphealth.Server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Truong on 5/24/2018.
 */

public class Habit {
    @SerializedName("QuestionContent")
    @Expose
    public String questionContent;
    @SerializedName("OptionContent")
    @Expose
    public String optionContent;
    @SerializedName("HabitNote")
    @Expose
    public String habitNote;
}
