package com.springsecurity.ws.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "typeo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeOrderEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 52)
    private String idbTypeo;

    @Column(nullable = false, length = 52)
    private String lib;

    @OneToMany(mappedBy="vehicule",cascade=CascadeType.ALL)
    private List<OrdersEntity> order;
}
