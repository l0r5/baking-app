package com.example.android.halfbaked.models;


public class Recipe {

    private int mId;
    private String mName;
    private Ingredient[] mIngredients;
    private Step[] mSteps;
    private int mServings;
    private String mImageUrl;

    public Recipe(int id, String name, Ingredient[] ingredients, Step[] steps, int servings, String imageUrl) {
        mId = id;
        mName = name;
        mIngredients = ingredients;
        mSteps = steps;
        mServings = servings;
        mImageUrl = imageUrl;
    }
}
