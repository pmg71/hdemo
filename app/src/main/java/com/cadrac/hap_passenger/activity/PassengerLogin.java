package com.cadrac.hap_passenger.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cadrac.hap_passenger.R;
import com.cadrac.hap_passenger.responses.OtpResponse;
import com.cadrac.hap_passenger.utils.Config;
import com.cadrac.hap_passenger.webservices.API;
import com.cadrac.hap_passenger.webservices.RestClient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PassengerLogin extends AppCompatActivity {

    EditText mobileNum;
    Button b1;
    String num, phoneNum;
    OtpResponse otpres;

    private static final int MULTIPLE_PERMISSIONS = 7;
    String[] permissions = new String[]{
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_login);

        mobileNum = (EditText) findViewById(R.id.number);

        checkPermissions();

        phoneNum = mobileNum.getText().toString();

        b1 = (Button) findViewById(R.id.button);

        mobileNum.setTransformationMethod(new NumTranslate());




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String mobileNum1=mobileNum.getText().toString();

                if (mobileNum1.startsWith("9")||mobileNum1.startsWith("8")||mobileNum1.startsWith("7")||mobileNum1.startsWith("6"))
                {
                    if(mobileNum1.length() != 10) {
                        mobileNum.setError("please enter valid phone number");

                    }else{

                        getOtp();

                        Intent i = new Intent(getApplicationContext(), ValidateOtp.class);
                        startActivity(i);
                    }
                }else{
                    mobileNum.setError("please enter valid phone number");
                }



            }

        });



    }

    public void getOtp() {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);

            Log.d("TAG", "onClick: " + mobileNum.getText().toString());

            num = mobileNum.getText().toString();

            Config.saveNumber(getApplication(),mobileNum.getText().toString());
            Log.d("TAG", "onClick: " + num);
            Call<OtpResponse> call = api.generateOTP(num);
            call.enqueue(new Callback<OtpResponse>() {
                @Override
                public void onResponse(Call<OtpResponse> call,
                                       Response<OtpResponse> response) {

                    otpres = new OtpResponse();
                    otpres = response.body();
                    if(otpres.getStatus().equalsIgnoreCase("true")){

                        Config.saveProfileStatus(getApplicationContext(), otpres.getResponse());

                        Toast.makeText(PassengerLogin.this, "OTP has been sent", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(getApplicationContext(), ValidateOtp.class);
                        startActivity(i);

                    }else{
                        Toast.makeText(PassengerLogin.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<OtpResponse> call, Throwable t) {


                    Log.d(" TAG", "onFailure:fail" + t);
                    //   Log.d(" TAG", "onFailure:fail1" + res.getStatus());
                }


            });


        } catch (Exception e) {
            System.out.print("Exception e" + e);

        }


    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

// all permissions are granted.
                } else {

//permissions missing

                }
                return;
            }
        }
    }

    private class NumTranslate extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return source;
        }
    }

}
