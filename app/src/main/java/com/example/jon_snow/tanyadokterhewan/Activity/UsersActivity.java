package com.example.jon_snow.tanyadokterhewan.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
//import android.widget.Toolbar;

import com.example.jon_snow.tanyadokterhewan.R;
import com.example.jon_snow.tanyadokterhewan.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class UsersActivity extends AppCompatActivity{
    private Toolbar mToolbar;
    private RecyclerView mListView;
    private String nambahaja;
    private DatabaseReference mDatabaseReference;
    //private FirebaseListAdapter adapter;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseRecyclerAdapter<Users,UserviewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        mToolbar = (Toolbar) findViewById(R.id.user_appbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("ALL User");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView = (RecyclerView) findViewById(R.id.user_list);
        mListView.setHasFixedSize(true);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        Query query = FirebaseDatabase.getInstance().getReference().child("Users").limitToLast(50);


        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(query, Users.class)
                        .setLifecycleOwner(this)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Users, UserviewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserviewHolder holder, int position, @NonNull Users model) {
                holder.setNama(model.getDisplay_name());

            }


            @NonNull
            @Override
            public UserviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_singgle_layout,parent,false);
                return new UserviewHolder(mView);



            }

        };
        mListView.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();


      adapter.startListening();



    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public class UserviewHolder extends RecyclerView.ViewHolder {
        View mView;
        public UserviewHolder(View itemView) {
            super(itemView);
            mView = itemView;


        }
        public void setNama(String display_name){
            TextView mdisplayname = (TextView) mView.findViewById(R.id.user_singgle_name);
            mdisplayname.setText(display_name);

        }
    }



}
