package com.News.ns2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class CancelPopup extends Activity {

    String key;
    int position;
    TextView txt;
    Button confirm;
    Button cancel;
    PreferenceManager1 pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cancel_popup);

        pref = new PreferenceManager1();

        //UI 객체생성
        txt = (TextView)findViewById(R.id.txt);
        confirm = findViewById(R.id.confirm);
        cancel = findViewById(R.id.cancel);


        //데이터 가져오기
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        int position = intent.getIntExtra("position", 0);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.putExtra("key", key);
                intent.putExtra("position", position);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
            });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}

