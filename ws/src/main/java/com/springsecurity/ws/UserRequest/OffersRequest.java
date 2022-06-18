package com.springsecurity.ws.UserRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffersRequest {
    private String nomOffer;
    private long nbVehicule;
    private String detailsOffer;
    private double price;
}
