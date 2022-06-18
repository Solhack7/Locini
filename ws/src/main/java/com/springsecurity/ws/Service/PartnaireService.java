package com.springsecurity.ws.Service;

import com.springsecurity.ws.Entity.PartenaireEntity;
import com.springsecurity.ws.Exception.PartnaireException;
import com.springsecurity.ws.Exception.UsernameExist;
import com.springsecurity.ws.Exception.UsernameNotExist;
import com.springsecurity.ws.UserRequest.OffersRequest;
import com.springsecurity.ws.UserRequest.PartnaireRequest;
import com.springsecurity.ws.Utility.Dto.OffersDto;
import com.springsecurity.ws.Utility.Dto.PartnaireDto;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

public interface PartnaireService {
    HashMap<String, Object> addPartner(PartnaireRequest partnaireRequest, Principal authentication) throws UsernameExist;

    PartenaireEntity checkExistPartenaire ( String partenaireIdBrowser) throws PartnaireException;

    HashMap<String, Object> getVehiculePartenaire(String idBrowserPartner);

    HashMap<String, Object> addOffer(OffersRequest offersRequest, String name) throws UsernameNotExist;

    HashMap<String, Object> addOfferToParner( String name,String idb,String idbP);

    List<PartnaireDto> getAllPartner();

    List<OffersDto> getAllOffer();
}
