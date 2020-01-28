package com.example.baekseokbus;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class NoticeWrite extends AppCompatActivity {

    private static String IP_ADDRESS = "buswlab.dothome.co.kr/bubus";
    private static String TAG = "phptest";

    private EditText title;
    private EditText date;
    private EditText content;
    private EditText writer;
    private TextView mTextViewResult;
    private Button btnAdmit;
    private Button btnCancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_write);
        setTitle("백석대학교 셔틀버스 예매 시스템");

        SharedPreferences getuserID = getSharedPreferences("getuserID", Context.MODE_PRIVATE);
        String userId = getuserID.getString("userId", "");

        title = (EditText)findViewById(R.id.titleText);
        date = (EditText)findViewById(R.id.dateText);
        writer = (EditText)findViewById(R.id.writerText);
        content = (EditText)findViewById(R.id.contentText);
        btnAdmit = (Button)findViewById(R.id.btnAdmit);
        btnCancel = (Button)findViewById(R.id.btnCancel);

        writer.setText(userId);


        ImageView btnhome = (ImageView)findViewById(R.id.btnhome);

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NoticeWrite.this, StartActivity.class);
                startActivity(i);
            }
        });

        mTextViewResult = findViewById(R.id.textView_main_result);

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NoticeWrite.this, NoticeActivity.class);
                startActivity(i);
            }
        });

        btnAdmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleText = title.getText().toString();
                String dateText = date.getText().toString();
                String contentText = content.getText().toString();
                String writerText = writer.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insertNotice.php",titleText,dateText,contentText,writerText);

                title.setText("");
                date.setText("");
                content.setText("");
                writer.setText("");

            }
        });

    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(NoticeWrite.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
            Toast.makeText(getApplicationContext(), "게시글이 등록되었습니다.",Toast.LENGTH_LONG).show();
            Intent i = new Intent(NoticeWrite.this, NoticeActivity.class);
            startActivity(i);
        }


        @Override
        protected String doInBackground(String... params) {

            String titleText = (String)params[1];
            String dateText = (String)params[2];
            String contentText = (String)params[3];
            String writerText = (String)params[4];

            String serverURL = (String)params[0];
            String postParameters = "titleText=" + titleText + "&dateText=" + dateText + "&contentText=" + contentText + "&writerText=" + writerText;


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
