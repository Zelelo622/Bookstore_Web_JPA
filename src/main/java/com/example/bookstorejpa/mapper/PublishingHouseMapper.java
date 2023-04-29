package com.example.bookstorejpa.mapper;
import com.example.bookstorejpa.dto.PublishingHouseDto;
import com.example.bookstorejpa.entity.PublishingHouse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublishingHouseMapper {
    PublishingHouseDto toDto(PublishingHouse entity);
    PublishingHouse toEntity(PublishingHouseDto dto);
}
