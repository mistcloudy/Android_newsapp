package com.News.ns2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class timeActivity extends AppCompatActivity {


    BackgroundThread backgroundThread;
    TextView myText;
    //private boolean myTextOn = true;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    Date date = new Date();
    String currDateStr = simpleDateFormat.format(date);
    //현재시간 구하기 (시작 시간)

    String inputDm = simpleDateFormat.format(date);
    //시간 차이 되는 부분 고려해서 넣어야 함  (종료 시간)

    Date inputDate = simpleDateFormat.parse(inputDm);
    Date currDate = simpleDateFormat.parse(currDateStr);
    // 시작 시간, 종료 시간 파싱해서 계산할 수 있게 바꿈

    long hour = (inputDate.getTime() - currDate.getTime()) / (60 * 60 * 1000);
    long min = (inputDate.getTime() - currDate.getTime()) / (60 * 1000);
    long second = (inputDate.getTime() - currDate.getTime()) / (1000);
    // (시작 시간 - 종료시간)  시간 차이나는 부분을 시, 분, 초로 얻음


    public timeActivity() throws ParseException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_activity);

        myText = findViewById(R.id.timetxt);
    }

    private final MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<timeActivity> mActivity;
        public MyHandler(timeActivity activity) {
            mActivity = new WeakReference<timeActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg){
           timeActivity activity = mActivity.get();
            if (activity != null){
                activity.handleMessage(msg);
            }
        }
    }


    public void handleMessage(Message msg) {
        myText.setText(DateFormat.getDateTimeInstance().format(new Date()));
//        if(myTextOn){
//            myTextOn = false;
//            myText.setText(DateFormat.getDateTimeInstance().format(new Date()));
//            myText.setVisibility(View.GONE);
//        } else{
//            myTextOn = true;
//            myText.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        backgroundThread = new BackgroundThread();
        backgroundThread.setRunning(true);
        backgroundThread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        boolean retry = true;
        backgroundThread.setRunning(false);

        while(retry){
            try{
                backgroundThread.join();
                retry = false;
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        Toast.makeText(this, "onStop()", Toast.LENGTH_SHORT).show();
    }

    public class BackgroundThread extends Thread{
        boolean running = false;
        void setRunning(boolean b) {
            running = b;
        }

        @Override
        public void run(){
            while (running){
                try {
                    sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                mHandler.sendMessage(mHandler.obtainMessage());
            }
        }
    }
}