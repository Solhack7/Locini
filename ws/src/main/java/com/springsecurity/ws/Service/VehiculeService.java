package com.springsecurity.ws.Service;

import com.springsecurity.ws.Exception.*;
import com.springsecurity.ws.UserRequest.VehiculeRequest;
import com.springsecurity.ws.Utility.Dto.PartnaireVehiculeDisplayDto;

import java.security.Principal;

import java.util.HashMap;
import java.util.List;

public interface VehiculeService {
    HashMap<String,Object> addVehicule(VehiculeRequest vehiculeRequest) throws ImageException, PartnaireException;

    HashMap<String, Object> updateVehicule(VehiculeRequest vehiculeRequest, String idb_vehicule) throws VehiculeException;

    List<PartnaireVehiculeDisplayDto> getByCategory(int page, int limit, String idbCategory) throws CategoryException;

    HashMap<String, Object> getByLowPrice();

    HashMap<String, Object> getVehicleAndSimiliarVehiculeByIdbVehicule(String idb_vehicule) throws VehiculeException;
    HashMap<String, Object> getSimilarVehiculeExceptionDetails(String idb_vehicule,String idb_category);

    HashMap<String, Object> getVehiculeByCBetweenPminAndPmax(String idb_category, int page, int limit, float pnmin, float pnmax) throws CategoryException;

    HashMap<String, Object> getVehiculeByJwt(Principal authentication, int page, int limit) throws UsernameNotExist;

    HashMap<String, Object> filterAction(int page, int limit, Float pnmin, Float pnmax, String idb_brand, String idb_category) throws BrandException, CategoryException;

    HashMap<String, Object> filterActionByJwt(int page, int limit, Float pnmin, Float pnmax, String idb_brand, String idb_category, Principal authentication) throws BrandException, CategoryException, UsernameNotExist;

    List<PartnaireVehiculeDisplayDto> getByCategoryAndJwt(int page, int limit,String idbCategory,Principal authentication) throws CategoryException, UsernameNotExist;
}
