package com.springsecurity.ws.Utility.Dto;

import com.springsecurity.ws.Entity.ImageEntity;
import com.springsecurity.ws.Entity.VehiculeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculeImageDto {

    private int id;

    private VehiculeDto vehicule;

    private ImageDto image;

    private String altImg;
}
