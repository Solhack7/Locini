package com.springsecurity.ws.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CityResponse {
    private String idbCity;
    private String cityName;
}
