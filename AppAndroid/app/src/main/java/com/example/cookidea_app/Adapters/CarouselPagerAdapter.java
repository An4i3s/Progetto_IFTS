package com.example.cookidea_app.Adapters;

import static com.example.cookidea_app.Activities.MainActivity.BASE_URL;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.cookidea_app.Backend.DownloadImageAsyncTask;
import com.example.cookidea_app.ModelClasses.Recipe;
import com.example.cookidea_app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CarouselPagerAdapter extends PagerAdapter {
    public List<Recipe> carouselRecipes;
    private Context context;
    private LayoutInflater layoutInflater;
    private static boolean changeDirection = false;
    private static int counter = 0;

    public CarouselPagerAdapter(Context context, List<Recipe> carouselRecipes){
        this.context = context;
        this.carouselRecipes = carouselRecipes;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return carouselRecipes.size();
    }
    /*public int getCount() {
        return Integer.MAX_VALUE;
    }*/

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.home_page_carousel_layout, container, false);
        if(carouselRecipes != null) {
            int realPosition = position % carouselRecipes.size();
            ImageView imageView = (ImageView) itemView.findViewById(R.id.carouselImageView);
            String imageUrl = BASE_URL + carouselRecipes.get(realPosition).getImg_name();
            new DownloadImageAsyncTask(imageView, null).execute(imageUrl);
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public void setCarouselRecipes(List<Recipe> carouselRecipes){
        this.carouselRecipes.clear();
        this.carouselRecipes = carouselRecipes;
    }

    public void startAutoScroll(final ViewPager viewPager, int delay) {
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                int currentItem = 0;
                if (!carouselRecipes.isEmpty()) {
                    //currentItem = counter % carouselRecipes.size();//viewPager.getCurrentItem();
                    viewPager.setCurrentItem(counter, true);
                    if (counter == carouselRecipes.size()-1 || counter == 0)
                        changeDirection = !changeDirection;
                    if (changeDirection)
                        counter++;
                    else
                        counter--;
                }
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, delay, delay);
    }

}
