package com.springsecurity.ws.UserRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnaireRequest {
    private String nom_agence;
    private String telephone;
    private String email;
    private String ville;
    private String adresse_agence;
    private String site_web;
}
