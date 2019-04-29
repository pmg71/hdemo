package com.cadrac.hap_passenger.responses;

public class RideInHistoryResponse {

    String status;


    public String getStatus() {
        return status;
    }


    public RideInHistoryResponse.data[] getData() {
        return data;
    }

    data[] data;



    public  class data{




        public String getSeats() {
            return seats;
        }

        public void setSeats(String seats) {
            this.seats = seats;
        }

        public String getDestination() {
            return Destination;
        }

        public void setDestination(String destination) {
            Destination = destination;
        }





        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
        String id;
        String source;
        String Destination;
        String fare;
        String d_id;
        String veh_type;
        String agent_id;
        String seats;

        public String getAgent_confirm() {
            return agent_confirm;
        }

        String agent_confirm;

        public String getD_id() {
            return d_id;
        }


        public String getAgent_id() {
            return agent_id;
        }



        public String getId() {
            return id;
        }



        public String getVeh_type() {
            return veh_type;
        }



        public String getFare() {
            return fare;
        }




    }
}
