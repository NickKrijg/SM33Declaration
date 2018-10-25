package com.declaration.nandm.declarationapp.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
import android.view.View;

import com.declaration.nandm.declarationapp.Domain.Authority;
import com.declaration.nandm.declarationapp.Domain.User;
import com.declaration.nandm.declarationapp.Layout.AllAuthoritiesAdapter;
import com.declaration.nandm.declarationapp.Layout.GridSpacingItemDecoration;
import com.declaration.nandm.declarationapp.Layout.SwipeController;
import com.declaration.nandm.declarationapp.Layout.SwipeControllerActions;
import com.declaration.nandm.declarationapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        final SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                removeAuthority(user.getAuthority().get(position));
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

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

    private void removeAuthority(Authority authority) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users/" + user.getKey());

        List<Authority> temp = user.getAuthority();
        temp.remove(authority);
        user.setAuthority(temp);

        ref.setValue(user);

        adapter.setList(user.getAuthority());
        adapter.notifyDataSetChanged();
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
