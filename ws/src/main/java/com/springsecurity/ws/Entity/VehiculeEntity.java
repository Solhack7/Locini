package com.springsecurity.ws.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
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
    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL)
    private Set<VehiculeImageEntity> vehiculeImageEntitySet;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "partnaire_id")
    private PartenaireEntity partenaire;
}
