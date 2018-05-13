package com.example.jon_snow.tanyadokterhewan.Activity;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jon_snow.tanyadokterhewan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;

public class ProfilActivity extends AppCompatActivity {
    TextView profil_displayname,profil_status,profil_totalfriend;
    ImageView profil_image;
    Button btn_sendrequest,btn_declineFriend;

    private ProgressDialog mProgressDialog;

    //FIrebase database for user information
    private DatabaseReference mDatabaseReference;

    //firebase database for request friend
    private DatabaseReference mRequestDatabaseReference;

    //firebase database to get userid userlogin or user add friend
    private FirebaseUser mFirebaseUser;

    //firebase database for Friends
    private DatabaseReference mFriendsDatabaseReference;

    //string for status friend, "send","accep","friend",
    private String mCurrentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        //get userid onclick user or user yang diklik
        final String userid = getIntent().getStringExtra("user_id");
        //default status friend
        mCurrentState = "not_friend";

        //inisialisasi firebase
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);   //firebase database user
        mRequestDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Friend_request");  //firebase database reqfriend
        mFriendsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Friends");
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //layout
        profil_displayname = (TextView) findViewById(R.id.profil_displayname);
        profil_status = (TextView) findViewById(R.id.profil_satus);
        profil_totalfriend = (TextView) findViewById(R.id.profil_totalfriend);
        profil_image = (ImageView) findViewById(R.id.profil_image);
        btn_sendrequest = (Button) findViewById(R.id.profil_btn_sendrequest);
        btn_declineFriend = (Button) findViewById(R.id.profil_btn_decline);
        mProgressDialog = new ProgressDialog(ProfilActivity.this);
        mProgressDialog.setTitle("loading user data");
        mProgressDialog.setMessage("please wait..");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        //User onclick information
        //firebase information user
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get data from firebase
                String display_name = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();
                String address = dataSnapshot.child("address").getValue().toString();
                String no_hp = dataSnapshot.child("no_hp").getValue().toString();

                //Display Information
                profil_displayname.setText(display_name);
                profil_status.setText(status);
                Picasso.with(ProfilActivity.this).load(image).placeholder(R.drawable.colapsing_bacground).into(profil_image);

                // ==================== FRIEND LIST / Request Feature =====//

            //firebase database request friend
                mRequestDatabaseReference.child(mFirebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() { //selection child user.getuid();
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
            //Menampilkan informasi load infromasi user, apakah statusnya send request = btn.setttext("cencel friend request"), // dan accep friend;
                        if (dataSnapshot.hasChild(userid)){
                            String req_type = dataSnapshot.child(userid).child("request_type").getValue().toString();
                            if (req_type.equals("received")){
                                mCurrentState = "req_received";
                                btn_sendrequest.setText("Accept Friend Request");

                            }else if (req_type.equals("send")){
                                mCurrentState = "req_send";
                                btn_sendrequest.setText("Cancel Request Friend");
                            }
                            mProgressDialog.dismiss();
                        }else{
                            mFriendsDatabaseReference.child(mFirebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.hasChild(userid)){
                                        mCurrentState = "friend";
                                        btn_sendrequest.setText("Unfriend this person");
                                    }
                                    mProgressDialog.dismiss();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    mProgressDialog.dismiss();

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn_sendrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_sendrequest.setEnabled(false);
                // ===== IF NOT FRIEND STATE
                if (mCurrentState.equals("not_friend")){


                    mRequestDatabaseReference.child(mFirebaseUser.getUid()).child(userid).child("request_type")
                            .setValue("send").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                mRequestDatabaseReference.child(userid).child(mFirebaseUser.getUid()).child("request_type").setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        mCurrentState = "req_send";
                                        btn_sendrequest.setText("Cancel Friend Request");
                                        Toast.makeText(ProfilActivity.this,"Request Send Succes",Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }else {
                                Toast.makeText(ProfilActivity.this,"Failled Sending Request",Toast.LENGTH_LONG).show();
                                mProgressDialog.hide();
                            }
                            btn_sendrequest.setEnabled(true);
                        }
                    });


                }
                //============ IF CANCEL FRIEND REQUEST STATE//

                if (mCurrentState.equals("req_send")){
                    mRequestDatabaseReference.child(mFirebaseUser.getUid()).child(userid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mRequestDatabaseReference.child(userid).child(mFirebaseUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    mCurrentState = "not_friend";
                                    btn_sendrequest.setText("Send Friend Request");
                                    btn_sendrequest.setEnabled(true);
                                }
                            });
                        }
                    });
                }

                //============ IF state is RECEIVED or ACCEP REQUEST ==========//

                if (mCurrentState.equals("req_received")){

                    final String currentDate = DateFormat.getDateTimeInstance().format(new Date());
                    mFriendsDatabaseReference.child(mFirebaseUser.getUid()).child(userid).child(userid).setValue(currentDate).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            mFriendsDatabaseReference.child(userid).child(mFirebaseUser.getUid()).setValue(currentDate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Hapus Request Add friend
                                    mRequestDatabaseReference.child(mFirebaseUser.getUid()).child(userid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            mRequestDatabaseReference.child(userid).child(mFirebaseUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    btn_sendrequest.setEnabled(true);
                                                    mCurrentState = "friend";
                                                    btn_sendrequest.setText("Unfriend");

                                                }
                                            });
                                        }
                                    });


                                }
                            });

                        }
                    });


                }
                //============ IF UNFRIEND FRIEND REQUEST STATE//

                if (mCurrentState.equals("friend")){
                    mFriendsDatabaseReference.child(mFirebaseUser.getUid()).child(userid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mFriendsDatabaseReference.child(userid).child(mFirebaseUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    mCurrentState = "not_friend";
                                    btn_sendrequest.setText("Send Friend Request");
                                    btn_sendrequest.setEnabled(true);
                                }
                            });
                        }
                    });
                }
            }
        });


    }
}
