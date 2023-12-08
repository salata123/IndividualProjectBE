package com.example.individualprojectbe.amadeus.request;

    import lombok.Data;

    @Data
    public class RequestData {
        private String currencyCode;
        private String originLocationCode;
        private String destinationLocationCode;
        private String departureDate;
        private String departureTime;
    }
