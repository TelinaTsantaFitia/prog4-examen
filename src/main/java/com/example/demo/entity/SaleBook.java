package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sale_book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleBook {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id_sale_book", updatable = false, nullable = false)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "id_sale", nullable = false)
  private Sale sale;

  @ManyToOne
  @JoinColumn(name = "id_book_copy", nullable = false)
  private BookCopy bookCopy;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;
}
