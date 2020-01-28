package com.example.baekseokbus;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Mypage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);
        setTitle("백석대학교 셔틀버스 예매 시스템");

        SharedPreferences getuserID = getSharedPreferences("getuserID", Context.MODE_PRIVATE);
        String userId2 = getuserID.getString("userId", "");

        SharedPreferences getuserNAME = getSharedPreferences("getuserNAME", Context.MODE_PRIVATE);
        String userName1 = getuserNAME.getString("userName", "");

        SharedPreferences getuserRANK = getSharedPreferences("getuserRANK", Context.MODE_PRIVATE);
        String userRank1 = getuserRANK.getString("userRank", "");

        SharedPreferences getuserPHONE = getSharedPreferences("getuserPHONE", Context.MODE_PRIVATE);
        String userPhone1 = getuserPHONE.getString("userPhone", "");

        SharedPreferences getuserBIRTH = getSharedPreferences("getuserBIRTH", Context.MODE_PRIVATE);
        String userBirth1 = getuserBIRTH.getString("userBirth", "");

        SharedPreferences getuserDRIVERBUS = getSharedPreferences("getuserDRIVERBUS", Context.MODE_PRIVATE);
        String userDriverbus1 = getuserDRIVERBUS.getString("driverBus", "");

        TextView userName = (TextView)findViewById(R.id.userName);
        TextView userId = (TextView)findViewById(R.id.userId);
        TextView userRank = (TextView)findViewById(R.id.userRank);
        TextView userPhone = (TextView)findViewById(R.id.userPhone);
        TextView userBirth = (TextView)findViewById(R.id.userBirth);
        TextView userdriverBus = (TextView)findViewById(R.id.userdriverBus);

        userName.setText(userName1);
        userId.setText(userId2);
        userRank.setText(userRank1);
        userPhone.setText(userPhone1);
        userBirth.setText(userBirth1);
        userdriverBus.setText(userDriverbus1);





        Button btnMain = (Button)findViewById(R.id.btnMain);

//        userId.setText(userId2);

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Mypage.this, StartActivity.class);

                startActivity(i);
            }
        });

    }
}