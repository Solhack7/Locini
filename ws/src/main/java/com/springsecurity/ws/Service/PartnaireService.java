package com.springsecurity.ws.Service;

import com.springsecurity.ws.Entity.PartenaireEntity;
import com.springsecurity.ws.Exception.PartnaireException;
import com.springsecurity.ws.Exception.UsernameExist;
import com.springsecurity.ws.UserRequest.PartnaireRequest;
import java.security.Principal;
import java.util.HashMap;

public interface PartnaireService {
    HashMap<String, Object> addPartner(PartnaireRequest partnaireRequest, Principal authentication) throws UsernameExist;

    PartenaireEntity checkExistPartenaire ( String partenaireIdBrowser) throws PartnaireException;

    HashMap<String, Object> getVehiculePartenaire(String idBrowserPartner);
}
