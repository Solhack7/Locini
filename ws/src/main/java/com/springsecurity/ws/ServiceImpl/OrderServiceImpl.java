package com.springsecurity.ws.ServiceImpl;

import com.springsecurity.ws.Entity.*;
import com.springsecurity.ws.Exception.*;
import com.springsecurity.ws.Repository.*;
import com.springsecurity.ws.Response.OrdersResponse;
import com.springsecurity.ws.Service.OrderService;
import com.springsecurity.ws.UserRequest.OrderRequest;
import com.springsecurity.ws.Utility.Dto.OrdersDto;
import com.springsecurity.ws.Utility.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.protocol.HTTP;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final VehiculeRepo vehiculeRepo;
    private final TypeOrderRepo typeOrderRepo;
    private final Utils utils;
    private final UsersAccountRepository usersAccountRepository;
    private final OrdersRepo ordersRepo;
    private final PartenaireRepo partenaireRepo;
    private final CityRepo cityRepo;
    @Override
    public HashMap<String, String> addOrder(OrderRequest orderRequest) throws VehiculeException, ParseException, PartnaireException, CityException {
        CityEntity city = cityRepo.findByIdbCity(orderRequest.getIdbCity());
        if (city==null) throw  new CityException("Cette Ville Exixt Pas");
        VehiculeEntity vehicule = vehiculeRepo.findByBrowserId(orderRequest.getIdbVehicule());
        if (vehicule==null) throw  new VehiculeException("Cette Vehicule Exixt Pas");
        PartenaireEntity partenaire = partenaireRepo.findByBrowserId(vehicule.getPartenaire().getBrowserId());
        if (partenaire==null) throw  new PartnaireException("Cette Partenaire Exixt Pas");
        TypeOrderEntity toi= typeOrderRepo.findById(1);
        log.info("getVehiculesOfOrder",vehicule);
        OrdersEntity orderEntity = new OrdersEntity();
        orderEntity.setDtOrder(utils.getDateNow());
        orderEntity.setDtOrderF(utils.getDateNow());
        orderEntity.setIdbOrder(utils.generateStringId(20));
        orderEntity.setFn(orderRequest.getFn());
        orderEntity.setLn(orderRequest.getLn());
        orderEntity.setDtfrom(orderRequest.getDtfrom());
        orderEntity.setDtto(orderRequest.getDtto());
        orderEntity.setTel(orderRequest.getTel());
        orderEntity.setVehicule(vehicule);
        orderEntity.setTypeOrder(toi);
        orderEntity.setCity(city);
        orderEntity.setPartenaire(partenaire);
        ordersRepo.save(orderEntity);
        HashMap<String,String> messageSucces = new HashMap<String,String>();
        messageSucces.put("MESSAGE_SUCCES","VOUS AVEZ AJOUTER AVEC SUCES UN ORDER");
        messageSucces.put("httpStatus", HttpStatus.CREATED.toString());
        return messageSucces;
    }

    @Override
    public List<OrdersResponse> getOrdersByTypeAndToken(Principal authentication,long typeo,int page,int limit) throws UsernameNotExist, TypeOrdersException {
        if(page>0){
            page-=page;
        }
        ModelMapper modelMapper = new ModelMapper();
        UsersAccount account = usersAccountRepository.findByUsername(authentication.getName());
        if (account==null) throw  new UsernameNotExist("Ce Utilisateur Exixt Pas");
        PartenaireEntity getPartenaire = partenaireRepo.findByUsersAccount(account);
        TypeOrderEntity to= typeOrderRepo.findById(typeo);
        if (to==null) throw  new TypeOrdersException("Ce Type Exxit Pas");
        Pageable pagaebaleRequest = PageRequest.of(page, limit,Sort.by("dtOrder").descending());
        Page<OrdersEntity> ordersEntityPage = ordersRepo.findByPartenaireAndTypeOrder(getPartenaire,to,pagaebaleRequest);
        List<OrdersEntity> ordersEntities = ordersEntityPage.getContent();
        List<OrdersDto> ordersDtos = new ArrayList<>();
        for (OrdersEntity order:ordersEntities) {
            OrdersDto ordersDto = modelMapper.map(order,OrdersDto.class);
            ordersDtos.add(ordersDto);
        }
        List<OrdersResponse> orderResponses = new ArrayList<>();
        for (OrdersDto order:ordersDtos) {
            OrdersResponse ordersResponse = modelMapper.map(order,OrdersResponse.class);
            orderResponses.add(ordersResponse);
        }
        return orderResponses;
    }

    @Override
    public OrdersDto updateOrders(String order_idb, String typeo) throws OrderException, TypeOrdersException, ParseException {
        ModelMapper modelMapper = new ModelMapper();
        OrdersEntity ordersEntity = ordersRepo.findByIdbOrder(order_idb);
        TypeOrderEntity to= typeOrderRepo.findByIdbTypeo(typeo);
        if (to==null) throw  new TypeOrdersException("Ce Type Exxit Pas");
        if (ordersEntity==null) throw  new OrderException("Ce Order Exixt Pas Pour Faire Une Update");
        if ((ordersEntity.getTypeOrder().getId()+1)==to.getId() || to.getId()==4 ){
            ordersEntity.setDtPc(utils.getDateNow());
            ordersEntity.setTypeOrder(to);
            ordersRepo.save(ordersEntity);
        }else{
            throw new TypeOrdersException("Vous Pouvez Pas Effectuer Cette Action");
        }
        return modelMapper.map(ordersEntity,OrdersDto.class);
    }

    @Override
    public List<OrdersResponse> getOrdersByTypeAndTokenAndDate(String findByIdbTypeo, Principal principal, String dt,String dtFrom,String dtTo) throws UsernameNotExist, TypeOrdersException, ParseException {
        ModelMapper modelMapper = new ModelMapper();
        List<OrdersEntity> ordersEntities = new ArrayList<>();
        UsersAccount account = usersAccountRepository.findByUsername(principal.getName());
        if (account==null) throw  new UsernameNotExist("Ce Utilisateur Exixt Pas");
        PartenaireEntity getPartenaire = partenaireRepo.findByUsersAccount(account);
        TypeOrderEntity to= typeOrderRepo.findByIdbTypeo(findByIdbTypeo);
        if (to==null) throw  new TypeOrdersException("Ce Type Exxit Pas");
        if(dtFrom==null&&dtTo==null){
            ordersEntities= ordersRepo.findByPartenaireAndTypeOrderAndDtOrderGreaterThan(getPartenaire,to,utils.convertStringToDate(dt));
            log.info("DATE === {}",utils.convertStringToDate(dt));
            System.out.println(utils.convertStringToDate(dt));
        }
        else if(dt==null){
            ordersEntities = ordersRepo.findByPartenaireAndTypeOrderAndDtOrderBetween(getPartenaire,to,utils.convertStringToDate(dtFrom),utils.convertStringToDate(dtTo));
        }
        List<OrdersDto> ordersDtos = new ArrayList<>();
        for (OrdersEntity order:ordersEntities) {
            OrdersDto ordersDto = modelMapper.map(order,OrdersDto.class);
            ordersDtos.add(ordersDto);
        }
        List<OrdersResponse> orderResponses = new ArrayList<>();
        for (OrdersDto order:ordersDtos) {
            OrdersResponse ordersResponse = modelMapper.map(order,OrdersResponse.class);
            orderResponses.add(ordersResponse);
        }
        return orderResponses;

    }
}
