package com.example.karaens.roomies;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Catalog extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    TextView cName,cTask1,cTask2,cTask3,cTask4,cHola;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    DatabaseReference DbRef=FirebaseDatabase.getInstance().getReference();
    User user=new User();

    private final static int  MY_PERMISSION_REQUEST_CODE=7171;
    private final static int PLAY_SERVICES_RES_REQUEST=7172;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private static int UPDATE_INTERVAL=5000;
    private static int FASTEST_INTERVAL=3000;
    private static int DISTANCE=10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        cName=findViewById(R.id.cName);
        cTask1=findViewById(R.id.cTask1);
        cTask2=findViewById(R.id.cTask2);
        cTask3=findViewById(R.id.cTask3);
        cTask4=findViewById(R.id.cTask4);
        cHola=findViewById(R.id.Hola);
        cHola.setText("Hola !");
        cName.startAnimation(AnimationUtils.loadAnimation(Catalog.this,android.R.anim.fade_in));
        DbRef.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cHola.setText("Hola !");
                cName.startAnimation(AnimationUtils.loadAnimation(Catalog.this,android.R.anim.fade_in));
                if(dataSnapshot.exists()){
                    user=dataSnapshot.getValue(User.class);
                    cName.setText(user.getName());
                    cName.startAnimation(AnimationUtils.loadAnimation(Catalog.this,android.R.anim.fade_in));
                }
                else
                    Log.d("tag","not exists");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("tag","not exists");
            }
        });

        cTask1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Catalog.this,CirclesActivity.class));
            }
        });

        cTask2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Catalog.this,JoinActivity.class));
            }
        });

        cTask3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(Catalog.this,CreateActivity.class));
            }
        });
        cTask4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Catalog.this,MyProfileActivity.class));
            }
        });

        if(android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED&&
                android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            android.support.v4.app.ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            },MY_PERMISSION_REQUEST_CODE);
        }
        else{
            if(checkPlayServices()){
                buildGoogleApiClient();
                createLocationRequest();
                displayLocation();
            }
        }

    }

    private void createLocationRequest() {
       mLocationRequest=new LocationRequest();
       mLocationRequest.setInterval(UPDATE_INTERVAL);
       mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
       mLocationRequest.setSmallestDisplacement(DISTANCE);
       mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient= new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).
                addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    private boolean checkPlayServices() {
        int resultCode= GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(resultCode!= ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode,this,PLAY_SERVICES_RES_REQUEST).show();
            }
            else
            {
                Toast.makeText(this,"Tracking not compatible in this device",Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    User getUser()
    {
        return user;
    }

    void updateUser(User user)
    {
        this.user=user;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        if(android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED&&
                android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
    }

    private void displayLocation() {
        if(android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED&&
                android.support.v4.app.ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
           return;
        }
        mLastLocation=LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation!=null){
           DbRef.child("location").child(mAuth.getCurrentUser().getUid()).
                   setValue(new Tracker(String.valueOf(mLastLocation.getLatitude()),String.valueOf(mLastLocation.getLongitude()),mAuth.getCurrentUser().getEmail()));
        }
        else
            DbRef.child("location").child(mAuth.getCurrentUser().getUid()).
                    setValue(null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUEST_CODE:
            {
             if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                 if(checkPlayServices()){
                     buildGoogleApiClient();
                     createLocationRequest();
                     displayLocation();
                 }
             }
            }
            break;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
         mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
         mLastLocation=location;
         displayLocation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mGoogleApiClient!=null)
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if(mGoogleApiClient!=null)
            mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }
}
