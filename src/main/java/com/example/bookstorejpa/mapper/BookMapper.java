package com.example.bookstorejpa.mapper;

import com.example.bookstorejpa.dto.BookDto;
import com.example.bookstorejpa.entity.Book;
import com.example.bookstorejpa.entity.Category;
import com.example.bookstorejpa.entity.PublishingHouse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "category_id", expression = "java(categoryIdFromCategory(book.getCategory()))")
    @Mapping(target = "publishing_house_id", expression = "java(publishingHouseIdFromCategory(book.getPublishingHouse()))")
    BookDto toDto(Book book);
    Book toEntity(BookDto dto);

    default Integer categoryIdFromCategory(Category category) {
        return category == null ? null : category.getCategoryId();
    }

    default Integer publishingHouseIdFromCategory(PublishingHouse publishingHouse) {
        return publishingHouse == null ? null : publishingHouse.getPublishingHouseId();
    }
}
