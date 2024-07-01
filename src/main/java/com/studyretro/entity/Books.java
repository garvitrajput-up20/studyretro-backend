package com.studyretro.entity;

import com.studyretro.enums.BookCategory;
import com.studyretro.enums.BookCondition;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String bookName;

    private String author;

    private String isbn;

    private Double price;

    private BookCondition condition;

    private BookCategory category;

    private String publisher;

    private int quantity;

    private String description;
}
