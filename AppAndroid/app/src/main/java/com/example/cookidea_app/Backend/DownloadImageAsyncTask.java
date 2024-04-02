package com.example.cookidea_app.Backend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

    public interface ImageDownloadCallback {
        public void downloaded(String imageUrl, Bitmap img);
    }

    ImageView imgView = null;
    String imageUrl = null;
    ImageDownloadCallback callback = null;

    public DownloadImageAsyncTask(ImageView imgView, ImageDownloadCallback callback) {
        this.imgView = imgView;
        this.callback = callback;
    }

    protected Bitmap doInBackground(String... urls) {
        imageUrl = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new  java.net.URL(imageUrl).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        imgView.setImageBitmap(result);

        if(this.callback != null)
            callback.downloaded(imageUrl, result);
    }
}
