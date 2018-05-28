package com.example.truong.apphealth;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.truong.apphealth.Server.QuestionOption;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Truong on 5/24/2018.
 */

public class RecyclerViewHistoryDetailAdapter extends RecyclerView.Adapter<RecyclerViewHistoryDetailAdapter.ViewHolder> {
    private Context mContext;
    private List<QuestionOption> questionOptions = new ArrayList<>();

    public RecyclerViewHistoryDetailAdapter(Context mContext, List<QuestionOption> questionOptions) {
        this.mContext = mContext;
        this.questionOptions = questionOptions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_history_detail, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textQuestion.setText(questionOptions.get(position).cauHoi);
        holder.textAnswer.setText(questionOptions.get(position).traLoi);
    }

    @Override
    public int getItemCount() {
        return questionOptions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textQuestion, textAnswer;

        public ViewHolder(View itemView) {
            super(itemView);
            textQuestion = itemView.findViewById(R.id.text_question_detail);
            textAnswer = itemView.findViewById(R.id.text_answer_detail);
        }
    }
}
