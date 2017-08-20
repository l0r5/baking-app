package com.example.android.halfbaked.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable{

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

    public String getName() {
        return mName;
    }

    public Ingredient[] getIngredients() {
        return mIngredients;
    }

    public int getServings() {
        return mServings;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    // Parcelable
    private Recipe(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mIngredients = in.createTypedArray(Ingredient.CREATOR);
        mSteps = in.createTypedArray(Step.CREATOR);
        mServings = in.readInt();
        mImageUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeTypedArray(mIngredients, 0);
        dest.writeTypedArray(mSteps, 0);
        dest.writeInt(mServings);
        dest.writeString(mImageUrl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
