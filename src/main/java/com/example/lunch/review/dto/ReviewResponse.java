package com.example.lunch.review.dto;

import com.example.lunch.review.Review;

public record ReviewResponse(
        Long id,
        Long menuId,
        int rating,
        String comment,
        String nickname
) {

    public static ReviewResponse from(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getMenu().getId(),
                review.getRating(),
                review.getComment(),
                review.getNickname()
        );
    }
}
