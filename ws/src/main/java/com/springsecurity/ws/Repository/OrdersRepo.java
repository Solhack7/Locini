package com.springsecurity.ws.Repository;

import com.springsecurity.ws.Entity.OrdersEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepo extends PagingAndSortingRepository<OrdersEntity,Long> {
}
