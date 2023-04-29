package com.example.bookstorejpa.service;

import com.example.bookstorejpa.dto.OrderDto;
import com.example.bookstorejpa.entity.Book;
import com.example.bookstorejpa.entity.Buyer;
import com.example.bookstorejpa.entity.Order;
import com.example.bookstorejpa.mapper.OrderMapper;
import com.example.bookstorejpa.repository.BookRepository;
import com.example.bookstorejpa.repository.BuyerRepository;
import com.example.bookstorejpa.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final BuyerRepository buyerRepository;
    private final BookRepository bookRepository;
    private final OrderMapper mapper;

    public OrderService(OrderRepository orderRepository, BuyerRepository buyerRepository, BookRepository bookRepository, OrderMapper mapper) {
        this.orderRepository = orderRepository;
        this.buyerRepository = buyerRepository;
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public Optional<OrderDto> getOrderById(Integer id) {
        return orderRepository.findById(id).map(mapper::toDto);
    }

    public void createOrder(OrderDto orderDto) {
        Order order = mapper.toEntity(orderDto);
        Buyer buyer = buyerRepository.findById(orderDto.getBuyerId()).orElseThrow(() -> new EntityNotFoundException("Покупатель не найден с id " + orderDto.getBuyerId()));
        order.setBuyer(buyer);
        List<Book> books = bookRepository.findAllById(orderDto.getBookIds());
        order.setBooks(books);
        orderRepository.save(order);
    }
}
