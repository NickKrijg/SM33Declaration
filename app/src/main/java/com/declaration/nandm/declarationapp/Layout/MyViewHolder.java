package com.declaration.nandm.declarationapp.Layout;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.declaration.nandm.declarationapp.R;


public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView title, description, price;
    public ImageView state;


    public MyViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.title);
        description = (TextView) view.findViewById(R.id.description);
        state = (ImageView) view.findViewById(R.id.overflow);
        price = (TextView) view.findViewById(R.id.price);
    }


}