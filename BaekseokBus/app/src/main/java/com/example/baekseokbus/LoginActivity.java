package com.example.baekseokbus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText idText, pwText;
    private Button btnLogin, btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle("백석대학교 셔틀버스 예매 시스템");

        idText = findViewById(R.id.idText);
        pwText = findViewById(R.id.pwText);
        btnLogin = findViewById(R.id.btnLogin);
        btnJoin = findViewById(R.id.btnJoin);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, JoinQuestionActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                final String userId = idText.getText().toString();
                final String userPw = pwText.getText().toString();
                Log.d("userid",userId);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("test1","123");
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                String userId = jsonObject.getString("userId");
                                String userPw = jsonObject.getString("userPw");
                                String userRank = jsonObject.getString("userRank");
                                String userName = jsonObject.getString("userName");
                                String userPhone = jsonObject.getString("userPhone");
                                String userBirth = jsonObject.getString("userBirth");
                                String driverBus = jsonObject.getString("driverBus");
                                Toast.makeText(getApplicationContext(), "로그인되었습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                                startActivity(intent);

                                SharedPreferences getuserID = getSharedPreferences("getuserID", MODE_PRIVATE);
                                SharedPreferences.Editor editor = getuserID.edit();
                                editor.putString("userId", userId).commit();

                                SharedPreferences getuserNAME = getSharedPreferences("getuserNAME", MODE_PRIVATE);
                                SharedPreferences.Editor editor1 = getuserNAME.edit();
                                editor1.putString("userName", userName).commit();

                                SharedPreferences getuserRANK = getSharedPreferences("getuserRANK", MODE_PRIVATE);
                                SharedPreferences.Editor editor2 = getuserRANK.edit();
                                editor2.putString("userRank", userRank).commit();

                                SharedPreferences getuserPHONE = getSharedPreferences("getuserPHONE", MODE_PRIVATE);
                                SharedPreferences.Editor editor3 = getuserPHONE.edit();
                                editor3.putString("userPhone", userPhone).commit();

                                SharedPreferences getuserBIRTH = getSharedPreferences("getuserBIRTH", MODE_PRIVATE);
                                SharedPreferences.Editor editor4 = getuserBIRTH.edit();
                                editor4.putString("userBirth", userBirth).commit();

                                SharedPreferences getuserDRIVERBUS = getSharedPreferences("getuserDRIVERBUS", MODE_PRIVATE);
                                SharedPreferences.Editor editor5 = getuserDRIVERBUS.edit();
                                editor5.putString("driverBus", driverBus).commit();



                            } else {
                                Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userId, userPw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

            }
        });

    }
}