package com.springsecurity.ws.Repository;

import com.springsecurity.ws.Entity.VehiculeEntity;
import com.springsecurity.ws.Entity.VehiculeImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VehiculeImageRepo extends CrudRepository<VehiculeImageEntity,Long > {
    List<VehiculeImageEntity> findByVehicule(VehiculeEntity vehicule);
}
