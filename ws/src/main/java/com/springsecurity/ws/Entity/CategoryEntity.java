package com.springsecurity.ws.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "category") @Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 52)
    private String idbCategory;

    @Column(nullable = false, length = 52)
    private String nomCategory;

    @OneToMany(mappedBy="categoryVehicule",cascade=CascadeType.ALL)
    private List<VehiculeEntity> vehiculeEntityList;
}
