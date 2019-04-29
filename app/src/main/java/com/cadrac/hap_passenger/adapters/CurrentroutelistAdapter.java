package com.cadrac.hap_passenger.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public  class CurrentroutelistAdapter extends  RecyclerView.Adapter<CurrentroutelistAdapter.EMSHolder> {


    @NonNull
    @Override
    public CurrentroutelistAdapter.EMSHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentroutelistAdapter.EMSHolder emsHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class EMSHolder extends RecyclerView.ViewHolder {


        public EMSHolder(@NonNull View itemView) {
            super( itemView );
        }
    }
}
