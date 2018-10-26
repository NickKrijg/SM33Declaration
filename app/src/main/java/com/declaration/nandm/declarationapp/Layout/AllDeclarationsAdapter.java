package com.declaration.nandm.declarationapp.Layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.declaration.nandm.declarationapp.Domain.Declaration;
import com.declaration.nandm.declarationapp.R;

import java.util.ArrayList;
import java.util.List;

public class AllDeclarationsAdapter extends RecyclerView.Adapter<DeclarationViewHolder> {


    private List<Declaration> declarations;
    private Context mContext;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private int mExpandedPosition = -1;

    public AllDeclarationsAdapter(List<Declaration> declarations) {
        this.declarations = declarations;
        //set initial expanded state to false
        for (int i = 0; i < declarations.size(); i++) {
            expandState.append(i, false);
        }
    }

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
    public DeclarationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.declarations_cardview, parent, false);

        return new DeclarationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final DeclarationViewHolder holder, final int position) {

        Declaration declaration = declarations.get(position);
        holder.title.setText(declaration.getAuthority());
        holder.description.setText(declaration.getDescription());
        holder.price.setText("€" + String.valueOf(declaration.getPrice()));

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


        holder.title2.setText(declaration.getAuthority());
        holder.description2.setText(declaration.getDescription());
        holder.price2.setText("€" + String.valueOf(declaration.getPrice()));

        switch(declaration.getState()){
            case Declined:
                holder.state2.setImageResource(R.color.statusDeclined);
                break;
            case Accepted:
                holder.state2.setImageResource(R.color.statusAccepted);
                break;
            case Pending:
                holder.state2.setImageResource(R.color.statusPending);
                break;
            default:
                holder.state2.setImageResource(R.color.statusPending);
        }


        final boolean isExpanded = expandState.get(position);
        holder.expandableLayout.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.originalLayout.setVisibility(isExpanded?View.VISIBLE:View.VISIBLE);

       // holder.stateClick.setRotation(expandState.get(position) ? 180f : 0f);
        holder.originalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(holder.expandableLayout,holder.originalLayout ,position);
            }
        });
        holder.expandableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(holder.expandableLayout,holder.originalLayout ,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return declarations.size();
    }

    private void onClickButton(final ConstraintLayout expandableLayout, final ConstraintLayout originalLayout,final  int i) {

        //Simply set View to Gone if not expanded
        //Not necessary but I put simple rotation on button layout
        if (expandableLayout.getVisibility() == View.VISIBLE){
            originalLayout.setVisibility(View.VISIBLE);
            expandableLayout.setVisibility(View.GONE);
            expandState.put(i, false);
        }else{
            originalLayout.setVisibility(View.GONE);
            expandableLayout.setVisibility(View.VISIBLE);
            expandState.put(i, true);
        }
    }
}


