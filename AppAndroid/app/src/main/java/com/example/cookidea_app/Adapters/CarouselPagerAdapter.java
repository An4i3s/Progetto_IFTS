package com.example.cookidea_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.example.cookidea_app.Backend.DownloadImageAsyncTask;
import com.example.cookidea_app.R;

import java.util.List;

public class CarouselPagerAdapter extends PagerAdapter {
    private List<String> imagesURLs;
    private Context context;
    private LayoutInflater layoutInflater;

    public CarouselPagerAdapter(Context context, List<String> imagesURLs){
        this.context = context;
        this.imagesURLs = imagesURLs;
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

        int realPosition = position % imagesURLs.size();
        ImageView imageView = (ImageView) itemView.findViewById(R.id.carouselImageView);
        new DownloadImageAsyncTask(imageView, null).execute(imagesURLs.get(realPosition));


        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
