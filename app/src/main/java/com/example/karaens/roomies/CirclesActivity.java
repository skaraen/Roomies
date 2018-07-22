package com.example.karaens.roomies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

public class CirclesActivity extends AppCompatActivity {

    ListView groupList;
    DatabaseReference DbRef=FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    List<Group> grpNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circles);
        groupList=findViewById(R.id.groupList);

        DbRef.child("users").child(mAuth.getCurrentUser().getUid()).child("groups").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    grpNames=new ArrayList<>();
                    for(DataSnapshot ps:dataSnapshot.getChildren()){
                        if((Boolean) ps.getValue()){
                            Group grp=new Group(ps.getKey());
                            grpNames.add(grp);
                        }
                    }
                    GroupAdapter adapter=new GroupAdapter(CirclesActivity.this,R.layout.grp_element_layout,grpNames);
                    groupList.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        groupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(CirclesActivity.this,GroupActivity.class);
                intent.putExtra("gname",grpNames.get(position).getName());
                startActivity(intent);
            }
        });
    }
}
