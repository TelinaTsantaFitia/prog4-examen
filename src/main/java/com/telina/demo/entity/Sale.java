package com.telina.demo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "sale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id_sale", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "sale_date", nullable = false)
  private LocalDateTime saleDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_status", nullable = false)
  private PaymentStatus paymentStatus;

  @ManyToOne
  @JoinColumn(name = "id_customer", nullable = false)
  private Customer customer;

  @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<SaleBook> books;

  @OneToMany(mappedBy = "sale")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<StockHistory> stockHistories;
}
