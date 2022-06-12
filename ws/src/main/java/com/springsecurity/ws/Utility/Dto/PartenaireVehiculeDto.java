package com.springsecurity.ws.Utility.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartenaireVehiculeDto implements Serializable {
    private static final long serialVersionUID = 61401522091607844L;
    private VehiculeDto vehicule;
}
