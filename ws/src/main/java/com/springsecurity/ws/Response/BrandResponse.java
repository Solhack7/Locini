package com.springsecurity.ws.Response;

import com.springsecurity.ws.Utility.Dto.ImageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandResponse {
    private String idbBrand;

    private String nomBrand;

    private ImageDto imageBrand;
}
