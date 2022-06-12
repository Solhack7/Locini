package com.springsecurity.ws.Response;

import com.springsecurity.ws.Entity.VehiculeImageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculeResponse {
    private static final long serialVersionUID = 6540152208691607844L;
    private String nomVehicule;
    private String browserId;
    private long place;
}
