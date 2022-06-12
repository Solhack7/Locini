package com.springsecurity.ws.Response;

import com.springsecurity.ws.Entity.VehiculeImageEntity;
import com.springsecurity.ws.Utility.Dto.VehiculeDto;
import com.springsecurity.ws.Utility.Dto.VehiculeImageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetVehiculeResponse {
    private List<VehiculeImageDto> vehicules;
    private VehiculeDto vehiculeDto;

}
