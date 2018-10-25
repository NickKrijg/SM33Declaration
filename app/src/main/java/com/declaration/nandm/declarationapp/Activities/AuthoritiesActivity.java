package com.declaration.nandm.declarationapp.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewDebug;

import com.declaration.nandm.declarationapp.Domain.Authority;
import com.declaration.nandm.declarationapp.Domain.User;
import com.declaration.nandm.declarationapp.Layout.AllAuthoritiesAdapter;
import com.declaration.nandm.declarationapp.Layout.AllDeclarationsAdapter;
import com.declaration.nandm.declarationapp.Layout.GridSpacingItemDecoration;
import com.declaration.nandm.declarationapp.R;

import java.io.Serializable;
import java.util.List;

public class AuthoritiesActivity extends AppCompatActivity {

    private User user;
    private RecyclerView recyclerView;
    private AllAuthoritiesAdapter adapter;
    private FloatingActionButton fabAddAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorities);

        fabAddAuth = (FloatingActionButton) findViewById(R.id.fabAddAuth);

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
        fabAddAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AuthoritiesActivity.this, AddAuthorityActivity.class);
                i.putExtra("authorities",(Serializable) (List<Authority>)getIntent().getSerializableExtra("authorities"));
                i.putExtra("user", user);
                startActivityForResult(i, 23);
            }
        });
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 23 && resultCode == RESULT_OK){
            user = (User) data.getSerializableExtra("user");
            adapter.setList(user.getAuthority());
            adapter.notifyDataSetChanged();
        }
    }
}
