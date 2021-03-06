package com.archat.engine.Chat;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.archat.engine.Chat.R;
import com.archat.engine.Chat.ui.User;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URI;


public class profileActivity extends AppCompatActivity {



    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    String name;
    String userId;
    String email;
    Uri profileImageURI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        userId = currentUser.getUid();
        email = currentUser.getEmail();
        name = currentUser.getDisplayName();
        profileImageURI = currentUser.getPhotoUrl();


        TextView username = (TextView)findViewById(R.id.Username);
        TextView useremail = (TextView)findViewById(R.id.Email);
        TextView userID = (TextView)findViewById(R.id.UID);
        TextView subusername = (TextView)findViewById(R.id.subUsername);
        ImageView profilepic = (ImageView)findViewById(R.id.profilePic);


        username.setText("Username: " + name);
        useremail.setText("email: " + email);
        userID.setText("UID: " + userId);
        subusername.setText("Username: " + name);
        Glide.with(this).load(profileImageURI).into(profilepic);


    }

}