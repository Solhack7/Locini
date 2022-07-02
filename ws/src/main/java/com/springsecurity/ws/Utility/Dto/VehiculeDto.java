package com.springsecurity.ws.Utility.Dto;

import com.springsecurity.ws.Entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculeDto implements Serializable {

    private static final long serialVersionUID = 6540152208691607844L;
    private String nomVehicule;
    private String browserId;
    private long place;
    private float pn;
    private CategoryDto categoryVehicule;
    private BrandDto brandVehicule;
}
