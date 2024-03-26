package com.example.cookidea_app.Adapters;

import static com.example.cookidea_app.Activities.MainActivity.BASE_URL;

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
    List<Recipe> recipes;


    public SearchPageListAdapter(@NonNull Context context, @NonNull List<Recipe> recipes) {
        super(context, R.layout.search_page_recipe_list, recipes);
        this.ctx = context;
        this.recipes = recipes;
    }

    private static class ResultsViewHolder{
        ImageView imgRecipeVh;
        TextView nameRecipeVH;
        TextView timeRecipeVH;
        TextView difficultyRecipeVH;
        TextView servingRecipeVH;
        Bitmap recipeImage;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ResultsViewHolder resultsViewHolder;

        if(convertView == null){
            resultsViewHolder = new ResultsViewHolder();
            LayoutInflater inflater = LayoutInflater.from(ctx);
            convertView = inflater.inflate(R.layout.search_page_recipe_list, parent, false);
            resultsViewHolder.imgRecipeVh = (ImageView) convertView.findViewById(R.id.imageRecipeSearchedImageView);
            resultsViewHolder.nameRecipeVH = (TextView) convertView.findViewById(R.id.nameRecipeSearchedTextView);
            resultsViewHolder.timeRecipeVH = (TextView) convertView.findViewById(R.id.timeRecipeSearchedTextView);
            resultsViewHolder.difficultyRecipeVH = (TextView) convertView.findViewById(R.id.difficultyRecipeSearchedTextView);
            resultsViewHolder.servingRecipeVH = (TextView) convertView.findViewById(R.id.servingRecipeSearchedTextView);

            String imgUrl = BASE_URL + "/static/recipes/" + recipes.get(position).getImg_name().toLowerCase() + ".jpg";

            new DownloadImageAsyncTask(resultsViewHolder.imgRecipeVh, new DownloadImageAsyncTask.ImageDownloadCallback() {
                @Override
                public void downloaded(Bitmap img) {
                    resultsViewHolder.recipeImage = img;
                }
            }).execute(imgUrl);

            convertView.setTag(resultsViewHolder);
        }else{
            resultsViewHolder = (SearchPageListAdapter.ResultsViewHolder) convertView.getTag();
        }

        resultsViewHolder.imgRecipeVh.setImageBitmap(resultsViewHolder.recipeImage);
        resultsViewHolder.nameRecipeVH.setText(recipes.get(position).getName());
        resultsViewHolder.timeRecipeVH.setText(recipes.get(position).getTime());
        resultsViewHolder.difficultyRecipeVH.setText(recipes.get(position).getDifficulty());
        resultsViewHolder.servingRecipeVH.setText(recipes.get(position).getServing());
        
        return convertView;
    }
}
