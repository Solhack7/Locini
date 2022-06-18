package com.springsecurity.ws.Controller;

import com.springsecurity.ws.Exception.*;
import com.springsecurity.ws.Response.GetVehiculeResponse;
import com.springsecurity.ws.Service.VehiculeService;
import com.springsecurity.ws.UserRequest.VehiculeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import netscape.security.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;


@Slf4j
@RestController
@RequestMapping("/vehicules") // localhost:8084/api/vehicules
@RequiredArgsConstructor
public class VehiculeControllers {

    private final VehiculeService vehiculeService;


    @PostMapping(path = "/add_vehicule",consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<HashMap<String,Object>> createVehicule(@RequestBody @Valid VehiculeRequest vehiculeRequest , Principal authentication) throws VehiculeException, ImageException, PartnaireException {
        if (vehiculeRequest.getNomVehicule().isEmpty() || vehiculeRequest.getPlace() < 1 || vehiculeRequest.getImgsId().size()==0)
            throw new VehiculeException("Vous Avez Raté Un Champs Obligatoire");
        HashMap<String,Object> addVehicule = vehiculeService.addVehicule(vehiculeRequest);
        return new ResponseEntity<HashMap<String,Object>>(addVehicule, HttpStatus.CREATED);
    }
    @PutMapping(path = "/vehicule/{idb_vehicule}",consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<HashMap<String,Object>> addOffersToPartner(@RequestBody VehiculeRequest vehiculeRequest, @PathVariable String idb_vehicule) throws PartnaireException, OffersException, UsernameNotExist, VehiculeException {
        if (vehiculeRequest.getNomVehicule().isEmpty() || vehiculeRequest.getPlace() < 1)
            throw new VehiculeException("Vous Avez Raté Un Champs Obligatoire");
        HashMap<String,Object> addOffer = vehiculeService.updateVehicule(vehiculeRequest,idb_vehicule);
        return new ResponseEntity<HashMap<String,Object>>(addOffer, HttpStatus.OK);
    }
}
