package com.example.jon_snow.tanyadokterhewan.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jon_snow.tanyadokterhewan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {
    private DatabaseReference mDatabaseReference;
    private FirebaseUser mCurrentuser;

    private static final int GALLERY_PICK = 1;

    //Android Layout
    private CircleImageView mCircleImageView;
    private TextView mDisplayname;
    private TextView mstatus;
    private Button btn_changeimage_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mCircleImageView = (CircleImageView) findViewById(R.id.circleImageView);
        mDisplayname = (TextView) findViewById(R.id.display_name_setting);
        mstatus = (TextView) findViewById(R.id.status_setting);
        btn_changeimage_setting = (Button) findViewById(R.id.change_image_setting);

        mCurrentuser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = mCurrentuser.getUid();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String display_name = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();
                String address = dataSnapshot.child("address").getValue().toString();
                String no_hp = dataSnapshot.child("no_hp").getValue().toString();

                mDisplayname.setText(display_name);
                mstatus.setText(status);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //onclik image change
        btn_changeimage_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galery_intent = new Intent();
                galery_intent.setType("image/*");
                galery_intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galery_intent,"Select Image"),GALLERY_PICK);
            }
        });
    }
}
