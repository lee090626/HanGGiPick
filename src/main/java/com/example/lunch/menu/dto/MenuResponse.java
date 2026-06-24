package com.example.lunch.menu.dto;

import com.example.lunch.menu.Menu;
import java.time.LocalDate;

public record MenuResponse(
        Long id,
        LocalDate menuDate,
        String menuName,
        String category,
        String cafeteria
) {

    public static MenuResponse from(Menu menu) {
        return new MenuResponse(
                menu.getId(),
                menu.getMenuDate(),
                menu.getMenuName(),
                menu.getCategory(),
                menu.getCafeteria()
        );
    }
}
