package com.moneyanalyzer.service;

import com.moneyanalyzer.dto.category.CategoryDto;
import com.moneyanalyzer.dto.category.CategoryRequestDto;
import com.moneyanalyzer.dto.category.CategoryResponseDto;
import com.moneyanalyzer.entity.User;

import java.util.List;

public interface CategoryService {

    List<com.moneyanalyzer.entity.Category> findByCategory(User user);

    List<CategoryDto> getCategoryForUser(User user);

    List<CategoryDto> getCategoryById(long id);

    CategoryResponseDto createCategory(CategoryRequestDto category);
}
