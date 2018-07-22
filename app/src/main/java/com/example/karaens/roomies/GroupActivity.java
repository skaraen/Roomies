package com.example.karaens.roomies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity {

    TextView gName;
    List<User> members=new ArrayList<>();
    ListView userList;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    DatabaseReference DbRef= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        userList=findViewById(R.id.userList);
        gName=findViewById(R.id.gName);

        String head=getIntent().getStringExtra("gname");
        gName.setText(head);

        DbRef.child("circles").child(head).child("members").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ps:dataSnapshot.getChildren()){
                        User user=ps.getValue(User.class);
                        members.add(user);
                        UserAdapter adapter=new UserAdapter(GroupActivity.this,R.layout.user_element_layout,members);
                        userList.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(GroupActivity.this,ProfileActivity.class);
                intent.putExtra("uid",members.get(position).getUid());
                startActivity(intent);
            }
        });
    }
}
