package com.example.lunch.menu;

import com.example.lunch.menu.dto.AverageRatingResponse;
import com.example.lunch.menu.dto.MenuCreateRequest;
import com.example.lunch.menu.dto.MenuResponse;
import com.example.lunch.menu.dto.MenuUpdateRequest;
import com.example.lunch.review.ReviewService;
import com.example.lunch.review.dto.ReviewCreateRequest;
import com.example.lunch.review.dto.ReviewResponse;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<MenuResponse> createMenu(@RequestBody MenuCreateRequest request) {
        MenuResponse response = menuService.createMenu(request);
        return ResponseEntity.created(URI.create("/menus/" + response.id())).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuResponse>> getMenus(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        return ResponseEntity.ok(menuService.getMenus(date));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuResponse> updateMenu(
            @PathVariable Long id,
            @RequestBody MenuUpdateRequest request
    ) {
        return ResponseEntity.ok(menuService.updateMenu(id, request));
    }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<ReviewResponse> createReview(
            @PathVariable Long id,
            @RequestBody ReviewCreateRequest request
    ) {
        ReviewResponse response = reviewService.createReview(id, request);
        return ResponseEntity.created(URI.create("/menus/" + id + "/reviews/" + response.id())).body(response);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewResponse>> getReviews(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewsByMenuId(id));
    }

    @GetMapping("/{id}/average-rating")
    public ResponseEntity<AverageRatingResponse> getAverageRating(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getAverageRating(id));
    }
}
