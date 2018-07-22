package com.example.karaens.roomies;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String uid;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    DatabaseReference DbRef= FirebaseDatabase.getInstance().getReference();
    Tracker userLoc,frndLoc;
    LatLng userLL,frndLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        uid=getIntent().getStringExtra("spId");

        DbRef.child("location").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userLoc=dataSnapshot.getValue(Tracker.class);
                Location lUser = new Location("");
                lUser.setLatitude(Double.parseDouble(userLoc.getLat()));
                lUser.setLongitude(Double.parseDouble(userLoc.getLng()));
                userLL = new LatLng(Double.parseDouble(userLoc.getLat()), Double.parseDouble(userLoc.getLng()));
                mMap.addMarker(new MarkerOptions().position(userLL).title("You are here"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MapsActivity.this,"canceled value",Toast.LENGTH_LONG).show();
            }
        });
        DbRef.child("location").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                frndLoc=dataSnapshot.getValue(Tracker.class);
                Location lFrnd = new Location("");
                lFrnd.setLatitude(Double.parseDouble(frndLoc.getLat()));
                lFrnd.setLongitude(Double.parseDouble(frndLoc.getLng()));
                frndLL = new LatLng(Double.parseDouble(frndLoc.getLat()), Double.parseDouble(frndLoc.getLng()));
                mMap.addMarker(new MarkerOptions().position(frndLL).title("Here's your friend !"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLL, 12.0f));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MapsActivity.this,"canceled value",Toast.LENGTH_LONG).show();
            }
        });

        }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
