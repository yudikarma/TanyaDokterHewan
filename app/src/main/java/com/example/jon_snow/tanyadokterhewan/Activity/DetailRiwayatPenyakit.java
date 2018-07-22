package com.example.jon_snow.tanyadokterhewan.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jon_snow.tanyadokterhewan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailRiwayatPenyakit extends AppCompatActivity {


    private TextView tnamapemilik,tnohp,tnamadokter,tgejala,tdugaan,ttanggalrekam,talamatpemilik,tnamahewan,tjenishewan,tjeniskelamin,tras,twarnabulu,tumur,tttl,talamathewan,tnamadokterperiksa,ttanggalperiksa,tpenyakitderita,tterapi;
    private Button simpan,kembali;
    private Toolbar toolbar;
    private String idriwayat_penyakit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat_penyakit);
        toolbar = findViewById(R.id.toolbardatailriwayatpeyakit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Rekam Medis");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(DetailRiwayatPenyakit.this,ListRekamMedis.class);

                startActivity(intent);

                finish();
            }
        });
        kembali =findViewById(R.id.kembali);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(DetailRiwayatPenyakit.this,ListRekamMedis.class);

                startActivity(intent);

                finish();
            }
        });

        tpenyakitderita = findViewById(R.id.dpenyakitdiderita);
        tterapi = findViewById(R.id.dterapi);
        kembali = findViewById(R.id.kembali);

        tnamapemilik = findViewById(R.id.dnamapemilik);
        tnohp = findViewById(R.id.dnohp);
        tnamadokter = findViewById(R.id.dnamadokter);
        tgejala = findViewById(R.id.dgejala);
        tdugaan = findViewById(R.id.ddugaan);
        ttanggalrekam = findViewById(R.id.dtanggal_rekam);
        talamatpemilik = findViewById(R.id.dalamatpemilik);
        tnamahewan = findViewById(R.id.dnama_hewan);
        tjenishewan= findViewById(R.id.djenis_hewan);
        tjeniskelamin = findViewById(R.id.djenis_lk);
        tras = findViewById(R.id.dras);
        twarnabulu = findViewById(R.id.dwarnabulu);
        tumur = findViewById(R.id.dumur);
        tttl = findViewById(R.id.dttl);
        talamathewan = findViewById(R.id.dalamat);
        tnamadokterperiksa = findViewById(R.id.dnamadokterperiksa);
        ttanggalperiksa = findViewById(R.id.dtanggal_periksa);
        tterapi = findViewById(R.id.dterapi);
        tpenyakitderita = findViewById(R.id.dpenyakitdiderita);
  idriwayat_penyakit = getIntent().getStringExtra("idriwayat_penyakit");

        FirebaseDatabase.getInstance().getReference().child("RiwayatPenyakit").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(idriwayat_penyakit).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String snamapemilik = dataSnapshot.child("nama_pemilik").getValue().toString();
                final String snohp = dataSnapshot.child("no_hp").getValue().toString();
                final String snamadokter = dataSnapshot.child("nama_dokter").getValue().toString();
                final String sgejala = dataSnapshot.child("gejala").getValue().toString();
                final String sdugaan = dataSnapshot.child("dugaan").getValue().toString();
                final String salamatpemilik = dataSnapshot.child("alamat_pemilik").getValue().toString();
                final String stanggalrekam  = dataSnapshot.child("tanggal_rekam").getValue().toString();
                final String idhewan = dataSnapshot.child("idhewan").getValue().toString();
                final String terapi2 = dataSnapshot.child("terapi").getValue().toString();
                final String stanggalperiksa = dataSnapshot.child("tanggal_periksa").getValue().toString();
                final String snamadokterperiksa = dataSnapshot.child("namadokter_periksa").getValue().toString();
                final String spenyakitdiderita = dataSnapshot.child("penyakit_di_derita").getValue().toString();

                FirebaseDatabase.getInstance().getReference().child("Hewan").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(idhewan).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String snamahewan = dataSnapshot.child("nama_hewan").getValue().toString();
                        String sjenishewan = dataSnapshot.child("jenis_hewan").getValue().toString();
                        String sjeniskelamin = dataSnapshot.child("jenis_lk").getValue().toString();
                        String sras = dataSnapshot.child("ras").getValue().toString();
                        String swarnabulu = dataSnapshot.child("warna_bulu").getValue().toString();
                        String sumur = dataSnapshot.child("umur").getValue().toString();
                        String sttl = dataSnapshot.child("ttl").getValue().toString();
                        String salamathewan = dataSnapshot.child("alamat").getValue().toString();

                        tnamapemilik.setText(snamapemilik);
                        tnohp.setText(snohp);
                        tnamadokter.setText(snamadokter);
                        tgejala.setText(sgejala);
                        tdugaan.setText(sdugaan);
                        talamatpemilik.setText(salamatpemilik);
                        ttanggalrekam.setText(stanggalrekam);
                        tnamahewan.setText(snamahewan);
                        tjenishewan.setText(sjenishewan);
                        tjeniskelamin.setText(sjeniskelamin);
                        tras.setText(sras);
                        twarnabulu.setText(swarnabulu);
                        tumur.setText(sumur);
                        tttl.setText(sttl);
                        talamathewan.setText(salamathewan);
                        tnamadokterperiksa.setText(snamadokterperiksa);
                        ttanggalperiksa.setText(stanggalperiksa);
                        tterapi.setText(terapi2);
                        tpenyakitderita.setText(spenyakitdiderita);



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
    }
}
