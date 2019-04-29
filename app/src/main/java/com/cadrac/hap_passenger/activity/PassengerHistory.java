package com.cadrac.hap_passenger.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cadrac.hap_passenger.R;
import com.cadrac.hap_passenger.adapters.PassengerHistoryAdapter;
import com.cadrac.hap_passenger.responses.RideInHistoryResponse;
import com.cadrac.hap_passenger.supporter_classes.Connection_Detector;
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

public class PassengerHistory extends AppCompatActivity {
    RecyclerView listview;
    RideInHistoryResponse getInHistoryResponse;
    ArrayList<RideInHistoryResponse.data> data = new ArrayList<>();
    PassengerHistoryAdapter passengerHistoryAdapter;
    String gid;
    EditText search;
    Connection_Detector connection_detector;
    String mobilenumber;
    TextView nodata;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_history2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("History");
        toolbar.setTitleTextColor(Color.WHITE);


        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        listview = (RecyclerView)findViewById( R.id.listview );
        search = (EditText)findViewById(R.id.search);
        nodata=findViewById( R.id.nodata);
        nodata.setVisibility( View.GONE );
        connection_detector = new Connection_Detector(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager( getApplicationContext());
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        listview.setLayoutManager( layoutManager );
        listview.setHasFixedSize( true );
        data = new ArrayList<RideInHistoryResponse.data>();
        getout();

    }

    public void getout(){

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        if (connection_detector.isConnectingToInternet()) {
            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                mobilenumber = Config.getNumber(getApplicationContext());

                Log.d("TAG", "number: "+mobilenumber);

                Call<RideInHistoryResponse> call = api.hap_hist(mobilenumber);

                call.enqueue(new Callback<RideInHistoryResponse>() {
                    @Override
                    public void onResponse(Call<RideInHistoryResponse> call, Response<RideInHistoryResponse> response) {
                        getInHistoryResponse = response.body();
                        Log.d("TAG", "onResponse: status"+getInHistoryResponse.getStatus());
                        try {
                            if (getInHistoryResponse.getStatus().equalsIgnoreCase("true")) {
                                Log.d("TAG", "onResponse: status1"+getInHistoryResponse.getData().length);
                                for (int i = 0; i < getInHistoryResponse.getData().length; i++) {
                                    data.add(getInHistoryResponse.getData()[i]);
                                    Log.d("TAG", "onResponse: source"+getInHistoryResponse.getData()[i].getSource());
                                    Log.d("TAG", "onResponse: dest"+getInHistoryResponse.getData()[i].getDestination());
                                }
                            }
                            setListView();
                            progressDialog.dismiss();
                        } catch (Exception e) {
                            Log.d("TAG", "onResponse: aaaa");
                              nodata.setVisibility( View.VISIBLE );
                            e.printStackTrace();


                        }

                    }


                    @Override
                    public void onFailure(Call<RideInHistoryResponse> call, Throwable t) {
                        Log.d("TAG", "onFailure:t" + t);
                    }
                });
            }catch (Exception e){


                Toast.makeText(this,"no data found",Toast.LENGTH_SHORT).show();
            }
        }else Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
    }
    public void setListView() {
        passengerHistoryAdapter=new PassengerHistoryAdapter( data,PassengerHistory.this,getApplicationContext() );
        listview.setAdapter(passengerHistoryAdapter);

    }

}
