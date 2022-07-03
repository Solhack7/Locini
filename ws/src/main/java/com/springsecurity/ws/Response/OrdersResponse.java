package com.springsecurity.ws.Response;

import com.springsecurity.ws.Entity.PartenaireEntity;
import com.springsecurity.ws.Entity.TypeOrderEntity;
import com.springsecurity.ws.Entity.VehiculeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersResponse {
    private String idbOrder;
    private String fn;
    private String ln;
    private Date dtfrom;
    private Date dtto;
    private Date dtOrder;
    private TypeOrderResponse typeOrder;
    private String tel;
    private Date dtPc;
    private VehiculeResponse vehicule;
    private PartnaireResponse partenaire;
}
