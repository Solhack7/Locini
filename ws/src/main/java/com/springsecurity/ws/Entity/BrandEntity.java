package com.springsecurity.ws.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "brand") @Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 52)
    private String idbBrand;

    @Column(nullable = false, length = 52)
    private String nomBrand;

    @ManyToOne
    @JoinColumn(name="image_id")
    private ImageEntity imageBrand;

    @OneToMany(mappedBy="brandVehicule",cascade=CascadeType.ALL)
    private List<VehiculeEntity> vehiculeEntityList;
}
