package com.example.truong.apphealth.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.truong.apphealth.Instance;
import com.example.truong.apphealth.R;
import com.example.truong.apphealth.Server.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Truong on 5/23/2018.
 */

public class RecyclerViewHistoryAdapter extends RecyclerView.Adapter<RecyclerViewHistoryAdapter.ViewHolder> {
    private Context mContext;
    private List<Result> results = new ArrayList<>();
    private int lastSelectedPosition = -1;

    public RecyclerViewHistoryAdapter(Context mContext, List<Result> results) {
        this.mContext = mContext;
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_history, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textdate.setText(results.get(position).thoiGianTraLoi);
        holder.textResult.setText(results.get(position).healthResult);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textdate, textResult;
        private RelativeLayout layoutroot;

        public ViewHolder(final View itemView) {
            super(itemView);
            textdate = itemView.findViewById(R.id.textdate);
            textResult = itemView.findViewById(R.id.textresult);
            layoutroot = itemView.findViewById(R.id.layout_item_history);
            layoutroot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastSelectedPosition = getAdapterPosition();
                    Instance.questionOptions = results.get(lastSelectedPosition).questionOption;
                    Log.d("BBB", "onClick: " + Instance.questionOptions.size());
                    if (Instance.questionOptions.size() != 0) {
                        mContext.startActivity(new Intent(mContext, HistoryDetailActivity.class));
                        Log.d("BBB","Size khác 0");
                    }
                    else {
                        Log.d("BBB","sai bằng 0");
                    }

                }
            });

        }
    }
}
