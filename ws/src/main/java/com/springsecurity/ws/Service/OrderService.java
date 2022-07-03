package com.springsecurity.ws.Service;

import com.springsecurity.ws.Exception.PartnaireException;
import com.springsecurity.ws.Exception.TypeOrdersException;
import com.springsecurity.ws.Exception.UsernameNotExist;
import com.springsecurity.ws.Exception.VehiculeException;
import com.springsecurity.ws.Response.OrdersResponse;
import com.springsecurity.ws.UserRequest.OrderRequest;
import com.springsecurity.ws.Utility.Dto.OrdersDto;

import java.security.Principal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public interface OrderService {

   // HashMap<String,String> addOrder(Order commandeDto);

    /*CommandeDto updatedLivraison(String commandeId,UpdatingCommande updatingCommande);

    CommandeDto updatedCommande(String commandeId);

    HashMap<String,List<CommandeResponse>> getCommandeInfo();

    HashMap<String, Double> getInfoToDb(String dateD, String dateF);

     */
    HashMap<String,String> addOrder(OrderRequest orderRequest) throws VehiculeException, ParseException, PartnaireException;

    List<OrdersResponse> getOrdersByTypeAndToken(Principal authentication, long typeo,int page,int limit) throws UsernameNotExist, TypeOrdersException;
}
