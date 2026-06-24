package com.example.lunch.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate menuDate;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String cafeteria;

    public Menu(LocalDate menuDate, String menuName, String category, String cafeteria) {
        this.menuDate = menuDate;
        this.menuName = menuName;
        this.category = category;
        this.cafeteria = cafeteria;
    }
}
