package com.springsecurity.ws.Repository;

import com.springsecurity.ws.Entity.BrandEntity;
import com.springsecurity.ws.Entity.CategoryEntity;
import com.springsecurity.ws.Entity.PartenaireEntity;
import com.springsecurity.ws.Entity.VehiculeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehiculeRepo extends PagingAndSortingRepository< VehiculeEntity,Long> {
    Page<VehiculeEntity> findByPartenaire(PartenaireEntity partenaire, Pageable pagaebaleRequest);


    VehiculeEntity findByBrowserId(String idb);

    @Query(value = "SELECT count(*) FROM vehicule vc WHERE vc.partnaire_id=:id_partnaire",nativeQuery = true)
    long getNumberOfVehiculeOfPartner(@Param("id_partnaire") long id_partnaire);

    Page<VehiculeEntity> findByCategoryVehicule(Pageable pageable, CategoryEntity categoryEntity);

    Page<VehiculeEntity> findByBrandVehicule(BrandEntity brand,Pageable pagaebaleRequest);

    Page<VehiculeEntity> findByBrandVehiculeAndCategoryVehicule(Pageable pagaebaleRequest,BrandEntity brand,CategoryEntity categoryEntity);

    @Query(value = "SELECT * FROM vehicule vc WHERE vc.id!=:id_vehicule AND vc.category_id=:id_category",nativeQuery = true)
    Page<VehiculeEntity> findByCategoryVehiculeExceptSelectedVehicule(Pageable pagaebaleRequest,@Param("id_category") long id,@Param("id_vehicule") long id1);

    @Query(value = "SELECT * FROM vehicule vc WHERE vc.category_id=:id_category AND vc.pn BETWEEN :pnmin AND :pnmax",nativeQuery = true)
    Page<VehiculeEntity> findByCategoryAndPricingBetweenPminAndPmax(Pageable pagaebaleRequest,@Param("pnmin") float pnmin,@Param("pnmax") float pnmax, @Param("id_category") long category_id);

    @Query(value = "SELECT * FROM vehicule vc WHERE vc.pn BETWEEN :pnmin AND :pnmax",nativeQuery = true)
    Page<VehiculeEntity> findByPricingBetweenPminAndPmax(Pageable pagaebaleRequest,@Param("pnmin") float pnmin,@Param("pnmax") float pnmax);

    @Query(value = "SELECT * FROM vehicule vc WHERE vc.category_id=:id_category AND vc.brand_id=:id_brand AND vc.pn BETWEEN :pnmin AND :pnmax ",nativeQuery = true)
    Page<VehiculeEntity> findByBrandVehiculeAndCAndCategoryVehiculeAndFiltrerPrincing(Pageable pagaebaleRequest,@Param("id_brand") long idbrand,@Param("id_category") long idcat,@Param("pnmin") float pnmin,@Param("pnmax") float pnmax);
    @Query(value = "SELECT * FROM vehicule vc WHERE vc.brand_id=:id_brand AND vc.pn BETWEEN :pnmin AND :pnmax ",nativeQuery = true)
    Page<VehiculeEntity> findByBrandVehiculeAndPricingBetwen(Pageable pagaebaleRequest,@Param("id_brand") long idbrand,@Param("pnmin") float pnmin,@Param("pnmax") float pnmax);
}
