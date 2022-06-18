package com.springsecurity.ws.Service;

import com.springsecurity.ws.Entity.CategoryEntity;
import com.springsecurity.ws.UserRequest.CategoryRequest;
import com.springsecurity.ws.Utility.Dto.CategoryDto;

import java.util.List;

public interface CategorieService {
    CategoryDto save(CategoryDto category);
    List<CategoryDto> findAll();
    CategoryEntity findCategoryByName(String name);
    CategoryEntity findCategoryById(Long id);
    List<CategoryEntity> getAllCategories();
    Boolean existsByNomCategory(String name);
    void removeCategory(CategoryEntity category);

    CategoryDto updateCategory(String categorieId, CategoryRequest categoryRequest);

    void deletedArticle(String clientId);
}
