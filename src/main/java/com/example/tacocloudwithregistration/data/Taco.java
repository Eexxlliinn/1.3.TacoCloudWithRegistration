package com.example.tacocloudwithregistration.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Taco {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taco_seq")
    //@Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    private Date createdAt = new Date();

    @ManyToOne
    @JoinColumn(name = "taco_order_id")
    private TacoOrder tacoOrder;

    @NotNull
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    @ManyToMany()
    @JoinTable(name = "IngredientRef", joinColumns = @JoinColumn(name = "taco_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredients;

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
}
