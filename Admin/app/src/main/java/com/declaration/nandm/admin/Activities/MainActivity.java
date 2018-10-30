package com.declaration.nandm.admin.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.declaration.nandm.admin.Layout.SwipeController;
import com.declaration.nandm.admin.Layout.SwipeControllerActions;
import com.declaration.nandm.admin.R;
import com.declaration.nandm.admin.Data.DataReceiver;
import com.declaration.nandm.admin.Domain.Authority;
import com.declaration.nandm.admin.Domain.Declaration;
import com.declaration.nandm.admin.Domain.State;
import com.declaration.nandm.admin.Domain.User;

import com.declaration.nandm.admin.Layout.AllDeclarationsAdapter;
import com.declaration.nandm.admin.Layout.GridSpacingItemDecoration;
import com.declaration.nandm.admin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AllDeclarationsAdapter adapter;
    private List<Declaration> declarations;

    private DataReceiver dataReceiver;

    private FirebaseDatabase mRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar Collapsing

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        // Call Adapter
        adapter = new AllDeclarationsAdapter(this);

        //Recycle View

        recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(1), true));

        recyclerView.setAdapter(adapter);

        // Set up swipecontrolleractions
        final SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                updateDec(position, false);
            }
            @Override
            public void onLeftClicked(int position) {
                updateDec(position, true);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(swipeController);
        helper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });


        //Get data firebase
        mRoot = FirebaseDatabase.getInstance();
    }

    private void updateDec(int position, boolean accepted) {
        declarations = dataReceiver.getDeclarations();
        Declaration declaration = declarations.get(position);
        DatabaseReference decRef = mRoot.getReference("Declarations/" + declaration.getKey());

        if (accepted){
            declaration.setState(State.Accepted);
        }
        else{
            declaration.setState(State.Declined);
        }
        decRef.setValue(declaration);
        System.out.println("something changed djsiofjaiodfgajdiofjsdioajf");

        adapter.notifyDataSetChanged();
    }

    // Toolbar Settings
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        dataReceiver = new DataReceiver("Sportvereniging", adapter);
    }
}

