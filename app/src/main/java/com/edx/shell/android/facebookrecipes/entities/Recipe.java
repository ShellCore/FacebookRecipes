package com.edx.shell.android.facebookrecipes.entities;

import com.edx.shell.android.facebookrecipes.db.RecipesDatabase;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = RecipesDatabase.class)
public class Recipe extends BaseModel {
    @SerializedName("recipe_id")
    @PrimaryKey
    private String recipeId;
    @Column
    private String title;
    @SerializedName("image_url")
    @Column
    private String imageUrl;
    @SerializedName("source_url")
    @Column
    private String sourceUrl;
    @Column
    private boolean favorite;

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj instanceof Recipe) {
            Recipe recipe = (Recipe) obj;
            equal = recipeId.equals(recipe.getRecipeId());
        }
        return equal;
    }
}
