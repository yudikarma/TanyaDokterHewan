package com.example.jon_snow.tanyadokterhewan.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jon_snow.tanyadokterhewan.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfilActivity extends AppCompatActivity {
    TextView profil_displayname,profil_status,profil_totalfriend;
    ImageView profil_image;

    //FIrebase
    DatabaseReference mDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        String userid = getIntent().getStringExtra("user_id");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);


        profil_displayname = (TextView) findViewById(R.id.profil_displayname);
        profil_status = (TextView) findViewById(R.id.profil_satus);
        profil_totalfriend = (TextView) findViewById(R.id.profil_totalfriend);
        profil_image = (ImageView) findViewById(R.id.profil_image);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String display_name = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();
                String address = dataSnapshot.child("address").getValue().toString();
                String no_hp = dataSnapshot.child("no_hp").getValue().toString();

                profil_displayname.setText(display_name);
                profil_status.setText(status);
                Picasso.with(ProfilActivity.this).load(image).placeholder(R.drawable.colapsing_bacground).into(profil_image);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
