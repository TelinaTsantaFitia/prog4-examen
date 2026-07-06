package com.example.demo.repository;

import com.example.demo.entity.Sale;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID> {
  List<Sale> findBySaleDateBetween(LocalDateTime start, LocalDateTime end);
}
