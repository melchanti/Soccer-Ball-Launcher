package com.example.testing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.testing.MyApplication.getLaunLatitude;
import static com.example.testing.MyApplication.getLaunLongitude;
import static com.example.testing.MyApplication.setLatitude;
import static com.example.testing.MyApplication.setLongitude;
import static com.example.testing.R.id.map;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback{

    private GoogleMap mMap;
    double playerLong=0;
    double playerLat=0;
    double launLong=getLaunLongitude();
    double launLat= getLaunLatitude();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Launcher and move the camera
        final LatLng launPosition = new LatLng(launLat, launLong);

        final Marker launMarker = mMap.addMarker(new MarkerOptions().
                position(launPosition).title("Launcher position"));

        CameraPosition position = CameraPosition.builder()
                .target(launPosition)
                .zoom( 20f )
                .bearing( 0.0f )
                .tilt( 0.0f )
                .build();

        mMap.animateCamera(
                CameraUpdateFactory.newCameraPosition( position ), null );
        mMap.setMapType( GoogleMap.MAP_TYPE_SATELLITE);

        //When the player clicks a position on the map, set a marker there
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                Toast.makeText(MapsActivity.this, "Add a marker",
                        Toast.LENGTH_LONG).show();
                playerLat= point.latitude;
                playerLong= point.longitude;
                mMap.addMarker(new MarkerOptions().position(point).title("Player's position"));
            }
        });

        //Remove marker if it's clicked
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {

                // Retrieve the data from the marker.
                String markerTitle =  marker.getTitle();

                String launTitle= launMarker.getTitle();
                Toast.makeText(MapsActivity.this, markerTitle,
                        Toast.LENGTH_LONG).show();
                // Check if the recent placed marker is clicked, then remove it.
                if (!markerTitle.equals( launTitle)) {
                    marker.remove();
                    return true;
                }
                return true;
            }
        });
    }

    public void sendMessage(View view) {
        if (!(playerLong==playerLat && playerLat==0.0)) {
        setLongitude(playerLong);
        setLatitude(playerLat);
        Intent intent1 = new Intent(this, Connect.class);
        Intent intent2= new Intent(this,Calculations.class);
        startActivity(intent1);
        startService(intent2);} else {

            Toast.makeText(MapsActivity.this, "Please select a player's location",
                    Toast.LENGTH_LONG).show();

        }
    }


}