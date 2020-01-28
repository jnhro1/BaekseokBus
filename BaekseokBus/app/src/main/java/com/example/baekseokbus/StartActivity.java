package com.example.baekseokbus;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class StartActivity extends AppCompatActivity {

    String userId;
    String userRank;
    String userName;
    String userPhone;
    String userBirth;
    String driverBus;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        setTitle("백석대학교 셔틀버스 예매 시스템");



        Button btnReservation = (Button)findViewById(R.id.btnReservation);
        Button btnConfirm = (Button)findViewById(R.id.btnConfirm);
        Button btnNotice = (Button)findViewById(R.id.btnNotice);
        Button btnComplain = (Button)findViewById(R.id.btnComplain);
        Button btnMypage = (Button)findViewById(R.id.btnMypage);



        btnReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this, ReservationActivity.class);
                startActivity(i);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this, ReservationlistActivity.class);
                startActivity(i);
            }
        });

        btnNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this, NoticeActivity.class);
                startActivity(i);
            }
        });

        btnComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this, ComplainActivity.class);
                startActivity(i);
            }
        });

        btnMypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test","test");
                //

                Intent intent = new Intent(StartActivity.this, Mypage.class);
                startActivity(intent);
            }
        });

    }
}