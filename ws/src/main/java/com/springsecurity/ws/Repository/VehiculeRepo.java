package com.springsecurity.ws.Repository;

import com.springsecurity.ws.Entity.PartenaireEntity;
import com.springsecurity.ws.Entity.VehiculeEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface VehiculeRepo extends PagingAndSortingRepository< VehiculeEntity,Long> {
    List<VehiculeEntity> findByPartenaire(PartenaireEntity partenaire);
    VehiculeEntity findByBrowserId(String idb);
}
