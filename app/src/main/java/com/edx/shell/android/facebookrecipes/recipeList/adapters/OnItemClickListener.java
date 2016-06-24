package com.edx.shell.android.facebookrecipes.recipeList.adapters;

import com.edx.shell.android.facebookrecipes.entities.Recipe;

public interface OnItemClickListener {
    void onFavorite(Recipe recipe);
    void onDelete(Recipe recipe);
    void onItemClick(Recipe recipe);
}
