/*
@author Kamaljot Saini
 */
package com.example.epsilonnutrition;

public class Recipes {
    //Declare Recipe Variables
    private String name_recipe, name_ingredient;
    private int num_servings;
    private boolean veganFlag, vegetarianFlag, glutenFreeFlag;
    private boolean optionalNutritionalInformation_Flag;
    private int calories, total_fat, total_carbs, total_protein;

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getTotal_fat() {
        return total_fat;
    }

    public void setTotal_fat(int total_fat) {
        this.total_fat = total_fat;
    }

    public int getTotal_carbs() {
        return total_carbs;
    }

    public void setTotal_carbs(int total_carbs) {
        this.total_carbs = total_carbs;
    }

    public int getTotal_protein() {
        return total_protein;
    }

    public void setTotal_protein(int total_protein) {
        this.total_protein = total_protein;
    }

    public boolean isOptionalNutritionalInformation_Flag() {
        return optionalNutritionalInformation_Flag;
    }

    public void setOptionalNutritionalInformation_Flag(boolean optionalNutritionalInformation_Flag) {
        this.optionalNutritionalInformation_Flag = optionalNutritionalInformation_Flag;
    }

    //private HashMap<String, Ingredient>; EVENTUALLY THE INGREDIENTS WILL HAVE TO BE ENTERED INTO A HASHMAP
    //Ingredient object needs to have nutritional information
    Recipes()
    {
        this.name_recipe = "DEFAULT RECIPE";
        this.name_ingredient = "DEFAULT INGREDIENT";
        this.num_servings = 0;
    }

    //If user WANTS to enter optional nutritional information
    Recipes(String input_nameRecipe, String input_nameIngredient, int input_numServings,
            boolean input_vegan, boolean input_vegetarian, boolean input_glutenFree, boolean input_optionalNutritionalInformation)
    {
        this.name_recipe = input_nameRecipe;
        this.name_ingredient = input_nameIngredient;
        this.num_servings = input_numServings;
        this.veganFlag = input_vegan;
        this.vegetarianFlag = input_vegetarian;
        this.glutenFreeFlag = input_glutenFree;
        this.optionalNutritionalInformation_Flag = input_optionalNutritionalInformation;
    }

    //If user DOES NOT WANT TO to enter optional nutritional information
    Recipes(String input_nameRecipe, String input_nameIngredient, int input_numServings,
            boolean input_vegan, boolean input_vegetarian, boolean input_glutenFree,
            boolean input_optionalNutritionalInformation, int input_calories, int input_totalFat,
            int input_totalCarbs, int input_totalProtein)
    {
        this.name_recipe = input_nameRecipe;
        this.name_ingredient = input_nameIngredient;
        this.num_servings = input_numServings;
        this.veganFlag = input_vegan;
        this.vegetarianFlag = input_vegetarian;
        this.glutenFreeFlag = input_glutenFree;
        this.optionalNutritionalInformation_Flag = input_optionalNutritionalInformation;
        this.calories = input_calories;
        this.total_fat = input_totalFat;
        this.total_carbs = input_totalCarbs;
        this.total_protein = input_totalProtein;
    }



    public boolean isVeganFlag() {
        return veganFlag;
    }

    public void setVeganFlag(boolean veganFlag) {
        this.veganFlag = veganFlag;
    }

    public boolean isVegetarianFlag() {
        return vegetarianFlag;
    }

    public void setVegetarianFlag(boolean vegetarianFlag) {
        this.vegetarianFlag = vegetarianFlag;
    }

    public boolean isGlutenFreeFlag() {
        return glutenFreeFlag;
    }

    public void setGlutenFreeFlag(boolean glutenFreeFlag) {
        this.glutenFreeFlag = glutenFreeFlag;
    }

    public String getName_recipe() {
        return name_recipe;
    }

    public void setName_recipe(String name_recipe) {
        this.name_recipe = name_recipe;
    }

    public String getName_ingredient() {
        return name_ingredient;
    }

    public void setName_ingredient(String name_ingredient) {
        this.name_ingredient = name_ingredient;
    }

    public int getNum_servings() {
        return num_servings;
    }

    public void setNum_servings(int num_servings) {
        this.num_servings = num_servings;
    }

    @Override
    public String toString() {
        return name_recipe + '/' +
                name_ingredient + '/' +
                num_servings + '/' +
                veganFlag + '/' +
                vegetarianFlag + '/' +
                glutenFreeFlag + '/' +
                optionalNutritionalInformation_Flag + '/' +
                calories + '/' +
                total_fat + '/' +
                total_carbs + '/' +
                total_protein;
    }
}
