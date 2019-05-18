package com.archat.engine.Chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.archat.engine.Chat.ui.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class profileActivity extends AppCompatActivity {



    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    String name;
    String userId;
    String email;


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


         TextView username = (TextView)findViewById(R.id.Username);
         TextView useremail = (TextView)findViewById(R.id.Email);
         TextView userID = (TextView)findViewById(R.id.UID);
         TextView subusername = (TextView)findViewById(R.id.subUsername);


         username.setText("Username: " + name);
         useremail.setText("email: " + email);
         userID.setText("UID: " + userId);
         subusername.setText(name);



    }

}
