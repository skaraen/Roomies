package com.example.karaens.roomies;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateActivity extends AppCompatActivity {

    EditText cName,cKey;
    Button circleCreate;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    User user;
    DatabaseReference DbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        cName=findViewById(R.id.cName);
        cKey=findViewById(R.id.cKey);
        circleCreate=findViewById(R.id.circleCreate);

        circleCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name=cName.getText().toString();
                final String key=cKey.getText().toString();

                DbRef=FirebaseDatabase.getInstance().getReference();
                DbRef.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            User user=dataSnapshot.getValue(User.class);
                            Map<String,Boolean> newGrps=user.getGroups();
                            if(newGrps==null)
                                newGrps=new HashMap<>();
                            newGrps.put(name,true);
                            user.setGroups(newGrps);
                            Log.e("ds",""+user.getName());
                            DbRef.child("circles").child(name).child("key").setValue(key);
                            DbRef.child("circles").child(name).child("members").child(mAuth.getCurrentUser().getUid()).setValue(user);
                            DbRef.child("users").child(mAuth.getCurrentUser().getUid()).setValue(user);
                            startActivity(new Intent(CreateActivity.this,Catalog.class));
                            finish();
                        }
                        else
                        Log.d("tag","c2");

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("tag","c3");

                    }
                });

            }
        });
    }
}
