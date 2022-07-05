package com.springsecurity.ws.Controller;

import com.springsecurity.ws.Exception.*;
import com.springsecurity.ws.Response.GetVehiculeResponse;
import com.springsecurity.ws.Response.OrdersResponse;
import com.springsecurity.ws.Service.VehiculeService;
import com.springsecurity.ws.UserRequest.VehiculeRequest;
import com.springsecurity.ws.Utility.Dto.PartnaireDto;
import com.springsecurity.ws.Utility.Dto.PartnaireVehiculeDisplayDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/vehicules") // localhost:8084/api/vehicules
@RequiredArgsConstructor
public class VehiculeControllers {

    private final VehiculeService vehiculeService;
    @PostMapping(path = "/add_vehicule",consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<HashMap<String,Object>> createVehicule(@RequestBody @Valid VehiculeRequest vehiculeRequest , Principal authentication) throws VehiculeException, ImageException, PartnaireException {
        if (vehiculeRequest.getNomVehicule().isEmpty() || vehiculeRequest.getPlace() < 1 || vehiculeRequest.getImgsId().size()==0 || vehiculeRequest.getBrandIdb().isEmpty())
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

    @GetMapping(path = "/getByCategory", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<PartnaireVehiculeDisplayDto>> getVehiculeByCategory(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "90") int limit,@RequestParam(value = "idbCategory") String idbCategory) throws CategoryException {
        List<PartnaireVehiculeDisplayDto> payload = vehiculeService.getByCategory(page,limit,idbCategory);
        return new ResponseEntity<List<PartnaireVehiculeDisplayDto>>(payload, HttpStatus.OK);
    }
    @GetMapping(path = "/getByLowPrice", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<HashMap<String,Object>> getByLowPrice(){
        HashMap<String,Object> payload = vehiculeService.getByLowPrice();
        return new ResponseEntity<HashMap<String,Object>>(payload, HttpStatus.OK);
    }

    @GetMapping(path = "/filtring", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<HashMap<String,Object>> filter_action(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "24") int limit,@RequestParam(value = "pnmin",required = false) Float pnmin, @RequestParam(value = "pnmax",required = false) Float pnmax,@RequestParam(value = "idb_brand",required = false) String idb_brand,@RequestParam(value = "idb_category",required = false) String idb_category) throws BrandException, CategoryException {
        HashMap<String,Object> payload = vehiculeService.filterAction(page,limit,pnmin,pnmax,idb_brand,idb_category);
        return new ResponseEntity<HashMap<String,Object>>(payload, HttpStatus.OK);
    }
    @GetMapping(path = "/get_vehiculeb/{idb_vehicule}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<HashMap<String,Object>> getVehiculeAndSimilarVehicule(@PathVariable String idb_vehicule) throws VehiculeException {
        HashMap<String,Object> payload = vehiculeService.getVehicleAndSimiliarVehiculeByIdbVehicule(idb_vehicule);
        return new ResponseEntity<HashMap<String,Object>>(payload, HttpStatus.OK);
    }

    @GetMapping(path = "/get_vehicule/{idb_category}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<HashMap<String,Object>> getVehiculeSameCategoryAndPriceBetweenAndCategory(@PathVariable String idb_category,@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "24") int limit,@RequestParam(value = "pnmin") float pnmin, @RequestParam(value = "pnmax") float pnmax,@RequestParam(value = "idb_brand") String idb_brand) throws VehiculeException, CategoryException {
        HashMap<String,Object> payload = vehiculeService.getVehiculeByCBetweenPminAndPmax(idb_category,page,limit,pnmin,pnmax);
        return new ResponseEntity<HashMap<String,Object>>(payload, HttpStatus.OK);
    }

    @GetMapping(path = "/get_vehicule_by_jwt", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<HashMap<String,Object>> getVehiculeByJwt(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "90") int limit, Principal authentication) throws VehiculeException, UsernameNotExist {
        HashMap<String,Object> payload = vehiculeService.getVehiculeByJwt(authentication,page,limit);
        return new ResponseEntity<HashMap<String,Object>>(payload, HttpStatus.OK);
    }

}
