package com.example.testing;

import android.app.Application;

/**
 * Created by el-chanti on 2017-01-17.
 */

public class MyApplication extends Application {
    private static String type;
    private static String speed;
    private static double longitude;
    private static double latitude;
    private static double launLongitude;
    private static double launLatitude;
    private static double angle;
    private static String deviceName;
    private static int horizAngle;
    private static int vertiAngle;
    private static int initSpeed;
    private static double Distance;

    public static String getType() {
        return type;
    }
    public static void setType(String someVariable) {
        type = someVariable;
    }

    public static String getSpeed() {
        return speed;
    }
    public static void setSpeed(String someVariable) {
        speed = someVariable;
    }

    public static double getLongitude() {return longitude;}
    public static void setLongitude(double someVariable) {
        longitude = someVariable;
    }

    public static double getLatitude() {return latitude;}
    public static void setLatitude(double someVariable) {
        latitude = someVariable;
    }

    public static double getLaunLongitude() {return launLongitude;}
    public static void setLaunLongitude(double someVariable) {launLongitude = someVariable;}

    public static double getLaunLatitude() {
        return launLatitude;
    }
    public static void setLaunLatitude(double someVariable) {
        launLatitude = someVariable;
    }

    public static String getdeviceName() {
        return deviceName;
    }
    public static void setdeviceName(String someVariable) {
        deviceName = someVariable;
    }

    public static void setAngle(double someVariable){ angle=someVariable;}
    public static double getAngle() {return angle;}

    public static int getHorizAngle() {
        return horizAngle;
    }
    public static void setHorizAngle(int someVariable) {horizAngle = someVariable;}

    public static int getVertiAngle() {
        return vertiAngle;
    }
    public static void setVertiAngle(int someVariable) { vertiAngle= someVariable;}

    public static int getInitSpeed() {return initSpeed;}
    public static void setInitSpeed(int someVariable) {initSpeed = someVariable;}

    public static double getDistance() {return Distance;}
    public static void setDistance(double someVariable) {Distance = someVariable;}
}
