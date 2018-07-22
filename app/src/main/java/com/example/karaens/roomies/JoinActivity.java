package com.example.karaens.roomies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class JoinActivity extends AppCompatActivity {

    EditText jName,jKey;
    Button circleJoin;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    String key,name;
    DatabaseReference DbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        jName=findViewById(R.id.jName);
        jKey=findViewById(R.id.jKey);
        circleJoin=findViewById(R.id.circleJoin);

        circleJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=jName.getText().toString();
                key=jKey.getText().toString();

                DbRef= FirebaseDatabase.getInstance().getReference();
                DbRef.child("circles").child(name).child("key").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            if(key.equals(dataSnapshot.getValue(String.class))){
                                Toast.makeText(JoinActivity.this,"success",Toast.LENGTH_LONG).show();
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
                                            DbRef.child("circles").child(name).child("members").child(mAuth.getCurrentUser().getUid())
                                                    .setValue(user);
                                            DbRef.child("users").child(mAuth.getCurrentUser().getUid()).setValue(user);
                                            startActivity(new Intent(JoinActivity.this,Catalog.class));
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
                            else {
                                Toast.makeText(JoinActivity.this, "Wrong credentials !", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                            Toast.makeText(JoinActivity.this, "Wrong credentials !", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(JoinActivity.this, "Wrong credentials !", Toast.LENGTH_LONG).show();                    }
                });
            }
        });

    }
}
