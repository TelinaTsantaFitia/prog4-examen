package com.example.demo.service;

import com.example.demo.dto.BookCopyResponseDto;
import com.example.demo.entity.BookCopy;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BookCopyRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookCopyService {

  private final BookCopyRepository bookCopyRepository;

  @Transactional(readOnly = true)
  public List<BookCopyResponseDto> getAllBookCopies() {
    return bookCopyRepository.findAll().stream().map(this::toDto).toList();
  }

  @Transactional(readOnly = true)
  public BookCopyResponseDto getBookCopyById(UUID id) {
    return bookCopyRepository
        .findById(id)
        .map(this::toDto)
        .orElseThrow(() -> new ResourceNotFoundException("BookCopy", id));
  }

  private BookCopyResponseDto toDto(BookCopy entity) {
    return new BookCopyResponseDto(
        entity.getId(),
        entity.getIsbn(),
        entity.getPurchasePrice(),
        entity.getSellingPrice(),
        entity.getCurrentStock(),
        entity.getFormatType(),
        entity.getBook().getId(),
        entity.getBook().getTitle());
  }
}
