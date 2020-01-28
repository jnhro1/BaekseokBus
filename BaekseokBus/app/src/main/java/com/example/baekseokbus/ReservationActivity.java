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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class ReservationActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "buswlab.dothome.co.kr/bubus";
    private static String TAG = "phptest";

    private TextView mTextViewResult;
    private EditText writer;
    Button btnSearch;
    TextView temp;
    Spinner startSpinner, endSpinner, timeSpinner;

    DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);
        setTitle("백석대학교 셔틀버스 예매 시스템");

        SharedPreferences getuserID = getSharedPreferences("getuserID", Context.MODE_PRIVATE);
        String userIds = getuserID.getString("userId", "");

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        writer = (EditText)findViewById(R.id.writerText);
        startSpinner = (Spinner) findViewById(R.id.spnStart);
        endSpinner = (Spinner) findViewById(R.id.spnEnd);
        timeSpinner = (Spinner) findViewById(R.id.spnTime);
        temp = (TextView) findViewById(R.id.temp);

        temp.setText("조나현");

        writer.setText(userIds);

        ImageView btnhome = (ImageView)findViewById(R.id.btnhome);

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReservationActivity.this, StartActivity.class);
                startActivity(i);
            }
        });

        mTextViewResult = findViewById(R.id.textView_main_result);

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        ArrayAdapter startAdapter = ArrayAdapter.createFromResource(this,
                R.array.start, android.R.layout.simple_spinner_item);
        startAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startSpinner.setAdapter(startAdapter);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR); //년도 추출, 가져오기 메소드
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePicker.init(year, month+1, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear += 1;
                String date = year + "/" + monthOfYear + "/" + dayOfMonth;
                temp.setText(date);
            }
        });

        startSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String start = startSpinner.getSelectedItem().toString();
                checkString(start);
                Log.d("start",start);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String start = startSpinner.getSelectedItem().toString();
                final String end = endSpinner.getSelectedItem().toString();
                final String time = timeSpinner.getSelectedItem().toString();
                String dateTemp = temp.getText().toString();
                String writerText = writer.getText().toString();
                writer.setText("");
              //  Toast.makeText(getApplicationContext(), dateTemp, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "예매완료.", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(ReservationActivity.this, StartActivity.class);
//                startActivity(i);
                Log.d("start",start);
                Log.d("end",end);
                Log.d("time",time);
                String startText = start;
                String arriveText = end;
                String dateText = dateTemp;
                String clockText = time;
                String userId = writerText;
                Toast.makeText(getApplicationContext(), clockText, Toast.LENGTH_SHORT).show();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insertReservation.php",startText,arriveText,dateText,clockText, userId);




            }
        });
    }
    public void checkString(String test){

        if (test.equals("BAEKSEOK")) {
            ArrayAdapter endAdapter = ArrayAdapter.createFromResource(this,
                    R.array.end, android.R.layout.simple_spinner_item);
            endAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            endSpinner.setAdapter(endAdapter);

            ArrayAdapter timeAdapter = ArrayAdapter.createFromResource(this,
                    R.array.time, android.R.layout.simple_spinner_item);
            timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            timeSpinner.setAdapter(timeAdapter);

        } else {
            ArrayAdapter endAdapter = ArrayAdapter.createFromResource(this,
                    R.array.end2, android.R.layout.simple_spinner_item);
            endAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            endSpinner.setAdapter(endAdapter);

            ArrayAdapter timeAdapter = ArrayAdapter.createFromResource(this,
                    R.array.time2, android.R.layout.simple_spinner_item);
            timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            timeSpinner.setAdapter(timeAdapter);

        }


    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ReservationActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
            Toast.makeText(getApplicationContext(), "예약이 완료되었습니다.",Toast.LENGTH_LONG).show();
            Intent i = new Intent(ReservationActivity.this, ReservationlistActivity.class);
            startActivity(i);
        }


        @Override
        protected String doInBackground(String... params) {

            String startText = (String)params[1];
            String arriveText = (String)params[2];
            String dateText = (String)params[3];
            String clockText = (String)params[4];
            String userId = (String)params[5];

            String serverURL = (String)params[0];
            String postParameters = "startText=" + startText + "&arriveText=" + arriveText + "&dateText=" + dateText + "&clockText=" + clockText + "&userId=" + userId;


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

