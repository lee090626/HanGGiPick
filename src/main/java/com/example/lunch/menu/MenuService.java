package com.example.lunch.menu;

import com.example.lunch.menu.dto.MenuCreateRequest;
import com.example.lunch.menu.dto.MenuResponse;
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
}
