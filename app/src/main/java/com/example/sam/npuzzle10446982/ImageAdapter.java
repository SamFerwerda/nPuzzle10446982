/*
Sam Ferwerda
10446982
Vrijdag 12 December 2014
sam.ferwerda@hotmail.com
*/

package com.example.sam.npuzzle10446982;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Bitmap> crops;

    public ImageAdapter(Context c, ArrayList<Bitmap> crops) {
        mContext = c;
        this.crops=crops;
    }

    public int getCount() {
        return crops.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);

        } else {
            imageView = (ImageView) convertView;
        }
        // imageView.setTag(position);
        imageView.setImageBitmap(crops.get(position));
        return imageView;
    }

}