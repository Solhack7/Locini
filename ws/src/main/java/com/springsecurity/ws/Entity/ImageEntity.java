package com.springsecurity.ws.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "image") @Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageEntity {
    @Id
    @GeneratedValue
    private long idPhoto;

    @Column(nullable = false, length = 52)
    private String idBrowserPhoto;

    @Column(nullable = false, length = 52)
    private String imagePath;

    @Column(nullable = false, length = 52)
    private String imageFileName;

    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.ALL)
    private Set<VehiculeImageEntity> vehiculeImageEntitySet;
}
