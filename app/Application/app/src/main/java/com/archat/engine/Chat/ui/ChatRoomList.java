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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.support.v7.widget.DividerItemDecoration.HORIZONTAL;

public class ChatRoomList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
                    }
                });
                return viewHolder;
            }
        };

        mChatRecyclerView.setAdapter(mFirebaseAdapter);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
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

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
