package com.cadrac.hap_passenger.webservices;

import com.cadrac.hap_passenger.responses.FeedBackResponse;
import com.cadrac.hap_passenger.responses.OtpResponse;
import com.cadrac.hap_passenger.responses.PassengerRequestResponse;
import com.cadrac.hap_passenger.responses.Profile_Response;
import com.cadrac.hap_passenger.responses.RideInHistoryResponse;
import com.cadrac.hap_passenger.responses.RouteListResponse;
import com.cadrac.hap_passenger.responses.ValidateOTPResponse;
import com.cadrac.hap_passenger.responses.currentroutelistResponse;
import com.cadrac.hap_passenger.responses.driverDetailsResponse;
import com.cadrac.hap_passenger.responses.statusResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    @GET("hap_profile.php")//update profile
    Call<Profile_Response> profile1(
            @Query("name") String name,
            @Query("number") String number,
            @Query("email") String email
    );
    @GET("hap_profiledata.php")//profile
    Call<Profile_Response> profile(
            @Query("number") String number
    );

    @GET("RouteList.php")
    Call<RouteListResponse> routes();

    @GET("agentlist.php")//places
    Call<RouteListResponse> agents( @Query("source") String source);


    @GET("otp.php")//supervisor login
    Call<OtpResponse> generateOTP(
            @Query("mobileNum") String Num
    );


    @GET("valotp.php")//supervisor login
    Call<ValidateOTPResponse> validateOTP(
            @Query("mobileNum") String Num,
            @Query("otp") String otp
    );

    @GET("passenger_feedback.php")//supervisor login
    Call<FeedBackResponse> passengerfeedback(
            @Query("driverid") String driverid,
            @Query("rideid") String rideid,
            @Query("performance") String performance,
            @Query("review") String review
    );
    @GET("count.php")
    Call<RouteListResponse> sendName(@Query( "name" ) String gi_id);

    @GET("getCurrentDetails.php")
    Call<currentroutelistResponse> getDetails(@Query( "number" ) String num);

    @GET("getDriverdetails.php")
    Call<driverDetailsResponse> driverDetails(@Query( "driver_id" ) String driver_id);
    @GET("destination.php")//supervisor login
    Call<PassengerRequestResponse> destination(
            @Query("source") String source1
       /*    @Query("source") String source,
           @Query("destination") String destination,
           @Query("fare") String fare,
           @Query("vehicletype") String vehicletype,
           @Query("seats") String seats*/
    );
    @GET(" passenger_seatcount.php")//seat count
    Call<PassengerRequestResponse> seatcount(
            @Query("vehicle_type") String mvehicletype);
    @GET("passenger_vehicletype.php")
    Call<PassengerRequestResponse> vehicleType();
    @GET("passengerRequest.php")//cost
    Call<PassengerRequestResponse> datainsertion(
            @Query("gi_id") String gi_id,
            @Query("passengerName") String passName,
            @Query("usernumber") String usernumber,
            @Query("source") String source1,
            @Query("destination") String selectdestination,
            @Query("vehicletype") String mvehicletype,
            @Query("seats") String numberofseats,
            @Query("fare") String l);
    @GET("passenger_cost.php")//cost
    Call<PassengerRequestResponse> cost(
            @Query("source") String source1,
            @Query("destination") String selectdestination,
            @Query("vehicle_type") String mvehicletype);
    @GET("setStatus.php")       //rideinresponse
    Call<statusResponse> status(@Query("ride_id") String ride_id,
                                @Query("status") String status);
    @GET("pass_history.php")
    Call<RideInHistoryResponse>hap_hist(
            @Query("mobilenumber") String mobilenumber);

}
