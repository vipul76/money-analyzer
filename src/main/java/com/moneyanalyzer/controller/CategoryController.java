package com.moneyanalyzer.controller;

import com.moneyanalyzer.dto.category.CategoryDto;
import com.moneyanalyzer.dto.category.CategoryRequestDto;
import com.moneyanalyzer.dto.category.CategoryResponseDto;
import com.moneyanalyzer.entity.User;
import com.moneyanalyzer.service.CategoryService;
import com.moneyanalyzer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    Method	Endpoint	Description
    GET	/api/categories	List all categories (global + user)
    GET	/api/categories/{id}	Get category by ID
    POST	/api/categories	Create new category
    PUT	/api/categories/{id}	Update category
    DELETE	/api/categories/{id}	Delete category
*/
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(@AuthenticationPrincipal UserDetails userDetails){

        User user = userService.findByUsername(userDetails.getUsername());
        List<CategoryDto> categoryDto = categoryService.getCategoryForUser(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<CategoryDto>> getCategoryById(@PathVariable("id") long id){
        List<CategoryDto> category = categoryService.getCategoryById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(category);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequestDto category){
        com.moneyanalyzer.dto.category.CategoryResponseDto categoryResponseDto = categoryService.createCategory(category);
        return categoryResponseDto!=null? new ResponseEntity<>(categoryResponseDto,HttpStatus.CREATED):
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
