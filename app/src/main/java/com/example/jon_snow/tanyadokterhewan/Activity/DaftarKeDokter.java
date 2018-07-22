package com.example.jon_snow.tanyadokterhewan.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jon_snow.tanyadokterhewan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DaftarKeDokter extends AppCompatActivity {

    private String snamapemilik,salamt,snohp,snamadokter,sgejala,sdugaan,stanggal,sidhewan;
    private EditText tnamapemilik,talamat,tnohp,tnamadokter,tgejala,tdugaan;
    private Button bsimpan;
    private DatabaseReference hewanDatabaseReference;
    private DatabaseReference mRootDatabaseReference;
    private FirebaseAuth mAuth;
    private String uid;
    ProgressDialog mprProgressDialog;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_ke_dokter);
        mToolbar = (Toolbar) findViewById(R.id.toolbardaftarkedokter);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomain = new Intent(DaftarKeDokter.this,MainActivity.class);
                gotomain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(gotomain);
            }
        });

        mprProgressDialog = new ProgressDialog(this);

        tnamapemilik = findViewById(R.id.namapemilik);
        talamat = findViewById(R.id.alamatpemilik);
        tnohp = findViewById(R.id.nohppemililk);
        tnamadokter = findViewById(R.id.namadokter);
        tgejala = findViewById(R.id.gejala);
        tdugaan = findViewById(R.id.dugaan);
        bsimpan = findViewById(R.id.eDaftarBerobat);

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        mRootDatabaseReference = FirebaseDatabase.getInstance().getReference();
        hewanDatabaseReference = FirebaseDatabase.getInstance().getReference().child("RekamMedis").child(mAuth.getCurrentUser().getUid());

        bsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                snamapemilik = tnamapemilik.getText().toString();
                salamt = talamat.getText().toString();
                snohp = tnohp.getText().toString();snamadokter = tnamadokter.getText().toString();sgejala = tgejala.getText().toString();sdugaan = tdugaan.getText().toString();

                if (!TextUtils.isEmpty(snamapemilik) && !TextUtils.isEmpty(salamt)
                        && !TextUtils.isEmpty(snamadokter) && !TextUtils.isEmpty(snohp)
                        && !TextUtils.isEmpty(sgejala)
                        ) {

                    mprProgressDialog.setTitle("wait a second");
                    mprProgressDialog.setMessage("add record data..");
                    mprProgressDialog.setCanceledOnTouchOutside(false);
                    mprProgressDialog.show();

                    DatabaseReference newNotificationRef = mRootDatabaseReference.child("RekamMedis").child(uid).push();
                    String idRekam = newNotificationRef.getKey();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("nama_pemilik", snamapemilik);
                    userMap.put("alamat_pemilik", salamt);
                    userMap.put("no_hp", snohp);
                    userMap.put("nama_dokter", snamadokter);
                    userMap.put("terapi", "default");
                    userMap.put("penyakit_di_derita", "default");

                    userMap.put("gejala", sgejala);
                    userMap.put("dugaan", "default");
                    Date c = Calendar.getInstance().getTime();
                   // System.out.println("Current time => " + c);
                    //Ambil tanggal rekam
                    /*SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(c);*/

                    final String currentDate = DateFormat.getDateTimeInstance().format(new Date());
                    stanggal = currentDate;
                    userMap.put("tanggal_rekam",stanggal );
                    sidhewan  = getIntent().getStringExtra("namahewan");
                    userMap.put("idhewan", sidhewan);

                    userMap.put("tanggal_periksa", "default");





                    Map simpanrekam = new HashMap();
                    simpanrekam.put("RekamMedis/"+uid+"/"+idRekam,userMap );

                    mRootDatabaseReference.updateChildren(simpanrekam, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError == null){
                                mprProgressDialog.dismiss();
                                Toast.makeText(DaftarKeDokter.this, "Anda telah berhasil mendaftar berobat pada klinik FKH Unsyah", Toast.LENGTH_LONG).show();
                                Toast.makeText(DaftarKeDokter.this, "Silahkan mengunjungin Klinik FKH unsyah untuk Proses Pemeriksaan selanjutnya.", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(DaftarKeDokter.this,MainActivity.class);
                                startActivity(intent);
                                finish();



                            }else{
                                String error = databaseError.getMessage();
                                Toast.makeText(DaftarKeDokter.this, error, Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                }else{

                        //Tell is all field is required

                        Toast.makeText(DaftarKeDokter.this, "Maaf semua field harus di isi !!!", Toast.LENGTH_LONG).show();

                }
            }
        });


    }
}
