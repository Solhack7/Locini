package com.springsecurity.ws.Repository;

import com.springsecurity.ws.Entity.OffersEntity;
import com.springsecurity.ws.Entity.PartenaireEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffersRepo extends CrudRepository<OffersEntity,Long> {
    OffersEntity findByBrowserId(String idb);
}
