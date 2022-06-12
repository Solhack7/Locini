package com.springsecurity.ws.Repository;

import com.springsecurity.ws.Entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepo extends JpaRepository<ImageEntity, Long> {
    ImageEntity findByIdBrowserPhoto(String idClientPhoto);
}
