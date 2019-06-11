package com.archat.engine.Chat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.archat.engine.Chat.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import static android.support.v7.widget.DividerItemDecoration.HORIZONTAL;

public class ChatRoomList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

    public static class ChatViewHolder extends RecyclerView.ViewHolder{
        TextView chatNameView;
        TextView lastMessageView;
        TextView timeStampView;
        private ChatViewHolder.ClickListener mClickListener;


        public ChatViewHolder(View v) {
            super(v);
            chatNameView = (TextView) itemView.findViewById(R.id.chatNameView);
            lastMessageView = (TextView) itemView.findViewById(R.id.lastMessageView);
            timeStampView = (TextView) itemView.findViewById(R.id.timeStampView);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {
                    mClickListener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }


        public interface ClickListener {
            void onItemClick(View view, int position);
        }

        public void setOnClickListener(ChatViewHolder.ClickListener clickListener) {
            mClickListener = clickListener;
        }

    }


    private RecyclerView mChatRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private TextView mUserNameView;
    private TextView mUserEmailView;

    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<ChatModel, ChatViewHolder>
            mFirebaseAdapter;

    private ArrayList<String> arrID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        arrID = new ArrayList<>();
        mChatRecyclerView = (RecyclerView) findViewById(R.id.chatRecyclerView);
        DividerItemDecoration itemDecor = new DividerItemDecoration(mChatRecyclerView.getContext(), HORIZONTAL);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(false);
        mChatRecyclerView.setLayoutManager(mLinearLayoutManager);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference chatRef = mFirebaseDatabaseReference.child("chats");

        FirebaseRecyclerOptions<ChatModel> options =
                new FirebaseRecyclerOptions.Builder<ChatModel>()
                        .setQuery(chatRef, ChatModel.class)
                        .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<ChatModel, ChatViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull ChatModel model) {
                holder.chatNameView.setText(model.getChatName());
                holder.lastMessageView.setText(model.getLastMessage());

                long timeStamp = Long.parseLong(model.getTimeStamp());
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
                Date resultdate = new Date(timeStamp);
                holder.timeStampView.setText(sdf.format(resultdate));

                arrID.add(model.getChatId());
            }

            @NonNull
            @Override
            public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                ChatViewHolder viewHolder = new ChatViewHolder(inflater.inflate(R.layout.item_chat, viewGroup, false));
                viewHolder.setOnClickListener(new ChatViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(ChatRoomList.this,ChatActivity.class);
                        intent.putExtra("CHAT_ID", arrID.get(position));
                        startActivity(intent);
                        finish();
                    }
                });
                return viewHolder;
            }
        };

        mChatRecyclerView.setAdapter(mFirebaseAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Chat Room Created", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Random rand = new Random();
                int randomint = rand.nextInt(1000);
                final String chatName = "Chat Room" + " " + String.valueOf(randomint);
                final String timeStamp = String.valueOf(System.currentTimeMillis());
                ChatModel tempChatModel = new ChatModel("chatName", "",timeStamp,"");
                chatRef.push().setValue(tempChatModel, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        String key = databaseReference.getKey();
                        ChatModel chatModel = new ChatModel(chatName, "",timeStamp,key);
                        chatRef.child(key).setValue(chatModel);
                        arrID.add(key);
                        Intent intent = new Intent(ChatRoomList.this,ChatActivity.class);
                        intent.putExtra("CHAT_ID", key);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View hView = navigationView.getHeaderView(0);
        mUserNameView = hView.findViewById(R.id.user_name);
        mUserEmailView = hView.findViewById(R.id.user_email);
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUserNameView.setText(Objects.requireNonNull(mFirebaseUser).getDisplayName());
        mUserEmailView.setText(mFirebaseUser.getEmail());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFirebaseAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chat_room_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sign_out) {
            mFirebaseAuth.signOut();

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("303031097876-iooqs7cqhniiupm1v5octpfsjao9o6bn.apps.googleusercontent.com")
                    .requestEmail()
                    .build();
            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            mGoogleSignInClient.signOut();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.nav_profile){
            Intent intent = new Intent(this, com.archat.engine.Chat.profileActivity.class);
            startActivity(intent);
        }
//         else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
