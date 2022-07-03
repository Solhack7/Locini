package com.springsecurity.ws.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PartenaireEntity {

    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String nom_agence;
    @Column(nullable = false)
    private String telephone;
    @Email
    @Unique
    private String email;
    @Column(nullable = false)
    private String browserId;
    @Column(nullable = false)
    private String ville;
    @Column(nullable = false)
    private String adresse_agence;
    private String site_web;
    @OneToMany(mappedBy = "partenaire", cascade = CascadeType.ALL)
    private Set<UsersAccount> usersAccounts;
    @OneToMany(mappedBy = "partenaire", cascade = CascadeType.ALL)
    private List<VehiculeEntity> vehiculeEntityList;
    @OneToMany(mappedBy="partenaire",cascade=CascadeType.ALL)
    private List<OrdersEntity> order;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UsersAccount usersAccount;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "offers_id")
    private OffersEntity offer;
}
