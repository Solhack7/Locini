package com.springsecurity.ws.Entity;

//import org.springframework.lang.NonNull;

//import lombok.NonNull;

//import javax.persistence.*;

import lombok.Data;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.List;

@Data // @Data From import lombok.Data will create all Setter and Getter
@Entity
@Table(name = "users")
public class UsersAccount implements Serializable { // @Data From import lombok.Data will create all Setter and Getter

	private static final long serialVersionUID = 8709776215922620598L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userId;

    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String username;

    @Email
    @NonNull
    private String email;
    @NonNull
    private String password;

    private String role;
    private String[] authorities;
    private boolean isActive; // Enable/Disable
    private boolean isNotLocked; //Locked/UnLocked
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "partenaireData")
    private PartenaireEntity partenaire;
    @OneToMany(mappedBy = "usersAccount", cascade = CascadeType.ALL)
    private List<PartenaireEntity> partenaireEntities;
    @OneToMany(mappedBy = "usersAccount", cascade = CascadeType.ALL)
    private List<OffersEntity> offersEntities;
public UsersAccount(){

}

    public UsersAccount(Long id, String userId, String firstName,PartenaireEntity partenaireEntity, String lastName, String username, String password, String email, String role, String[] authorities, boolean isActive, boolean isNotLocked) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.partenaire = partenaireEntity;
        this.role = role;
        this.authorities = authorities;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
    }

}
