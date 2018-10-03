package com.declaration.nandm.declarationapp.Layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.declaration.nandm.declarationapp.Domain.Declaration;
import com.declaration.nandm.declarationapp.R;

import java.util.ArrayList;
import java.util.List;

public class AllDeclarationsAdapter extends RecyclerView.Adapter<MyViewHolder> {


    private List<Declaration> declarations;
    private Context mContext;

    public AllDeclarationsAdapter(Context mContext) {
        declarations = new ArrayList<>();
        this.mContext = mContext;
    }

    public void setList(List<Declaration> dec){
        declarations.clear();
        declarations = dec;
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
        holder.title.setText(declaration.getAuthority());
        holder.description.setText(declaration.getUserId());
        switch(declaration.getState()){
            case Declined:
                holder.state.setImageResource(R.color.statusDeclined);
                break;
            case Accepted:
                holder.state.setImageResource(R.color.statusAccepted);
                break;
            case Pending:
                holder.state.setImageResource(R.color.statusPending);
                break;
            default:
                holder.state.setImageResource(R.color.statusPending);
        }
    }

    @Override
    public int getItemCount() {
        return declarations.size();
    }
}


