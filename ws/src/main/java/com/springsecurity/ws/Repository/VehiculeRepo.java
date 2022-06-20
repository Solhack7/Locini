package com.springsecurity.ws.Repository;

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
    List<VehiculeEntity> findByPartenaire(PartenaireEntity partenaire);
    VehiculeEntity findByBrowserId(String idb);
    @Query(value = "SELECT count(*) FROM vehicule vc WHERE vc.partnaire_id=:id_partnaire",nativeQuery = true)
    long getNumberOfVehiculeOfPartner(@Param("id_partnaire") long id_partnaire);
    Page<VehiculeEntity> findByCategoryVehicule(Pageable pageable, CategoryEntity categoryEntity);
    @Query(value = "SELECT * FROM vehicule vc WHERE vc.id!=:id_vehicule AND vc.category_id=:id_category",nativeQuery = true)
    Page<VehiculeEntity> findByCategoryVehiculeExceptSelectedVehicule(Pageable pagaebaleRequest,@Param("id_category") long id,@Param("id_vehicule") long id1);
}
