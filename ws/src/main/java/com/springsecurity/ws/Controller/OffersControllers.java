package com.springsecurity.ws.Controller;


import com.springsecurity.ws.Exception.OffersException;
import com.springsecurity.ws.Exception.PartnaireException;
import com.springsecurity.ws.Exception.UsernameExist;
import com.springsecurity.ws.Exception.UsernameNotExist;
import com.springsecurity.ws.Service.OffersService;
import com.springsecurity.ws.Service.PartnaireService;
import com.springsecurity.ws.UserRequest.OffersRequest;
import com.springsecurity.ws.UserRequest.PartnaireRequest;
import com.springsecurity.ws.Utility.Dto.OffersDto;
import com.springsecurity.ws.Utility.Dto.PartnaireDto;
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
@RequestMapping("/offers") // localhost:8084/api/offers
@RequiredArgsConstructor
public class OffersControllers {
    private final PartnaireService partnaireService;
    private final OffersService offersService;
    @PostMapping(path = "/add_offers",consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<HashMap<String,Object>> createVehicule(@RequestBody @Valid OffersRequest offersRequest , Principal authentication) throws PartnaireException, OffersException, UsernameNotExist {
        if (offersRequest.getNomOffer().isEmpty() || offersRequest.getNbVehicule()<5 || offersRequest.getDetailsOffer().isEmpty()|| offersRequest.getPrice()<0)
            throw new OffersException("Vous Avez Raté Un Champs Obligatoire");
        HashMap<String,Object> addOffer = partnaireService.addOffer(offersRequest,authentication.getName());
        return new ResponseEntity<HashMap<String,Object>>(addOffer, HttpStatus.CREATED);
    }

    @PutMapping(path = "/offers_partner/{idb_offers}/{partnerIdb}",consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<HashMap<String,Object>> addOffersToPartner(Principal authentication, @PathVariable String idb_offers,@PathVariable String partnerIdb) throws PartnaireException, OffersException, UsernameNotExist {
        HashMap<String,Object> addOffer = partnaireService.addOfferToParner(authentication.getName(),idb_offers, partnerIdb);
        return new ResponseEntity<HashMap<String,Object>>(addOffer, HttpStatus.CREATED);
    }

    @GetMapping(path = "/offersList", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<OffersDto>> getAllOffer(){
        List<OffersDto> payload = partnaireService.getAllOffer();
        return new ResponseEntity<List<OffersDto>>(payload, HttpStatus.OK);
    }

}
