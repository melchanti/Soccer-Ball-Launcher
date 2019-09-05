package com.example.testing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static com.example.testing.MyApplication.setHorizAngle;
import static com.example.testing.MyApplication.setInitSpeed;
import static com.example.testing.MyApplication.setVertiAngle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user clicks the Send button */
    public void LauncherPosition(View view) {
        Intent intent1 = new Intent(this, Launcher.class);
        startActivity(intent1);
    }

    public void PlayerPosition(View view) {
        Intent intent1 = new Intent(this, Type.class);
        startActivity(intent1);
    }

    public void ResetPosition(View view) {
        int IAngle=90;
        setInitSpeed(0);
        setHorizAngle(90);
        setVertiAngle(0);
        Intent intent1 = new Intent(this, Connect.class);
        startActivity(intent1);
    }


}
