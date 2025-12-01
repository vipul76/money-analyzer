package com.moneyanalyzer.serviceImpl;

import com.moneyanalyzer.dto.UserSummaryDto;
import com.moneyanalyzer.dto.category.CategoryDto;
import com.moneyanalyzer.dto.category.CategoryRequestDto;
import com.moneyanalyzer.dto.category.CategoryResponseDto;
import com.moneyanalyzer.entity.User;
import com.moneyanalyzer.entity.Category;
import com.moneyanalyzer.exception.UserNotFoundException;
import com.moneyanalyzer.repository.CategoryRepository;
import com.moneyanalyzer.repository.UserRepository;
import com.moneyanalyzer.service.CategoryService;
import lombok.RequiredArgsConstructor;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    public final CategoryRepository categoryRepository;
    public final UserRepository userRepository;


    @Override
    public List<Category> findByCategory(User user) {

        return categoryRepository.findByUserOrUserIsNull(user);
    }

    @Override
    public List<CategoryDto> getCategoryForUser(User user) {
        List<Category> categories = categoryRepository.findByUserOrUserIsNull(user);
        return categories
                .stream()
                .map(category ->
                    CategoryDto
                            .builder()
                            .id(category.getId())
                            .name(category.getName())
                            .icon(category.getIcon())
                            .type(category.getType())
                            .transactions(category.getTransactions())
                            .userSummaryDto(category.getUser()==null?null:
                                    UserSummaryDto
                                            .builder()
                                            .id(category.getUser().getId())
                                            .name(category.getUser().getName())
                                            .email(category.getUser().getEmail())
                                            .role(category.getUser().getRoles().toString())
                                            .build())
                            .build()
                )
                .toList();
    }

    @Override
    public List<CategoryDto> getCategoryById(long id) {
        return categoryRepository.findById(id)
                .stream()
                .map(c ->
                        CategoryDto
                        .builder()
                        .id(c.getId())
                        .name(c.getName())
                        .userSummaryDto(c.getUser()==null?null:
                                UserSummaryDto
                                        .builder()
                                        .id(c.getUser().getId())
                                        .email(c.getUser().getEmail())
                                        .name(c.getUser().getName())
                                        .role(c.getUser().getRoles().toString())
                                        .build())
                        .type(c.getType())
                        .icon(c.getIcon())
                        .build())
                .toList();
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        User user = userRepository.findById(categoryRequestDto.getUserId())
                .orElseThrow(()-> new UserNotFoundException("user not found with id : "+categoryRequestDto.getUserId()));

        Category categoryByName = categoryRepository.findByNameAndUserId(categoryRequestDto.getName(),user.getId());

        if(categoryByName!=null) {
            return mapCategoryToDto(categoryByName);
        }

        Category category = mapDtoToCategory(categoryRequestDto,user);
        Category categoryResponse = categoryRepository.saveAndFlush(category);
        return mapCategoryToDto(categoryResponse);
    }

    private Category mapDtoToCategory(CategoryRequestDto categoryRequestDto, User user) {
        Category category = new Category();
        category.setName(categoryRequestDto.getName());
        category.setIcon(categoryRequestDto.getIcon());
        category.setType(categoryRequestDto.getType());
        category.setUser(user);
        return category;
    }

    private CategoryResponseDto mapCategoryToDto(Category categoryResponse) {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setId(categoryResponse.getId());
        categoryResponseDto.setName(categoryResponse.getName());
        categoryResponseDto.setIcon(categoryResponse.getIcon());
        categoryResponseDto.setType(categoryResponse.getType());
        return categoryResponseDto;
    }
}
