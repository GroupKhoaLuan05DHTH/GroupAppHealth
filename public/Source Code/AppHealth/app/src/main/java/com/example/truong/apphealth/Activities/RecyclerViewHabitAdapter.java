package com.example.truong.apphealth.Activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.truong.apphealth.R;
import com.example.truong.apphealth.Server.Habit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Truong on 5/24/2018.
 */

public class RecyclerViewHabitAdapter extends RecyclerView.Adapter<RecyclerViewHabitAdapter.ViewHolder> {
    private Context mContext;
    private List<Habit> habits = new ArrayList<>();

    public RecyclerViewHabitAdapter(Context mContext, List<Habit> habits) {
        this.mContext = mContext;
        this.habits = habits;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_habit_note, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text_QuestionContent.setText(habits.get(position).questionContent);
        holder.text_OptionContent.setText(habits.get(position).optionContent);
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_QuestionContent, text_OptionContent;
        Button btn_why;

        public ViewHolder(View itemView) {
            super(itemView);
            text_QuestionContent = itemView.findViewById(R.id.text_QuestionContent);
            text_OptionContent = itemView.findViewById(R.id.text_OptionContent);
            btn_why = itemView.findViewById(R.id.btn_why);
        }
    }
}
