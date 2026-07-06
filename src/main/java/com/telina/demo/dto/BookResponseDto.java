package com.telina.demo.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record BookResponseDto(
    UUID id, String title, LocalDate publicationDate, String categoryName, List<String> authors) {}
