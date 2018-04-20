package com.example.jon_snow.tanyadokterhewan;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    //fIREBASE
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ImageView imgkeluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_pasien);
        //fIREBASE
        mAuth = FirebaseAuth.getInstance();

        imgkeluar = (ImageView) findViewById(R.id.keluar);
        imgkeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                sendTostart();
            }
        });






    }
    @Override
    public void onStart() {
        super.onStart();
       sendTostart();
    }

    private void sendTostart() {
        //Check i user is Sign-in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (!(currentUser != null)) {
            // User is signed in
            Intent intent = new Intent(MainActivity.this,Flashscreen.class);
            startActivity(intent);
            finish();


        } else {
            // No user is signed in

        }

    }


}
