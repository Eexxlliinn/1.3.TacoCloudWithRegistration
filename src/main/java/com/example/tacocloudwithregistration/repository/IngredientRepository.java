package com.example.tacocloudwithregistration.repository;

import com.example.tacocloudwithjpa.data.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
