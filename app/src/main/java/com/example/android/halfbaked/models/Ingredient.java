package com.example.android.halfbaked.models;


public class Ingredient {
    private String mName;
    private double mQuantity;
    private String mMeasure;

    public Ingredient(String name, double quantity, String measure) {
        mName = name;
        mQuantity = quantity;
        mMeasure = measure;
    }
}
