package com.springsecurity.ws.Service;

import com.springsecurity.ws.Exception.ImageException;
import com.springsecurity.ws.Exception.PartnaireException;
import com.springsecurity.ws.Exception.VehiculeException;
import com.springsecurity.ws.UserRequest.VehiculeRequest;

import java.util.HashMap;

public interface VehiculeService {
    HashMap<String,Object> addVehicule(VehiculeRequest vehiculeRequest) throws ImageException, PartnaireException;

    HashMap<String, Object> updateVehicule(VehiculeRequest vehiculeRequest, String idb_vehicule) throws VehiculeException;

    HashMap<String, Object> getByCategory(int page, int limit,String idbCategory);

    HashMap<String, Object> getByLowPrice();

    HashMap<String, Object> getVehicleAndSimiliarVehiculeByIdbVehicule(String idb_vehicule) throws VehiculeException;
    HashMap<String, Object> getSimilarVehiculeExceptionDetails(String idb_vehicule,String idb_category);
}
