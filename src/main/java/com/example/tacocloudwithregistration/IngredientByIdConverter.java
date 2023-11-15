package com.example.tacocloudwithregistration;

import com.example.tacocloudwithjpa.data.Ingredient;
import com.example.tacocloudwithjpa.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private IngredientRepository ingredientRepo;

    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public Ingredient convert(String id) {
        try {
            return ingredientRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ingredient ID: " + id));
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert ingredient ID: " + id, e);
        }
    }

}
