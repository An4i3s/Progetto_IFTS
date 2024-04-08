package com.example.cookidea_app.Adapters;



import static com.example.cookidea_app.Activities.CookIdeaApp.BASE_URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookidea_app.Backend.DownloadImageAsyncTask;
import com.example.cookidea_app.ModelClasses.WeeklyMenu;
import com.example.cookidea_app.R;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class MenuListAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    public  List<WeeklyMenu> weeklyMenus;
    LayoutInflater inflater;

    public static class HeaderMenuViewHolder {
        TextView weekDay;
        TextView date;
    }

    public static class MenuViewHolder {
        ImageView image;
        TextView name;
        TextView meal;

    }

    public MenuListAdapter(Context context, List<WeeklyMenu> weeklyMenus){
        inflater = LayoutInflater.from(context);
        this.weeklyMenus = weeklyMenus;
    }

    @Override
    public int getCount() {
        return weeklyMenus.size();
    }

    @Override
    public Object getItem(int position) {
        return weeklyMenus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuViewHolder menuViewHolder;

        if (convertView == null) {
            menuViewHolder = new MenuViewHolder();
            convertView = inflater.inflate(R.layout.menu_page_recipe_list, parent, false);

            menuViewHolder.image = (ImageView) convertView.findViewById(R.id.imageRecipeMenuImageView);
            menuViewHolder.name = (TextView) convertView.findViewById(R.id.nameRecipeMenuTextView);
            menuViewHolder.meal = (TextView) convertView.findViewById(R.id.mealRecipeMenuTextView);

            convertView.setTag(menuViewHolder);
        } else {
            menuViewHolder = (MenuViewHolder) convertView.getTag();
        }
        String imgUrl = BASE_URL + "/static/recipes" + weeklyMenus.get(position).getRecipeName();
        new DownloadImageAsyncTask(menuViewHolder.image, new DownloadImageAsyncTask.ImageDownloadCallback() {
            @Override
            public void downloaded(Bitmap img) {
                menuViewHolder.image.setImageBitmap(img);
            }
        }).execute(imgUrl);

        menuViewHolder.name.setText(weeklyMenus.get(position).getRecipeName());
        menuViewHolder.meal.setText(weeklyMenus.get(position).getMeal());

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderMenuViewHolder headerMenuViewHolder;

        if (convertView == null) {
            headerMenuViewHolder = new HeaderMenuViewHolder();
            convertView = inflater.inflate(R.layout.menu_header_recipe_page_list, parent, false);

            headerMenuViewHolder.weekDay = (TextView) convertView.findViewById(R.id.menuWeekDay);
            headerMenuViewHolder.date = (TextView) convertView.findViewById(R.id.menuDate);

            convertView.setTag(headerMenuViewHolder);

        } else {
            headerMenuViewHolder = (HeaderMenuViewHolder) convertView.getTag();
        }

        headerMenuViewHolder.weekDay.setText(convertWeekDay(weeklyMenus.get(position).getMenuDate()));
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return weeklyMenus.get(position).getId();
    }

    String convertWeekDay(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek){
            case 1:
                return "Domenica";
            case 2:
                return "Lunedì";
            case 3:
                return "Martedì";
            case 4:
                return "Mercoledì";
            case 5:
                return "Giovedì";
            case 6:
                return "Venerdì";
            case 7:
                return "Sabato";
            default:
                return "Error";
        }
    }
}
