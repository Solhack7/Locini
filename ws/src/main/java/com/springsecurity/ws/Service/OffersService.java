package com.springsecurity.ws.Service;

import com.springsecurity.ws.Exception.UsernameNotExist;
import com.springsecurity.ws.UserRequest.OffersRequest;

import java.security.Principal;
import java.util.HashMap;

public interface OffersService {
    HashMap<String, Object> addOffer(OffersRequest offersRequest, String userName) throws UsernameNotExist;
     String generateStringId(int length);
}
