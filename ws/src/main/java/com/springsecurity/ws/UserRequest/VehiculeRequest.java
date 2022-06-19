package com.springsecurity.ws.UserRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculeRequest {
    private String nomVehicule;
    private long place;
    private Date dt_creation;
    private Set<String> imgsId;
    private String altImg;
    private String partenaireIdBrowser;
    private float pn;
    private String categoryIdb;
}
