package com.telina.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.telina.demo.dto.BookCopyResponseDto;
import com.telina.demo.entity.FormatType;
import com.telina.demo.exception.ResourceNotFoundException;
import com.telina.demo.service.BookCopyService;
import java.math.BigDecimal;
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
class BookCopyControllerTest {

  @Mock private BookCopyService bookCopyService;

  private BookCopyController bookCopyController;

  @BeforeEach
  void setUp() {
    bookCopyController = new BookCopyController(bookCopyService);
  }

  private BookCopyResponseDto sampleDto(UUID id) {
    return new BookCopyResponseDto(
        id,
        "978-2-1234-5678-9",
        new BigDecimal("5.00"),
        new BigDecimal("9.90"),
        3,
        FormatType.POCHE,
        UUID.randomUUID(),
        "One Piece");
  }

  @Test
  void getAllBookCopies_shouldReturnOkWithList() {
    when(bookCopyService.getAllBookCopies()).thenReturn(List.of(sampleDto(UUID.randomUUID())));

    ResponseEntity<List<BookCopyResponseDto>> response = bookCopyController.getAllBookCopies();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).hasSize(1);
  }

  @Test
  void getBookCopyById_shouldReturnOk_whenValidUuidAndFound() {
    UUID id = UUID.randomUUID();
    BookCopyResponseDto dto = sampleDto(id);
    when(bookCopyService.getBookCopyById(id)).thenReturn(dto);

    ResponseEntity<?> response = bookCopyController.getBookCopyById(id.toString());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(dto);
  }

  @Test
  void getBookCopyById_shouldReturnBadRequest_whenUuidIsInvalid() {
    ResponseEntity<?> response = bookCopyController.getBookCopyById("not-a-uuid");

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isEqualTo("the provided identifier is not a valid UUID");
  }

  @Test
  void getBookCopyById_shouldReturnNotFound_whenBookCopyDoesNotExist() {
    UUID id = UUID.randomUUID();
    when(bookCopyService.getBookCopyById(id))
        .thenThrow(new ResourceNotFoundException("BookCopy", id));

    ResponseEntity<?> response = bookCopyController.getBookCopyById(id.toString());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody())
        .isEqualTo("Erreur 404 : BookCopy not found with identifier: " + id);
  }
}
