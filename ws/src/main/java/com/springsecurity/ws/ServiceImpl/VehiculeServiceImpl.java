package com.springsecurity.ws.ServiceImpl;

import com.springsecurity.ws.Entity.*;
import com.springsecurity.ws.Exception.*;
import com.springsecurity.ws.Repository.*;
import com.springsecurity.ws.Service.ImageService;
import com.springsecurity.ws.Service.OffersService;
import com.springsecurity.ws.Service.PartnaireService;
import com.springsecurity.ws.Service.VehiculeService;
import com.springsecurity.ws.UserRequest.VehiculeRequest;
import com.springsecurity.ws.Utility.Dto.CategoryDto;
import com.springsecurity.ws.Utility.Dto.PartnaireVehiculeDisplayDto;
import com.springsecurity.ws.Utility.Dto.VehiculeDto;
import com.springsecurity.ws.Utility.Dto.VehiculeImageDto;
import com.springsecurity.ws.Utility.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.security.Principal;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private final CategorieRepo categorieRepo;
    private final OffersService offersService;
    private final BrandRepo brandRepo;
    private final UsersAccountRepository usersAccountRepository;
    private final PartenaireRepo partenaireRepo;
    @Override
    public HashMap<String, Object> addVehicule(VehiculeRequest vehiculeRequest) throws ImageException, PartnaireException {
        HashMap<String,Object> hashMap = new HashMap<String,Object>();
        PartenaireEntity partenaireEntity = partnaireService.checkExistPartenaire(vehiculeRequest.getPartenaireIdBrowser());
        CategoryEntity categoryEntity =categorieRepo.findByIdbCategory(vehiculeRequest.getCategoryIdb());
        BrandEntity brandEntity = brandRepo.findByIdbBrand(vehiculeRequest.getBrandIdb());

        if(vehiculeRepo.getNumberOfVehiculeOfPartner(partenaireEntity.getId())<partenaireEntity.getOffer().getNbVehicule()){
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration()
                    .setMatchingStrategy(MatchingStrategies.STRICT);
            for (String imgIdCheck : vehiculeRequest.getImgsId()){
                ImageEntity image = imageService.checkExixtImg(imgIdCheck);
            }
            VehiculeEntity vehicule = modelMapper.map(vehiculeRequest,VehiculeEntity.class);
            vehicule.setNomVehicule(vehiculeRequest.getNomVehicule().toLowerCase(Locale.ROOT));
            vehicule.setBrowserId(utils.generateStringId(15));
            vehicule.setPartenaire(partenaireEntity);
            vehicule.setCategoryVehicule(categoryEntity);
            vehicule.setBrandVehicule(brandEntity);
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
        }
        else{

            hashMap.put("msg","Vous Devez upgrader votre offre");
            hashMap.put("list_offers",partnaireService.getAllOffer());
        }
        return hashMap;
    }

    @Override
    public HashMap<String, Object> updateVehicule(VehiculeRequest vehiculeRequest,String idb_vehicule) throws VehiculeException {
        HashMap<String, Object> hashMap = new HashMap<>();
        ModelMapper modelMapper = new ModelMapper();
        VehiculeEntity vehiculeEntity = vehiculeRepo.findByBrowserId(idb_vehicule);
        if(vehiculeEntity==null)throw new VehiculeException("Cette Vehicule Exixt Pas");
        vehiculeEntity.setNomVehicule(vehiculeRequest.getNomVehicule());
        vehiculeEntity.setPlace(vehiculeRequest.getPlace());
        vehiculeRepo.save(vehiculeEntity);
        hashMap.put("msg","la modification est faite avec succes");
        hashMap.put("vehicule_details",modelMapper.map(vehiculeEntity,VehiculeDto.class));
        return hashMap;
    }

    @Override
    public List<PartnaireVehiculeDisplayDto> getByCategory(int page, int limit,String idbCategory) throws CategoryException {
        CategoryEntity categoryEntity = categorieRepo.findByIdbCategory(idbCategory);
        if(categoryEntity==null) throw new CategoryException("Cette Category Exixt Pas");
        HashMap<String , Object> hashMap = new HashMap<>();
        if (page>0) {
            page = page -1;
        }
        ModelMapper modelmapper = new ModelMapper();
        modelmapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        Pageable pagaebaleRequest = PageRequest.of(page, limit);
        Page<VehiculeEntity> vehiculeEntityPage = vehiculeRepo.findByCategoryVehicule(pagaebaleRequest,categoryEntity);
        List<VehiculeEntity> vehiculeEntityList =vehiculeEntityPage.getContent();
        List<PartnaireVehiculeDisplayDto>  partnaireVehiculeDisplayDtos = new ArrayList<>();
        for (VehiculeEntity vehicule:vehiculeEntityList){
            VehiculeDto vehiculeDto = modelmapper.map(vehicule,VehiculeDto.class);
            PartnaireVehiculeDisplayDto partnaireVehiculeDisplayDto = new PartnaireVehiculeDisplayDto();
            List<VehiculeImageEntity> vehiculeImageEntities = vehiculeImageRepo.findByVehicule(vehicule);
            List<VehiculeImageDto> vehiculeImageDtos= new ArrayList<>();
            for (VehiculeImageEntity vehiculeImageEntity : vehiculeImageEntities){
                VehiculeImageDto vehiculeImageDto = modelmapper.map(vehiculeImageEntity,VehiculeImageDto.class);
                vehiculeImageDtos.add(vehiculeImageDto);
            }
            partnaireVehiculeDisplayDto.setImg(vehiculeImageDtos);
            partnaireVehiculeDisplayDto.setVehicule(vehiculeDto);
            partnaireVehiculeDisplayDtos.add(partnaireVehiculeDisplayDto);
        }

        return partnaireVehiculeDisplayDtos;
    }

    @Override
    public HashMap<String, Object> getByLowPrice() {
        HashMap<String , Object> hashMap = new HashMap<>();
        ModelMapper modelMapper = new ModelMapper();
        ModelMapper modelmapper = new ModelMapper();
        modelmapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        Pageable pagaebaleRequest = PageRequest.of(0,2, Sort.by("pn").ascending());
        Page<VehiculeEntity> vehiculeEntityPage = vehiculeRepo.findAll(pagaebaleRequest);
        List<VehiculeEntity> vehiculeEntityList = vehiculeEntityPage.getContent();
        List<PartnaireVehiculeDisplayDto>  partnaireVehiculeDisplayDtos = new ArrayList<>();
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
        hashMap.put("msg","request has been proccesed succes");
        hashMap.put("payload",partnaireVehiculeDisplayDtos);
        return hashMap;
    }

    @Override
    public HashMap<String, Object> getVehicleAndSimiliarVehiculeByIdbVehicule(String idb_vehicule) throws VehiculeException {
        HashMap<String, Object> hashMap = new HashMap<>();
        ModelMapper modelMapper = new ModelMapper();
        VehiculeEntity vehiculeEntity = vehiculeRepo.findByBrowserId(idb_vehicule);
        if(vehiculeEntity==null)throw new VehiculeException("Cette Vehicule Exixt Pas");
        List<VehiculeImageEntity> vehiculeImageEntities = vehiculeImageRepo.findByVehicule(vehiculeEntity);
        PartnaireVehiculeDisplayDto partnaireVehiculeDisplayDto = new PartnaireVehiculeDisplayDto();
        List<VehiculeImageDto> vehiculeImageDtos= new ArrayList<>();
        for (VehiculeImageEntity vehiculeImageEntity : vehiculeImageEntities){
            VehiculeImageDto vehiculeImageDto = modelMapper.map(vehiculeImageEntity,VehiculeImageDto.class);
            vehiculeImageDtos.add(vehiculeImageDto);
        }
        partnaireVehiculeDisplayDto.setImg(vehiculeImageDtos);
        partnaireVehiculeDisplayDto.setVehicule(modelMapper.map(vehiculeEntity,VehiculeDto.class));
        hashMap.put("main_vehicule",partnaireVehiculeDisplayDto);
        hashMap.put("similar_vehicule",getSimilarVehiculeExceptionDetails(vehiculeEntity.getBrowserId(),vehiculeEntity.getCategoryVehicule().getIdbCategory()));
        return hashMap;
    }

    @Override
    public HashMap<String, Object> getSimilarVehiculeExceptionDetails(String idb_vehicule, String idb_category) {
        CategoryEntity categoryEntity = categorieRepo.findByIdbCategory(idb_category);
        HashMap<String , Object> hashMap = new HashMap<>();
        ModelMapper modelmapper = new ModelMapper();
        VehiculeEntity vehiculeEntity = vehiculeRepo.findByBrowserId(idb_vehicule);
        modelmapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        Pageable pagaebaleRequest = PageRequest.of(0, 20);
        Page<VehiculeEntity> vehiculeEntityPage = vehiculeRepo.findByCategoryVehiculeExceptSelectedVehicule(pagaebaleRequest,categoryEntity.getId(),vehiculeEntity.getId());
        List<VehiculeEntity> vehiculeEntityList =vehiculeEntityPage.getContent();
        List<PartnaireVehiculeDisplayDto>  partnaireVehiculeDisplayDtos = new ArrayList<>();
        for (VehiculeEntity vehicule:vehiculeEntityList){
            VehiculeDto vehiculeDto = modelmapper.map(vehicule,VehiculeDto.class);
            PartnaireVehiculeDisplayDto partnaireVehiculeDisplayDto = new PartnaireVehiculeDisplayDto();
            List<VehiculeImageEntity> vehiculeImageEntities = vehiculeImageRepo.findByVehicule(vehicule);
            List<VehiculeImageDto> vehiculeImageDtos= new ArrayList<>();
            for (VehiculeImageEntity vehiculeImageEntity : vehiculeImageEntities){
                VehiculeImageDto vehiculeImageDto = modelmapper.map(vehiculeImageEntity,VehiculeImageDto.class);
                vehiculeImageDtos.add(vehiculeImageDto);
            }
            partnaireVehiculeDisplayDto.setImg(vehiculeImageDtos);
            partnaireVehiculeDisplayDto.setVehicule(vehiculeDto);
            partnaireVehiculeDisplayDtos.add(partnaireVehiculeDisplayDto);
        }
        hashMap.put("category",modelmapper.map(categoryEntity, CategoryDto.class));
        hashMap.put("payload",partnaireVehiculeDisplayDtos);
        return hashMap;
    }

    @Override
    public HashMap<String, Object> getVehiculeByCBetweenPminAndPmax(String idb_category, int page, int limit, float pnmin, float pnmax) throws CategoryException {
        if (page>0) {
            page = page -1;
        }
        CategoryEntity categoryEntity = categorieRepo.findByIdbCategory(idb_category);
        if(categoryEntity==null) throw new CategoryException("Cette Category Exixt Pas");
        HashMap<String , Object> hashMap = new HashMap<>();
        ModelMapper modelmapper = new ModelMapper();
        Pageable pagaebaleRequest = PageRequest.of(page, limit, Sort.by("pn").ascending());
        Page<VehiculeEntity> vehiculeEntityPage = vehiculeRepo.findByCategoryAndPricingBetweenPminAndPmax(pagaebaleRequest,pnmin,pnmax,categoryEntity.getId());
        List<VehiculeEntity> vehiculeEntityList =vehiculeEntityPage.getContent();
        List<PartnaireVehiculeDisplayDto>  partnaireVehiculeDisplayDtos = new ArrayList<>();
        for (VehiculeEntity vehicule:vehiculeEntityList){
            VehiculeDto vehiculeDto = modelmapper.map(vehicule,VehiculeDto.class);
            PartnaireVehiculeDisplayDto partnaireVehiculeDisplayDto = new PartnaireVehiculeDisplayDto();
            List<VehiculeImageEntity> vehiculeImageEntities = vehiculeImageRepo.findByVehicule(vehicule);
            List<VehiculeImageDto> vehiculeImageDtos= new ArrayList<>();
            for (VehiculeImageEntity vehiculeImageEntity : vehiculeImageEntities){
                VehiculeImageDto vehiculeImageDto = modelmapper.map(vehiculeImageEntity,VehiculeImageDto.class);
                vehiculeImageDtos.add(vehiculeImageDto);
            }
            partnaireVehiculeDisplayDto.setImg(vehiculeImageDtos);
            partnaireVehiculeDisplayDto.setVehicule(vehiculeDto);
            partnaireVehiculeDisplayDtos.add(partnaireVehiculeDisplayDto);
        }
        hashMap.put("category",modelmapper.map(categoryEntity, CategoryDto.class));
        hashMap.put("payload",partnaireVehiculeDisplayDtos);
        return hashMap;

    }

    @Override
    public HashMap<String, Object> getVehiculeByJwt(Principal authentication, int page, int limit) throws UsernameNotExist {
        ModelMapper modelMapper = new ModelMapper();
        HashMap<String , Object> hashMap = new HashMap<>();
        if(page>0){
            page-=page;
        }
        UsersAccount account = usersAccountRepository.findByUsername(authentication.getName());
        if (account==null) throw  new UsernameNotExist("Ce Utilisateur Exixt Pas");
        PartenaireEntity getPartenaire = partenaireRepo.findByUsersAccount(account);
        Pageable pagaebaleRequest = PageRequest.of(page, limit);
        Page<VehiculeEntity> vehiculeEntityPage = vehiculeRepo.findByPartenaire(getPartenaire,pagaebaleRequest);
        List<VehiculeEntity> vehiculeEntityList =vehiculeEntityPage.getContent();
        List<PartnaireVehiculeDisplayDto>  partnaireVehiculeDisplayDtos = new ArrayList<>();
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
        hashMap.put("payload",partnaireVehiculeDisplayDtos);
        return hashMap;
    }

    @Override
    public HashMap<String, Object> filterAction(int page, int limit, Float pnmin, Float pnmax, String idb_brand, String idb_category) throws BrandException, CategoryException {

        ModelMapper modelMapper = new ModelMapper();
        HashMap<String , Object> hashMap = new HashMap<>();
        if (page>0) {
            page -= page;
        }

        Pageable pagaebaleRequest = PageRequest.of(page, limit, Sort.by("pn").ascending());
        if(((pnmin)==null)&&((pnmax)==null)&&(idb_category==null))
        {
            BrandEntity brand = brandRepo.findByIdbBrand(idb_brand);
            if(brand==null) throw  new BrandException("Cette Brand Exixt Pas");
            Page<VehiculeEntity> vehiculeEntityPage = vehiculeRepo.findByBrandVehicule(brand,pagaebaleRequest);
            List<VehiculeEntity> vehiculeEntityList =vehiculeEntityPage.getContent();
            List<PartnaireVehiculeDisplayDto>  partnaireVehiculeDisplayDtos = new ArrayList<>();
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
            hashMap.put("payload",partnaireVehiculeDisplayDtos);
        }
        else if((idb_brand==null) && (idb_category==null)){
            Page<VehiculeEntity> vehiculeEntityPage = vehiculeRepo.findByPricingBetweenPminAndPmax(pagaebaleRequest,pnmin,pnmax);
            List<VehiculeEntity> vehiculeEntityList =vehiculeEntityPage.getContent();
            List<PartnaireVehiculeDisplayDto>  partnaireVehiculeDisplayDtos = new ArrayList<>();
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
            hashMap.put("payload",partnaireVehiculeDisplayDtos);
        }

        else if (idb_brand==null){
            return getVehiculeByCBetweenPminAndPmax(idb_category,page,limit,pnmin,pnmax);
        }
        else if (idb_category==null){
            BrandEntity brand = brandRepo.findByIdbBrand(idb_brand);
            if(brand==null) throw  new BrandException("Cette Brand Exixt Pas");
            Page<VehiculeEntity> vehiculeEntityPage = vehiculeRepo.findByBrandVehiculeAndPricingBetwen(pagaebaleRequest,brand.getId(),pnmin,pnmax);
            List<VehiculeEntity> vehiculeEntityList =vehiculeEntityPage.getContent();
            List<PartnaireVehiculeDisplayDto>  partnaireVehiculeDisplayDtos = new ArrayList<>();
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
            hashMap.put("payload",partnaireVehiculeDisplayDtos);
        }
        else if(((pnmin)==null)&&((pnmax)==null)){
            BrandEntity brand = brandRepo.findByIdbBrand(idb_brand);
            if(brand==null) throw  new BrandException("Cette Brand Exixt Pas");
            CategoryEntity categoryEntity = categorieRepo.findByIdbCategory(idb_category);
            if(categoryEntity==null) throw new CategoryException("Cette Category Exixt Pas");
            Page<VehiculeEntity> vehiculeEntityPage = vehiculeRepo.findByBrandVehiculeAndCategoryVehicule(pagaebaleRequest,brand,categoryEntity);
            List<VehiculeEntity> vehiculeEntityList =vehiculeEntityPage.getContent();
            List<PartnaireVehiculeDisplayDto>  partnaireVehiculeDisplayDtos = new ArrayList<>();
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
            hashMap.put("payload",partnaireVehiculeDisplayDtos);
        }
        else {
            BrandEntity brand = brandRepo.findByIdbBrand(idb_brand);
            if(brand==null) throw  new BrandException("Cette Brand Exixt Pas");
            CategoryEntity categoryEntity = categorieRepo.findByIdbCategory(idb_category);
            if(categoryEntity==null) throw new CategoryException("Cette Category Exixt Pas");
            Page<VehiculeEntity> vehiculeEntityPage = vehiculeRepo.findByBrandVehiculeAndCAndCategoryVehiculeAndFiltrerPrincing(pagaebaleRequest,brand.getId(),categoryEntity.getId(),pnmin,pnmax);
            List<VehiculeEntity> vehiculeEntityList =vehiculeEntityPage.getContent();
            List<PartnaireVehiculeDisplayDto>  partnaireVehiculeDisplayDtos = new ArrayList<>();
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
            hashMap.put("payload",partnaireVehiculeDisplayDtos);
        }
        return hashMap;
    }

    @Override
    public HashMap<String, Object> filterActionByJwt(int page, int limit, Float pnmin, Float pnmax, String idb_brand, String idb_category, Principal authentication) throws BrandException, CategoryException, UsernameNotExist {
        ModelMapper modelMapper = new ModelMapper();
        HashMap<String , Object> hashMap = new HashMap<>();
        if (page>0) {
            page -= page;
        }
        UsersAccount account = usersAccountRepository.findByUsername(authentication.getName());
        if (account==null) throw  new UsernameNotExist("Ce Utilisateur Exixt Pas");
        PartenaireEntity getPartenaire = partenaireRepo.findByUsersAccount(account);

        Pageable pagaebaleRequest = PageRequest.of(page, limit, Sort.by("pn").ascending());
        if(((pnmin)==null)&&((pnmax)==null)&&(idb_category==null))
        {
            BrandEntity brand = brandRepo.findByIdbBrand(idb_brand);
            if(brand==null) throw  new BrandException("Cette Brand Exixt Pas");
            Page<VehiculeEntity> vehiculeEntityPage = vehiculeRepo.findByBrandVehiculeAndPartenaire(brand,getPartenaire,pagaebaleRequest);
            List<VehiculeEntity> vehiculeEntityList =vehiculeEntityPage.getContent();
            List<PartnaireVehiculeDisplayDto>  partnaireVehiculeDisplayDtos = new ArrayList<>();
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
            hashMap.put("payload",partnaireVehiculeDisplayDtos);
        }
        else if((idb_brand==null) && (idb_category==null)){
            Page<VehiculeEntity> vehiculeEntityPage = vehiculeRepo.findByPricingBetweenPminAndPmaxAndPartenaire(pagaebaleRequest,pnmin,pnmax,getPartenaire.getId());
            List<VehiculeEntity> vehiculeEntityList =vehiculeEntityPage.getContent();
            List<PartnaireVehiculeDisplayDto>  partnaireVehiculeDisplayDtos = new ArrayList<>();
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
            hashMap.put("payload",partnaireVehiculeDisplayDtos);
        }
        else if ((pnmin==null)&&(pnmax==null)&&(idb_brand==null)){
            hashMap.put("payload",getByCategoryAndJwt(page,limit,idb_category,authentication));
        }
        else if (idb_brand==null){
            return getVehiculeByCBetweenPminAndPmax(idb_category,page,limit,pnmin,pnmax);
        }
        else if (idb_category==null){
            BrandEntity brand = brandRepo.findByIdbBrand(idb_brand);
            if(brand==null) throw  new BrandException("Cette Brand Exixt Pas");
            Page<VehiculeEntity> vehiculeEntityPage = vehiculeRepo.findByBrandVehiculeAndPricingBetwenAndPartenaire(pagaebaleRequest,brand.getId(),pnmin,pnmax,getPartenaire.getId());
            List<VehiculeEntity> vehiculeEntityList =vehiculeEntityPage.getContent();
            List<PartnaireVehiculeDisplayDto>  partnaireVehiculeDisplayDtos = new ArrayList<>();
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
            hashMap.put("payload",partnaireVehiculeDisplayDtos);
        }
        else if(((pnmin)==null)&&((pnmax)==null)){
            BrandEntity brand = brandRepo.findByIdbBrand(idb_brand);
            if(brand==null) throw  new BrandException("Cette Brand Exixt Pas");
            CategoryEntity categoryEntity = categorieRepo.findByIdbCategory(idb_category);
            if(categoryEntity==null) throw new CategoryException("Cette Category Exixt Pas");
            Page<VehiculeEntity> vehiculeEntityPage = vehiculeRepo.findByBrandVehiculeAndCategoryVehiculeAndPartenaire(pagaebaleRequest,brand,categoryEntity,getPartenaire);
            List<VehiculeEntity> vehiculeEntityList =vehiculeEntityPage.getContent();
            List<PartnaireVehiculeDisplayDto>  partnaireVehiculeDisplayDtos = new ArrayList<>();
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
            hashMap.put("payload",partnaireVehiculeDisplayDtos);
        }
        else {
            BrandEntity brand = brandRepo.findByIdbBrand(idb_brand);
            if(brand==null) throw  new BrandException("Cette Brand Exixt Pas");
            CategoryEntity categoryEntity = categorieRepo.findByIdbCategory(idb_category);
            if(categoryEntity==null) throw new CategoryException("Cette Category Exixt Pas");
            Page<VehiculeEntity> vehiculeEntityPage = vehiculeRepo.findByBrandVehiculeAndCAndCategoryVehiculeAndFiltrerPrincingAndPartenaire(pagaebaleRequest,brand.getId(),categoryEntity.getId(),pnmin,pnmax,getPartenaire.getId());
            List<VehiculeEntity> vehiculeEntityList =vehiculeEntityPage.getContent();
            List<PartnaireVehiculeDisplayDto>  partnaireVehiculeDisplayDtos = new ArrayList<>();
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
            hashMap.put("payload",partnaireVehiculeDisplayDtos);
        }
        return hashMap;
    }

    @Override
    public List<PartnaireVehiculeDisplayDto> getByCategoryAndJwt(int page, int limit, String idbCategory, Principal authentication) throws CategoryException, UsernameNotExist {
        CategoryEntity categoryEntity = categorieRepo.findByIdbCategory(idbCategory);
        if(categoryEntity==null) throw new CategoryException("Cette Category Exixt Pas");
        UsersAccount account = usersAccountRepository.findByUsername(authentication.getName());
        if (account==null) throw  new UsernameNotExist("Ce Utilisateur Exixt Pas");
        PartenaireEntity getPartenaire = partenaireRepo.findByUsersAccount(account);

        HashMap<String , Object> hashMap = new HashMap<>();
        if (page>0) {
            page = page -1;
        }
        ModelMapper modelmapper = new ModelMapper();
        modelmapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        Pageable pagaebaleRequest = PageRequest.of(page, limit);
        Page<VehiculeEntity> vehiculeEntityPage = vehiculeRepo.findByCategoryVehiculeAndPartenaire(pagaebaleRequest,categoryEntity,getPartenaire);
        List<VehiculeEntity> vehiculeEntityList =vehiculeEntityPage.getContent();
        List<PartnaireVehiculeDisplayDto>  partnaireVehiculeDisplayDtos = new ArrayList<>();
        for (VehiculeEntity vehicule:vehiculeEntityList){
            VehiculeDto vehiculeDto = modelmapper.map(vehicule,VehiculeDto.class);
            PartnaireVehiculeDisplayDto partnaireVehiculeDisplayDto = new PartnaireVehiculeDisplayDto();
            List<VehiculeImageEntity> vehiculeImageEntities = vehiculeImageRepo.findByVehicule(vehicule);
            List<VehiculeImageDto> vehiculeImageDtos= new ArrayList<>();
            for (VehiculeImageEntity vehiculeImageEntity : vehiculeImageEntities){
                VehiculeImageDto vehiculeImageDto = modelmapper.map(vehiculeImageEntity,VehiculeImageDto.class);
                vehiculeImageDtos.add(vehiculeImageDto);
            }
            partnaireVehiculeDisplayDto.setImg(vehiculeImageDtos);
            partnaireVehiculeDisplayDto.setVehicule(vehiculeDto);
            partnaireVehiculeDisplayDtos.add(partnaireVehiculeDisplayDto);
        }
        return partnaireVehiculeDisplayDtos;
    }
}
