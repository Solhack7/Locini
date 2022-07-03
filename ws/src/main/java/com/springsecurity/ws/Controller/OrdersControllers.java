package com.springsecurity.ws.Controller;


import com.springsecurity.ws.Exception.OrderException;
import com.springsecurity.ws.Exception.PartnaireException;
import com.springsecurity.ws.Exception.VehiculeException;
import com.springsecurity.ws.Service.OrderService;
import com.springsecurity.ws.UserRequest.OrderRequest;
import com.springsecurity.ws.Utility.Dto.OrdersDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/order") // localhost:8084/api/order
@RequiredArgsConstructor
public class OrdersControllers {
    
    private final OrderService orderService;
    @PostMapping(path = "/add_order",consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<HashMap<String,String>> addOrder(@RequestBody OrderRequest orderRequest) throws OrderException, ParseException, VehiculeException, PartnaireException {
        if (orderRequest.getIdbVehicule().isEmpty() || orderRequest.getIdbPartenaire().isEmpty() || orderRequest.getDtto().toString().isEmpty()
                || orderRequest.getDtfrom().toString().isEmpty() || orderRequest.getFn().isEmpty() || orderRequest.getLn().isEmpty() || orderRequest.getTel().isEmpty())
            throw new OrderException("Vous Devez Remplir Les Champs Obligatoire");
        HashMap<String,String> addToDb = orderService.addOrder(orderRequest);
        return new ResponseEntity<HashMap<String,String>>(addToDb, HttpStatus.CREATED);
    }

}
