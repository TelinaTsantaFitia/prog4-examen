package com.telina.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.telina.demo.dto.BookResponseDto;
import com.telina.demo.entity.Author;
import com.telina.demo.entity.Book;
import com.telina.demo.entity.BookCopy;
import com.telina.demo.entity.Category;
import com.telina.demo.entity.CategoryEnum;
import com.telina.demo.entity.PaymentStatus;
import com.telina.demo.entity.Sale;
import com.telina.demo.entity.SaleBook;
import com.telina.demo.exception.ResourceNotFoundException;
import com.telina.demo.repository.BookRepository;
import com.telina.demo.repository.SaleRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

  @Mock private BookRepository bookRepository;
  @Mock private SaleRepository saleRepository;

  private BookService bookService;

  @BeforeEach
  void setUp() {
    bookService = new BookService(bookRepository, saleRepository);
  }

  @Test
  void getAllBooks_shouldMapBooksToDtos_includingCategoryAndAuthors() {
    Category category = Category.builder().id(UUID.randomUUID()).categoryEnum(CategoryEnum.MANGA).build();
    Author author = Author.builder().id(UUID.randomUUID()).firstName("Eiichiro").lastName("Oda").build();
    Book bookWithDetails =
        Book.builder()
            .id(UUID.randomUUID())
            .title("One Piece")
            .publicationDate(LocalDate.of(1997, 7, 22))
            .category(category)
            .authors(List.of(author))
            .build();
    Book bookWithoutDetails =
        Book.builder()
            .id(UUID.randomUUID())
            .title("Unknown Book")
            .publicationDate(null)
            .category(null)
            .authors(null)
            .build();

    when(bookRepository.findAll()).thenReturn(List.of(bookWithDetails, bookWithoutDetails));

    List<BookResponseDto> result = bookService.getAllBooks();

    assertThat(result).hasSize(2);
    BookResponseDto dto1 = result.get(0);
    assertThat(dto1.title()).isEqualTo("One Piece");
    assertThat(dto1.categoryName()).isEqualTo("MANGA");
    assertThat(dto1.authors()).containsExactly("Eiichiro Oda");

    BookResponseDto dto2 = result.get(1);
    assertThat(dto2.title()).isEqualTo("Unknown Book");
    assertThat(dto2.categoryName()).isNull();
    assertThat(dto2.authors()).isEmpty();
  }

  @Test
  void getBookById_shouldReturnDto_whenBookExists() {
    UUID id = UUID.randomUUID();
    Book book = Book.builder().id(id).title("Naruto").authors(List.of()).build();
    when(bookRepository.findById(id)).thenReturn(java.util.Optional.of(book));

    BookResponseDto result = bookService.getBookById(id);

    assertThat(result.id()).isEqualTo(id);
    assertThat(result.title()).isEqualTo("Naruto");
  }

  @Test
  void getBookById_shouldThrow_whenBookDoesNotExist() {
    UUID id = UUID.randomUUID();
    when(bookRepository.findById(id)).thenReturn(java.util.Optional.empty());

    assertThatThrownBy(() -> bookService.getBookById(id))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessageContaining(id.toString());
  }

  @Test
  void getBooksSoldToday_shouldReturnDistinctBooksSoldToday() {
    Book book1 = Book.builder().id(UUID.randomUUID()).title("Book1").authors(List.of()).build();
    Book book2 = Book.builder().id(UUID.randomUUID()).title("Book2").authors(List.of()).build();

    BookCopy copy1 = BookCopy.builder().id(UUID.randomUUID()).book(book1).build();
    BookCopy copy2 = BookCopy.builder().id(UUID.randomUUID()).book(book2).build();
    // Same book sold twice, in two different sales, to exercise the de-duplication logic.
    BookCopy copy1Again = BookCopy.builder().id(UUID.randomUUID()).book(book1).build();

    SaleBook saleBook1 = SaleBook.builder().id(UUID.randomUUID()).bookCopy(copy1).quantity(1).build();
    SaleBook saleBook2 = SaleBook.builder().id(UUID.randomUUID()).bookCopy(copy2).quantity(1).build();
    SaleBook saleBook3 =
        SaleBook.builder().id(UUID.randomUUID()).bookCopy(copy1Again).quantity(1).build();

    Sale sale1 =
        Sale.builder()
            .id(UUID.randomUUID())
            .saleDate(LocalDateTime.now())
            .paymentStatus(PaymentStatus.PAID)
            .books(List.of(saleBook1, saleBook2))
            .build();
    Sale sale2 =
        Sale.builder()
            .id(UUID.randomUUID())
            .saleDate(LocalDateTime.now())
            .paymentStatus(PaymentStatus.PAID)
            .books(List.of(saleBook3))
            .build();

    when(saleRepository.findBySaleDateBetween(any(), any())).thenReturn(List.of(sale1, sale2));

    List<BookResponseDto> result = bookService.getBooksSoldToday();

    assertThat(result).extracting(BookResponseDto::title).containsExactlyInAnyOrder("Book1", "Book2");
  }
}
