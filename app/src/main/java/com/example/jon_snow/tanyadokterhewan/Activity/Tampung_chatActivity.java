package com.example.jon_snow.tanyadokterhewan.Activity;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.jon_snow.tanyadokterhewan.Adapter.SectionPageAdapter;
import com.example.jon_snow.tanyadokterhewan.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Tampung_chatActivity extends AppCompatActivity {
    private Toolbar mtoolbar;
    private String mChatuser;
    private String musername;
    private DatabaseReference mRootDatabaseReference;

    private TextView mTitleView;
    private TextView mLastView;
    private CircleImageView mProfilImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampung_chat);
        mtoolbar = (Toolbar) findViewById(R.id.tampungchat_app_bar);

        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowCustomEnabled(true);
        mChatuser = getIntent().getStringExtra("user_id");
        musername = getIntent().getStringExtra("user_name");

        mRootDatabaseReference = FirebaseDatabase.getInstance().getReference();
        /*getSupportActionBar().setTitle(musername);



        mRootDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mRootDatabaseReference.child("Users").child(mChatuser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String chatUsername  = dataSnapshot.child("name").getValue().toString();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionbar_view = layoutInflater.inflate(R.layout.chat_custom_bar,null);
        actionBar.setCustomView(actionbar_view);

       /* ==========Custom action bar item==========*/

       mTitleView = (TextView) findViewById(R.id.custom_bar_title);
       mLastView = (TextView) findViewById(R.id.custom_bar_seen);
       mProfilImage = (CircleImageView) findViewById(R.id.custom_bar_image);

       mTitleView.setText(musername);
       mRootDatabaseReference.child("Users").child(mChatuser).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               String online = dataSnapshot.child("online").getValue().toString();
               String image = dataSnapshot.child("thumb_image").getValue().toString();

               if (online.equals("true")){
                   mLastView.setText("Online");
               }else{
                   mLastView.setText(online);
               }


           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });












    }


}
