package com.example.jon_snow.tanyadokterhewan.Activity;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.jon_snow.tanyadokterhewan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DaftarBerobat extends AppCompatActivity {
    EditText namaHewan, jenisHewan, ras, ttl, umur, alamat,warnabulu;
    RadioGroup jenis_lk;
    RadioButton jantan, betina;
    Button daftar;
    private DatabaseReference hewanDatabaseReference;
    private DatabaseReference mRootDatabaseReference;
    private FirebaseAuth mAuth;
    private String uid;
    ProgressDialog mprProgressDialog;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_berobat);
        mToolbar = (Toolbar) findViewById(R.id.toolbardaftarberobat);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomain = new Intent(DaftarBerobat.this,MainActivity.class);
                gotomain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(gotomain);
            }
        });

        mprProgressDialog = new ProgressDialog(this);


        namaHewan = findViewById(R.id.enama_hewan);
        jenisHewan = findViewById(R.id.ejenis_hewan);
        ras = findViewById(R.id.eras);
        ttl = findViewById(R.id.ettl);
        umur = findViewById(R.id.eumur);
        alamat = findViewById(R.id.ealamat);
        jenis_lk = findViewById(R.id.ejenis_lk);
        jantan = findViewById(R.id.rjantan);
        betina = findViewById(R.id.rbetina);
        daftar = findViewById(R.id.eDaftarBerobat);
        warnabulu = findViewById(R.id.ewarnabulu);

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        mRootDatabaseReference = FirebaseDatabase.getInstance().getReference();
        hewanDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Hewan").child(mAuth.getCurrentUser().getUid());

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String snamehewan = namaHewan.getText().toString();
                final String sjenishewan = jenisHewan.getText().toString();
                final String sras = ras.getText().toString();
                final String sttl = ttl.getText().toString();
                final String sumur = umur.getText().toString();
                final String salamat = alamat.getText().toString();
                final String swrnabulu = warnabulu.getText().toString();

                //for radio choice
                String jenislk2 = "";
                int jenisid = jenis_lk.getCheckedRadioButtonId();
                if (jenisid == jantan.getId()) {
                    jenislk2 = jantan.getText().toString();
                } else if (jenisid == betina.getId()) {
                    jenislk2 = betina.getText().toString();
                } else {
                    jenislk2 = "bukan apa2";
                }


                if (!TextUtils.isEmpty(snamehewan) && !TextUtils.isEmpty(sjenishewan)
                        && !TextUtils.isEmpty(sras) && !TextUtils.isEmpty(sttl)
                        && !TextUtils.isEmpty(sumur) && !TextUtils.isEmpty(salamat)
                        ) {
                    //do somethings

                    //save to firebase Databse

                    final String finalJenislk = jenislk2;

                    mprProgressDialog.setTitle("wait a second");
                    mprProgressDialog.setMessage("add record data..");
                    mprProgressDialog.setCanceledOnTouchOutside(false);
                    mprProgressDialog.show();
                    DatabaseReference newNotificationRef = mRootDatabaseReference.child("Hewan").child(uid).push();
                    String idHewan = newNotificationRef.getKey();


                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("nama_hewan", snamehewan);
                    userMap.put("jenis_hewan", sjenishewan);
                    userMap.put("ras", sras);
                    userMap.put("ttl", sttl);
                    userMap.put("warna_bulu", swrnabulu);

                    userMap.put("umur", sumur);
                    userMap.put("alamat", salamat);
                    userMap.put("jenis_lk", finalJenislk);

                    Map pussMap = new HashMap();

                    Map tambahhewan = new HashMap();
                    tambahhewan.put("Hewan/"+uid+"/"+idHewan,userMap );


                    mRootDatabaseReference.updateChildren(tambahhewan, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError == null){
                                        mprProgressDialog.dismiss();
                                        Toast.makeText(DaftarBerobat.this, "data berhasil di tambahkan", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(DaftarBerobat.this,ListDaftarBerobat.class);
                                        startActivity(intent);
                                        finish();



                                    }else{
                                        String error = databaseError.getMessage();
                                        Toast.makeText(DaftarBerobat.this, error, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                   /* hewanDatabaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            DatabaseReference user_message_push = mRootDatabaseReference.child("Hewan").child(uid).push();
                            String push_id = user_message_push.getKey();

                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("nama_hewan", snamehewan);
                            userMap.put("jenis_hean", sjenishewan);
                            userMap.put("ras", sras);
                            userMap.put("ttl", sttl);
                            userMap.put("umur", sumur);
                            userMap.put("alamat", salamat);
                            userMap.put("jenis_lk", finalJenislk);

                            Map pussMap = new HashMap();
                            pussMap.put("Hewan/"+uid+"/"+push_id, userMap);

                            mRootDatabaseReference.updateChildren(pussMap, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError != null) {
                                        Intent intent = new Intent(DaftarBerobat.this, ListDaftarBerobat.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(DaftarBerobat.this, "Data Gagal Ditambahkan", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });



                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
*/

                } else {
                    //Tell is all field is required

                    Toast.makeText(DaftarBerobat.this, "Maaf semua field harus di isi !!!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}