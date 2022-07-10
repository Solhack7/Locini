package com.springsecurity.ws.Service;

import com.springsecurity.ws.Exception.CityException;
import com.springsecurity.ws.UserRequest.CityRequest;
import com.springsecurity.ws.Utility.Dto.CityDto;

import java.util.List;

public interface CityService {
    CityDto addCity(CityRequest cityRequest) throws CityException;

    List<CityDto> findAll();

    CityDto updateCity(String cityIdb, CityRequest cityRequest) throws CityException;

    void deletedCity(String cityIdb) throws CityException;
}
