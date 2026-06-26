package com.example.lunch.review.dto;

public record ReviewCreateRequest(
        int rating,
        String comment,
        String nickname
) {
}
