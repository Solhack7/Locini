package com.springsecurity.ws.ServiceImpl;

import com.springsecurity.ws.Entity.CategoryEntity;
import com.springsecurity.ws.Repository.CategorieRepo;
import com.springsecurity.ws.Service.CategorieService;
import com.springsecurity.ws.UserRequest.CategoryRequest;
import com.springsecurity.ws.Utility.Dto.CategoryDto;
import com.springsecurity.ws.Utility.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class CategoryServiceImpl implements CategorieService {

    private final CategorieRepo categoryRepository;
    private  final Utils utils;


    @Override
    public CategoryDto save(CategoryDto category) {

        ModelMapper modelMapper = new ModelMapper();
        CategoryEntity categorieEntity = new CategoryEntity();

        if(categoryRepository.existsByNomCategory(category.getNomCategory())){
            throw new RuntimeException("Categorie Exixt");
        }
        else{
            categorieEntity.setIdbCategory(utils.generateStringId(20));
            categorieEntity.setNomCategory(category.getNomCategory());
            categoryRepository.save(categorieEntity);
        }
        CategoryDto categoryDto = modelMapper.map(categorieEntity,CategoryDto.class);
        return categoryDto;
    }
    @Override
    public List<CategoryDto> findAll(){
        List<CategoryEntity> categorieEntities =  categoryRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (CategoryEntity categorieEntity:categorieEntities) {
            CategoryDto categoryDto = modelMapper.map(categorieEntity,CategoryDto.class);
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }
    @Override
    public CategoryEntity findCategoryByName(String name){
        return categoryRepository.findCategoryByNomCategory(name);
    }
    @Override
    public CategoryEntity findCategoryById(Long id){
        return categoryRepository.findCategoryById(id);
    }
    @Override
    public List<CategoryEntity> getAllCategories(){
        return categoryRepository.findAll();
    }
    @Override
    public Boolean existsByNomCategory(String name) {
        return categoryRepository.existsByNomCategory(name);
    }
    @Override
    public void removeCategory(CategoryEntity category){
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDto updateCategory(String categorieId, CategoryRequest categoryRequest) {
        ModelMapper modelMapper = new ModelMapper();
        CategoryEntity categorieEntity = categoryRepository.findByIdbCategory(categorieId);
        if(categoryRequest.getNomCategory().isEmpty())throw  new RuntimeException("LE CHAMP EST VIDE");
        else
        {
            categorieEntity.setNomCategory(categoryRequest.getNomCategory());
            categoryRepository.save(categorieEntity);

        }
        CategoryDto categoryDto = modelMapper.map(categorieEntity, CategoryDto.class);
        return categoryDto;
    }

    @Override
    public void deletedArticle(String clientId) {
        CategoryEntity categorieEntity = categoryRepository.findByIdbCategory(clientId);
        categoryRepository.delete(categorieEntity);
    }
}
