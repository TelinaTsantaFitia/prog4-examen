package com.example.demo.dto;

import com.example.demo.entity.FormatType;
import java.math.BigDecimal;
import java.util.UUID;

public record BookCopyResponseDto(
    UUID id,
    String isbn,
    BigDecimal purchasePrice,
    BigDecimal sellingPrice,
    Integer currentStock,
    FormatType formatType,
    UUID bookId,
    String bookTitle) {}
