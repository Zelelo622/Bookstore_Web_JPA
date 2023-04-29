package com.example.bookstorejpa.service;

import com.example.bookstorejpa.dto.BookDto;
import com.example.bookstorejpa.entity.Book;
import com.example.bookstorejpa.entity.Category;
import com.example.bookstorejpa.entity.PublishingHouse;
import com.example.bookstorejpa.mapper.BookMapper;
import com.example.bookstorejpa.repository.BookRepository;
import com.example.bookstorejpa.repository.CategoryRepository;
import com.example.bookstorejpa.repository.PublishingHouseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository repository;
    private final CategoryRepository categoryRepository;
    private final PublishingHouseRepository publishingHouseRepository;
    private final BookMapper mapper;

    public BookService(BookRepository repository, CategoryRepository categoryRepository, PublishingHouseRepository publishingHouseRepository, BookMapper mapper) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.publishingHouseRepository = publishingHouseRepository;
        this.mapper = mapper;
    }

    public List<BookDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public Optional<BookDto> getById(Integer id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public void addNewBook(BookDto book) {
        Book newBook = mapper.toEntity(book);
        Category category = categoryRepository.findById(book.getCategory_id())
                .orElseThrow(() -> new EntityNotFoundException("Категория с id " + book.getCategory_id() + " не найдена"));
        PublishingHouse publishingHouse = publishingHouseRepository.findById(book.getPublishing_house_id())
                .orElseThrow(() -> new EntityNotFoundException("Издание с id " + book.getPublishing_house_id() + " не найдено"));
        newBook.setCategory(category);
        newBook.setPublishingHouse(publishingHouse);
        repository.save(newBook);
    }


    public void deleteBook(Integer id) {
        Optional<Book> bookOptional = repository.findById(id);
        if (bookOptional.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Книга с id " + id + " не найдена");
        }
    }

    public void updateBook(BookDto book, Integer id) {
        Book entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Книга с id " + id + " не найдена"));
        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        entity.setPrice(book.getPrice());
        entity.setCategory(categoryRepository.findById(book.getCategory_id())
                .orElseThrow(() -> new IllegalArgumentException("Категория не найдена")));
        entity.setPublishingHouse(publishingHouseRepository.findById(book.getPublishing_house_id())
                .orElseThrow(() -> new IllegalArgumentException("Издательство не найдено")));
        repository.save(entity);
    }
}
