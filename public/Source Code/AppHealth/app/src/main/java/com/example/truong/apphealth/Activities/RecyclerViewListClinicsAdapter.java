package com.example.truong.apphealth.Activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.truong.apphealth.R;
import com.example.truong.apphealth.Server.Clinics;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Truong on 5/18/2018.
 */

public class RecyclerViewListClinicsAdapter extends RecyclerView.Adapter<RecyclerViewListClinicsAdapter.ViewHolder> {
    private Context mContext;
    private List<Clinics> clinicsList = new ArrayList<>();
    private int lastSelectedPosition = -1;
    private GoogleMap mMap;


    public RecyclerViewListClinicsAdapter(Context mContext, List<Clinics> clinicsList, GoogleMap mMap) {
        this.mContext = mContext;
        this.clinicsList = clinicsList;
        this.mMap = mMap;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_clinics, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textName.setText(clinicsList.get(position).name);
        holder.textAddress.setText(clinicsList.get(position).vicinity);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clinicsList.size() != 0) {
                    if (clinicsList.get(position).lat != 0 && clinicsList.get(position).lng != 0) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(clinicsList.get(position).lat, clinicsList.get(position).lng), 16.0f));
                    }
                } else {
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return clinicsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textName, textAddress;
        private LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name_item);
            textAddress = itemView.findViewById(R.id.text_address);
            linearLayout = itemView.findViewById(R.id.layout_root);
        }
    }
}
