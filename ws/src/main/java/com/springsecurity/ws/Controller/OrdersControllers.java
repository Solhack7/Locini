package com.springsecurity.ws.Controller;


import com.springsecurity.ws.Exception.*;
import com.springsecurity.ws.Response.OrdersResponse;
import com.springsecurity.ws.Service.OrderService;
import com.springsecurity.ws.UserRequest.OrderRequest;
import com.springsecurity.ws.Utility.Dto.OffersDto;
import com.springsecurity.ws.Utility.Dto.OrdersDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

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
    @GetMapping(path = "/getOrders", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<OrdersResponse>> getOrdersByTypeAndToken(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "90") int limit,@RequestParam(value = "typeo", defaultValue = "1") long typeo, Principal authentication) throws UsernameNotExist, TypeOrdersException {
        List<OrdersResponse> payload = orderService.getOrdersByTypeAndToken(authentication,typeo,page,limit);
        return new ResponseEntity<List<OrdersResponse>>(payload, HttpStatus.OK);
    }

    @PutMapping(path = "/update_orders/{order_idb}",produces = { MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<OrdersResponse> updatedLivraison( @RequestParam(value = "typeo") String typeo,@PathVariable String order_idb) throws ParseException, OrderException, TypeOrdersException {
        ModelMapper modelMapper = new ModelMapper();
        OrdersDto ordersDto = orderService.updateOrders(order_idb,typeo);
        OrdersResponse ordersResponse = modelMapper.map(ordersDto, OrdersResponse.class);
        return new ResponseEntity<OrdersResponse>(ordersResponse, HttpStatus.ACCEPTED);
    }
}
