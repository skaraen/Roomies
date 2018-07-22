package com.example.karaens.roomies;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    EditText sName,sPassword,sEmail,sKey,sPhone;
    Button buttonSignUp;
    private FirebaseAuth mAuth;
    int gCode,flag;
    String name,pWord,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sName = findViewById(R.id.sName);
        sPassword = findViewById(R.id.sPassword);
        sEmail = findViewById(R.id.sEmail);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        mAuth = FirebaseAuth.getInstance();

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag1","t0");
                name = sName.getText().toString();
                pWord = sPassword.getText().toString();
                email = sEmail.getText().toString();
                gCode = 0;
                flag = 0;

                if (name.isEmpty()) {
                    sName.setError("Field cannot be empty");
                    sName.findFocus();
                    gCode = 1;
                }
                if (pWord.isEmpty()) {
                    sPassword.setError("Field cannot be empty");
                    sPassword.findFocus();
                    gCode = 1;
                }
                if(pWord.length()<6){
                    sPassword.setError("Password must be atleast 6 characters long");
                    sPassword.findFocus();
                    gCode = 1;
                }
                if (email.isEmpty()) {
                    sEmail.setError("Field cannot be empty");
                    sEmail.findFocus();
                    gCode = 1;
                }
                if (gCode == 0) {
                    Log.d("tag1","t0.5");
                    mAuth.createUserWithEmailAndPassword(email, pWord)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("tag1","t2");
                                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                        DatabaseReference DbRef=FirebaseDatabase.getInstance().getReference();
                                        User user=new User(name,firebaseUser.getUid(),email);
                                        DbRef.child("users").child(firebaseUser.getUid()).setValue(user);
                                        Toast.makeText(SignUpActivity.this, "Success !",
                                                Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUpActivity.this,MainActivity.class));

                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Authentication failed !",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


    }
}
