package com.example.baekseokbus;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class NoticeUpdate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_update);
        setTitle("백석대학교 셔틀버스 예매 시스템");

        EditText titleText = (EditText)findViewById(R.id.titleText);
        EditText dateText = (EditText)findViewById(R.id.dateText);
        EditText contentText = (EditText)findViewById(R.id.contentText);
        Button btnAdmit2 = (Button)findViewById(R.id.btnAdmit2);
        Button btnDelete = (Button)findViewById(R.id.btnDelete);

        ImageView btnhome = (ImageView)findViewById(R.id.btnhome);

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NoticeUpdate.this, StartActivity.class);
                startActivity(i);
            }
        });

        btnAdmit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NoticeUpdate.this, NoticeRead.class);
                startActivity(i);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NoticeUpdate.this, NoticeActivity.class);
                startActivity(i);
            }
        });


    }
}
