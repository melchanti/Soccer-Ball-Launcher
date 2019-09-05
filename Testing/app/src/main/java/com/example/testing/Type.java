package com.example.testing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.testing.MyApplication.setType;

public class Type extends AppCompatActivity {

    public final static String Shot_Type = "com.example.Testing.type";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, speed.class);
        Button clicked = (Button) view;
        String type = clicked.getText().toString();
        setType(type);
        startActivity(intent);
    }
}
