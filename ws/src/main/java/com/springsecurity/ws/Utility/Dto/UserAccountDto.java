package com.springsecurity.ws.Utility.Dto;

import com.springsecurity.ws.Entity.PartenaireEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Email;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDto  implements Serializable {
    private static final long serialVersionUID = 8709776215922221548L;

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private PartnaireDto partenaire;
}
