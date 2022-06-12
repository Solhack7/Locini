package com.springsecurity.ws.Repository;

import com.springsecurity.ws.Entity.PartenaireEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartenaireRepo extends PagingAndSortingRepository<PartenaireEntity,Long> {
    PartenaireEntity findByBrowserId(String idBrowser);
}
