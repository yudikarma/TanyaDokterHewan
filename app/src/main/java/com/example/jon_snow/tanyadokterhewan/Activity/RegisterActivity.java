package com.example.jon_snow.tanyadokterhewan.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.jon_snow.tanyadokterhewan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout displayname;
    private TextInputLayout email;
    private TextInputLayout password;
    private Button register;
    private Toolbar mToolbar;
    private ProgressDialog mpProgressDialog;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mToolbar = (Toolbar) findViewById(R.id.regist_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Registrasi");


        displayname = (TextInputLayout) findViewById(R.id.reg_display_name);
        email = (TextInputLayout) findViewById(R.id.reg_email);
        password = (TextInputLayout) findViewById(R.id.reg_password);
        register = (Button) findViewById(R.id.regist_btn);

        mpProgressDialog = new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edisplayname = displayname.getEditText().getText().toString();
                String eemail = email.getEditText().getText().toString();
                String epassword = password.getEditText().getText().toString();
                if(!TextUtils.isEmpty(edisplayname)||TextUtils.isEmpty(eemail)||TextUtils.isEmpty(epassword)) {
                    mpProgressDialog.setTitle("Creating new acount..");
                    mpProgressDialog.setMessage("Please wait.. while we create your acount..");
                    mpProgressDialog.setCanceledOnTouchOutside(false);
                    mpProgressDialog.show();
                    register_user(edisplayname, eemail, epassword);
                }else{
                    Toast.makeText(RegisterActivity.this,"Field Tidak Boleh Kosong",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void register_user(String edisplayname, String eemail, String epassword) {
        mAuth.createUserWithEmailAndPassword(eemail,epassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isComplete()){
                    mpProgressDialog.dismiss();
                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(RegisterActivity.this,"Something Error!!, please check form and try again",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}