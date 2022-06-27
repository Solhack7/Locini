package com.springsecurity.ws.Repository;

import com.springsecurity.ws.Entity.BrandEntity;
import com.springsecurity.ws.Entity.OffersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepo extends CrudRepository<BrandEntity,Long> {
    BrandEntity findByIdbBrand(String idb);
    BrandEntity findByNomBrand(String nom);
}
