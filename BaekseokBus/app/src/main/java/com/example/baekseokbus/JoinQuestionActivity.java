package com.example.baekseokbus;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class JoinQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_question);
        setTitle("백석대학교 셔틀버스 예매 시스템");

        TextView studentText = (TextView)findViewById(R.id.studentText);
        TextView driverText = (TextView)findViewById(R.id.driverText);

        studentText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JoinQuestionActivity.this, JoinStudentActivity.class);
                startActivity(i);
            }
        });

        driverText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JoinQuestionActivity.this, JoinDriverActivity.class);
                startActivity(i);
            }
        });

    }
}
