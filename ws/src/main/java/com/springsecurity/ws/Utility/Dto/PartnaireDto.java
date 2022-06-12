package com.springsecurity.ws.Utility.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnaireDto implements Serializable {

    private static final long serialVersionUID = 6140152208691607844L;
    private long id;
    private String nom_agence;
    private String telephone;
    private String email;
    private String browserId;
    private String ville;
    private String adresse_agence;
    private String site_web;
}
