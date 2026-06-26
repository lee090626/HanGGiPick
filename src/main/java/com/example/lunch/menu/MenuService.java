package com.example.lunch.menu;

import com.example.lunch.menu.dto.AverageRatingResponse;
import com.example.lunch.menu.dto.MenuCreateRequest;
import com.example.lunch.menu.dto.MenuResponse;
import com.example.lunch.menu.dto.MenuUpdateRequest;
import com.example.lunch.review.ReviewRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public MenuResponse createMenu(MenuCreateRequest request) {
        Menu menu = new Menu(
                request.menuDate(),
                request.menuName(),
                request.category(),
                request.cafeteria()
        );

        return MenuResponse.from(menuRepository.save(menu));
    }

    public List<MenuResponse> getMenus(LocalDate date) {
        List<Menu> menus = date == null ? menuRepository.findAll() : menuRepository.findByMenuDate(date);
        return menus.stream()
                .map(MenuResponse::from)
                .toList();
    }

    @Transactional
    public MenuResponse updateMenu(Long id, MenuUpdateRequest request) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found: " + id));

        menu.update(
                request.menuDate(),
                request.menuName(),
                request.category(),
                request.cafeteria()
        );

        return MenuResponse.from(menu);
    }

    public AverageRatingResponse getAverageRating(Long id) {
        menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found: " + id));

        double averageRating = reviewRepository.findByMenuId(id).stream()
                .mapToInt(review -> review.getRating())
                .average()
                .orElse(0.0);

        return new AverageRatingResponse(id, averageRating);
    }
}
