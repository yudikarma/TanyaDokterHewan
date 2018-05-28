package com.example.jon_snow.tanyadokterhewan.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jon_snow.tanyadokterhewan.Model.GetTimeAgo;
import com.example.jon_snow.tanyadokterhewan.Model.Messages;
import com.example.jon_snow.tanyadokterhewan.Model.Users;
import com.example.jon_snow.tanyadokterhewan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{

    private List<Messages> mMessagesList;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private String role;


    public MessageAdapter(List<Messages> mMessagesList) {
        this.mMessagesList = mMessagesList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout, parent,false);
        return new MessageViewHolder(v);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        public TextView messageText;
        public CircleImageView profileImage;
        public TextView displayName;
        public ImageView messageImage;
        public TextView time_text_layout;
        public MessageViewHolder(View view) {
            super(view);
            messageText = (TextView) view.findViewById(R.id.message_text_layout);
            profileImage = (CircleImageView) view.findViewById(R.id.message_profile_layout);
            displayName = (TextView) view.findViewById(R.id.name_text_layout);
            messageImage = (ImageView) view.findViewById(R.id.message_image_layout);
            time_text_layout = (TextView) view.findViewById(R.id.time_text_layout);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, final int position) {


        Messages c = mMessagesList.get(position);
        String from_user = c.getFrom();
        Long time = c.getTime();
        String Message_type = c.getType();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("thumb_image").getValue().toString();

                holder.displayName.setText(name);
                Picasso.with(holder.profileImage.getContext()).load(image)
                        .placeholder(R.drawable.default_avatar).into(holder.profileImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (Message_type.equals("text")){
            holder.messageText.setText(c.getMessage());
            holder.messageImage.setVisibility(View.INVISIBLE);

            GetTimeAgo getTimeAgo = new GetTimeAgo();



            @SuppressLint("RestrictedApi") String lastSeenTime = getTimeAgo.getTimeAgo(time, getApplicationContext());

            // mLastView.setText(lastSeenTime);
            holder.time_text_layout.setText(lastSeenTime);
        }else{
            holder.messageText.setVisibility(View.INVISIBLE);
            Picasso.with(holder.profileImage.getContext()).load(c.getMessage())
                    .placeholder(R.drawable.default_avatar).into(holder.messageImage);
        }
/*

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Dokters").child(from_user);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("thumb_image").getValue().toString();

                holder.displayName.setText(name);
                Picasso.with(holder.profileImage.getContext()).load(image)
                        .placeholder(R.drawable.default_avatar).into(holder.profileImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (Message_type.equals("text")){
            holder.messageText.setText(c.getMessage());
            holder.messageImage.setVisibility(View.INVISIBLE);

            GetTimeAgo getTimeAgo = new GetTimeAgo();



            @SuppressLint("RestrictedApi") String lastSeenTime = getTimeAgo.getTimeAgo(time, getApplicationContext());

            // mLastView.setText(lastSeenTime);
            holder.time_text_layout.setText(lastSeenTime);
        }else{
            holder.messageText.setVisibility(View.INVISIBLE);
            Picasso.with(holder.profileImage.getContext()).load(c.getMessage())
                    .placeholder(R.drawable.default_avatar).into(holder.messageImage);
        }
*/


    }

            @Override
            public int getItemCount() {
                return mMessagesList.size();
            }


        }
