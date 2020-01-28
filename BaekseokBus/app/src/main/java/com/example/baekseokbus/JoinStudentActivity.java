package com.example.baekseokbus;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;


public class JoinStudentActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "buswlab.dothome.co.kr/bubus";
    private static String TAG = "phptest";

    private EditText idText;
    private EditText pwText;
    private EditText nameText;
    private EditText birthText;
    private EditText phoneText;
    private Button btnJoin;
    private TextView mTextViewResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_student);
        setTitle("백석대학교 셔틀버스 예매 시스템");

        idText = findViewById(R.id.idText);
        pwText = findViewById(R.id.pwText);
        nameText = findViewById(R.id.nameText);
        birthText = findViewById(R.id.birthText);
        phoneText = findViewById(R.id.phoneText);
        btnJoin = findViewById(R.id.btnJoin);

        mTextViewResult = findViewById(R.id.textView_main_result);

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());


        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId = idText.getText().toString();
                String userPw = pwText.getText().toString();
                String userName = nameText.getText().toString();
                String userBirth = birthText.getText().toString();
                String userPhone = phoneText.getText().toString();


                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insertStudent.php",userId,userPw,userName,userBirth,userPhone);

                idText.setText("");
                pwText.setText("");
                nameText.setText("");
                birthText.setText("");
                phoneText.setText("");

            }
        });

    }



    class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(JoinStudentActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
            Intent i = new Intent(JoinStudentActivity.this, LoginActivity.class);
            startActivity(i);
        }


        @Override
        protected String doInBackground(String... params) {

            String userId = (String)params[1];
            String userPw = (String)params[2];
            String userName = (String)params[3];
            String userBirth = (String)params[4];
            String userPhone = (String)params[5];


            String serverURL = (String)params[0];
            String postParameters = "userId=" + userId + "&userPw=" + userPw + "&userName=" + userName + "&userBirth=" + userBirth + "&userPhone=" + userPhone;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }


}