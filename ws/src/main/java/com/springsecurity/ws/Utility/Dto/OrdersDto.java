package com.springsecurity.ws.Utility.Dto;

import com.springsecurity.ws.Response.PartnaireResponse;
import com.springsecurity.ws.Response.VehiculeResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDto {
    private String idbOrder;
    private String fn;
    private String ln;
    private Date dtfrom;
    private Date dtto;
    private Date dtOrder;
    private Date dtPc;
    private String tel;
    private TypeOrderDto typeOrder;
    private VehiculeDto vehicule;
    private PartnaireDto partenaire;
}
