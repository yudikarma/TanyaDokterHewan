package com.example.jon_snow.tanyadokterhewan;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText displayname;
    private EditText email;
    private EditText password;
    private Button register;
    private Toolbar mToolbar;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mToolbar = (Toolbar) findViewById(R.id.regist_appbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Registrasi");


        displayname = (EditText) findViewById(R.id.displayname);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edisplayname = displayname.getText().toString();
                String eemail = email.getText().toString();
                String epassword = password.getText().toString();

                register_user(edisplayname, eemail, epassword);

            }
        });
    }

    private void register_user(String edisplayname, String eemail, String epassword) {
        mAuth.createUserWithEmailAndPassword(eemail,epassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isComplete()){
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(RegisterActivity.this,"You Gak bisa loginbangsat",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}