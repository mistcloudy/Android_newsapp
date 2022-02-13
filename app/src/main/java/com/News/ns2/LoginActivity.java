package com.News.ns2;

import android.content.Intent;
import android.os.Bundle;
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

    private EditText login_email, login_password; // 아이디 비밀번호 에디트 텍스트 초기화
    private Button login_button, join_button; // 로그인 회원가입 버튼 초기화

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        login_email = findViewById( R.id.login_email );// 아이디 입력창 초기화
        login_password = findViewById( R.id.login_password ); // 비밀번호 입력창 초기화

        join_button = findViewById( R.id.join_button ); // 회원가입 버튼 초기화
        join_button.setOnClickListener( new View.OnClickListener() { // 회원가입 버튼 클릭 입력받을 수 있게 세팅

            @Override
            public void onClick(View view) { //버튼을 클릭 결과
                Intent intent = new Intent( LoginActivity.this, RegisterActivity.class );
                startActivity(intent); // 회원가입으로 이동
            }
        });


        login_button = findViewById( R.id.login_button ); // 로그인 버튼 초기화
        login_button.setOnClickListener( new View.OnClickListener() { // 로그인 버튼 클릭 입력받을 수 있게 세팅
            @Override
            public void onClick(View view) { // 버튼 클릭 결
                String UserEmail = login_email.getText().toString(); // 아이디 값을 불러옴
                String UserPwd = login_password.getText().toString(); // 비밀번호 값을 불러옴

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    //
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );

                            if(success) {//로그인 성공시

                                String UserEmail = jsonObject.getString( "UserEmail" );
                                String UserPwd = jsonObject.getString( "UserPwd" );
                                String UserName = jsonObject.getString( "UserName" );

                                Toast.makeText( getApplicationContext(), String.format("%s님 환영합니다.", UserName), Toast.LENGTH_SHORT ).show();

                                Intent intent = new Intent( LoginActivity.this, MainActivity.class ); // 메인액티비티로 인텐트를 보

                                intent.putExtra( "UserEmail", UserEmail );
                                intent.putExtra( "UserPwd", UserPwd );
                                intent.putExtra( "UserName", UserName );

                                startActivity( intent );

                            } else {//로그인 실패시
                                Toast.makeText( getApplicationContext(), "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT ).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest( UserEmail, UserPwd, responseListener );// 로그인리퀘스트 클래스 불러
                RequestQueue queue = Volley.newRequestQueue( LoginActivity.this );
                queue.add( loginRequest );

            }
        });
    }
}