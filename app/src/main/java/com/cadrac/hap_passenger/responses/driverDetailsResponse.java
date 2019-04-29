package com.cadrac.hap_passenger.responses;



public class driverDetailsResponse {

    public String getStatus() {
        return status;
    }

    String status;
    public data[] getData() {
        return data;
    }
    data[] data;

   public class data{
       public String getFirstName() {
           return firstName;
       }

       public String getLastName() {
           return lastName;
       }

       public String getContact_num() {
           return contact_num;
       }

       public String getVeh_num() {
           return veh_num;
       }

       String firstName,lastName,contact_num,veh_num;

   }
}
