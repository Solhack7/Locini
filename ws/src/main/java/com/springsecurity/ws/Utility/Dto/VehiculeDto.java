package com.springsecurity.ws.Utility.Dto;

import com.springsecurity.ws.Entity.VehiculeImageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculeDto implements Serializable {

    private static final long serialVersionUID = 6540152208691607844L;
    private String nomVehicule;
    private String browserId;
    private long place;
}
