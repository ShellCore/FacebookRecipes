package com.edx.shell.android.facebookrecipes.recipeList.adapters;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.edx.shell.android.facebookrecipes.R;
import com.edx.shell.android.facebookrecipes.entities.Recipe;
import com.edx.shell.android.facebookrecipes.libs.base.ImageLoader;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.SendButton;
import com.facebook.share.widget.ShareButton;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    // Servicios
    private ImageLoader imageLoader;

    // Variables
    private List<Recipe> recipes;
    private OnItemClickListener onItemClickListener;

    public RecipesAdapter(ImageLoader imageLoader, List<Recipe> recipes, OnItemClickListener onItemClickListener) {
        this.imageLoader = imageLoader;
        this.recipes = recipes;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_stored_recipe, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe currentRecipe = recipes.get(position);
        imageLoader.load(holder.imgRecipe, currentRecipe.getImageUrl());
        holder.txtRecipeName.setText(currentRecipe.getTitle());
        holder.btnFavorite.setTag(currentRecipe.getFavorite());

        if (currentRecipe.getFavorite()) {
            holder.btnFavorite.setImageResource(android.R.drawable.btn_star_big_on);
            holder.btnFavorite.setColorFilter(Color.YELLOW);
        } else {
            holder.btnFavorite.setImageResource(android.R.drawable.btn_star_big_off);
            holder.btnFavorite.setColorFilter(Color.LTGRAY);
        }

        holder.setOnItemClickListener(currentRecipe, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public void removeRecipe(Recipe recipe) {
        recipes.remove(recipe);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // Variables
        private View view;

        // Componentes
        @Bind(R.id.img_recipe)
        ImageView imgRecipe;
        @Bind(R.id.txt_recipe_name)
        TextView txtRecipeName;
        @Bind(R.id.btn_favorite)
        ImageButton btnFavorite;
        @Bind(R.id.btn_delete)
        ImageButton btnDelete;
        @Bind(R.id.btn_share)
        ShareButton btnShare;
        @Bind(R.id.btn_send)
        SendButton btnSend;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

        public void setOnItemClickListener(final Recipe currentRecipe, final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(currentRecipe);
                }
            });

            btnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onFavorite(currentRecipe);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDelete(currentRecipe);
                }
            });

            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(currentRecipe.getSourceUrl()))
                    .build();

            btnShare.setShareContent(content);
            btnSend.setShareContent(content);
        }
    }
}
