package com.springsecurity.ws.Controller;


import com.springsecurity.ws.Exception.*;
import com.springsecurity.ws.Service.PartnaireService;
import com.springsecurity.ws.UserRequest.PartnaireRequest;
import com.springsecurity.ws.UserRequest.VehiculeRequest;
import com.springsecurity.ws.Utility.Dto.PartnaireDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.security.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/partner") // localhost:8084/api/partner
@RequiredArgsConstructor
public class PartnaireControllers {
    private final PartnaireService partnaireService;
    @PostMapping(path = "/add_partnaire",consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<HashMap<String,Object>> createVehicule(@RequestBody @Valid PartnaireRequest partnaireRequest , Principal authentication) throws PartnaireException, UsernameExist {
        if (partnaireRequest.getNom_agence().isEmpty() || partnaireRequest.getAdresse_agence().isEmpty() || partnaireRequest.getEmail().isEmpty()|| partnaireRequest.getVille().isEmpty())
            throw new PartnaireException("Vous Avez Raté Un Champs Obligatoire");
        HashMap<String,Object> addVehicule = partnaireService.addPartner(partnaireRequest,authentication);
        return new ResponseEntity<HashMap<String,Object>>(addVehicule, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{idBrowserPartner}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<HashMap<String,Object>> getEtatMag(@PathVariable String idBrowserPartner){
        HashMap<String,Object> payload = partnaireService.getVehiculePartenaire(idBrowserPartner);
        return new ResponseEntity<HashMap<String,Object>>(payload, HttpStatus.OK);
    }

    @GetMapping(path = "/partnerlist", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<PartnaireDto>> getAllPartner(){
        List<PartnaireDto> payload = partnaireService.getAllPartner();
        return new ResponseEntity<List<PartnaireDto>>(payload, HttpStatus.OK);
    }
    @PutMapping(path = "/update_partner/{idb_partnaire}",consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<HashMap<String,Object>> addOffersToPartner(@RequestBody @Valid PartnaireRequest partnaireRequest, @PathVariable String idb_partnaire) throws PartnaireException, OffersException, UsernameNotExist, VehiculeException {
        if (partnaireRequest.getNom_agence().isEmpty() || partnaireRequest.getAdresse_agence().isEmpty() || partnaireRequest.getEmail().isEmpty() || partnaireRequest.getVille().isEmpty() || partnaireRequest.getTelephone().isEmpty())
            throw new PartnaireException("Vous Avez Raté Un Champs Obligatoire");
        HashMap<String,Object> updatePartnaire = partnaireService.updatePartnaire(partnaireRequest,idb_partnaire);
        return new ResponseEntity<HashMap<String,Object>>(updatePartnaire, HttpStatus.OK);
    }

}
