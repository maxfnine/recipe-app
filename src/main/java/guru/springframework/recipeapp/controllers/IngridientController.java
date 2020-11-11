package guru.springframework.recipeapp.controllers;

import guru.springframework.recipeapp.commands.IngredientCommand;
import guru.springframework.recipeapp.commands.RecipeCommand;
import guru.springframework.recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.recipeapp.services.IngridientService;
import guru.springframework.recipeapp.services.RecipeService;
import guru.springframework.recipeapp.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class IngridientController {
    private final RecipeService recipeService;
    private final IngridientService ingridientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngridientController(RecipeService recipeService, IngridientService ingridientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingridientService = ingridientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String listIngridients(Model model, @PathVariable(name = "id") Long id){
        log.debug("Getting ingridient list for recipe id: {}",id);
        model.addAttribute("recipe",recipeService.findCommandById(id));
        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showIngridient(Model model,@PathVariable(name = "recipeId") Long recipeId,@PathVariable(name = "id") Long id){
        model.addAttribute("ingredient",ingridientService.findByRecipeIdAndIngredientId(recipeId,id));
        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String showUpdateIngridientForm(Model model,@PathVariable(name = "recipeId") Long recipeId,@PathVariable(name = "id") Long id){
        model.addAttribute("ingredient",ingridientService.findByRecipeIdAndIngredientId(recipeId,id));
        model.addAttribute("uomList",unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        IngredientCommand savedCommand = ingridientService.saveIngredientCommand(command);

        log.debug("saved receipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String showNewIngridientForm(Model model,@PathVariable(name = "recipeId") Long recipeId){
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        model.addAttribute("ingredient",ingredientCommand);
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("uomList",unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId,
                                   @PathVariable String id){

        log.debug("deleting ingredient id:" + id);
        ingridientService.deleteById(Long.valueOf(recipeId), Long.valueOf(id));

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
