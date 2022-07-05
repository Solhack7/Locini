package com.springsecurity.ws.ServiceImpl;

import com.springsecurity.ws.Entity.*;
import com.springsecurity.ws.Exception.PartnaireException;
import com.springsecurity.ws.Exception.UsernameExist;
import com.springsecurity.ws.Exception.UsernameNotExist;
import com.springsecurity.ws.Repository.OffersRepo;
import com.springsecurity.ws.Repository.PartenaireRepo;
import com.springsecurity.ws.Repository.VehiculeImageRepo;
import com.springsecurity.ws.Repository.VehiculeRepo;
import com.springsecurity.ws.Service.PartnaireService;
import com.springsecurity.ws.Service.UserService;
import com.springsecurity.ws.UserRequest.OffersRequest;
import com.springsecurity.ws.UserRequest.PartnaireRequest;
import com.springsecurity.ws.Utility.Dto.*;
import com.springsecurity.ws.Utility.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.security.Principal;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final OffersRepo offersRepo;
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
        OffersEntity offersEntity = offersRepo.findByBrowserId("KP2sZrbWy1yl3uF");
        partenaireEntity.setOffer(offersEntity);
        partenaireRepo.save(partenaireEntity);
        hashMap.put("newPartnare",modelMapper.map(partenaireEntity, PartnaireDto.class));
        hashMap.put("msg","Create succes");
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
        Pageable pagaebaleRequest = PageRequest.of(0, 20);
        Page<VehiculeEntity> vehiculeEntityList = vehiculeRepo.findByPartenaire(partenaire,pagaebaleRequest);

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

    @Override
    public HashMap<String, Object> addOffer(OffersRequest offersRequest, String name) throws UsernameNotExist {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        HashMap<String,Object> hashMap = new HashMap<>();
        OffersEntity offersEntity = modelMapper.map(offersRequest,OffersEntity.class);
        offersEntity.setBrowserId(utils.generateStringId(15));
        UsersAccount userAccount = userService.findByUsername(name);
        UserAccountDto userAccountDto =  new UserAccountDto();
        userAccountDto.setUsername(userAccount.getUsername());
        userAccountDto.setEmail(userAccount.getEmail());
        userAccountDto.setFirstName(userAccount.getFirstName());
        userAccountDto.setLastName(userAccount.getLastName());
        if(userAccount==null)throw new UsernameNotExist("Ce Utilisateur N'Exixt Pas ");
        offersEntity.setUsersAccount(userAccount);
        OffersDto offersDtoo = modelMapper.map(offersEntity,OffersDto.class);
        offersRepo.save(offersEntity);
        hashMap.put("msg","Offre Ajouté Avec Succes");
        hashMap.put("offer_details",offersDtoo);
        return hashMap;
    }

    @Override
    public HashMap<String, Object> addOfferToParner( String name,String idb,String idbP) {
         HashMap<String,Object> hashMap =new HashMap<>();
         OffersEntity offersEntity = offersRepo.findByBrowserId(idb);
         PartenaireEntity partenaireEntity = partenaireRepo.findByBrowserId(idbP);
         partenaireEntity.setOffer(offersEntity);
        hashMap.put("msg","Ajouter de Offer Pour "+partenaireEntity.getNom_agence()+"est fait avec succes");
        partenaireRepo.save(partenaireEntity);
        return hashMap;
    }

    @Override
    public List<PartnaireDto> getAllPartner() {
        ModelMapper modelMapper = new ModelMapper();
        List<PartenaireEntity> partenaireEntities = (List<PartenaireEntity>) partenaireRepo.findAll();
        List<PartnaireDto> partnaireDtos = new ArrayList<>();
        for (PartenaireEntity partnerEntity:partenaireEntities) {
            PartnaireDto partnaireDto = modelMapper.map(partnerEntity,PartnaireDto.class);
            partnaireDtos.add(partnaireDto);
        }
        return partnaireDtos;
    }

    @Override
    public List<OffersDto> getAllOffer() {
        ModelMapper modelMapper = new ModelMapper();
        List<OffersEntity> offersEntities = (List<OffersEntity>) offersRepo.findAll();
        List<OffersDto> offersDtos = new ArrayList<>();
        for (OffersEntity offersEntity:offersEntities) {
            OffersDto offersDto = modelMapper.map(offersEntity,OffersDto.class);
            offersDtos.add(offersDto);
        }
        return offersDtos;
    }

    @Override
    public HashMap<String, Object> updatePartnaire(PartnaireRequest partnaireRequest, String idb_partnaire) throws PartnaireException {
        HashMap<String,Object> hashMap = new HashMap<>();
        PartenaireEntity partenaire = partenaireRepo.findByBrowserId(idb_partnaire);
        if(partenaire==null) throw new PartnaireException("ce partenaire exixt pas");
        ModelMapper modelMapper= new ModelMapper();
        partenaire.setNom_agence(partnaireRequest.getNom_agence());
        partenaire.setEmail(partnaireRequest.getEmail());
        partenaire.setAdresse_agence(partnaireRequest.getAdresse_agence());
        partenaire.setTelephone(partnaireRequest.getTelephone());
        partenaire.setSite_web(partnaireRequest.getSite_web());
        partenaire.setVille(partenaire.getVille());
        partenaireRepo.save(partenaire);
        hashMap.put("msg","la modification est faite avec succes");
        hashMap.put("payload",modelMapper.map(partenaire,PartnaireDto.class));
        return hashMap;
    }
}