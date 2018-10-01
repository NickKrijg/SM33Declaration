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

import com.declaration.nandm.declarationapp.Domain.Declaration;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    private RecyclerView recyclerView;
    private AllDeclarationsAdapter adapter;
    private List<Declaration> declarations;

    private FirebaseDatabase mRoot;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton)findViewById(R.id.floatingActionButton);

        fab.setEnabled(false);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewDeclarationActivity.class);
                if (user != null){
                    intent.putExtra("user", user);
                    startActivity(intent);
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

        user = new User();

        getUser("nick");
        getDeclaration("nick");



        // Fill database with mock data, uncomment when db is empty
//         fillDatabaseMockData();

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

    /**
     * Gets user information and stores it into the user variable of this activity.
     * @param email unique String in Firebase.
     */
    private void getUser(String email){
        DatabaseReference userRef = mRoot.getReference("Users/" + email);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                fab.setEnabled(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Gets all the declarations of a specific user. And stores them into the list @declaration,
     * and updates/notifies the adapter.
     * @param email unique String in Firebase.
     */
    private void getDeclaration(String email){
        DatabaseReference decRef = mRoot.getReference("Declarations");
        Query query = decRef.orderByChild("userId").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && user != null){
                    List<Declaration> currentList = new ArrayList<>();
                    for(DataSnapshot declaration: dataSnapshot.getChildren()){
                        currentList.add(declaration.getValue(Declaration.class));
                    }
                    user.getDeclaration().clear();
                    user.setDeclaration(currentList);
                    adapter.setList(user.getDeclaration());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fillDatabaseMockData(){
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
            dec.setUserId("nick");
            dec.setDescription(Integer.toString(i));
            dec.setPrice(i);
            reference.push().setValue(dec);
        }
    }
}
