package com.cadrac.hap_passenger.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cadrac.hap_passenger.R;
import com.cadrac.hap_passenger.adapters.CurrentRoutesAdapter;
import com.cadrac.hap_passenger.responses.currentroutelistResponse;

import com.cadrac.hap_passenger.utils.Config;
import com.cadrac.hap_passenger.webservices.API;
import com.cadrac.hap_passenger.webservices.RestClient;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrentRoutes extends AppCompatActivity {
     RecyclerView listView;
     currentroutelistResponse currentroutelistResponse;
     ArrayList<currentroutelistResponse.data> data = new ArrayList<>();
     CurrentRoutesAdapter list_adapter;
     String phonenumber;
     TextView nodata;
     ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_currentroutes );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Current Rides");
        toolbar.setTitleTextColor( Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                finish();

            }
        });
        listView = (RecyclerView)findViewById( R.id.list );
        nodata=findViewById( R.id.nodata );
        nodata.setVisibility( View.GONE );
        LinearLayoutManager layoutManager = new LinearLayoutManager( getApplicationContext() );
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        listView.setHasFixedSize( true );
        listView.setLayoutManager( layoutManager);
        data=new ArrayList<currentroutelistResponse.data>();
        phonenumber = Config.getNumber( getApplicationContext());
        Log.d( "TAG", "onCreate: ph"+phonenumber );

        getCurrentdetails();
    }

    public void getCurrentdetails() {

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
         try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory( GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);
            Log.d( "TAG", "getCurrentdetails: phone" +phonenumber);

            Call<currentroutelistResponse> call = api.getDetails(phonenumber);
            call.enqueue(new Callback<currentroutelistResponse>() {
                @Override
                public void onResponse(Call<currentroutelistResponse> call,
                                       Response<currentroutelistResponse> response) {

                    currentroutelistResponse = new currentroutelistResponse();
                    currentroutelistResponse = response.body();
                    Log.d( "TAG", "onResponse: "+currentroutelistResponse);
                    Log.d( "TAG", "onResponse:123 "+currentroutelistResponse.getStatus());
                    try{

                        if(currentroutelistResponse.getStatus().equalsIgnoreCase("true")){

                            for( int i=0; i<currentroutelistResponse.getData().length; i++){
                                Log.d( "TAG", "onResponse: resS" +currentroutelistResponse.getData()[i].getSource());
                                Log.d( "TAG", "onResponse: resD" +currentroutelistResponse.getData()[i].getDestination());
                                Log.d( "TAG", "onResponse: resV" +currentroutelistResponse.getData()[i].getVehicletype());
                                Log.d( "TAG", "onResponse: resS" +currentroutelistResponse.getData()[i].getSeats());
                                Log.d( "TAG", "onResponse: resS" +currentroutelistResponse.getData()[i].getFare());
                                Log.d( "TAG", "onResponse: resS" +currentroutelistResponse.getData()[i].getAgent_confirm());
                                Log.d( "TAG", "onResponse: resdriver" +currentroutelistResponse.getData()[i].getDriver_id());
                                Log.d( "TAG", "onResponse: resdriver" +currentroutelistResponse.getData()[i].getFirstName());
                                Log.d( "TAG", "onResponse: resride" +currentroutelistResponse.getData()[i].getRide_id());



                                data.add(currentroutelistResponse.getData()[i]);
                              //  Log.d( "TAG", "onResponse:jaja "+data );
                            }
                            progressDialog.dismiss();

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }

                        setListView();



                    }
                    catch (NullPointerException n){
                        nodata.setVisibility( View.VISIBLE );
                        progressDialog.dismiss();
                        n.printStackTrace();
                    }}


                @Override
                public void onFailure(Call<currentroutelistResponse> call, Throwable t) {


                    Log.d(" TAG", "onFailure:fail" + t);
                    //   Log.d(" TAG", "onFailure:fail1" + res.getStatus());
                }
            });


        } catch (Exception e) {
            System.out.print("Exception e" + e);

        }


    }


    public void setListView(){
        Log.d( "TAG", "setListView: "+ data.size() );
        list_adapter =  new CurrentRoutesAdapter( data, CurrentRoutes.this, getApplicationContext() );
        listView.setAdapter( list_adapter );
    }




}
