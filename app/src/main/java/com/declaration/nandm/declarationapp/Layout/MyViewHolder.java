package com.declaration.nandm.declarationapp.Layout;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.declaration.nandm.declarationapp.R;


public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title, description;

    public MyViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.title);
        description = (TextView) view.findViewById(R.id.description);
    }
}