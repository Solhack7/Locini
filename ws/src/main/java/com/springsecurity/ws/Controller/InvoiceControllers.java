package com.springsecurity.ws.Controller;


import com.springsecurity.ws.Exception.TypeOrdersException;
import com.springsecurity.ws.Exception.UsernameNotExist;
import com.springsecurity.ws.Response.OrdersResponse;
import com.springsecurity.ws.Service.OrderService;
import com.springsecurity.ws.Utility.ExportFile.ExportOrdersExcel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView exportToExcel(@PathVariable String type_order, @RequestParam(value = "dt",required = false) String dt,@RequestParam(value = "dt_from",required = false) String dtFrom,@RequestParam(value = "dt_to",required = false) String dtTo, Principal principal) throws UsernameNotExist, TypeOrdersException, ParseException {
            List<OrdersResponse> orders = orderService.getOrdersByTypeAndTokenAndDate(type_order,principal,dt,dtFrom,dtTo);
            ModelAndView mav = new ModelAndView();
            mav.setView(new ExportOrdersExcel());
            mav.addObject("orders", orders);
            return mav;
        }
    }
