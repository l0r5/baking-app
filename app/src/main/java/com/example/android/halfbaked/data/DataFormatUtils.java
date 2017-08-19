package com.example.android.halfbaked.data;


import android.util.Log;

import com.example.android.halfbaked.models.Ingredient;
import com.example.android.halfbaked.models.Recipe;
import com.example.android.halfbaked.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public final class DataFormatUtils {

    public static Recipe[] getRecipeObjectsFromJson(String jsonRecipeString) throws JSONException {

        final String RECIPE_ID = "id";
        final String RECIPE_NAME = "name";
        final String RECIPE_INGREDIENTS = "ingredients";
        final String RECIPE_STEPS = "steps";
        final String RECIPE_SERVINGS = "servings";
        final String RECIPE_IMAGE = "image";

        JSONArray jsonRecipeArray = new JSONArray(jsonRecipeString);
        Recipe[] parsedRecipeData = new Recipe[jsonRecipeArray.length()];

        // build recipe object array
        for (int i = 0; i < jsonRecipeArray.length(); i++) {

            JSONObject recipes = jsonRecipeArray.getJSONObject(i);

            // JSON Arrays needed for getting Ingredients and Steps
            JSONArray jsonIngredientsArray = recipes.getJSONArray(RECIPE_INGREDIENTS);
            JSONArray jsonStepsArray = recipes.getJSONArray(RECIPE_STEPS);

            int id = recipes.getInt(RECIPE_ID);
            String name = recipes.getString(RECIPE_NAME);
            Ingredient[] ingredients = getIngredients(jsonIngredientsArray);
            Step[] steps = getSteps(jsonStepsArray);
            int servings = recipes.getInt(RECIPE_SERVINGS);
            String imageUrl = recipes.getString(RECIPE_IMAGE);

            Recipe recipeObject = new Recipe(id, name, ingredients, steps, servings, imageUrl);
            parsedRecipeData[i] = recipeObject;
        }
        return parsedRecipeData;
    }

    private static Ingredient[] getIngredients(JSONArray ingredientsArray) throws JSONException {

        final String INGREDIENTS_QUANTITY = "quantity";
        final String INGREDIENTS_MEASURE = "measure";
        final String INGREDIENTS_INGREDIENT = "ingredient";

        Ingredient[] parsedIngredientsData = new Ingredient[ingredientsArray.length()];

        for (int i = 0; i < ingredientsArray.length(); i++) {

            JSONObject ingredients = ingredientsArray.getJSONObject(i);

            double quantity = ingredients.getDouble(INGREDIENTS_QUANTITY);
            String measure = ingredients.getString(INGREDIENTS_MEASURE);
            String ingredient = ingredients.getString(INGREDIENTS_INGREDIENT);

            Ingredient ingredientObject = new Ingredient(quantity, measure, ingredient);
            parsedIngredientsData[i] = ingredientObject;
        }
        return parsedIngredientsData;
    }

    private static Step[] getSteps(JSONArray stepsArray) throws JSONException {

        final String STEPS_ID = "id";
        final String STEPS_SHORT_DESCRIPTION = "shortDescription";
        final String STEPS_DESCRIPTION = "description";
        final String STEPS_VIDEO_URL = "videoURL";
        final String STEPS_THUMBNAIL_URL = "thumbnailURL";

        Step[] parsedStepsData = new Step[stepsArray.length()];

        for (int i = 0; i < stepsArray.length(); i++) {

            JSONObject steps = stepsArray.getJSONObject(i);

            int id = steps.getInt(STEPS_ID);
            String shortDescription = steps.getString(STEPS_SHORT_DESCRIPTION);
            String description = steps.getString(STEPS_DESCRIPTION);
            String videoUrl = steps.getString(STEPS_VIDEO_URL);
            String thumbnailUrl = steps.getString(STEPS_THUMBNAIL_URL);

            Step stepObject = new Step(id, shortDescription, description, videoUrl, thumbnailUrl);
            parsedStepsData[i] = stepObject;
        }
        return parsedStepsData;
    }
}
