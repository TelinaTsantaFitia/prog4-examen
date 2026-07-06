package com.telina.demo.controller;

import com.telina.demo.dto.BookCopyResponseDto;
import com.telina.demo.exception.ResourceNotFoundException;
import com.telina.demo.service.BookCopyService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book-copies")
@RequiredArgsConstructor
public class BookCopyController {

  private final BookCopyService bookCopyService;

  @GetMapping
  public ResponseEntity<List<BookCopyResponseDto>> getAllBookCopies() {
    return ResponseEntity.ok(bookCopyService.getAllBookCopies());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getBookCopyById(@PathVariable String id) {
    try {
      UUID uuid = UUID.fromString(id);

      BookCopyResponseDto response = bookCopyService.getBookCopyById(uuid);
      return ResponseEntity.ok(response);

    } catch (IllegalArgumentException e) {

      return ResponseEntity.badRequest().body("the provided identifier is not a valid UUID");

    } catch (ResourceNotFoundException e) {

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erreur 404 : " + e.getMessage());
    }
  }
}
