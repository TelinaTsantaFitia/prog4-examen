package com.telina.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
@Table(name = "author")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id_author", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @ManyToMany(mappedBy = "authors")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @com.fasterxml.jackson.annotation.JsonIgnoreProperties("authors")
  private List<Book> books;
}
