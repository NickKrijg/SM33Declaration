package com.declaration.nandm.admin.Layout;


import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.declaration.nandm.admin.R;


public class DeclarationViewHolder extends RecyclerView.ViewHolder {

    public TextView title, description, price;
    public TextView title2, description2, price2;

    public ImageView state, state2, photoReceipt;
    public ConstraintLayout originalLayout, expandableLayout;


    public DeclarationViewHolder(View view) {
        super(view);
        title = (TextView) view.findViewById(R.id.title);
        description = (TextView) view.findViewById(R.id.description);
        state = (ImageView) view.findViewById(R.id.overflow);
        price = (TextView) view.findViewById(R.id.price);

        title2 = (TextView) view.findViewById(R.id.title2);
        description2 = (TextView) view.findViewById(R.id.description2);
        state2 = (ImageView) view.findViewById(R.id.overflow2);
        price2 = (TextView) view.findViewById(R.id.price2);
        photoReceipt = (ImageView)view.findViewById(R.id.photoReceipt);

        expandableLayout = (ConstraintLayout) view.findViewById((R.id.expandableLayout));
        originalLayout = (ConstraintLayout) view.findViewById((R.id.originalLayout));
    }


}