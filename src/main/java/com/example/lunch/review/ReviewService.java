package com.example.lunch.review;

import com.example.lunch.menu.Menu;
import com.example.lunch.menu.MenuRepository;
import com.example.lunch.review.dto.ReviewCreateRequest;
import com.example.lunch.review.dto.ReviewResponse;
import com.example.lunch.review.dto.ReviewUpdateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public ReviewResponse createReview(Long menuId, ReviewCreateRequest request) {
        validateRating(request.rating());
        Menu menu = findMenu(menuId);
        Review review = new Review(
                menu,
                request.rating(),
                request.comment(),
                request.nickname()
        );

        return ReviewResponse.from(reviewRepository.save(review));
    }

    public List<ReviewResponse> getReviewsByMenuId(Long menuId) {
        findMenu(menuId);
        return reviewRepository.findByMenuId(menuId).stream()
                .map(ReviewResponse::from)
                .toList();
    }

    @Transactional
    public ReviewResponse updateReview(Long id, ReviewUpdateRequest request) {
        validateRating(request.rating());
        Review review = findReview(id);
        review.update(request.rating(), request.comment());

        return ReviewResponse.from(review);
    }

    @Transactional
    public void deleteReview(Long id) {
        Review review = findReview(id);
        reviewRepository.delete(review);
    }

    private Menu findMenu(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found: " + menuId));
    }

    private Review findReview(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found: " + id));
    }

    private void validateRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
    }
}
