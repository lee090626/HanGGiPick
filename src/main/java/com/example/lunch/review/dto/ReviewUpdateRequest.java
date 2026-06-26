package com.example.lunch.review.dto;

public record ReviewUpdateRequest(
        int rating,
        String comment
) {
}
