package com.News.ns2;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {


    final static private String URL = "http://nova5882.dothome.co.kr/Login.php"; //서버 URL 설정(php 파일 연동)
    private Map<String, String> map; // map 선언

    public LoginRequest(String UserEmail, String UserPwd, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>(); // map 해쉬맵 적용
        map.put("UserEmail", UserEmail); // 아이디 입력
        map.put("UserPwd", UserPwd); // 패스워드 입력
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
