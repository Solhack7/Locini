package com.springsecurity.ws.Controller;


import com.springsecurity.ws.Exception.*;
import com.springsecurity.ws.Response.CategoryResponse;
import com.springsecurity.ws.Response.OrdersResponse;
import com.springsecurity.ws.Service.OrderService;
import com.springsecurity.ws.UserRequest.CategoryRequest;
import com.springsecurity.ws.Utility.Dto.CategoryDto;
import com.springsecurity.ws.Utility.ExportFile.ExportOrdersExcel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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
    @GetMapping("/ExportExcel/{type_order}")
    public ModelAndView exportToExcel(@PathVariable String type_order, @RequestParam(value = "dt",required = false) String dt,@RequestParam(value = "dt_from",required = false) String dtFrom,@RequestParam(value = "dt_to",required = false) String dtTo, Principal principal) throws UsernameNotExist, TypeOrdersException, ParseException, InvoiceException {
            if((dt==null&&dtFrom==null&&dtTo==null)||(dt!=null&&dtFrom!=null&&dtTo!=null)
                || (dt!=null&&dtFrom!=null) || (dt!=null&&dtTo!=null)) throw  new InvoiceException("Vous Avez Une Bad request");
            List<OrdersResponse> orders = orderService.getOrdersByTypeAndTokenAndDate(type_order,principal,dt,dtFrom,dtTo);
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
    }
