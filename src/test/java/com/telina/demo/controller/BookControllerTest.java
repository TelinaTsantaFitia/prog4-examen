package com.telina.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.telina.demo.dto.BookResponseDto;
import com.telina.demo.exception.ResourceNotFoundException;
import com.telina.demo.service.BookService;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

  @Mock private BookService bookService;

  private BookController bookController;

  @BeforeEach
  void setUp() {
    bookController = new BookController(bookService);
  }

  private BookResponseDto sampleDto() {
    return new BookResponseDto(
        UUID.randomUUID(), "One Piece", LocalDate.of(1997, 7, 22), "MANGA", List.of("Eiichiro Oda"));
  }

  @Test
  void getAllBooks_shouldReturnOkWithList() {
    when(bookService.getAllBooks()).thenReturn(List.of(sampleDto()));

    ResponseEntity<?> response = bookController.getAllBooks();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat((List<?>) response.getBody()).hasSize(1);
  }

  @Test
  void getAllBooks_shouldReturn500_whenServiceThrows() {
    when(bookService.getAllBooks()).thenThrow(new RuntimeException("boom"));

    ResponseEntity<?> response = bookController.getAllBooks();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody()).isEqualTo("server error");
  }

  @Test
  void getBookById_shouldReturnOk_whenFound() {
    BookResponseDto dto = sampleDto();
    when(bookService.getBookById(dto.id())).thenReturn(dto);

    ResponseEntity<?> response = bookController.getBookById(dto.id());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(dto);
  }

  @Test
  void getBookById_shouldReturn404_whenNotFound() {
    UUID id = UUID.randomUUID();
    when(bookService.getBookById(id)).thenThrow(new ResourceNotFoundException("Book", id));

    ResponseEntity<?> response = bookController.getBookById(id);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isEqualTo("Livre introuvable");
  }

  @Test
  void getBookById_shouldReturn500_whenUnexpectedError() {
    UUID id = UUID.randomUUID();
    when(bookService.getBookById(id)).thenThrow(new RuntimeException("boom"));

    ResponseEntity<?> response = bookController.getBookById(id);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    assertThat(response.getBody()).isEqualTo("server error");
  }

  @Test
  void getBooksSoldToday_shouldReturnOkWithList() {
    when(bookService.getBooksSoldToday()).thenReturn(List.of(sampleDto()));

    ResponseEntity<List<BookResponseDto>> response = bookController.getBooksSoldToday();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
  }
}
