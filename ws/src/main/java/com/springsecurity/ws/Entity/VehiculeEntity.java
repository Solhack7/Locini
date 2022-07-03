package com.springsecurity.ws.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity(name = "vehicule")
@AllArgsConstructor
@NoArgsConstructor
public class VehiculeEntity {

    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String nomVehicule;
    @Column(nullable = false)
    private String browserId;
    @Column(nullable = false)
    private long place;
    @Column(nullable = false)
    private Date dt_creation;

    @Column(nullable = false)
    private float pn;

    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL)
    private Set<VehiculeImageEntity> vehiculeImageEntitySet;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "partnaire_id")
    private PartenaireEntity partenaire;

    @ManyToOne
    @JoinColumn(name="category_id")
    private CategoryEntity categoryVehicule;

    @ManyToOne
    @JoinColumn(name="brand_id")
    private BrandEntity brandVehicule;

    @OneToMany(mappedBy="typeOrder",cascade=CascadeType.ALL)
    private List<OrdersEntity> order;
}
