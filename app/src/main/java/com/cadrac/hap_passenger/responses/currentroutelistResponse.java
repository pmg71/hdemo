package com.cadrac.hap_passenger.responses;



public class currentroutelistResponse {

    public String getStatus() {
        return status;
    }

    String status;

    public data[] getData() {
        return data;
    }

    data[] data;
    public class data {

        public String getSource() {
            return source;
        }

        public String getDestination() {
            return destination;
        }

        public String getFare() {
            return fare;
        }

        public String getSeats() {
            return seats;
        }

        public String getVehicletype() {
            return vehicletype;
        }

        String source;
        String destination;
        String fare;
        String seats;
        String vehicletype;
        String firstName;
        String lastName;
        String contact_num;

        public String getRide_id() {
            return ride_id;
        }

        String ride_id;

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

        String veh_num;

        public String getDriver_id() {
            return driver_id;
        }

        String driver_id;

        public String getAgent_confirm() {
            return agent_confirm;
        }

        String agent_confirm;

    }

}
