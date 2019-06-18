package com.example.mybaking;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeIngredients implements  Parcelable{

        String recipeQuantity;
        String recipeMeasure;
        String ingredients;
        protected RecipeIngredients(Parcel in){
            recipeQuantity=in.readString();
            recipeMeasure=in.readString() ;
            ingredients=in.readString();
        }

    public static final Creator<RecipeIngredients> CREATOR = new Creator<RecipeIngredients>() {
        @Override
        public RecipeIngredients createFromParcel(Parcel in) {
            return new RecipeIngredients(in);
        }

        @Override
        public RecipeIngredients[] newArray(int size) {
            return new RecipeIngredients[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recipeQuantity);
        dest.writeString(recipeMeasure);
        dest.writeString(ingredients);

    }

    public RecipeIngredients(String recipeQuantity, String recipeMeasure, String ingredients) {
        this.recipeQuantity = recipeQuantity;
        this.recipeMeasure = recipeMeasure;
        this.ingredients = ingredients;
    }

    public String getRecipeQuantity() {
        return recipeQuantity;
    }

    public void setRecipeQuantity(String recipeQuantity) {
        this.recipeQuantity = recipeQuantity;
    }

    public String getRecipeMeasure() {
        return recipeMeasure;
    }

    public void setRecipeMeasure(String recipeMeasure) {
        this.recipeMeasure = recipeMeasure;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}