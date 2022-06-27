package com.springsecurity.ws.ServiceImpl;

import com.springsecurity.ws.Entity.BrandEntity;
import com.springsecurity.ws.Entity.ImageEntity;
import com.springsecurity.ws.Exception.BrandException;
import com.springsecurity.ws.Repository.BrandRepo;
import com.springsecurity.ws.Repository.ImageRepo;
import com.springsecurity.ws.Service.BrandService;
import com.springsecurity.ws.UserRequest.BrandRequest;
import com.springsecurity.ws.Utility.Dto.BrandDto;
import com.springsecurity.ws.Utility.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    private final BrandRepo brandRepo;
    private final Utils utils;
    private final ImageRepo imageRepo;
    @Override
    public BrandDto addBrand(BrandRequest brandRequest,String idb_photo) throws BrandException {
        ModelMapper modelMapper = new ModelMapper();
        BrandEntity brandEntity = modelMapper.map(brandRequest, BrandEntity.class);
        BrandEntity checkingName = brandRepo.findByNomBrand(brandRequest.getNomBrand().toLowerCase(Locale.ROOT));
        if (checkingName!=null) throw new BrandException("Cette Brand Exixt Déja");
        brandEntity.setNomBrand(brandRequest.getNomBrand().toLowerCase(Locale.ROOT));
        brandEntity.setIdbBrand(utils.generateStringId(15));
        ImageEntity image = imageRepo.findByIdBrowserPhoto(idb_photo);
        brandEntity.setImageBrand(image);
        brandRepo.save(brandEntity);
        BrandDto brandDto = modelMapper.map(brandEntity, BrandDto.class);
        return brandDto;
    }

    @Override
    public List<BrandDto> findAll() {
        ModelMapper modelMapper = new ModelMapper();
        List<BrandEntity> brandEntityList = (List<BrandEntity>) brandRepo.findAll();
        List<BrandDto> brandDtos = new ArrayList<>();
        for (BrandEntity brandEntity: brandEntityList) {
            BrandDto brandDto = modelMapper.map(brandEntity, BrandDto.class);
            brandDtos.add(brandDto);
        }
        return brandDtos;
    }

    @Override
    public BrandDto updateBrand(String brandId, BrandRequest brandRequest) throws BrandException {
        ModelMapper modelMapper = new ModelMapper();
        BrandEntity brandEntity = brandRepo.findByIdbBrand(brandId);
        BrandEntity checkingName = brandRepo.findByNomBrand(brandEntity.getNomBrand());
        if (brandEntity==null) throw new BrandException("Brand Introuvable");
        if (checkingName.getNomBrand().equals(brandRequest.getNomBrand().toLowerCase(Locale.ROOT))) throw new BrandException("Ce Nom De Brand Exixt Déja");
        brandEntity.setNomBrand(brandRequest.getNomBrand().toLowerCase(Locale.ROOT));

        brandRepo.save(brandEntity);
        return modelMapper.map(brandEntity,BrandDto.class);
    }

    @Override
    public void deletedBrand(String brandId) throws BrandException {
        BrandEntity brandEntity = brandRepo.findByIdbBrand(brandId);
        if (brandEntity==null) throw new BrandException("Brand Introuvable");
        brandRepo.delete(brandEntity);
    }
}
