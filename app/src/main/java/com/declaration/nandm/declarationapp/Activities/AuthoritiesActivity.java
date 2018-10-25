package com.declaration.nandm.declarationapp.Activities;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import com.declaration.nandm.declarationapp.Domain.User;
import com.declaration.nandm.declarationapp.Layout.AllAuthoritiesAdapter;
import com.declaration.nandm.declarationapp.Layout.AllDeclarationsAdapter;
import com.declaration.nandm.declarationapp.Layout.GridSpacingItemDecoration;
import com.declaration.nandm.declarationapp.R;

public class AuthoritiesActivity extends AppCompatActivity {

    private User user;
    private RecyclerView recyclerView;
    private AllAuthoritiesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorities);

        if(user == null){
            user = (User) getIntent().getSerializableExtra("user");
        }

        adapter = new AllAuthoritiesAdapter(this);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,dpToPx(1),true));

        recyclerView.setAdapter(adapter);

        if (user !=null){
            adapter.setList(user.getAuthority());
            adapter.notifyDataSetChanged();
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
