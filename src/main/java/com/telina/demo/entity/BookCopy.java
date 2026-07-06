package com.telina.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book_copy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCopy {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id_book_copy", updatable = false, nullable = false)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "id_book", nullable = false)
  private Book book;

  @Column(name = "isbn", nullable = false, unique = true)
  private String isbn;

  @Column(name = "purchase_price")
  private BigDecimal purchasePrice;

  @Column(name = "selling_price")
  private BigDecimal sellingPrice;

  @Column(name = "current_stock")
  private Integer currentStock;

  @Enumerated(EnumType.STRING)
  @Column(name = "format_type", nullable = false)
  private FormatType formatType;
}
