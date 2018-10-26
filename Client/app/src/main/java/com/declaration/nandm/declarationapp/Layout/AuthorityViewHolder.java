package com.declaration.nandm.declarationapp.Layout;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.declaration.nandm.declarationapp.R;

public class AuthorityViewHolder extends RecyclerView.ViewHolder {
    public TextView authorityName;

    public AuthorityViewHolder(@NonNull View itemView) {
        super(itemView);
        authorityName = (TextView) itemView.findViewById(R.id.authorityName);
    }
}
