package com.example.tacocloudwithregistration;

import com.example.tacocloudwithjpa.data.Ingredient;
import com.example.tacocloudwithjpa.data.Ingredient.Type;
import com.example.tacocloudwithjpa.data.Taco;
import com.example.tacocloudwithjpa.data.TacoOrder;
import com.example.tacocloudwithjpa.repositories.IngredientRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = ingredientRepo.findAll();
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
        if (errors.hasErrors()) {
            return "design";
        }
        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);

        return "redirect:/orders/current";

    }
    private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients, Type type) {
        List<Ingredient> ingredientList = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            ingredientList.add(ingredient);
        }
        return ingredientList
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

}

