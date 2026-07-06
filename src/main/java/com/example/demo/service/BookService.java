package com.example.demo.service;

import com.example.demo.dto.BookResponseDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.BookCopy;
import com.example.demo.entity.SaleBook;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.SaleRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final SaleRepository saleRepository;

  @Transactional(readOnly = true)
  public List<BookResponseDto> getAllBooks() {
    return bookRepository.findAll().stream().map(this::toDto).toList();
  }

  @Transactional(readOnly = true)
  public BookResponseDto getBookById(UUID id) {
    Book book =
        bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", id));
    return toDto(book);
  }

  @Transactional(readOnly = true)
  public List<BookResponseDto> getBooksSoldToday() {
    LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
    LocalDateTime endOfDay = LocalDate.now().plusDays(1).atStartOfDay();

    return new ArrayList<>(
            saleRepository.findBySaleDateBetween(startOfDay, endOfDay).stream()
                .flatMap(sale -> sale.getBooks().stream())
                .map(SaleBook::getBookCopy)
                .map(BookCopy::getBook)
                .collect(Collectors.toMap(Book::getId, b -> b, (a, b) -> a))
                .values())
        .stream().map(this::toDto).toList();
  }

  private BookResponseDto toDto(Book book) {
    return new BookResponseDto(
        book.getId(),
        book.getTitle(),
        book.getPublicationDate(),
        book.getCategory() != null ? book.getCategory().getCategoryEnum().name() : null,
        book.getAuthors() != null
            ? book.getAuthors().stream().map(a -> a.getFirstName() + " " + a.getLastName()).toList()
            : List.of());
  }
}
