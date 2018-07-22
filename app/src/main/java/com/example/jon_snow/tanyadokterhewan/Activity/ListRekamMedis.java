package com.example.jon_snow.tanyadokterhewan.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jon_snow.tanyadokterhewan.Model.Hewan;
import com.example.jon_snow.tanyadokterhewan.Model.RekamMedis;
import com.example.jon_snow.tanyadokterhewan.Model.Users;
import com.example.jon_snow.tanyadokterhewan.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ListRekamMedis extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView mListView;
    private String nambahaja;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference userDatabase;
    //private FirebaseListAdapter adapter;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseRecyclerAdapter<RekamMedis, ListRekamMedis.ListDaftarHolder> adapter;

    private ProgressDialog mProgressDialog;

    private Users users1;
    private String muser;
    private Query query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_rekam_medis);

        muser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mToolbar = (Toolbar) findViewById(R.id.tolbarlistrekammedis);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Rekam Medis");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomain = new Intent(ListRekamMedis.this,MainActivity.class);
                gotomain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(gotomain);
                finish();
            }
        });


        mListView = (RecyclerView) findViewById(R.id.listrekammedis);
        mListView.setHasFixedSize(true);
        mListView.setLayoutManager(new LinearLayoutManager(this));

        Query query = FirebaseDatabase.getInstance().getReference().child("RiwayatPenyakit").child(muser).limitToLast(50);


        FirebaseRecyclerOptions<RekamMedis> options =
                new FirebaseRecyclerOptions.Builder<RekamMedis>()
                        .setQuery(query, RekamMedis.class)
                        .setLifecycleOwner(this)
                        .build();
        adapter = new FirebaseRecyclerAdapter<RekamMedis, ListDaftarHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ListDaftarHolder holder, final int position, @NonNull RekamMedis model) {
                holder.setPenyakitderita(model.getPenyakit_di_derita());
                holder.setTerapi(model.getTerapi());
                holder.setTanggalperiksa(model.getTanggal_periksa());

                final String idhewan = model.getIdhewan();
                FirebaseDatabase.getInstance().getReference().child("Hewan").child(muser).child(idhewan).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String snamahewan = dataSnapshot.child("nama_hewan").getValue().toString();
                        holder.setNamahewan(snamahewan);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String listriwayatpenyakit = getRef(position).getKey();
                        Intent intent = new Intent(ListRekamMedis.this,DetailRiwayatPenyakit.class);
                        intent.putExtra("idriwayat_penyakit", listriwayatpenyakit);
                        startActivity(intent);

                    }
                });



            }

            @NonNull
            @Override
            public ListDaftarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.riwayat_penyakit_singgle_layout,parent,false);
                return new ListDaftarHolder(mView);
            }
        };
        mListView.setAdapter(adapter);



    }


    @Override
    protected void onStart() {
        super.onStart();
       /* mProgressDialog = new ProgressDialog(UsersActivity.this);
        mProgressDialog.setTitle("load all user data..");
        mProgressDialog.setMessage("please wait..");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();*/


        adapter.startListening();



    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public class ListDaftarHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView penyakitderita,terapi,tanggalperiksa,namahewan ;
        public ListDaftarHolder(View itemView) {
            super(itemView);
            mView = itemView;

            penyakitderita = mView.findViewById(R.id.penyakitderita);
            terapi = mView.findViewById(R.id.terap);
            tanggalperiksa = mView.findViewById(R.id.tanggalperiksa);
            namahewan = mView.findViewById(R.id.rnama_hewan);
        }

        public void setPenyakitderita (String namapenyakit){

            penyakitderita.setText(namapenyakit);

        }
        public void setTerapi(String jenisterapi){
            terapi.setText(jenisterapi);
        }
        public void setTanggalperiksa(String tanggal){
            tanggalperiksa.setText(tanggal);
        }
        public void setNamahewan(String namahewan1){
            namahewan.setText(namahewan1);
        }

    }

}
