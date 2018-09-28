package com.declaration.nandm.declarationapp.Layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.declaration.nandm.declarationapp.Domain.Declaration;
import com.declaration.nandm.declarationapp.R;

import java.util.List;

public class AllDeclarationsAdapter extends RecyclerView.Adapter<MyViewHolder> {


    private List<Declaration> declarations;
    private Context mContext;

    public AllDeclarationsAdapter(List<Declaration> declarations, Context mContext) {
        this.declarations = declarations;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.declarations_cardview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Declaration declaration = declarations.get(position);
        holder.title.setText(declaration.getTitle());
        holder.description.setText(declaration.getDescription());

    }

    @Override
    public int getItemCount() {
        return declarations.size();
    }
}


