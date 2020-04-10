package ru.itpark.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    private String id= generatedId();
    private String name;
    private String ingredients;
    private String description;
    public String generatedId() {
        return UUID.randomUUID().toString();
    }

}
