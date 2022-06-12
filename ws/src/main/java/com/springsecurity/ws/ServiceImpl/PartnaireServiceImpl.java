package com.springsecurity.ws.ServiceImpl;

import com.springsecurity.ws.Entity.PartenaireEntity;
import com.springsecurity.ws.Entity.UsersAccount;
import com.springsecurity.ws.Entity.VehiculeEntity;
import com.springsecurity.ws.Entity.VehiculeImageEntity;
import com.springsecurity.ws.Exception.PartnaireException;
import com.springsecurity.ws.Exception.UsernameExist;
import com.springsecurity.ws.Repository.PartenaireRepo;
import com.springsecurity.ws.Repository.VehiculeImageRepo;
import com.springsecurity.ws.Repository.VehiculeRepo;
import com.springsecurity.ws.Service.PartnaireService;
import com.springsecurity.ws.Service.UserService;
import com.springsecurity.ws.UserRequest.PartnaireRequest;
import com.springsecurity.ws.Utility.Dto.PartnaireDto;
import com.springsecurity.ws.Utility.Dto.PartnaireVehiculeDisplayDto;
import com.springsecurity.ws.Utility.Dto.VehiculeDto;
import com.springsecurity.ws.Utility.Dto.VehiculeImageDto;
import com.springsecurity.ws.Utility.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.security.Principal;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class PartnaireServiceImpl implements PartnaireService {
    private final PartenaireRepo partenaireRepo;
    private final Utils utils;
    private final UserService userService;
    private final VehiculeRepo vehiculeRepo;
    private final VehiculeImageRepo vehiculeImageRepo;
    @Override
    public HashMap<String, Object> addPartner(PartnaireRequest partnaireRequest, Principal authentication) throws UsernameExist {
        HashMap<String,Object> hashMap = new HashMap<>();
        ModelMapper modelMapper = new ModelMapper();
        PartenaireEntity partenaireEntity = modelMapper.map(partnaireRequest,PartenaireEntity.class);
        partenaireEntity.setBrowserId(utils.generateStringId(15));
        partenaireEntity.setNom_agence(partnaireRequest.getNom_agence().toLowerCase(Locale.ROOT));
        UsersAccount userAccount = userService.findByUsername(authentication.getName());
        if (userAccount==null) throw  new UsernameExist("ce utilisateur exixt pas");
        partenaireEntity.setUsersAccount(userAccount);
        partenaireRepo.save(partenaireEntity);
        hashMap.put("newPartnare",modelMapper.map(partenaireEntity, PartnaireDto.class));
        hashMap.put("msg","Create succes");
        hashMap.put("added",userAccount.getUsername());
        return hashMap;
    }

    @Override
    public PartenaireEntity checkExistPartenaire(String partenaireIdBrowser) throws PartnaireException {
        PartenaireEntity partenaire = partenaireRepo.findByBrowserId(partenaireIdBrowser);
        if(partenaire==null) throw new PartnaireException("Ce Partenaire Exixt Pas");
        return partenaire;
    }

    @Override
    public HashMap<String, Object> getVehiculePartenaire(String idBrowserPartner) {
        ModelMapper modelMapper = new ModelMapper();
        HashMap<String,Object> hashmapVehicule = new HashMap<>();

        PartenaireEntity partenaire = partenaireRepo.findByBrowserId(idBrowserPartner);
        PartnaireDto partnaireDto = modelMapper.map(partenaire,PartnaireDto.class);
        hashmapVehicule.put("info",partnaireDto);
        List<VehiculeEntity> vehiculeEntityList = vehiculeRepo.findByPartenaire(partenaire);
        List<PartnaireVehiculeDisplayDto> partnaireVehiculeDisplayDtos = new ArrayList<>();
        for (VehiculeEntity vehicule:vehiculeEntityList){
            VehiculeDto vehiculeDto = modelMapper.map(vehicule,VehiculeDto.class);
            PartnaireVehiculeDisplayDto partnaireVehiculeDisplayDto = new PartnaireVehiculeDisplayDto();
            List<VehiculeImageEntity> vehiculeImageEntities = vehiculeImageRepo.findByVehicule(vehicule);
            List<VehiculeImageDto> vehiculeImageDtos= new ArrayList<>();
            for (VehiculeImageEntity vehiculeImageEntity : vehiculeImageEntities){
                VehiculeImageDto vehiculeImageDto = modelMapper.map(vehiculeImageEntity,VehiculeImageDto.class);
                vehiculeImageDtos.add(vehiculeImageDto);
            }
            partnaireVehiculeDisplayDto.setImg(vehiculeImageDtos);
            partnaireVehiculeDisplayDto.setVehicule(vehiculeDto);
            partnaireVehiculeDisplayDtos.add(partnaireVehiculeDisplayDto);
        }

        hashmapVehicule.put("vehiculeData",partnaireVehiculeDisplayDtos);
        return hashmapVehicule;
    }
}
