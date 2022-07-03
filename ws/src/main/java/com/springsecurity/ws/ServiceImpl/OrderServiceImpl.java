package com.springsecurity.ws.ServiceImpl;

import com.springsecurity.ws.Entity.OrdersEntity;
import com.springsecurity.ws.Entity.PartenaireEntity;
import com.springsecurity.ws.Entity.TypeOrderEntity;
import com.springsecurity.ws.Entity.VehiculeEntity;
import com.springsecurity.ws.Exception.PartnaireException;
import com.springsecurity.ws.Exception.VehiculeException;
import com.springsecurity.ws.Repository.OrdersRepo;
import com.springsecurity.ws.Repository.PartenaireRepo;
import com.springsecurity.ws.Repository.TypeOrderRepo;
import com.springsecurity.ws.Repository.VehiculeRepo;
import com.springsecurity.ws.Service.OrderService;
import com.springsecurity.ws.UserRequest.OrderRequest;
import com.springsecurity.ws.Utility.Dto.OrdersDto;
import com.springsecurity.ws.Utility.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.HashMap;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final VehiculeRepo vehiculeRepo;
    private final TypeOrderRepo typeOrderRepo;
    private final Utils utils;
    private final OrdersRepo ordersRepo;
    private final PartenaireRepo partenaireRepo;
    @Override
    public HashMap<String, String> addOrder(OrderRequest orderRequest) throws VehiculeException, ParseException, PartnaireException {

        VehiculeEntity vehicule = vehiculeRepo.findByBrowserId(orderRequest.getIdbVehicule());
        if (vehicule==null) throw  new VehiculeException("Cette Vehicule Exixt Pas");
        PartenaireEntity partenaire = partenaireRepo.findByBrowserId(vehicule.getPartenaire().getBrowserId());
        if (partenaire==null) throw  new PartnaireException("Cette Partenaire Exixt Pas");
        TypeOrderEntity toi= typeOrderRepo.findById(1);
        log.info("getVehiculesOfOrder",vehicule);
        OrdersEntity orderEntity = new OrdersEntity();
        orderEntity.setDtOrder(utils.getDateNow());
        orderEntity.setIdbOrder(utils.generateStringId(20));
        orderEntity.setFn(orderRequest.getFn());
        orderEntity.setLn(orderRequest.getLn());
        orderEntity.setDtfrom(orderRequest.getDtfrom());
        orderEntity.setDtto(orderRequest.getDtto());
        orderEntity.setTel(orderRequest.getTel());
        orderEntity.setVehicule(vehicule);
        orderEntity.setTypeOrder(toi);
        orderEntity.setPartenaire(partenaire);
        ordersRepo.save(orderEntity);
        HashMap<String,String> messageSucces = new HashMap<String,String>();
        messageSucces.put("MESSAGE_SUCCES","VOUS AVEZ AJOUTER AVEC SUCES Un Order");
        messageSucces.put("httpStatus", HttpStatus.CREATED.toString());
        return messageSucces;
    }
}
