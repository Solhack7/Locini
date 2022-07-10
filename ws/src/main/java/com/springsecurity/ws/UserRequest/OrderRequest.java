package com.springsecurity.ws.UserRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String fn;
    private String ln;
    private Date dtfrom;
    private Date dtto;
    private String idbVehicule;
    private String tel;
    private String idbPartenaire;
    private String idbCity;
}
