package com.example.lunch.menu.dto;

import java.time.LocalDate;

public record MenuCreateRequest(
        LocalDate menuDate,
        String menuName,
        String category,
        String cafeteria
) {
}
