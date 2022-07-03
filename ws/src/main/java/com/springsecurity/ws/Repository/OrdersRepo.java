package com.springsecurity.ws.Repository;

import com.springsecurity.ws.Entity.OrdersEntity;
import com.springsecurity.ws.Entity.PartenaireEntity;
import com.springsecurity.ws.Entity.TypeOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepo extends PagingAndSortingRepository<OrdersEntity,Long> {
    Page<OrdersEntity> findByPartenaireAndTypeOrder(PartenaireEntity partenaire, TypeOrderEntity typeo, Pageable pagaebaleRequest);
    OrdersEntity findByIdbOrder(String idb);
}
