package com.example.jon_snow.tanyadokterhewan.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.Toolbar;

import com.example.jon_snow.tanyadokterhewan.R;
import com.example.jon_snow.tanyadokterhewan.Model.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersActivity extends AppCompatActivity{
    private Toolbar mToolbar;
    private RecyclerView mListView;
    private String nambahaja;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference userDatabase;
    //private FirebaseListAdapter adapter;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseRecyclerAdapter<Users,UserviewHolder> adapter;

    private ProgressDialog mProgressDialog;
    private ImageView statusOnline;
    private Users users1;
    private String muser;
    private  Query query;

    private TextView notifnull;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        userDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabaseReference.keepSynced(true);
        muser = FirebaseAuth.getInstance().getUid();


        mToolbar = (Toolbar) findViewById(R.id.user_appbar);
        setSupportActionBar(mToolbar);

        notifnull = findViewById(R.id.notifnulluser);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle("Dokter List");
        getSupportActionBar().setTitle("List Dokter");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Intent intent  = new Intent(UsersActivity.this,MainActivity.class);

                startActivity(intent);*/

                finish();
            }
        });
        statusOnline = (ImageView) findViewById(R.id.user_single_online_icon);

        mListView = (RecyclerView) findViewById(R.id.user_list);
        mListView.setHasFixedSize(true);
        mListView.setLayoutManager(new LinearLayoutManager(this));

      /*  userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    users1 = dataSnapshot1.getValue(Users.class);
                    if (!users1.getRole().equals("U") && users1.getUid().equals(muser)){

                         query = FirebaseDatabase.getInstance().getReference().child("Users").limitToLast(50);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        Query query = FirebaseDatabase.getInstance().getReference().child("Users").child("Dokters").limitToLast(50);



        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(query, Users.class)
                        .setLifecycleOwner(this)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Users, UserviewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserviewHolder holder, int position, @NonNull Users model) {


                holder.setNama(model.getName());
                holder.setstatus(model.getStatus());
                holder.setMcCircleImageView(model.getThumb_image());


              /*mProgressDialog.dismiss();*/


                final String user_id = getRef(position).getKey();

                holder.mView.setOnClickListener(    new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(UsersActivity.this,ProfilActivity.class);
                        i.putExtra("user_id",user_id);
                        startActivity(i);

                    }
                });

            }


            @NonNull
            @Override
            public UserviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_singgle_layout,parent,false);
                return new UserviewHolder(mView);



            }

        };
        FirebaseDatabase.getInstance().getReference().child("Users").child("Dokters").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long jumlah = dataSnapshot.getChildrenCount();
                if (jumlah>0){
                    mListView.setAdapter(adapter);
                }else {
                    notifnull.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
       /* mProgressDialog = new ProgressDialog(UsersActivity.this);
        mProgressDialog.setTitle("load all user data..");
        mProgressDialog.setMessage("please wait..");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();*/


      adapter.startListening();



    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public class UserviewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView mdisplayname,mstatus ;
        CircleImageView mcCircleImageView;
        public UserviewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            mdisplayname = (TextView) mView.findViewById(R.id.user_singgle_name);
            mstatus  = (TextView) mView.findViewById(R.id.user_single_status);
            mcCircleImageView = (CircleImageView) mView.findViewById(R.id.profil_single_layout);



        }
        public void setNama(String display_name){

            mdisplayname.setText(display_name);

        }
        public void setstatus(String status){
            mstatus.setText(status);
        }
        public  void setMcCircleImageView(final String img_uri){
            //Picasso.with(UsersActivity.this).load(img_uri).placeholder(R.drawable.user).into(mcCircleImageView);
            if (!img_uri.equals("default")){
                Picasso.with(UsersActivity.this).load(img_uri).networkPolicy(NetworkPolicy.OFFLINE)
                        .placeholder(R.drawable.user).into(mcCircleImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(UsersActivity.this).load(img_uri).placeholder(R.drawable.user).into(mcCircleImageView);

                    }
                });

            }else{
                Picasso.with(UsersActivity.this).load(R.drawable.user).into(mcCircleImageView);

            }
        }
    }



}
