package com.springsecurity.ws.Service;

import com.springsecurity.ws.Exception.*;
import com.springsecurity.ws.Response.OrdersResponse;
import com.springsecurity.ws.UserRequest.OrderRequest;
import com.springsecurity.ws.Utility.Dto.OrdersDto;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface OrderService {
    HashMap<String,String> addOrder(OrderRequest orderRequest) throws VehiculeException, ParseException, PartnaireException, CityException;

    List<OrdersResponse> getOrdersByTypeAndToken(Principal authentication, long typeo,int page,int limit) throws UsernameNotExist, TypeOrdersException;

    OrdersDto updateOrders(String order_idb, String typeo) throws OrderException, TypeOrdersException, ParseException;

    List<OrdersResponse> getOrdersByTypeAndTokenAndDate(String type_order, Principal principal, String dt,String dtFrom,String dtTo) throws UsernameNotExist, TypeOrdersException, ParseException;

    List<OrdersResponse> filtringOrders(Principal authentication, int page, int limit, String idb_vehicule, String idb_brand, String idb_brand1, Date dt_order, long typeo);

    List<OrdersResponse> filtringOrdersMultipleChoice(Principal authentication, Pageable pageable, String idb_vehicule, String idb_brand, String idb_city, Date dt_from, Date dt_to, String typeo) throws UsernameNotExist, TypeOrdersException, CityException, VehiculeException;

    HashMap<String, Object> getData(String idborder,Principal authentication) throws OrderException;
}
