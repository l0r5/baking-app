package com.example.android.halfbaked.models;


public class Ingredient {
    private double mQuantity;
    private String mMeasure;
    private String mIngredient;

    public Ingredient(double quantity, String measure, String ingredient) {
        mQuantity = quantity;
        mMeasure = measure;
        mIngredient = ingredient;
    }
}
