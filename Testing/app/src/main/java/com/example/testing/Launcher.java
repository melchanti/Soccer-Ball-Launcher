package com.example.testing;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.testing.MyApplication.setLaunLatitude;
import static com.example.testing.MyApplication.setLaunLongitude;

public class Launcher extends AppCompatActivity {

    private LocationManager lM;
    double longitude = 0;
    double latitude = 0;
    private Location location;
    private LocationListener locationListen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(linearLayout);

        lM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListen = new LocationListener() {
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                return;
            }
        } else {
            lM.requestLocationUpdates("gps", 5000, 0, locationListen);
        }
        location = lM.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        latitude= location.getLatitude();
        longitude= location.getLongitude();
        double accuracy= location.getAccuracy();

        setLaunLongitude(longitude);
        setLaunLatitude(latitude);

        TextView latView = new TextView(this);
        latView.setTextSize(20);
        latView.setText("Latitude = " + latitude);
        linearLayout.addView(latView);
        TextView longView = new TextView(this);
        longView.setTextSize(20);
        longView.setText("Longitude = " + longitude);
        linearLayout.addView(longView);
        TextView accView = new TextView(this);
        accView.setTextSize(20);
        accView.setText("Accuracy = " + accuracy);
        linearLayout.addView(accView);
        Button sendInfo = new Button(this);
        sendInfo.setTextSize(30);
        sendInfo.setText("Set Launcher Location");
        linearLayout.addView(sendInfo);


        sendInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendMessage(v);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                lM.requestLocationUpdates("gps", 5000, 0, locationListen);;
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        longitude= location.getLongitude();
        latitude= location.getLatitude();

    }

    public void sendMessage(View view) {
        Intent intent1 = new Intent(this, MainActivity.class);
        startActivity(intent1);
        Toast.makeText(getBaseContext(), "Launcher location established", Toast.LENGTH_SHORT).show();
    }
}
