package com.springsecurity.ws.Repository;

import com.springsecurity.ws.Entity.PartenaireEntity;
import com.springsecurity.ws.Entity.UsersAccount;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartenaireRepo extends PagingAndSortingRepository<PartenaireEntity,Long> {
    PartenaireEntity findByBrowserId(String idBrowser);
    PartenaireEntity findByUsersAccount(UsersAccount usersAccount);
}
