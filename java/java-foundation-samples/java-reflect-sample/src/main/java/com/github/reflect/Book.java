package com.github.reflect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * book entity
 * @author laba zhang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private final static String TAG = "BookTag";

    private String name;
    private String author;

    private void declaredMethod(int num) {
        System.out.println(num);
    }
}