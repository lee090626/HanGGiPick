package com.example.lunch.menu.dto;

public record AverageRatingResponse(
        Long menuId,
        double averageRating
) {
}
