package com.example.karaens.roomies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    TextView pName,pNickname,pEmail,pFb,pTwit,pPhno,pUid;
    Button locate;
    DatabaseReference DbRef= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        pName=findViewById(R.id.pName);
        pNickname=findViewById(R.id.pNickname);
        pEmail=findViewById(R.id.pEmail);
        pFb=findViewById(R.id.pFb);
        pTwit=findViewById(R.id.pTwit);
        pPhno=findViewById(R.id.pPhno);
        pUid=findViewById(R.id.pUid);
        locate=findViewById(R.id.locate);

        final String uid=getIntent().getStringExtra("uid");

        DbRef.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               User user=dataSnapshot.getValue(User.class);
               pName.setText(user.getName());
               pEmail.setText(user.getEmail());
               pUid.setText(user.getUid());
               pFb.setText(user.getFb());
               pTwit.setText(user.getTwit());
               pPhno.setText(user.getPhno());
               pNickname.setText(user.getNickName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        locate.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(ProfileActivity.this,MapsActivity.class);
             intent.putExtra("spId",uid);
             startActivity(intent);
         }
     });

        pPhno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+pPhno.getText().toString()));
                startActivity(intent);
            }
        });

    }
}
