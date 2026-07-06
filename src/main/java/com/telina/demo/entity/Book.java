package com.telina.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
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
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id_book", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "title")
  private String title;

  @Column(name = "publication_date")
  private LocalDate publicationDate;

  @ManyToOne
  @JoinColumn(name = "id_category", nullable = false)
  private Category category;

  @ManyToMany
  @JoinTable(
      name = "book_author",
      joinColumns = @JoinColumn(name = "id_book"),
      inverseJoinColumns = @JoinColumn(name = "id_author"))
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @com.fasterxml.jackson.annotation.JsonIgnoreProperties("books")
  private List<Author> authors;
}
