package com.example.karaens.roomies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProfileActivity extends AppCompatActivity {

    EditText mName,mNickname,mEmail,mFb,mTwit,mPhno,mUid;
    Button mUpdate;
    DatabaseReference DbRef= FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        mName=findViewById(R.id.mName);
        mNickname=findViewById(R.id.mNickname);
        mEmail=findViewById(R.id.mEmail);
        mFb=findViewById(R.id.mFb);
        mTwit=findViewById(R.id.mTwit);
        mPhno=findViewById(R.id.mPhno);
        mUid=findViewById(R.id.mUid);
        mUpdate=findViewById(R.id.mUpdate);

        DbRef.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user=dataSnapshot.getValue(User.class);
                mName.setText(user.getName());
                mNickname.setText(user.getNickName());
                mEmail.setText(user.getEmail());
                mFb.setText(user.getFb());
                mTwit.setText(user.getTwit());
                mPhno.setText(user.getPhno());
                mUid.setText(user.getUid());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newUser=new User(mName.getText().toString(),mUid.getText().toString(),mEmail.getText().toString(),mPhno.getText().toString(),
                        mFb.getText().toString(),mTwit.getText().toString(),mNickname.getText().toString(),user.getGroups());

                DbRef.child("users").child(mAuth.getCurrentUser().getUid()).setValue(newUser);
                startActivity(new Intent(MyProfileActivity.this,Catalog.class));
            }
        });
    }
}
