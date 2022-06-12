package com.springsecurity.ws.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnaireResponse implements Serializable {
    private static final long serialVersionUID = 6130152208691607844L;
    private String nom_agence;
    private String telephone;
    private String email;
    private String browserId;
    private String ville;
    private String adresse_agence;
    private String site_web;
}
