package com.example.bookstorejpa.service;

import com.example.bookstorejpa.dto.CategoryDto;
import com.example.bookstorejpa.entity.Category;
import com.example.bookstorejpa.mapper.CategoryMapper;
import com.example.bookstorejpa.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryService(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CategoryDto> getAll() {
        List<Category> categories = repository.findAll();
        return categories.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public Optional<CategoryDto> getById(Integer id) {
        Optional<Category> categoryOptional = repository.findById(id);
        return categoryOptional.map(mapper::toDto);
    }

    public void addNewCategory(CategoryDto categoryDto) {
        Category category = mapper.toEntity(categoryDto);
        repository.save(category);
    }

    public void deleteCategory(Integer id) {
        Optional<Category> categoryOptional = repository.findById(id);
        if (categoryOptional.isPresent()) {
            repository.delete(categoryOptional.get());
        } else {
            throw new IllegalArgumentException("Категория с id " + id + " не найдена");
        }
    }

    public void updateCategory(CategoryDto categoryDto, Integer id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Категория с id " + id + " не найдена"));
        category.setName(categoryDto.getName());
        repository.save(category);
    }

}
