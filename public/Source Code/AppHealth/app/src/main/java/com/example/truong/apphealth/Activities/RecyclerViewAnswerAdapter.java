package com.example.truong.apphealth.Activities;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truong.apphealth.Instance;
import com.example.truong.apphealth.R;
import com.example.truong.apphealth.Server.Answer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Truong on 5/17/2018.
 */

public class RecyclerViewAnswerAdapter extends RecyclerView.Adapter<RecyclerViewAnswerAdapter.ViewHolder> {

    private Context mContext;
    private List<Answer> answers = new ArrayList<>();
    private int lastSelectedPosition = -1;

    public RecyclerViewAnswerAdapter(Context mContext, List<Answer> answers) {
        this.mContext = mContext;
        this.answers = answers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_answer, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(answers.get(position).DapAn);
        if (lastSelectedPosition == position) {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundResource(R.drawable.bg_item_answer);
            holder.textView.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
            holder.itemView.setBackgroundResource(R.drawable.bg_button);
            holder.textView.setTextColor(ContextCompat.getColor(mContext, R.color.text_body));

        }
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_check);
            textView = itemView.findViewById(R.id.text);
            linearLayout = itemView.findViewById(R.id.layout_root);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    Instance.QuestionOptionID = answers.get(lastSelectedPosition).QuestionOptionID;
                    Log.d("BBB", "QuestionOptionID " + Instance.QuestionOptionID);

                    Toast.makeText(RecyclerViewAnswerAdapter.this.mContext,
                            "selected offer is " + textView.getText(),
                            Toast.LENGTH_LONG).show();
                }
            });


        }
    }
}
