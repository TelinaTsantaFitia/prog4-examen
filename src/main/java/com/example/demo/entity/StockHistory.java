package com.example.demo.entity;

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
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stock_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id_stock_history", updatable = false, nullable = false)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "id_book_copy", nullable = false)
  private BookCopy bookCopy;

  @Enumerated(EnumType.STRING)
  @Column(name = "movement_type", nullable = false)
  private MovementType movementType;

  @Column(name = "movement_date", nullable = false)
  private LocalDateTime movementDate;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  @ManyToOne
  @JoinColumn(name = "sale_id", nullable = true)
  private Sale sale;

  @Column(name = "reason")
  private String reason;
}
