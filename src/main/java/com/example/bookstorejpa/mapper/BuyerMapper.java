package com.example.bookstorejpa.mapper;

import com.example.bookstorejpa.dto.BuyerDto;
import com.example.bookstorejpa.entity.Buyer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BuyerMapper {
    BuyerDto toDto(Buyer entity);

    Buyer toEntity(BuyerDto dto);
}
