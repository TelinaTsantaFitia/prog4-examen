package com.telina.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.telina.demo.dto.BookCopyResponseDto;
import com.telina.demo.entity.Book;
import com.telina.demo.entity.BookCopy;
import com.telina.demo.entity.FormatType;
import com.telina.demo.exception.ResourceNotFoundException;
import com.telina.demo.repository.BookCopyRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookCopyServiceTest {

  @Mock private BookCopyRepository bookCopyRepository;

  private BookCopyService bookCopyService;

  @BeforeEach
  void setUp() {
    bookCopyService = new BookCopyService(bookCopyRepository);
  }

  private BookCopy sampleBookCopy() {
    Book book = Book.builder().id(UUID.randomUUID()).title("One Piece").build();
    return BookCopy.builder()
        .id(UUID.randomUUID())
        .book(book)
        .isbn("978-2-1234-5678-9")
        .purchasePrice(new BigDecimal("5.00"))
        .sellingPrice(new BigDecimal("9.90"))
        .currentStock(3)
        .formatType(FormatType.POCHE)
        .build();
  }

  @Test
  void getAllBookCopies_shouldReturnMappedDtos() {
    BookCopy bookCopy = sampleBookCopy();
    when(bookCopyRepository.findAll()).thenReturn(List.of(bookCopy));

    List<BookCopyResponseDto> result = bookCopyService.getAllBookCopies();

    assertThat(result).hasSize(1);
    BookCopyResponseDto dto = result.get(0);
    assertThat(dto.isbn()).isEqualTo(bookCopy.getIsbn());
    assertThat(dto.bookTitle()).isEqualTo("One Piece");
    assertThat(dto.formatType()).isEqualTo(FormatType.POCHE);
  }

  @Test
  void getBookCopyById_shouldReturnDto_whenFound() {
    BookCopy bookCopy = sampleBookCopy();
    when(bookCopyRepository.findById(bookCopy.getId())).thenReturn(Optional.of(bookCopy));

    BookCopyResponseDto result = bookCopyService.getBookCopyById(bookCopy.getId());

    assertThat(result.id()).isEqualTo(bookCopy.getId());
    assertThat(result.currentStock()).isEqualTo(3);
  }

  @Test
  void getBookCopyById_shouldThrow_whenNotFound() {
    UUID id = UUID.randomUUID();
    when(bookCopyRepository.findById(id)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> bookCopyService.getBookCopyById(id))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessageContaining(id.toString());
  }
}
