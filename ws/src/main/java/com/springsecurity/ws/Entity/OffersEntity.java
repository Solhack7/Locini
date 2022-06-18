package com.springsecurity.ws.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "offers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OffersEntity{
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String nomOffer;
    @Column(nullable = false)
    private String browserId;
    @Column(nullable = false)
    private long nbVehicule;
    @Column(nullable = false)
    private String detailsOffer;
    @Column(nullable = false)
    private double price;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UsersAccount usersAccount;
    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL)
    private List<PartenaireEntity> partenaireEntities;
}
