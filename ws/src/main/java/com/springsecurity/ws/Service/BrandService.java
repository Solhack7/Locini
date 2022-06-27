package com.springsecurity.ws.Service;

import com.springsecurity.ws.Exception.BrandException;
import com.springsecurity.ws.UserRequest.BrandRequest;
import com.springsecurity.ws.Utility.Dto.BrandDto;
import java.util.List;


public interface BrandService{
    BrandDto addBrand(BrandRequest brandRequest,String idb_photo);

    List<BrandDto> findAll();

    BrandDto updateBrand(String brandId, BrandRequest brandRequest) throws BrandException;

    void deletedBrand(String brandId) throws BrandException;
}
