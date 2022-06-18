package com.springsecurity.ws.Utility.Dto;

import com.springsecurity.ws.Entity.UsersAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffersDto {
    private String nomOffer;
    private String browserId;
    private long nbVehicule;
    private String detailsOffer;
    private double price;
   // private UserAccountDto usersAccount;
}
