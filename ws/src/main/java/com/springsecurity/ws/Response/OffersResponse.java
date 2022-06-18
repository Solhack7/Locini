package com.springsecurity.ws.Response;

import com.springsecurity.ws.Utility.Dto.UserAccountDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OffersResponse implements Serializable {
    private static final long serialVersionUID = 6130152208691607844L;
    private String nomOffer;
    private String browserId;
    private long nbVehicule;
    private String detailsOffer;
    private double price;
    private UserAccountDto usersAccount;
}
