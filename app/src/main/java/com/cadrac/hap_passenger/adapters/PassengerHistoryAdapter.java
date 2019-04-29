package com.cadrac.hap_passenger.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
//
//import com.cadrac.hap.R;
//import com.cadrac.hap.activites.agent_ridein_history;
//import com.cadrac.hap.responses.RideInHistoryResponse;
import com.cadrac.hap_passenger.R;
import com.cadrac.hap_passenger.activity.PassengerHistory;
import com.cadrac.hap_passenger.responses.RideInHistoryResponse;

import java.util.ArrayList;

public class PassengerHistoryAdapter extends RecyclerView.Adapter<PassengerHistoryAdapter.EMSHolder> {

    int FADE_DURATION = 100;
    ArrayList<RideInHistoryResponse.data> data;
    ArrayList<RideInHistoryResponse.data> dataFilter;
    Context context;
    PassengerHistory passengerHistory;

    String date,time,time1;

    public PassengerHistoryAdapter(ArrayList<RideInHistoryResponse.data> data, PassengerHistory passengerHistory, Context applicationContext) {
        this.context=applicationContext;
        this.data = data;
        this.dataFilter = new ArrayList<RideInHistoryResponse.data>();
        this.dataFilter.addAll(data);
        this.passengerHistory=passengerHistory;
    }

    @Override
    public void onBindViewHolder(@NonNull PassengerHistoryAdapter.EMSHolder holder, int position) {
        setScaleAnimation(holder.itemView);

        holder.source.setText(data.get(position).getSource());
        holder.seats.setText(data.get(position).getSeats());
        holder.destination.setText(data.get(position).getDestination());
        holder.veh_type.setText(data.get(position).getVeh_type());
        holder.fare.setText(data.get(position).getFare());
        holder.id.setText(data.get(position).getId());
        holder.d_id.setText(data.get(position).getD_id());
        holder.AgentId.setText(data.get(position).getAgent_id());

        holder.status.setText(data.get(position).getAgent_confirm());


        String s = holder.status.getText().toString();
        Log.d("TAG", "onBindViewHolder:status "+s);


        Log.d("TAG", "onBindViewHolder: so"+data.get(position).getSource());


        if (s.equalsIgnoreCase( "4" )) {
            holder.status.setText( "Your Ride is Cancelled." );


        } else if (s.equalsIgnoreCase( "5" )) {

            holder.status.setText( "Your Ride is Completed" );
            holder.status.setTextColor( Color.parseColor( "#449D44" ) );
        }

        Log.d("TAG", "onBindViewHolder: so"+data.get(position).getSource());
//        String string = data.get(position).getTime();
//        String[] parts = string.split(":");
//        String part1 = parts[0];
//        String part2 = parts[1];
//        String part3 = parts[2];
//        String stime=part1+":"+part2+":"+part3;
//        holder.time.setText(stime);
//        if(data.get(position).getRideStatus().equals("0")){
//            holder.status.setText("pending");
//        }else {
//            holder.status.setText("received");
//        }
    }
    private void setScaleAnimation(View view)
    {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter()
//        {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence)
//            {
//
//                data.clear();
//
//                String charString = charSequence.toString();
//
//                if (charString.isEmpty())
//                {
//                    data.addAll(dataFilter);
//                }
//                else
//                {
//                    ArrayList<RideInHistoryResponse.data> filteredList = new ArrayList<>();
//                    for (RideInHistoryResponse.data row : dataFilter)
//                    {
//                        if (row.getSource().toLowerCase().contains(charString.toLowerCase())||
//                                row.getDate().toLowerCase().contains(charString.toLowerCase())||
//                                row.getTime().toLowerCase().contains(charString.toLowerCase())||
//                                row.getDestination().toUpperCase().contains(charString.toUpperCase()))
//                        {
//                            filteredList.add(row);
//                        }
//                    }
//
//                    data = filteredList;
//                }
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = data;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
//            {
//
//                data = (ArrayList<RideInHistoryResponse.data>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

    public static class EMSHolder extends RecyclerView.ViewHolder
    {
        Context context;
        //        LinearLayout ll1;
        TextView source, seats,destination, fare, veh_type, id,d_id,AgentId, status,
                reqIdText,src,dest,veh,faretext,agentid,seat;
//        Button accept, reject, assign, addon;

        public EMSHolder(View v)
        {
            super(v);
//            Log.d("TAG", "getItemCount: ");
            source =(TextView) v.findViewById(R.id.source);
            destination=(TextView) v.findViewById(R.id.destination);
            seats=(TextView) v.findViewById(R.id.seats);
            fare= (TextView)v.findViewById(R.id.fare);
            veh_type = (TextView)v.findViewById(R.id.veh_type);
            d_id=v.findViewById(R.id.d_id);

            id= (TextView) v.findViewById(R.id.reqId);
            AgentId=(TextView)v.findViewById(R.id.AgentId);
            status = v.findViewById(R.id.status);
//            ll1 = (LinearLayout)v.findViewById(R.id.ll1);
        }
    }

    public int getItemCount() {
        return  data.size();
    }

    public Object getItem(int i) {
        return i;
    }

    @NonNull
    @Override
    public PassengerHistoryAdapter.EMSHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.passengerhistory_adapter,
                parent, false);

        return new PassengerHistoryAdapter.EMSHolder(itemView);
    }

    public long getItemId(int i) {
        return i;
    }


}