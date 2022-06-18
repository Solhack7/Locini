package com.springsecurity.ws.Repository;

import com.springsecurity.ws.Entity.OffersEntity;
import com.springsecurity.ws.Entity.PartenaireEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OffersRepo extends CrudRepository<OffersEntity,Long> {
    OffersEntity findByBrowserId(String idb);
}
