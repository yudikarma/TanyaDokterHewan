package com.example.jon_snow.tanyadokterhewan.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jon_snow.tanyadokterhewan.Model.GetTimeAgo;
import com.example.jon_snow.tanyadokterhewan.Model.Messages;
import com.example.jon_snow.tanyadokterhewan.Model.Users;
import com.example.jon_snow.tanyadokterhewan.R;
import com.firebase.ui.auth.ui.phone.SpacedEditText;
import com.google.android.gms.common.api.internal.StatusPendingResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;
import java.util.zip.InflaterInputStream;


import de.hdodenhof.circleimageview.CircleImageView;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {


    private List<Messages> mMessagesList;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private DatabaseReference messageDatabase;
    private DatabaseReference dokterdatabase;
    private DatabaseReference pasiendatabase;
    private static final int VIEW_TYPE_ME = 1;
    private static final int VIEW_TYPE_OTHER = 2;
    private String userlogin = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String role;




    public MessageAdapter(List<Messages> mMessagesList) {
        this.mMessagesList = mMessagesList;
    }

    @NonNull
    @Override


    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout, parent, false);

        return new MessageViewHolder(v);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {


        public TextView messageText;
        public CircleImageView profileImage;
        public TextView displayName;
        public ImageView messageImage;
        public TextView time_text_layout;
        public LinearLayoutCompat itemBody,linear_background_chat;


        public MessageViewHolder(View view) {
            super(view);
            messageText = (TextView) view.findViewById(R.id.message_text_layout);
            profileImage = (CircleImageView) view.findViewById(R.id.message_profile_layout);
            displayName = (TextView) view.findViewById(R.id.name_text_layout);
            messageImage = (ImageView) view.findViewById(R.id.message_image_layout);
            itemBody = view.findViewById(R.id.message_single_layout);
            time_text_layout = (TextView) view.findViewById(R.id.time_text_layout);
            linear_background_chat = view.findViewById(R.id.linear_background_chat);


        }
    }


    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, final int position) {


        Messages c = mMessagesList.get(position);
        final String from_user = c.getFrom();
        Long time = c.getTime();
        String Message_type = c.getType();

/*        ============================== SET JIKA PESAN DARI DIRISENDIRI PUT KE KANAN, KALO DARI ORANG PUT KE KIRI============*/
        if (from_user.equals(userlogin)){
           /* FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.itemBody.getLayoutParams();*/
           holder.itemBody.setGravity(Gravity.RIGHT);
           holder.displayName.setVisibility(View.GONE);
           holder.profileImage.setVisibility(View.GONE);
           holder.linear_background_chat.setBackgroundResource(R.drawable.ic_my_message_shape);
            holder.messageText.setTextColor(Color.WHITE);

        }

        /* ====================== INI BAGIAN ATUR SETDISPLAY NAME AND DISPLAYPROFILIMAGE ==============================*/

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("UserCampur").child(from_user);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("thumb_image").getValue().toString();

                holder.displayName.setText(name);
                Picasso.with(holder.profileImage.getContext()).load(image)
                        .placeholder(R.drawable.default_avatar).into(holder.profileImage);
                Log.i("hhhhhhhhhhhhhhhhhhhhhhj", name+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*messageDatabase = FirebaseDatabase.getInstance().getReference().child()*/


       /* ====================== INI BAGIAN ATUR SETTextMessage ==============================*/


        if (Message_type.equals("text")) {
            holder.messageText.setText(c.getMessage());
            holder.messageImage.setVisibility(View.GONE);

            GetTimeAgo getTimeAgo = new GetTimeAgo();


                @SuppressLint("RestrictedApi") String lastSeenTime = getTimeAgo.getTimeAgo(time, getApplicationContext());

            // mLastView.setText(lastSeenTime);
            holder.time_text_layout.setText(lastSeenTime);
        } else {
            holder.messageText.setVisibility(View.INVISIBLE);
            holder.profileImage.setVisibility(View.GONE);
            holder.displayName.setVisibility(View.GONE);


            Picasso.with(holder.profileImage.getContext()).load(c.getMessage())
                    .placeholder(R.drawable.default_avatar).into(holder.messageImage);
            GetTimeAgo getTimeAgo = new GetTimeAgo();


            @SuppressLint("RestrictedApi") String lastSeenTime = getTimeAgo.getTimeAgo(time, getApplicationContext());

            // mLastView.setText(lastSeenTime);
            holder.time_text_layout.setText(lastSeenTime);
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
