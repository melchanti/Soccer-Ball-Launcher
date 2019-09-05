package com.example.testing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static com.example.testing.MyApplication.setSpeed;


public class speed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);

    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        Intent intent1 = new Intent(this, MapsActivity.class);
        Button clicked = (Button) view;
        String speed = clicked.getText().toString();
        setSpeed(speed);
        startActivity(intent1);
    }
}
