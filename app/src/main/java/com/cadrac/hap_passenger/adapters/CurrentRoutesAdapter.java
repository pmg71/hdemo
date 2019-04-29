package com.cadrac.hap_passenger.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.cadrac.hap_passenger.R;
import com.cadrac.hap_passenger.activity.CurrentRoutes;
import com.cadrac.hap_passenger.responses.currentroutelistResponse;
import com.cadrac.hap_passenger.responses.driverDetailsResponse;
import com.cadrac.hap_passenger.responses.statusResponse;
import com.cadrac.hap_passenger.supporter_classes.Connection_Detector;
import com.cadrac.hap_passenger.utils.Config;
import com.cadrac.hap_passenger.webservices.API;
import com.cadrac.hap_passenger.webservices.RestClient;

import java.text.BreakIterator;
import java.util.ArrayList;

import okhttp3.OkHttpClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.*;
import static android.Manifest.permission.*;
import static android.support.constraint.Constraints.TAG;
import static android.support.v4.app.ActivityCompat.requestPermissions;

public class CurrentRoutesAdapter extends RecyclerView.Adapter<CurrentRoutesAdapter.EMSHolder> {

    int FADE_DURATION = 100;
    ArrayList<currentroutelistResponse.data> data;
    Context context;
    String a[] = new String[10];

    statusResponse status_response;
    CurrentRoutes curr_routes;
    Connection_Detector connection_detector;
    String driver_id,number,status,ride_id;

    public CurrentRoutesAdapter(ArrayList<currentroutelistResponse.data> data, CurrentRoutes curr_routes, Context context) {
        this.context = context;
        this.data = data;
        this.curr_routes = curr_routes;
        connection_detector = new Connection_Detector( context );

    }

    public EMSHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d( TAG, "onCreateViewHolder: aaaaaa" );
        View itemView = LayoutInflater.from( parent.getContext() ).inflate( R.layout.activity_current_routes_adapter,
                parent, false );

        return new EMSHolder( itemView );
    }

    public void onBindViewHolder(final EMSHolder holder, final int position) {
        setScaleAnimation( holder.itemView );
        Log.d( TAG, "onBindViewHolder:pos " + position );

        holder.destination.setText( data.get( position ).getDestination() );
        holder.source.setText( data.get( position ).getSource() );
        holder.seats.setText( data.get( position ).getSeats() );
        holder.fare.setText( data.get( position ).getFare() );
        holder.vehicletype.setText( data.get( position ).getVehicletype() );
        holder.agentcnfrm.setText( data.get( position ).getAgent_confirm() );
        holder.driver_name.setText( data.get( position ).getFirstName() );
        holder.veh_num.setText( data.get( position ).getVeh_num() );
        ride_id=data.get( position ).getRide_id();
        Log.d( TAG, "onBindViewHolder: rideid"+ride_id );

    //    number=Config.getNumber( context );

        driver_id = data.get( position ).getDriver_id();
        Log.d( TAG, "onBindViewHolder: driv_id" + driver_id );


        a[position] = data.get( position ).getDriver_id();
        Log.d( TAG, "onBindViewHolder: a" + a[position] );

        String s = holder.agentcnfrm.getText().toString();

        holder.cancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ll3.setVisibility( View.GONE );
                status = "4";
                ride_id=data.get( position ).getRide_id();
        //        number=Config.getNumber( context );





                holder.ll3.removeView(v);
                data.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), data.size());
                setStaus();
            }
        } );


        holder.contact_num.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts( "tel", data.get( position ).getContact_num(), null ));
                context.startActivity( callIntent );
              }
        } );



        if (s.equalsIgnoreCase( "0" )) {
            holder.agentcnfrm.setText( "Waiting for agent Conformation..." );


        } else if (s.equalsIgnoreCase( "1" )) {

            holder.agentcnfrm.setText( "Agent Conformed Your Request" );
            holder.agentcnfrm.setTextColor( Color.parseColor( "#449D44" ) );
            holder.cancel.setVisibility( View.GONE );
        }
        else if (s.equalsIgnoreCase( "2" )) {

            holder.agentcnfrm.setText( "Your Ride Conformed by Agent" );
            holder.agentcnfrm.setTextColor( Color.parseColor( "#449D44" ) );

            holder.ll1.setVisibility( View.VISIBLE );
            holder.ll2.setVisibility( View.VISIBLE );
            holder.cancel.setVisibility( View.GONE );



        } else if (s.equalsIgnoreCase( "3" )) {
            holder.agentcnfrm.setText( "Agent Rejected Your Ride" );
            holder.agentcnfrm.setTextColor( Color.parseColor( "#F82032" ) );
            holder.cancel.setVisibility( View.GONE );

        }



    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation( 0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f );
        anim.setDuration( FADE_DURATION );
        view.startAnimation( anim );
    }


//    public void driverDetails() {
//
//        try {
//            OkHttpClient okHttpClient = new OkHttpClient();
//            RestClient.client = new Retrofit.Builder().baseUrl( RestClient.baseUrl ).
//                    client( okHttpClient ).
//                    addConverterFactory( GsonConverterFactory
//                            .create() ).build();
//            API api = RestClient.client.create( API.class );
//            Log.d( "TAG", "driverDetails: d_id" + driver_id );
//            Call<driverDetailsResponse> call = api.driverDetails( driver_id );
//            call.enqueue( new Callback<driverDetailsResponse>() {
//                @Override
//                public void onResponse(Call<driverDetailsResponse> call,
//                                       Response<driverDetailsResponse> response) {
//
//                    driverDetailsResponse = new driverDetailsResponse();
//                    driverDetailsResponse = response.body();
//                    Log.d( "TAG", "onResponse:current " + driverDetailsResponse );
//                    Log.d( "TAG", "onResponse:45454 " + driverDetailsResponse.getStatus() );
//                    try {
//                        if (driverDetailsResponse.getStatus().equalsIgnoreCase( "true" )) {
//
//                            Toast.makeText( context, "success", Toast.LENGTH_SHORT ).show();
//                           String a1 = driverDetailsResponse.getData()[0].getFirstName();
//                            //   Log.d( "TAG", "onResponse: frst"+a1 ) ;
//                          String  a2 = driverDetailsResponse.getData()[0].getLastName();
//                           String a3 = driverDetailsResponse.getData()[0].getContact_num();
//                            a4 = driverDetailsResponse.getData()[0].getVeh_num();
//
//
//                            a5 = a1 + a2;
//                            Log.d( TAG, "onResponse: dname"+a5 );
//                            Log.d( TAG, "onResponse: vehno"+a4 );
//                            contact = a3;
//
//
//
//
//                            Log.d( "TAG", "onResponse: a1+a2" + a5 );
//
//
//                        } else {
//                            Toast.makeText( context, "Failed", Toast.LENGTH_SHORT ).show();
//                        }
//
//                    } catch (NullPointerException n) {
//                        n.printStackTrace();
//                    }
//                }
//
//
//                @Override
//                public void onFailure(Call<driverDetailsResponse> call, Throwable t) {
//
//
//                    Log.d( " TAG", "onFailure:fail" + t );
//                    //   Log.d(" TAG", "onFailure:fail1" + res.getStatus());
//                }
//            } );
//
//
//        } catch (Exception e) {
//            System.out.print( "Exception e" + e );
//
//        }
//
//
//    }
public void setStaus(){
    if (connection_detector.isConnectingToInternet()) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);

            Call<statusResponse> call = api.status(ride_id,status);
            call.enqueue(new Callback<statusResponse>() {
                @Override
                public void onResponse(Call<statusResponse> call,
                                       Response<statusResponse> response) {

                    status_response = new statusResponse();
                    status_response = response.body();
                }

                @Override
                public void onFailure(Call<statusResponse> call, Throwable t) {

                }


            });


        } catch (Exception e) {
            System.out.print("Exception e" + e);

        }
    }else {
        Toast.makeText(context, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
    }
}





    public static class EMSHolder extends RecyclerView.ViewHolder
    {

       Context context;
        TextView source;
        TextView destination;
        TextView vehicletype;
        TextView fare;
        TextView seats;
         Button cancel;
        TextView agentcnfrm;
        TextView driver_name,veh_num, d_id;
        LinearLayout ll1,ll2,ll3;
        ImageView contact_num;
    String phonenumber;


        public EMSHolder(View v)
        {
            super(v);

            Log.d( TAG, "onCreateViewHolder: aaaaaa" );

            source=(TextView)v.findViewById( R.id.source );
            destination=(TextView)v.findViewById( R.id.destination );
            vehicletype=v.findViewById( R.id.vehicletype );
            fare=v.findViewById( R.id.fare );
            seats=v.findViewById( R.id.seats );
            cancel=v.findViewById( R.id.cancel );
            agentcnfrm=(TextView)v.findViewById( R.id.status );
      //     phonenumber = Config.getNumber(context);
            contact_num=v.findViewById( R.id.call );
            driver_name=v.findViewById( R.id.drivername );
            veh_num=v.findViewById( R.id.vehicleno );
            ll1 = (LinearLayout)v.findViewById( R.id.ll2 );
            ll2 = (LinearLayout)v.findViewById( R.id.ll3 );
            ll3=(LinearLayout)v.findViewById( R.id.ll1 );
            ll1.setVisibility( View.GONE );
            ll2.setVisibility( View.GONE );


            d_id = v.findViewById( R.id.d_id );

        }
    }

    public int getItemCount() {
        Log.d("TAG", "getItemCount: "+data.size());
        return  data.size();
    }

    public Object getItem(int i) {
        return i;
    }

    public long getItemId(int i) {
        return i;
    }

}

