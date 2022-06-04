package com.example.onlaynmagazin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MainActivity7 extends AppCompatActivity {

    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        imageView=findViewById(R.id.imageview5);
        textView=findViewById(R.id.textview5);
        Intent intent=getIntent();
        String imageuri=intent.getStringExtra("image");
        String malumot=intent.getStringExtra("text");
        Glide.with(this).load(imageuri).into(imageView);
        textView.setText(malumot);
    }
}