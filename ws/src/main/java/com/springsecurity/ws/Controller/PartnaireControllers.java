package com.springsecurity.ws.Controller;


import com.springsecurity.ws.Exception.ImageException;
import com.springsecurity.ws.Exception.PartnaireException;
import com.springsecurity.ws.Exception.UsernameExist;
import com.springsecurity.ws.Exception.VehiculeException;
import com.springsecurity.ws.Service.PartnaireService;
import com.springsecurity.ws.UserRequest.PartnaireRequest;
import com.springsecurity.ws.UserRequest.VehiculeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.security.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/partner") // localhost:8084/api/vehicules
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

}
