package com.example.cookidea_app.Adapters;


import static com.example.cookidea_app.Activities.CookIdeaApp.BASE_URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cookidea_app.Backend.DownloadImageAsyncTask;
import com.example.cookidea_app.ModelClasses.Recipe;
import com.example.cookidea_app.R;

import java.util.List;

public class SearchPageListAdapter extends ArrayAdapter<Recipe> {

    Context ctx;
    List<Recipe> recipeList;



    public SearchPageListAdapter(@NonNull Context context, @NonNull List<Recipe> recipes) {
        super(context, R.layout.search_page_recipe_list, recipes);
        this.ctx = context;
        this.recipeList = recipes;
    }

    private static class ResultsViewHolder{
        ImageView imgRecipeVh;
        TextView nameRecipeVH;
        TextView timeRecipeVH;
        TextView difficultyRecipeVH;
        TextView servingRecipeVH;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ResultsViewHolder holder;

        View rowView = convertView;

        if (rowView==null){
            holder = new ResultsViewHolder();
            rowView = LayoutInflater.from(ctx).inflate(R.layout.search_page_recipe_list, parent, false);

            holder.imgRecipeVh = (ImageView) rowView.findViewById(R.id.imageRecipeSearchedImageView);
            holder.nameRecipeVH = (TextView) rowView.findViewById(R.id.nameRecipeSearchedTextView);
            holder.timeRecipeVH = (TextView) rowView.findViewById(R.id.timeRecipeSearchedTextView);
            holder.difficultyRecipeVH = (TextView) rowView.findViewById(R.id.difficultyRecipeSearchedTextView);
            holder.servingRecipeVH = (TextView) rowView.findViewById(R.id.servingRecipeSearchedTextView);

            rowView.setTag(holder);

        } else {
            holder = (ResultsViewHolder) rowView.getTag();
        }

        Recipe recipe = getItem(position);

        holder.nameRecipeVH.setText(recipe.getName());
        holder.timeRecipeVH.setText(" " + recipe.getTime() + "min");
        holder.difficultyRecipeVH.setText(" " + recipe.getDifficulty());
        holder.servingRecipeVH.setText(" " + recipe.getServing());

        String imgUrl = BASE_URL + "/static/recipes/" + getItem(position).getImg_name().toLowerCase();
        if (recipe.getBitmap() != null) {
            holder.imgRecipeVh.setImageBitmap(recipe.getBitmap());
        } else {
            new DownloadImageAsyncTask(holder.imgRecipeVh, new DownloadImageAsyncTask.ImageDownloadCallback() {
                @Override
                public void downloaded(Bitmap img) {
                    recipe.setBitmap(img);
                    holder.imgRecipeVh.setImageBitmap(recipe.getBitmap());
                    notifyDataSetChanged();
                }
            }).execute(imgUrl);
        }

        return rowView;

    }

}
