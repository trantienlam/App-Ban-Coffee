package com.example.coffeeshop.Services;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.example.coffeeshop.R;

public class DrawableHandler {

    public static int getImgFromDrawable(Context ctx, String filename)
    {
        Context context = ctx;
        int resourceId = context.getResources().getIdentifier(filename, "drawable", context.getPackageName());
        //return context.getResources().getDrawable(resourceId); get drawable
        Integer i = R.drawable.coffee;
        return  resourceId == 0 ? i:    resourceId;
    }
}
