package com.springsecurity.ws.Repository;

import com.springsecurity.ws.Entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrdersRepo extends PagingAndSortingRepository<OrdersEntity,Long> {
    OrdersEntity findByIdbOrderAndPartenaire(String idb,PartenaireEntity partenaire);
    Page<OrdersEntity> findByPartenaireAndTypeOrder(PartenaireEntity partenaire, TypeOrderEntity typeo, Pageable pagaebaleRequest);
    OrdersEntity findByIdbOrder(String idb);
    List<OrdersEntity> findByPartenaireAndTypeOrderAndDtOrderGreaterThan(PartenaireEntity getPartenaire, TypeOrderEntity to, Date dt);
    Page<OrdersEntity> findByPartenaireAndCity(PartenaireEntity partenaire, CityEntity cityEntity,Pageable pagaebaleRequest);
    Page<OrdersEntity> findByPartenaireAndDtOrderBetween(PartenaireEntity partenaire,Date dtFrom,Date dtTo,Pageable pagaebaleRequest);
    Page<OrdersEntity> findByPartenaireAndVehicule(PartenaireEntity partenaire, VehiculeEntity vehicule,Pageable pagaebaleRequest);
    List<OrdersEntity> findByPartenaireAndTypeOrderAndDtOrderBetween(PartenaireEntity getPartenaire, TypeOrderEntity to, Date dtFrom,Date dtTo);
    Page<OrdersEntity> findByPartenaireAndTypeOrderAndDtOrderBetween(PartenaireEntity getPartenaire, TypeOrderEntity to, Date dtFrom,Date dtTo,Pageable pageable);
    Page<OrdersEntity> findByPartenaireAndTypeOrderAndCity(PartenaireEntity getPartenaire, TypeOrderEntity to, CityEntity city, Pageable pagaebaleRequest);
    Page<OrdersEntity> findByPartenaireAndTypeOrderAndVehicule(PartenaireEntity getPartenaire, TypeOrderEntity to, VehiculeEntity vehicule, Pageable pagaebaleRequest);
    Page<OrdersEntity> findByPartenaireAndCityAndDtOrderBetween(PartenaireEntity getPartenaire, CityEntity city, Date dt_from, Date dt_to, Pageable pagaebaleRequest);
    Page<OrdersEntity> findByPartenaireAndVehiculeAndDtOrderBetween(PartenaireEntity getPartenaire, VehiculeEntity vehicule, Date dt_from, Date dt_to, Pageable pagaebaleRequest);
    Page<OrdersEntity> findByPartenaireAndTypeOrderAndCityAndDtOrderBetween(PartenaireEntity getPartenaire, TypeOrderEntity to, CityEntity city, Date dt_from, Date dt_to, Pageable pagaebaleRequest);
    Page<OrdersEntity> findByPartenaireAndTypeOrderAndVehiculeAndDtOrderBetween(PartenaireEntity getPartenaire, TypeOrderEntity to, VehiculeEntity vehicule, Date dt_from, Date dt_to, Pageable pagaebaleRequest);
    Page<OrdersEntity> findByPartenaireAndCityAndVehiculeAndDtOrderBetween(PartenaireEntity partenaire,CityEntity city,VehiculeEntity vehicule,Date dt_from, Date dt_to,Pageable pageable);
    Page<OrdersEntity> findByPartenaireAndTypeOrderAndCityAndVehiculeAndDtOrderBetween(PartenaireEntity partenaire,TypeOrderEntity typeOrder,CityEntity city,VehiculeEntity vehicule,Date dt_from, Date dt_to,Pageable pageable);
}

