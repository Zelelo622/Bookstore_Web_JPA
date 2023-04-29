package com.example.bookstorejpa.mapper;

import com.example.bookstorejpa.dto.CategoryDto;
import com.example.bookstorejpa.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category entity);
    Category toEntity(CategoryDto dto);
}
