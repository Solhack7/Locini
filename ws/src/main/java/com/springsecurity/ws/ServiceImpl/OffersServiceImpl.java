package com.springsecurity.ws.ServiceImpl;

import com.springsecurity.ws.Entity.OffersEntity;
import com.springsecurity.ws.Entity.UsersAccount;
import com.springsecurity.ws.Exception.UsernameNotExist;
import com.springsecurity.ws.Repository.UsersAccountRepository;
import com.springsecurity.ws.Service.OffersService;
import com.springsecurity.ws.Service.UserService;
import com.springsecurity.ws.UserRequest.OffersRequest;
import com.springsecurity.ws.Utility.Dto.OffersDto;
import com.springsecurity.ws.Utility.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class OffersServiceImpl implements OffersService {
    private Utils utils;
    private UserService userService;
    private UsersAccountRepository usersAccountRepository;
    private final Random RANDOM = new SecureRandom();
    private final String ALPHANUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    @Override
    public HashMap<String, Object> addOffer(OffersRequest offersRequest, String userName) throws UsernameNotExist {
        ModelMapper modelMapper = new ModelMapper();
        HashMap<String,Object> hashMap = new HashMap<>();
        OffersEntity offersEntity = modelMapper.map(offersRequest,OffersEntity.class);
        offersEntity.setBrowserId(utils.generateStringId(15));
        UsersAccount userAccount = usersAccountRepository.findByUsername(userName);
        if(userAccount==null)throw new UsernameNotExist("Ce Utilisateur N'Exixt Pas ");
        offersEntity.setUsersAccount(userAccount);
        OffersDto offersDto = modelMapper.map(offersEntity,OffersDto.class);
        hashMap.put("msg","Offre Ajouté Avec Succes");
        hashMap.put("offer_details",offersDto);
        return hashMap;
    }

    @Override
    public String generateStringId(int length) {
            StringBuilder returnValue = new StringBuilder(length);

            for (int i = 0; i < length; i++) {
                returnValue.append(ALPHANUM.charAt(RANDOM.nextInt(ALPHANUM.length())));
            }
            return new String(returnValue);

    }
}
