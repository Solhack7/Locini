package com.springsecurity.ws.Utility.Dto;

import com.springsecurity.ws.Entity.ImageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto implements Serializable {
    private static final long serialVersionUID = 654020269160181L;

    private String idbBrand;

    private String nomBrand;

    private ImageDto imageBrand;
}
