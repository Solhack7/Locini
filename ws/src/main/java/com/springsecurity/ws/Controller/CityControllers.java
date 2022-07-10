package com.springsecurity.ws.Controller;


import com.springsecurity.ws.Exception.CityException;
import com.springsecurity.ws.Response. CityResponse;
import com.springsecurity.ws.Service.CityService;
import com.springsecurity.ws.UserRequest. CityRequest;
import com.springsecurity.ws.Utility.Dto. CityDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/city") // localhost:8083/api/city
@RequiredArgsConstructor
public class CityControllers {

    private final CityService cityService;

    @PostMapping(path = "/add_city")
    public ResponseEntity<CityResponse> addBrand(@RequestBody @Valid CityRequest  cityRequest) throws CityException {
        ModelMapper modelMapper = new ModelMapper();
        if ( cityRequest.getCityName().isEmpty())
            throw new CityException("Vous Avez Raté un Champ obligatoire");
         CityDto addToDb =  cityService.addCity(cityRequest);
         CityResponse  CityResponse =modelMapper.map(addToDb,  CityResponse.class);
        return new ResponseEntity< CityResponse>( CityResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List< CityResponse>> getAllBrand() {
        ModelMapper modelMapper= new ModelMapper();
        List< CityResponse> allBrand = new ArrayList<>();
        List< CityDto> allArticleFromStock =  cityService.findAll();
        for ( CityDto  CityDto : allArticleFromStock) {
             CityResponse  CityResponse =modelMapper.map( CityDto,  CityResponse.class);
            allBrand.add( CityResponse);
        }
        return new ResponseEntity<List<CityResponse>>(allBrand,HttpStatus.OK);
    }

    @PutMapping(path = "/update_ city/{cityIdb}")
    public ResponseEntity< CityResponse> updateBrand(@RequestBody @Valid  CityRequest  cityRequest , @PathVariable String cityIdb) throws CityException {
        if ( cityRequest.getCityName().isEmpty())
            throw new CityException("Vous Avez Raté un Champ obligatoire");
        ModelMapper modelMapper = new ModelMapper();
         CityDto  cityDto =  cityService.updateCity(cityIdb,  cityRequest);
         CityResponse  cityResponse = modelMapper.map(cityDto,  CityResponse.class);
        return new ResponseEntity<CityResponse>( cityResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/delete_city/{cityIdb}")
    public ResponseEntity<Object> deleteBrand(@PathVariable String cityIdb) throws CityException {
         cityService.deletedCity(cityIdb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
