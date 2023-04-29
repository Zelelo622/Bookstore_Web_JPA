package com.example.bookstorejpa.mapper;
import com.example.bookstorejpa.dto.OrderDto;
import com.example.bookstorejpa.entity.Book;
import com.example.bookstorejpa.entity.Buyer;
import com.example.bookstorejpa.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "buyerId", expression = "java(buyerIdFromBuyer(order.getBuyer()))")
    @Mapping(target = "bookIds", expression = "java(bookIdsFromBooks(order.getBooks()))")
    OrderDto toDto(Order order);
    Order toEntity(OrderDto dto);

    default Integer buyerIdFromBuyer(Buyer buyer) {
        return buyer == null ? null : buyer.getBuyerId();
    }

    default List<Integer> bookIdsFromBooks(List<Book> books) {
        return books == null ? null : books.stream().map(Book::getBookId).collect(Collectors.toList());
    }
}
