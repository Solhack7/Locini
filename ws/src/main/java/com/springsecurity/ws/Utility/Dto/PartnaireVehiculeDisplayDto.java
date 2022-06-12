package com.springsecurity.ws.Utility.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnaireVehiculeDisplayDto {
    private VehiculeDto vehicule;
    private List<VehiculeImageDto> img;
}
