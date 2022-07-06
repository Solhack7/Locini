package com.springsecurity.ws.Utility;


import com.springsecurity.ws.Entity.VehiculeEntity;
import com.springsecurity.ws.Entity.VehiculeImageEntity;
import com.springsecurity.ws.Repository.VehiculeImageRepo;
import com.springsecurity.ws.Utility.Dto.PartnaireVehiculeDisplayDto;
import com.springsecurity.ws.Utility.Dto.VehiculeDto;
import com.springsecurity.ws.Utility.Dto.VehiculeImageDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class Utils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHANUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final VehiculeImageRepo vehiculeImageRepo;

    public Utils(VehiculeImageRepo vehiculeImageRepo) {
        this.vehiculeImageRepo = vehiculeImageRepo;
    }


    public String generateStringId(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHANUM.charAt(RANDOM.nextInt(ALPHANUM.length())));
        }
        return new String(returnValue);
    }
    public String ConvertDateToStr(Date date) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(date);
        return  strDate;
    }
    public int LoopForAutoSize(int legnth) {
        int helpers=0;
        for (int i = 0; i < legnth; i++) {
            helpers=i;
        }
        return helpers;
    }
    public Date getDateNow() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        SimpleDateFormat formatter6=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        Date date = new Date();
        Date dateHelpers = formatter6.parse(formatter.format(date));
        return dateHelpers;
    }

    public List<PartnaireVehiculeDisplayDto> getVehiculeAndImage(List<VehiculeEntity> vehiculeEntityList){
        ModelMapper modelMapper = new ModelMapper();
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
        return partnaireVehiculeDisplayDtos;
    }
}
