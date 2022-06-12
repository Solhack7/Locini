package com.springsecurity.ws.ServiceImpl;

import com.springsecurity.ws.Entity.ImageEntity;
import com.springsecurity.ws.Entity.PartenaireEntity;
import com.springsecurity.ws.Entity.VehiculeEntity;
import com.springsecurity.ws.Entity.VehiculeImageEntity;
import com.springsecurity.ws.Exception.ImageException;
import com.springsecurity.ws.Exception.PartnaireException;
import com.springsecurity.ws.Repository.VehiculeImageRepo;
import com.springsecurity.ws.Repository.VehiculeRepo;
import com.springsecurity.ws.Service.ImageService;
import com.springsecurity.ws.Service.PartnaireService;
import com.springsecurity.ws.Service.VehiculeService;
import com.springsecurity.ws.UserRequest.VehiculeRequest;
import com.springsecurity.ws.Utility.Dto.VehiculeDto;
import com.springsecurity.ws.Utility.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Locale;


@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class VehiculeServiceImpl implements VehiculeService {
    private final VehiculeRepo vehiculeRepo;
    private final Utils utils;
    private final ImageService imageService;
    private final VehiculeImageRepo vehiculeImageRepo;
    private final PartnaireService partnaireService;
    @Override
    public HashMap<String, Object> addVehicule(VehiculeRequest vehiculeRequest) throws ImageException, PartnaireException {
        PartenaireEntity partenaireEntity = partnaireService.checkExistPartenaire(vehiculeRequest.getPartenaireIdBrowser());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        HashMap<String,Object> hashMap = new HashMap<String,Object>();
        for (String imgIdCheck : vehiculeRequest.getImgsId()){
            ImageEntity image = imageService.checkExixtImg(imgIdCheck);
        }
        VehiculeEntity vehicule = modelMapper.map(vehiculeRequest,VehiculeEntity.class);
        vehicule.setNomVehicule(vehiculeRequest.getNomVehicule().toLowerCase(Locale.ROOT));
        vehicule.setBrowserId(utils.generateStringId(15));
        vehicule.setPartenaire(partenaireEntity);
        vehiculeRepo.save(vehicule);
        for (String imgId:vehiculeRequest.getImgsId()){
            ImageEntity image = imageService.checkExixtImg(imgId);
            VehiculeImageEntity vehiculeImageEntity = new VehiculeImageEntity();
            vehiculeImageEntity.setVehicule(vehicule);
            vehiculeImageEntity.setImage(image);
            vehiculeImageEntity.setAltImg(vehicule.getNomVehicule()+"_"+vehiculeRequest.getAltImg());
            vehiculeImageRepo.save(vehiculeImageEntity);
        }
        VehiculeDto vehiculeDto = modelMapper.map(vehicule,VehiculeDto.class);
        hashMap.put("msg","CREATED WITH SUCCES");
        hashMap.put("createdVehicule",vehiculeDto);
        return hashMap;
    }
}
