package com.springsecurity.ws.Repository;

import com.springsecurity.ws.Entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepo extends JpaRepository<CityEntity,Long> {
    CityEntity findByCityName(String cityName);
    CityEntity findByIdbCity(String idbCity);
}