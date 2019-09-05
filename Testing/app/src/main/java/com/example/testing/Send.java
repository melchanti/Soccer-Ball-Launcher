package com.example.testing;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import static com.example.testing.MyApplication.getHorizAngle;
import static com.example.testing.MyApplication.getInitSpeed;
import static com.example.testing.MyApplication.getSpeed;
import static com.example.testing.MyApplication.getType;
import static com.example.testing.MyApplication.getVertiAngle;
import static com.example.testing.MyApplication.getdeviceName;

public class Send extends AppCompatActivity {


    //Memeber Fields
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private OutputStream outStream = null;

    // UUID service - This is the type of Bluetooth device that the BT module is
    // It is very likely yours will be the same, if not google UUID for your manufacturer
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // MAC-address of Bluetooth module
    public String newAddress = null;

    private String type = getType();
    private String speed = getSpeed();
    private int horizAngle= getHorizAngle();
    private int verticalAngle= getVertiAngle();
    private int initSpeed = getInitSpeed();
    private String deviceName= getdeviceName();
    private double angle= MyApplication.getAngle();
    private double Latitude=0;
    private double Longitude=0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        //LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //MyCurrentLoctionListener locationListener = new MyCurrentLoctionListener();

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(linearLayout);

        TextView typeView = new TextView(this);
        typeView.setTextSize(50);
        typeView.setText(type);
        linearLayout.addView(typeView);
        TextView speedView = new TextView(this);
        speedView.setTextSize(50);
        speedView.setText(speed);
        linearLayout.addView(speedView);

        TextView locView = new TextView(this);
        //locView.setText("Latitude=" +Latitude);
        linearLayout.addView(locView);

        Button sendInfo = new Button(this);
        sendInfo.setTextSize(50);
        sendInfo.setText("Verify and Send");
        linearLayout.addView(sendInfo);

        //getting the bluetooth adapter value and calling checkBTstate function
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBTState();

        /**************************************************************************************************************************8
         *  Buttons are set up with onclick listeners so when pressed a method is called
         *  In this case send data is called with a value and a toast is made
         *  to give visual feedback of the selection made
         */

        sendInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Sending", Toast.LENGTH_SHORT).show();
                String typeNum="0";
                String speedNum="0";
                String location;

                sendData("*"+horizAngle+"#" + verticalAngle +"$" + initSpeed + "@");
            }
        });

    }

   /* private void makeUseofNewLocation(Location location) {
        Latitude= location.getLatitude();
        Longitude= location.getLongitude();
    }*/

    @Override
    public void onResume() {
        super.onResume();
        // connection methods are best here in case program goes into the background etc

        //Get MAC address from Connect

        newAddress = getdeviceName();

        // Set up a pointer to the remote device using its address.
        BluetoothDevice device = btAdapter.getRemoteDevice(newAddress);

        //Attempt to create a bluetooth socket for comms
        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e1) {
            Toast.makeText(getBaseContext(), "ERROR - Could not create Bluetooth socket", Toast.LENGTH_SHORT).show();
        }

        // Establish the connection.
        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();        //If IO exception occurs attempt to close socket
            } catch (IOException e2) {
                Toast.makeText(getBaseContext(), "ERROR - Could not close Bluetooth socket", Toast.LENGTH_SHORT).show();
            }
        }

        // Create a data stream so we can talk to the device
        try {
            outStream = btSocket.getOutputStream();
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "ERROR - Could not create bluetooth outstream", Toast.LENGTH_SHORT).show();
        }
        //When activity is resumed, attempt to send a piece of junk data ('x') so that it will fail if not connected
        // i.e don't wait for a user to press button to recognise connection failure
        sendData("x");
    }

    @Override
    public void onPause() {
        super.onPause();
        //Pausing can be the end of an app if the device kills it or the user doesn't open it again
        //close all connections so resources are not wasted

        //Close BT socket to device
        try     {
            btSocket.close();
        } catch (IOException e2) {
            Toast.makeText(getBaseContext(), "ERROR - Failed to close Bluetooth socket", Toast.LENGTH_SHORT).show();
        }
    }
    //takes the UUID and creates a comms socket
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    //same as in device list activity
    private void checkBTState() {
        // Check device has Bluetooth and that it is turned on
        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "ERROR - Device does not support bluetooth", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    // Method to send data
    private void sendData(String message) {
        byte[] msgBuffer = message.getBytes();

        try {
            //attempt to place data on the outstream to the BT device
            outStream.write(msgBuffer);

        } catch (IOException e) {
            //if the sending fails this is most likely because device is no longer there
            Toast.makeText(getBaseContext(), "ERROR - Device not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


}
