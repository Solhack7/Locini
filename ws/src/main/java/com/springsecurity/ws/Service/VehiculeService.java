package com.springsecurity.ws.Service;

import com.springsecurity.ws.Exception.ImageException;
import com.springsecurity.ws.Exception.PartnaireException;
import com.springsecurity.ws.UserRequest.VehiculeRequest;

import java.util.HashMap;

public interface VehiculeService {
    HashMap<String,Object> addVehicule(VehiculeRequest vehiculeRequest) throws ImageException, PartnaireException;

}
