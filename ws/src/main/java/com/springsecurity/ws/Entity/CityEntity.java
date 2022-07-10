package com.springsecurity.ws.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "city") @Data
@NoArgsConstructor
@AllArgsConstructor
public class CityEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 52)
    private String idbCity;

    @Column(nullable = false, length = 52)
    private String cityName;

    @OneToMany(mappedBy="city",cascade=CascadeType.ALL)
    private List<OrdersEntity> order;
}
