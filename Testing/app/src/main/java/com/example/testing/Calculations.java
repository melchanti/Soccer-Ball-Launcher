package com.example.testing;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import static com.example.testing.MyApplication.getLatitude;
import static com.example.testing.MyApplication.getLaunLatitude;
import static com.example.testing.MyApplication.getLaunLongitude;
import static com.example.testing.MyApplication.getLongitude;
import static com.example.testing.MyApplication.getSpeed;
import static com.example.testing.MyApplication.getType;
import static com.example.testing.MyApplication.setDistance;
import static com.example.testing.MyApplication.setHorizAngle;
import static com.example.testing.MyApplication.setInitSpeed;
import static com.example.testing.MyApplication.setVertiAngle;
import static java.lang.Math.PI;
import static java.lang.Math.acos;
import static java.lang.Math.sqrt;

public class Calculations extends Service {
    private static final String TAG = "HelloService";

    private boolean isRunning  = false;

    @Override
    public void onCreate() {

        Toast.makeText(getBaseContext(), "Calculations are proccessing", Toast.LENGTH_SHORT).show();

        Log.i(TAG, "Service onCreate");

        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Service onStartCommand");

        //Creating new thread for my service
        //Always write your long running tasks in a separate thread, to avoid ANR
        new Thread(new Runnable() {
            @Override
            public void run() {
                double launLong=getLaunLongitude();
                double playerLong= getLongitude();
                double launLat= getLaunLatitude();
                double playerLat= getLatitude();
                String stringSpeed= getSpeed();
                String stringType= getType();
                double finalSpeed=0;
                double finalSpeedY=0;
                double height=0;
                double horizAngle;
                double initSpeedX=0;
                double initSpeedY=0;
                double initialSpeed=0;
                double motorSpeed=0;
                double vertAngle=0;
                double radius= 0.5;


                double theta = playerLong - launLong;
                //Calculate the hypotenuse between the longitude and latitude i.e. distance between
                // player and machine
                double dist = Math.sin((playerLat/180)*Math.PI) * Math.sin((launLat/180)*Math.PI)
                        + Math.cos((playerLat/180)*Math.PI) * Math.cos((launLat/180)*Math.PI) * Math.cos((theta/180)*Math.PI);
                dist = acos(dist);
                dist = (dist/Math.PI)*180;
                dist = dist * 60 * 1.1515;
                dist = dist * 1.609344;
                dist= dist*1000;

                //calculate the angle between the player and the machine
                // using latitude and longitude in XX.XXXXXX format
                double y = Math.sin(theta) * Math.cos(playerLat);
                double x = Math.cos(launLat) * Math.sin(playerLat) - Math.sin(launLat)
                        * Math.cos(playerLat) * Math.cos(theta);

                double brng = Math.atan2(y, x);

                brng = Math.toDegrees(brng);
                brng = (brng + 360) % 360;
                horizAngle = 360 - brng; // horizontal angle

                //Getting the final speed value depending on the player selection
                // medium= 27 m/s
                // High = 37 m/s
                // Low= 17 m/s
                if (stringSpeed.equals("MEDIUM"))
                    finalSpeed=27;
                else if (stringSpeed.equals("HIGH"))
                    finalSpeed=37;
                else if (stringSpeed.equals("LOW"))
                    finalSpeed=17;

                if (stringType.equals("HEADER"))
                    height= 1.8-0.5;
                else if (stringType.equals("CHEST"))
                    height= 1.3-0.5;
                else if (stringType.equals("FOOT"))
                    height= 0-0.5;

                double time1= 19.62*19.62;
                double time2= time1+ (7552.6*height);
                double time3= sqrt(time2);
                double time4= 19.62+ time3;
                double time= time4/(2*96.236);


                double initSpeedY1= time*time*4.905;
                double initSpeedY2= initSpeedY1 + height;
                initSpeedY= initSpeedY2/time;

                initSpeedX = dist/time;

                double initSpeedXX= initSpeedX * initSpeedX;
                double initSpeedYY= initSpeedY * initSpeedY;
                initialSpeed = sqrt (initSpeedXX + initSpeedYY);

                double vertAngle1= initSpeedX;
                double vertAngle2= acos(vertAngle1/finalSpeed);
                vertAngle= (vertAngle2/Math.PI)*180;
                motorSpeed= initialSpeed*2*PI*radius;
                int intVertAngle= (int) vertAngle;
                int intHorizAngle= (int) horizAngle;
                int intMotorSpeed= (int) motorSpeed;
                if (intVertAngle>30)
                    intVertAngle = intVertAngle-30;
                if (intVertAngle>30)
                    intVertAngle= intVertAngle-30;
                if (intVertAngle>30)
                    intVertAngle= intVertAngle-30;
                if (intVertAngle>30)
                    intVertAngle=30;
                if (intVertAngle==0){
                    if (stringType.equals("HEADER"))
                        intVertAngle=30;
                    else if (stringType.equals("CHEST"))
                        intVertAngle=10;
                }

                if (intMotorSpeed==0){
                    intMotorSpeed=10;
                }
                setInitSpeed(intMotorSpeed);
                setHorizAngle(intHorizAngle);
                setVertiAngle(intVertAngle);
                setDistance(dist);
                for (int i = 0; i < 5; i++) {
                    try {

                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }

                    if(isRunning){
                        Log.i(TAG, "Service running");
                    }
                }

                //Stop service once it finishes its task
                stopSelf();
            }
        }).start();

        return Service.START_STICKY;
    }


    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {

        isRunning = false;

        Log.i(TAG, "Service onDestroy");
    }
}
