package com.example.jon_snow.tanyadokterhewan.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jon_snow.tanyadokterhewan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailHewan extends AppCompatActivity {

    private TextView tnama_hewan,tjenis_hewan,tjenis_lk,tras,tumur,talamat,tttl;
    private DatabaseReference hewandatabase;
    private FirebaseAuth mAuth;
    private Button kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hewan);
        tnama_hewan = findViewById(R.id.dnama_hewan);
        tjenis_hewan = findViewById(R.id.djenis_hewan);
        tjenis_lk = findViewById(R.id.djenis_lk);
        tras = findViewById(R.id.dras);
        tumur = findViewById(R.id.dumur);
        talamat = findViewById(R.id.dalamat);
        tttl = findViewById(R.id.dttl);
        String uid = mAuth.getInstance().getCurrentUser().getUid();
        kembali = findViewById(R.id.back_to_home);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailHewan.this,ListDaftarBerobat.class);
                startActivity(intent);
                finish();
            }
        });

        final String namahewan = getIntent().getStringExtra("namahewan");
        hewandatabase = FirebaseDatabase.getInstance().getReference().child("Hewan").child(uid).child(namahewan);
        hewandatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String namahewan = dataSnapshot.child("nama_hewan").getValue().toString();
                String jenishewan = dataSnapshot.child("jenis_hewan").getValue().toString();
                String jenislk = dataSnapshot.child("jenis_lk").getValue().toString();
                String ras = dataSnapshot.child("ras").getValue().toString();
                String ttl = dataSnapshot.child("ttl").getValue().toString();
                String umur = dataSnapshot.child("umur").getValue().toString();
                String alamat = dataSnapshot.child("alamat").getValue().toString();

                tnama_hewan.setText(""+namahewan);
                tjenis_hewan.setText(""+jenishewan);
                tjenis_lk.setText(""+jenislk);
                tras.setText(""+ras);
                tttl.setText(""+ttl);
                tumur.setText(""+umur);
                talamat.setText(""+alamat);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
}
