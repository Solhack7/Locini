package com.springsecurity.ws.Controller;


import com.springsecurity.ws.Exception.BrandException;
import com.springsecurity.ws.Exception.CategoryException;
import com.springsecurity.ws.Response.BrandResponse;
import com.springsecurity.ws.Response.BrandResponse;
import com.springsecurity.ws.Service.BrandService;
import com.springsecurity.ws.Service.CategorieService;
import com.springsecurity.ws.UserRequest.BrandRequest;
import com.springsecurity.ws.UserRequest.BrandRequest;
import com.springsecurity.ws.Utility.Dto.BrandDto;
import com.springsecurity.ws.Utility.Dto.BrandDto;
import com.springsecurity.ws.Utility.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/brand") // localhost:8084/api/brand
@RequiredArgsConstructor
public class BrandControllers {

    private final BrandService brandService;
    @PostMapping(path = "/add_brand/{idb_photo}")
    public ResponseEntity<BrandResponse> addBrand(@RequestBody @Valid BrandRequest brandRequest , @PathVariable String idb_photo) throws BrandException {
       ModelMapper modelMapper = new ModelMapper();
        if (brandRequest.getNomBrand().isEmpty())
            throw new BrandException("Vous Avez Raté un Champ obligatoire");
        BrandDto addToDb = brandService.addBrand(brandRequest,idb_photo);
        BrandResponse brandResponse =modelMapper.map(addToDb, BrandResponse.class);
        return new ResponseEntity<BrandResponse>(brandResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BrandResponse>> getAllBrand() {
        ModelMapper modelMapper= new ModelMapper();
        List<BrandResponse> allBrand = new ArrayList<>();
        List<BrandDto> allArticleFromStock = brandService.findAll();
        for (BrandDto BrandDto : allArticleFromStock) {
            BrandResponse BrandResponse =modelMapper.map(BrandDto, BrandResponse.class);
            allBrand.add(BrandResponse);
        }
        return new ResponseEntity<List<BrandResponse>>(allBrand,HttpStatus.OK);
    }
    @PutMapping(path = "/update_ brand/{brandId}")
    public ResponseEntity<BrandResponse> updateBrand(@RequestBody @Valid BrandRequest BrandRequest , @PathVariable String brandId) throws BrandException {
        if (BrandRequest.getNomBrand().isEmpty())
            throw new BrandException("Vous Avez Raté un Champ obligatoire");
        ModelMapper modelMapper = new ModelMapper();
        BrandDto BrandDto = brandService.updateBrand(brandId, BrandRequest);
        BrandResponse brandResponse = modelMapper.map(BrandDto, BrandResponse.class);
        return new ResponseEntity<BrandResponse>(brandResponse, HttpStatus.ACCEPTED);
    }
    @DeleteMapping(path = "/delete_brand/{brandId}")
    public ResponseEntity<Object> deleteBrand(@PathVariable String brandId) throws BrandException {
        brandService.deletedBrand(brandId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
