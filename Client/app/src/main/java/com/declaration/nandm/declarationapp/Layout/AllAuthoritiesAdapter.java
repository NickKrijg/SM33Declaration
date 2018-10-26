package com.declaration.nandm.declarationapp.Layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.declaration.nandm.declarationapp.Domain.Authority;
import com.declaration.nandm.declarationapp.R;

import java.util.ArrayList;
import java.util.List;

public class AllAuthoritiesAdapter extends RecyclerView.Adapter<AuthorityViewHolder> {

    private List<Authority> authorities;
    private Context mContext;

    public AllAuthoritiesAdapter(Context mContext){
        authorities = new ArrayList<>();
        this.mContext = mContext;
    }

    public void setList(List<Authority> authorities){
//        this.authorities.clear();
        this.authorities = authorities;
    }

    @NonNull
    @Override
    public AuthorityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.authorities_cardview, viewGroup, false);

        return new AuthorityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorityViewHolder holder, int position) {
        Authority authority = authorities.get(position);
        holder.authorityName.setText(authority.getName());
    }

    @Override
    public int getItemCount() {
        return authorities.size();
    }
}
