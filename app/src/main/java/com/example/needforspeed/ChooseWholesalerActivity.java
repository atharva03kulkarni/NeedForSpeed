package com.example.needforspeed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ChooseWholesalerActivity extends AppCompatActivity {

    ListView chooseWholesalerListView;
    ArrayList<String> wholesalers = new ArrayList<>();
    ArrayList<String> wholesalerKeys = new ArrayList<>();
    String Name;
    String Location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_wholesaler);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Disclaimer")
                .setMessage("Choose the user to whom you want to make this item available!")
                .setPositiveButton("Okay", null)
                .show();

        chooseWholesalerListView = findViewById(R.id.chooseWholesalerListView);

        ArrayAdapter<String> wholesalerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, wholesalers);
        chooseWholesalerListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        chooseWholesalerListView.setItemChecked(2, true);
        chooseWholesalerListView.setAdapter(wholesalerArrayAdapter);

        FirebaseDatabase.getInstance().getReference().child("wholesaler").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String user = snapshot.child("name").getValue().toString();
                Location = snapshot.child("location").getValue().toString();
                wholesalers.add(user + " (" + Location + ")");                                                                    // adds all the users from the database
                wholesalerKeys.add(snapshot.getKey());                                                        // gets the key of each user from the database
                wholesalerArrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        //getting the name of wholesaler(current)
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("farmer").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Name = snapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        chooseWholesalerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> wholesalerMap = new HashMap<>();
                wholesalerMap.put("phone", FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                wholesalerMap.put("from", Name);
                wholesalerMap.put("type", getIntent().getStringExtra("Value"));
                wholesalerMap.put("rate", getIntent().getStringExtra("Amount"));
                wholesalerMap.put("quantity", getIntent().getStringExtra("Quantity"));

                FirebaseDatabase.getInstance().getReference().child("wholesaler").child(wholesalerKeys.get(position)).child("items").push().setValue(wholesalerMap);          // item gets add to the database when a user is selected

                // add a disclaimer here
            }
        });

    }

    public void homeScreen(View view){

        Intent home = new Intent(this, shoppingScreen.class);
        startActivity(home);

    }

}