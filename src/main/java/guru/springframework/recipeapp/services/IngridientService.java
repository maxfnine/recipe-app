package guru.springframework.recipeapp.services;

import guru.springframework.recipeapp.commands.IngredientCommand;

public interface IngridientService {
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand command);
    void deleteById(Long recipeId,Long id);
}
