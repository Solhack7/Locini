package com.springsecurity.ws.Service;

import com.springsecurity.ws.Exception.*;
import com.springsecurity.ws.Response.OrdersResponse;
import com.springsecurity.ws.UserRequest.OrderRequest;
import com.springsecurity.ws.Utility.Dto.OrdersDto;

import java.security.Principal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface OrderService {
    HashMap<String,String> addOrder(OrderRequest orderRequest) throws VehiculeException, ParseException, PartnaireException;

    List<OrdersResponse> getOrdersByTypeAndToken(Principal authentication, long typeo,int page,int limit) throws UsernameNotExist, TypeOrdersException;

    OrdersDto updateOrders(String order_idb, String typeo) throws OrderException, TypeOrdersException, ParseException;

    List<OrdersResponse> getOrdersByTypeAndTokenAndDate(String type_order, Principal principal, String dt,String dtFrom,String dtTo) throws UsernameNotExist, TypeOrdersException, ParseException;
}
