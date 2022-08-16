package com.burakgulsoy.freelancerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");
    }

    public void login(View view) {
        Intent firstLoginIntent = new Intent(this, LoginPage.class);
        startActivity(firstLoginIntent);

    }

    public void register(View view) {
        Intent registerLoginIntent = new Intent(this, RegisterPage.class);
        startActivity(registerLoginIntent);


    }

}