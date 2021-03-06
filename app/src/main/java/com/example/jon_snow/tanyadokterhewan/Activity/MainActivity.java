package com.example.jon_snow.tanyadokterhewan.Activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.jon_snow.tanyadokterhewan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class MainActivity extends AppCompatActivity {


    //fIREBASE
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ImageView imgkeluar;
    private Toolbar mtToolbar;
    private LinearLayout pindahToChat,pindahToBerobat,pindahToRekamMedis,pindahToListDokter,pindahToPanduan,pindahToTentang;
    private DatabaseReference mUserRef;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private String background = "default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_pasien);
        mtToolbar = (Toolbar) findViewById(R.id.toolbarid);
        setSupportActionBar(mtToolbar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {


            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Pasien").child(mAuth.getCurrentUser().getUid());

        }


        pindahToChat = (LinearLayout) findViewById(R.id.layout_chat);
        pindahToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ChatActivity2.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        pindahToBerobat = findViewById(R.id.DaftarBerobat);
        pindahToBerobat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ListDaftarBerobat.class);
                startActivity(intent);
            }
        });
        pindahToRekamMedis = findViewById(R.id.rekammedis);
        pindahToRekamMedis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ListRekamMedis.class);
                startActivity(intent);
            }
        });

        pindahToListDokter = findViewById(R.id.pindahToListDokter);
        pindahToListDokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, UsersActivity.class);
                // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (background.equals("default")){
                    collapsingToolbarLayout.setBackgroundResource(R.drawable.matterial_background);
                    background = "matterial";
                }
                else if (background.equals("matterial")){
                    collapsingToolbarLayout.setBackgroundResource(R.drawable.colapsing_bacground);
                    background = "default";

                }else{
                    collapsingToolbarLayout.setBackgroundResource(R.drawable.colapsing_bacground);

                }

            }
        });

        pindahToPanduan = findViewById(R.id.panduan);
        pindahToPanduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Panduan.class);
                startActivity(intent);

            }
        });

        pindahToTentang = findViewById(R.id.tentang);
        pindahToTentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Tentang.class);
                startActivity(intent);

            }
        });




    }

    @Override
    public void onStart() {
        super.onStart();
        /*FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){

            sendTostart();

        } else {

            mUserRef.child("online").setValue("true");

        }*/
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (!(currentUser != null)) {
            // !User is signed in
           sendTostart();


        } else {
            mUserRef.child("online").setValue("true");

        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {

            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);

        }
    }

    private void sendTostart() {
        //Check i user is Sign-out
        Intent startIntent = new Intent(MainActivity.this, FlashScreen.class);
        startActivity(startIntent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);


        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.logout_menu_main) {
            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);

            FirebaseAuth.getInstance().signOut();
            sendTostart();
        } else if (item.getItemId() == R.id.acount_setting_main) {
            Intent i = new Intent(MainActivity.this, SettingActivity.class);
            /*i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
            startActivity(i);
        } /*else if (item.getItemId() == R.id.All_user) {
            Intent i = new Intent(MainActivity.this, UsersActivity.class);
           // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);

        }*/
        return true;
    }
}