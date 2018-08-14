package com.example.jon_snow.tanyadokterhewan.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jon_snow.tanyadokterhewan.Model.Hewan;
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

public class ListDaftarBerobat extends AppCompatActivity {
    private FloatingActionButton addHewan;
    private Toolbar mToolbar;
    private RecyclerView mListView;
    private String nambahaja;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference userDatabase;
    //private FirebaseListAdapter adapter;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseRecyclerAdapter<Hewan, ListDaftarHolder> adapter;

    private ProgressDialog mProgressDialog;

    private Users users1;
    private String muser;
    private Query query;
    private TextView notifnul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdaftar_berobat);

        /*mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Hewan").child(muser);

        *//*  userDatabase = FirebaseDatabase.getInstance().getReference().child("users");*//*

        mDatabaseReference.keepSynced(true);*/
        muser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mToolbar = (Toolbar) findViewById(R.id.tolbarlistdaftarberobat);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("List Hewan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotomain = new Intent(ListDaftarBerobat.this,MainActivity.class);
                gotomain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(gotomain);
            }
        });
        notifnul = findViewById(R.id.notifnulllisthewan);


        mListView = (RecyclerView) findViewById(R.id.listhewan);
        mListView.setHasFixedSize(true);
        mListView.setLayoutManager(new LinearLayoutManager(this));

        //QUERY
        Query query = FirebaseDatabase.getInstance().getReference().child("Hewan").child(muser).limitToLast(50);



        FirebaseRecyclerOptions<Hewan> options =
                new FirebaseRecyclerOptions.Builder<Hewan>()
                        .setQuery(query, Hewan.class)
                        .setLifecycleOwner(this)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Hewan, ListDaftarBerobat.ListDaftarHolder>(options) {

            @NonNull
            @Override
            public ListDaftarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hewan_singgle_layout,parent,false);
                return new ListDaftarHolder(mView);
            }

            @Override
            protected void onBindViewHolder(@NonNull final ListDaftarHolder holder, int position, @NonNull Hewan model) {
//                holder.setNamehewan(model.getNamaHewan());
                final String listNamaHewan = getRef(position).getKey();
                FirebaseDatabase.getInstance().getReference().child("Hewan").child(muser).child(listNamaHewan).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String nama = dataSnapshot.child("nama_hewan").getValue().toString();
                        String jenis = dataSnapshot.child("jenis_hewan").getValue().toString();
                        String Ras = dataSnapshot.child("ras").getValue().toString();

                       /* holder.setNamehewan(nama);
                        holder.setJenishewan(jenis);
                        holder.setRashewan(Ras);*/
                        Log.i("namaaaaaaaa", nama);
                        holder.setNamehewan(nama);
                        holder.setJenishewan(jenis);
                      /*  holder.setRashewan(Ras);*/

                        holder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CharSequence options[] = new CharSequence[]{"Daftar Berobat","Lihat Detail"};
                                AlertDialog.Builder builder = new AlertDialog.Builder(ListDaftarBerobat.this);

                                builder.setTitle("Select Options");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //click event for each item
                                        if (i == 0){

                                            //DAFTAR BEROBAT LANJUT
                                            Intent profilIntent = new Intent(ListDaftarBerobat.this,DaftarKeDokter.class);
                                            // i.putExtra("user_id",list_user_id);
                                            profilIntent.putExtra("namahewan",listNamaHewan );
                                            startActivity(profilIntent);
                                        }
                                        if (i == 1){
                                            //LIHAT DETAIL
                                            Intent profilIntent = new Intent(ListDaftarBerobat.this,DetailHewan.class);
                                            // i.putExtra("user_id",list_user_id);
                                            profilIntent.putExtra("namahewan",listNamaHewan );

                                            startActivity(profilIntent);

                                        }
                                        /*if (i == 2){
                                            //HAPUS RECORD
                                        }*/
                                    }
                                });
                                builder.show();

                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
               /* holder.setJenishewan(model.getJenisHewan());
                holder.setRashewan(model.getRas());*/
            }
        };

        addHewan = findViewById(R.id.add_hewan);

        addHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListDaftarBerobat.this,DaftarBerobat.class);
                startActivity(intent);
                finish();

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Hewan").child(muser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long jumlah = dataSnapshot.getChildrenCount();
                if (jumlah>0){
                    mListView.setAdapter(adapter);
                }else {
                    notifnul.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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
        TextView namehewan,jenishewan ;
        public ListDaftarHolder(View itemView) {
            super(itemView);
            mView = itemView;

            namehewan = mView.findViewById(R.id.namaHewan);
            jenishewan = mView.findViewById(R.id.jenis_hewan);

        }

        public void setNamehewan (String namahewan1){

            namehewan.setText(namahewan1);

        }
        public void setJenishewan(String jenishewan1){
            jenishewan.setText(jenishewan1);
        }


    }
}