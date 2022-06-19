package com.springsecurity.ws.Controller;

import com.springsecurity.ws.Exception.CategoryException;
import com.springsecurity.ws.Response.CategoryResponse;
import com.springsecurity.ws.Utility.Dto.CategoryDto;
import com.springsecurity.ws.Service.CategorieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.springsecurity.ws.UserRequest.CategoryRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category") // localhost:8084/api/category
@RequiredArgsConstructor
public class CategoryControllers {

    private final CategorieService categorieService;
    @PostMapping(path = "/add_categorie")
    public ResponseEntity<CategoryResponse> createArticle(@RequestBody @Valid CategoryRequest categoryRequest , Principal authentication) throws CategoryException {
        if (categoryRequest.getNomCategory().isEmpty())
            throw new CategoryException("Vous Avez Raté un Champ obligatoire");
        ModelMapper modelMapper = new ModelMapper();
        CategoryDto categoryDto = modelMapper.map(categoryRequest,CategoryDto.class);
        CategoryDto addToDb = categorieService.save(categoryDto);
        CategoryResponse categoryResponse =modelMapper.map(addToDb, CategoryResponse.class);
        return new ResponseEntity<CategoryResponse>(categoryResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllArticle() {
        ModelMapper modelMapper= new ModelMapper();
        List<CategoryResponse> allCategorie = new ArrayList<>();
        List<CategoryDto> allArticleFromStock = categorieService.findAll();
        for (CategoryDto categoryDto : allArticleFromStock) {
            CategoryResponse categoryResponse =modelMapper.map(categoryDto, CategoryResponse.class);
            allCategorie.add(categoryResponse);
        }
        return new ResponseEntity<List<CategoryResponse>>(allCategorie,HttpStatus.OK);
    }

    @PutMapping(path = "/update_categories/{categorieId}")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody @Valid CategoryRequest categoryRequest , @PathVariable String categorieId) {
        ModelMapper modelMapper = new ModelMapper();
        CategoryDto categoryDto = categorieService.updateCategory(categorieId, categoryRequest);
        CategoryResponse categoryResponse = modelMapper.map(categoryDto, CategoryResponse.class);
        return new ResponseEntity<CategoryResponse>(categoryResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/delete_category/{clientId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable String clientId) {
        categorieService.deletedArticle(clientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
