package com.example.capstoneapp.ui;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstoneapp.R;

public class UiUtils {
    public static void setViewImage(Context context, ImageView imageView, String imageUrl, Transformation transformation, int defaultImage) {
        if (!imageUrl.isEmpty()) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (transformation != null) {
                Glide.with(context)
                        .load(imageUrl)
                        .transform(transformation)
                        .into(imageView);
            } else {
                Glide.with(context)
                        .load(imageUrl)
                        .into(imageView);
            }
        } else {
            imageView.setImageResource(defaultImage);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }

    public static void setViewText(Context context, TextView textView, String text) {
        if (text.isEmpty()) {
            textView.setText(context.getString(R.string.data_not_available));
        } else {
            textView.setText(text);
        }
    }

}
