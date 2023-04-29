package com.example.bookstorejpa.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    private Integer totalPrice;
    private Integer buyerId;
    private List<Integer> bookIds;
}

