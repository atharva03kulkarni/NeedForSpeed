package com.example.needforspeed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class farmer_buy_seeds extends AppCompatActivity {

    ListView seedsListView;
    ArrayList<String> seedsUsers = new ArrayList<>();
    FirebaseAuth mAuth;
    ArrayList<DataSnapshot> seedInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_buy_seeds);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        seedsListView = findViewById(R.id.seedsListView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, seedsUsers);
        seedsListView.setAdapter(arrayAdapter);

        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase.getInstance().getReference().child("farmer").child(mAuth.getCurrentUser().getUid()).child("seeds").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                seedsUsers.add(snapshot.child("type").getValue().toString());
                seedInfo.add(snapshot);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        seedsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataSnapshot snapshot = seedInfo.get(position);

                Intent intent = new Intent(farmer_buy_seeds.this, ViewSeeds.class);

                intent.putExtra("from", snapshot.child("from").getValue().toString());
                intent.putExtra("type", snapshot.child("type").getValue().toString());
                intent.putExtra("rate", snapshot.child("rate").getValue().toString());
                intent.putExtra("quantity", snapshot.child("quantity").getValue().toString());
                intent.putExtra("description", snapshot.child("description").getValue().toString());
                intent.putExtra("key", snapshot.getKey());

                startActivity(intent);
            }
        });
    }
}