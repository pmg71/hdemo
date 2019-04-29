package com.cadrac.hap_passenger.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cadrac.hap_passenger.MainActivity;
import com.cadrac.hap_passenger.R;
import com.cadrac.hap_passenger.activity.PassengerRequest;
import com.cadrac.hap_passenger.responses.RouteListResponse;
import com.cadrac.hap_passenger.utils.Config;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class AgentListAdapter extends  RecyclerView.Adapter<AgentListAdapter.EMSHolder>{

    int FADE_DURATION = 100;
    ArrayList<RouteListResponse.data> data;
    ArrayList<RouteListResponse.data> search;
    Context context;
    MainActivity Settlement;
    String charString;
//    SettlementResponse settlementResponse;


    public AgentListAdapter(ArrayList<RouteListResponse.data> data, MainActivity Settlement, Context context) {
        this.context=context;
        this.data = data;
        this.Settlement=Settlement;

        this.search= new ArrayList<RouteListResponse.data>();
        this.search.addAll(data);
    }

    @Override
    public void onBindViewHolder(@NonNull final AgentListAdapter.EMSHolder holder, final int position) {
        setScaleAnimation(holder.itemView);

        Log.d(TAG, "onBindViewHolder: "+data.get(position).getAgentname());
        Log.d(TAG, "onBindViewHolder: "+data.get(position).getAgentnumber());
        holder.textView.setText(data.get(position).getAgentname());

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Agentnumber=data.get(position).getAgentnumber();

                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts( "tel", Agentnumber, null ));
                context.startActivity( callIntent );
//



            }


        });

        holder.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gi_id=data.get( position ).getAgentid();
                Config.saveinAgent(context,gi_id);
                Intent intent=new Intent(context, PassengerRequest.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });


    }

    private void setScaleAnimation(View view)
    {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    public static class EMSHolder extends RecyclerView.ViewHolder
    {

        TextView textView,request;
        ImageView call;
        LinearLayout linearLayout;



        public EMSHolder(View v)
        {
            super(v);
            textView=(TextView)v.findViewById(R.id.agentname);
            request=v.findViewById(R.id.request);
            call=(ImageView)v.findViewById(R.id.call);
            linearLayout=(LinearLayout)v.findViewById(R.id.ll);



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
    public AgentListAdapter.EMSHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.agentlistdisplay,
                parent, false);

        return new AgentListAdapter.EMSHolder(itemView);
    }
    public long getItemId(int i) {
        return i;
    }


}
