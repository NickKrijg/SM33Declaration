package com.declaration.nandm.declarationapp.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.declaration.nandm.declarationapp.Data.DataReceiver;
import com.declaration.nandm.declarationapp.Data.MockDataClass;
import com.declaration.nandm.declarationapp.Domain.Authority;
import com.declaration.nandm.declarationapp.Domain.Declaration;
import com.declaration.nandm.declarationapp.Domain.State;
import com.declaration.nandm.declarationapp.Domain.User;

import com.declaration.nandm.declarationapp.Layout.AllDeclarationsAdapter;
import com.declaration.nandm.declarationapp.Layout.GridSpacingItemDecoration;
import com.declaration.nandm.declarationapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    private RecyclerView recyclerView;
    private AllDeclarationsAdapter adapter;
    private List<Declaration> declarations;
    private List<Authority> authorities;
    private ImageView settings;

    private DataReceiver dataReceiver;

    private FirebaseDatabase mRoot;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton)findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = dataReceiver.getUser();
                Intent intent = new Intent(MainActivity.this, NewDeclarationActivity.class);
                if (user != null){
                    intent.putExtra("user", user);
                    startActivityForResult(intent, 1);
                }
            }
        });

        settings = (ImageView)findViewById(R.id.settings);


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = dataReceiver.getUser();
                authorities = dataReceiver.getAuthorities();
                Intent intent = new Intent(MainActivity.this, AuthoritiesActivity.class);
                if (user != null){
                    intent.putExtra("user", user);
                    intent.putExtra("authorities", (Serializable) authorities);
                    startActivityForResult(intent, 1);
                }
            }
        });

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


        //Get data firebase
        mRoot = FirebaseDatabase.getInstance();




        // Fill database with mock data, uncomment when db is empty
//        fillDatabaseMockData();

        // Make Mock List
//        PopulateList();
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

    private void PopulateList(){
        Declaration d = new Declaration("Bier Halen","Bier halen voor de vergadering");
        declarations.add(d);

        d = new Declaration("Muziekinstrument","muziek instrument halen voor de kapotgemaakten fluit");
        declarations.add(d);

        d = new Declaration("Muziekinstrument","muziek instrument halen voor de kapotgemaakten fluit");
        declarations.add(d);

        d = new Declaration("Muziekinstrument","muziek instrument halen voor de kapotgemaakten fluit");
        declarations.add(d);

        d = new Declaration("Muziekinstrument","muziek instrument halen voor de kapotgemaakten fluit");
        declarations.add(d);

        d = new Declaration("Muziekinstrument","muziek instrument halen voor de kapotgemaakten fluit");
        declarations.add(d);

        d = new Declaration("Muziekinstrument","muziek instrument halen voor de kapotgemaakten fluit");
        declarations.add(d);

        d = new Declaration("Muziekinstrument","muziek instrument halen voor de kapotgemaakten fluit");
        declarations.add(d);

        d = new Declaration("Muziekinstrument","muziek instrument halen voor de kapotgemaakten fluit");
        declarations.add(d);

        d = new Declaration("Muziekinstrument","muziek instrument halen voor de kapotgemaakten fluit");
        declarations.add(d);

        d = new Declaration("Muziekinstrument","muziek instrument halen voor de kapotgemaakten fluit");
        declarations.add(d);

        d = new Declaration("Max","moet peopen");
        declarations.add(d);

        d = new Declaration("Max","moet peopen");
        declarations.add(d);

        d = new Declaration("Max","moet peopen");
        declarations.add(d);

        adapter.notifyDataSetChanged();
    }

    private void fillDatabaseMockData(){

        //Mock data for Authorities

        DatabaseReference authRef = mRoot.getReference("Authorities");

        for(String line:new MockDataClass().getAuthorities()){
            authRef.push().setValue(line);
        }

        //Mock user data
        DatabaseReference databaseReference = mRoot.getReference("Users");
        User u = new User();

        ArrayList<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("Sportvereniging"));
        authorities.add(new Authority("Muziekvereniging"));
        authorities.add(new Authority("Studentenvereniging"));

        u.setAuthority(authorities);
        u.setEmail("nick@jids.nl");
        u.setName("HENKEIEEEE");
        databaseReference.push().setValue(u);

        //Mock data for declarations
        DatabaseReference reference = mRoot.getReference("Declarations");

        for (int i =0; i<10;i++){
            Declaration dec = new Declaration();
            dec.setAuthority("Authority " + i);
            dec.setUserId("notNick");
            dec.setDescription(Integer.toString(i));
            dec.setPrice(i);
            reference.push().setValue(dec);
        }

        for (int i =0; i<10;i++){
            Declaration dec = new Declaration();
            dec.setAuthority("Authority " + i);
            dec.setUserId("nick@jids.nl");
            dec.setDescription(Integer.toString(i));
            dec.setPrice(i);
            if (i%2 == 1){
                dec.setState(State.Accepted);
            }
            else{
                dec.setState(State.Declined);
            }
            reference.push().setValue(dec);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        dataReceiver = new DataReceiver("nick@jids.nl", adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

    }
}