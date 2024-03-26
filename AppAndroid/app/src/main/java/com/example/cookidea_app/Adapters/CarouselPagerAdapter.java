package com.example.cookidea_app.Adapters;

import static com.example.cookidea_app.Activities.MainActivity.BASE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.example.cookidea_app.Backend.DownloadImageAsyncTask;
import com.example.cookidea_app.ModelClasses.Recipe;
import com.example.cookidea_app.R;

import java.util.List;

public class CarouselPagerAdapter extends PagerAdapter {
    public List<Recipe> carouselRecipes;
    private Context context;
    private LayoutInflater layoutInflater;

    public CarouselPagerAdapter(Context context, List<Recipe> carouselRecipes){
        this.context = context;
        this.carouselRecipes = carouselRecipes;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.home_page_carousel_layout, container, false);
        if(!carouselRecipes.isEmpty()) {
            int realPosition = position % carouselRecipes.size();
            ImageView imageView = (ImageView) itemView.findViewById(R.id.carouselImageView);
            String imgUrl = BASE_URL + "/static/recipes" + carouselRecipes.get(realPosition);
            new DownloadImageAsyncTask(imageView, null).execute(imgUrl);
        }

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


}
