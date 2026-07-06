package com.telina.demo.dto;

import com.telina.demo.entity.FormatType;
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
