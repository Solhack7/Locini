package com.springsecurity.ws.Repository;

import com.springsecurity.ws.Entity.TypeOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOrderRepo extends JpaRepository<TypeOrderEntity,Long> {
    TypeOrderEntity findById(long id);
}
