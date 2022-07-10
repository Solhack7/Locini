package com.springsecurity.ws.ServiceImpl;

import com.springsecurity.ws.Entity. CityEntity;
import com.springsecurity.ws.Exception.CityException;
import com.springsecurity.ws.Repository.CityRepo;
import com.springsecurity.ws.Service.CityService;
import com.springsecurity.ws.UserRequest.CityRequest;
import com.springsecurity.ws.Utility.Dto.CityDto;
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
public class CityServiceImpl implements CityService {
    private final Utils utils;
    private final CityRepo cityRepo;

    @Override
    public CityDto addCity(CityRequest cityRequest) throws CityException {
        ModelMapper modelMapper = new ModelMapper();
         CityEntity  cityEntity = modelMapper.map(cityRequest,  CityEntity.class);
         CityEntity checkingName = cityRepo.findByCityName(cityRequest.getCityName().toLowerCase(Locale.ROOT));
        if (checkingName!=null) throw new CityException("Cette Ville Exixt Déja");
        cityEntity.setCityName(cityRequest.getCityName().toLowerCase(Locale.ROOT));
        cityEntity.setIdbCity(utils.generateStringId(15));
        cityRepo.save( cityEntity);
        CityDto CityDto = modelMapper.map( cityEntity, CityDto.class);
        return CityDto;
    }

    @Override
    public List<CityDto> findAll() {
        ModelMapper modelMapper = new ModelMapper();
        List<CityEntity> cityEntityList = (List<CityEntity>) cityRepo.findAll();
        List<CityDto> cityDtos = new ArrayList<>();
        for (CityEntity cityEntity: cityEntityList) {
            CityDto cityDto = modelMapper.map(cityEntity, CityDto.class);
            cityDtos.add(cityDto);
        }
        return cityDtos;
    }

    @Override
    public CityDto updateCity(String cityIdb, CityRequest cityRequest) throws CityException {
        ModelMapper modelMapper = new ModelMapper();
        CityEntity prepareCityUpdate = cityRepo.findByIdbCity(cityIdb);
        if (prepareCityUpdate==null) throw new CityException("Cette Ville Exixt Pas");
        if (prepareCityUpdate.getCityName().toLowerCase(Locale.ROOT).equals(cityRequest.getCityName().toLowerCase(Locale.ROOT))) throw new CityException("Cette Ville Exixt Déja");
        prepareCityUpdate.setCityName(cityRequest.getCityName());
        cityRepo.save(prepareCityUpdate);
        return modelMapper.map(prepareCityUpdate,CityDto.class);
    }

    @Override
    public void deletedCity(String cityIdb) throws CityException {
        CityEntity cityEntity = cityRepo.findByIdbCity(cityIdb);
        if (cityEntity==null) throw new CityException("Cette Ville Exixt Pas");
        cityRepo.delete(cityEntity);
    }
}
