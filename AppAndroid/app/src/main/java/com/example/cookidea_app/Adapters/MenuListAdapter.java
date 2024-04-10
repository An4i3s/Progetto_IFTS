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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class MenuListAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    public  List<WeeklyMenu> weeklyMenus;
    LayoutInflater inflater;
    //MenuViewHolder menuViewHolder;
    //HeaderMenuViewHolder headerMenuViewHolder;



    /*public static class HeaderMenuViewHolder {
        TextView weekDay;
        TextView date;
    }*/

    /*public static class MenuViewHolder {
        TextView dayOfWeek;
        ImageView image;
        TextView name;
        TextView meal;

    }*/

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

        convertView = inflater.inflate(R.layout.menu_page_recipe_list, parent, false);

        TextView dayOfWeek = (TextView) convertView.findViewById(R.id.dayOfWeek);
        ImageView image = (ImageView) convertView.findViewById(R.id.imageRecipeMenuImageView);
        TextView name = (TextView) convertView.findViewById(R.id.nameRecipeMenuTextView);
        TextView meal = (TextView) convertView.findViewById(R.id.mealRecipeMenuTextView);

        //convertView.setTag(menuViewHolder);

        String imgUrl = BASE_URL + "/static/recipes/" + weeklyMenus.get(position).getRecipeImg();
        new DownloadImageAsyncTask(image, new DownloadImageAsyncTask.ImageDownloadCallback() {
            @Override
            public void downloaded(Bitmap img) {
                image.setImageBitmap(img);
            }
        }).execute(imgUrl);


        //menuViewHolder = (MenuViewHolder) convertView.getTag();


        Date menuDate = weeklyMenus.get(position).getMenuDate();
        String daysOfWeek = convertWeekDay(menuDate);
        if (position > 0) {
            Date prevMenuDate = weeklyMenus.get(position).getMenuDate();
            if (!convertWeekDay(prevMenuDate).equals(daysOfWeek)) {
                daysOfWeek = convertWeekDay(prevMenuDate);
            }
        }

        dayOfWeek.setText(daysOfWeek);
        name.setText(weeklyMenus.get(position).getRecipeName());
        meal.setText(weeklyMenus.get(position).getMeal());

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {

        //headerMenuViewHolder = new HeaderMenuViewHolder();
        convertView = inflater.inflate(R.layout.menu_header_recipe_page_list, parent, false);

        TextView weekDay = (TextView) convertView.findViewById(R.id.menuWeekDay);
        TextView date = (TextView) convertView.findViewById(R.id.menuDate);

        //convertView.setTag(headerMenuViewHolder);
        //headerMenuViewHolder = (HeaderMenuViewHolder) convertView.getTag();


        Date menuDate = weeklyMenus.get(position).getMenuDate();

        String weekDays = convertWeekDay(menuDate);

        if (position > 0) {
            Date prevMenuDate = weeklyMenus.get(position).getMenuDate();
            if (!convertWeekDay(prevMenuDate).equals(weekDays)) {
                weekDays = convertWeekDay(prevMenuDate);
            }
        }

        weekDay.setText(weekDays);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(menuDate);
        date.setText(formattedDate);

        return convertView;
    }



    @Override
    public long getHeaderId(int position) {
        //return weeklyMenus.get(position).getId();
        return 0;
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
