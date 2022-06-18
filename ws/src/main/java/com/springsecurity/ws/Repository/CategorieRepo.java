package com.springsecurity.ws.Repository;

import com.springsecurity.ws.Entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepo extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity findCategoryById(Long id);
    CategoryEntity findCategoryByNomCategory(String name);
    CategoryEntity findByIdbCategory(String idCategoryClient);
    boolean existsByNomCategory(String name);
}
