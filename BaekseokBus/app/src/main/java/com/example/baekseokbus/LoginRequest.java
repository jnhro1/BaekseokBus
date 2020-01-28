package com.example.baekseokbus;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    //서버 url 설정 (php 파일 연동)
    final static private String URL = "http://buswlab.dothome.co.kr/bubus/Login.php";
    private Map<String, String> map;

    public LoginRequest(String userId, String userPw, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userId", userId);
        map.put("userPw", userPw);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
