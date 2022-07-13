package com.springsecurity.ws.Controller;


import com.springsecurity.ws.Exception.*;
import com.springsecurity.ws.Response.CategoryResponse;
import com.springsecurity.ws.Response.OrdersResponse;
import com.springsecurity.ws.Service.OrderService;
import com.springsecurity.ws.UserRequest.CategoryRequest;
import com.springsecurity.ws.Utility.Dto.CategoryDto;
import com.springsecurity.ws.Utility.ExportFile.ExportOrdersExcel;
import com.springsecurity.ws.Utility.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceControllers {
    private final OrderService orderService;
    private final Utils utils;
    @GetMapping("/ExportExcel")
    public ModelAndView exportToExcel(@RequestParam(value = "typeo",required = false) String typeo, @RequestParam(value = "dt_from",required = false) String dt_from,@RequestParam(value = "dt_to",required = false ) String dt_to,@RequestParam(value = "idb_brand",required = false) String idb_brand,@RequestParam(value = "idb_city",required = false) String idb_city, @RequestParam(value = "idb_vehicule",required = false) String idb_vehicule, Principal authentication) throws UsernameNotExist, TypeOrdersException, CityException, VehiculeException, OrderException, ParseException {
            if(dt_from==null && dt_to==null) throw new OrderException("Bad Request You Should Add Params");
        List<OrdersResponse> orders = orderService.filtringOrdersMultipleChoice(authentication,null,idb_vehicule,idb_brand,idb_city,utils.convertStringToDate(dt_from),utils.convertStringToDate(dt_to),typeo);

        ModelAndView mav = new ModelAndView();
            mav.setView(new ExportOrdersExcel());
            mav.addObject("orders", orders);
            return mav;
        }

    @GetMapping(path = "/get_orders_to_invoice/{idborder}")
    public ResponseEntity<HashMap<String,Object>> getDataToInvoice(@PathVariable String idborder,Principal authentication) throws CategoryException, OrderException {
        HashMap<String,Object> hashMap = orderService.getData(idborder,authentication);
        return new ResponseEntity<HashMap<String,Object>>(hashMap, HttpStatus.OK);
    }
    @GetMapping(path = "/filtring_orders_invoice", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<OrdersResponse>> filtringOrders( @RequestParam(value = "typeo",required = false) String typeo, @RequestParam(value = "dt_from",required = false) String dt_from,@RequestParam(value = "dt_to",required = false ) String dt_to,@RequestParam(value = "idb_brand",required = false) String idb_brand,@RequestParam(value = "idb_city",required = false) String idb_city, @RequestParam(value = "idb_vehicule",required = false) String idb_vehicule, Principal authentication) throws UsernameNotExist, TypeOrdersException, CityException, VehiculeException, OrderException, ParseException {
        if(dt_from==null && dt_to==null) throw new OrderException("Bad Request You Should Add Params");
        List<OrdersResponse> payload = orderService.filtringOrdersMultipleChoice(authentication,null,idb_vehicule,idb_brand,idb_city,utils.convertStringToDate(dt_from),utils.convertStringToDate(dt_to),typeo);
        return new ResponseEntity<List<OrdersResponse>>(payload, HttpStatus.OK);
    }

    }
