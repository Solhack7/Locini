package com.springsecurity.ws.Utility.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculeImageDto {

    private int id;

    private VehiculeDto vehicule;

    private ImageDto image;

    private String altImg;
}
