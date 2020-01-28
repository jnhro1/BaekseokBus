package com.example.baekseokbus;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ComplainRead extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complain_read);
        setTitle("백석대학교 셔틀버스 예매 시스템");

        SharedPreferences getuserID = getSharedPreferences("getuserID", Context.MODE_PRIVATE);
        String userId = getuserID.getString("userId", "");

        TextView titleText = (TextView)findViewById(R.id.titleText);
        TextView dateText = (TextView)findViewById(R.id.dateText);
        TextView contentText = (TextView)findViewById(R.id.contentText);
        TextView writerText = (TextView)findViewById(R.id.writerText);
        Button btnList = (Button)findViewById(R.id.btnList);
        Button btnUpdate = (Button)findViewById(R.id.btnUpdate);
        ImageView btnhome = (ImageView)findViewById(R.id.btnhome);

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ComplainRead.this, StartActivity.class);
                startActivity(i);
            }
        });

        writerText.setText(userId);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ComplainRead.this, ComplainActivity.class);
                startActivity(i);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ComplainRead.this, NoticeUpdate.class);
                startActivity(i);
            }
        });


    }
}
