package com.News.ns2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.News.ns2.MainActivity.tv;

public class TimerActivity extends AppCompatActivity {

    private TextView mTimeTextView;
    private TextView mRecordTextView;
    private Thread timeThread = null;
    private static Boolean isRunning = true;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;

  // private List<TaskModel> taskList;
    private List<TaskModel> taskList2;


    static ArrayList<String> array = new ArrayList<>();
    String alt;
    private static final String SETTINGS_PLAYER_JSON = "settings_item_json";

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




    public TimerActivity() throws ParseException {
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        recyclerView = findViewById(R.id.recyclerview12);
        mTimeTextView = (TextView) findViewById(R.id.timeView);
        //mRecordTextView = (TextView) findViewById(R.id.recordView);

        taskList2 = PrefConfig.readListFromPref(this);
        Collections.reverse(taskList2);

      //  taskList2.clear();
        if (taskList2 == null) {
            taskList2 = new ArrayList<>();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setHasFixedSize(true);
        adapter = new TaskAdapter(getApplicationContext(), taskList2);
        recyclerView.setAdapter(adapter);



        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try{
                    while(isRunning){
                            Thread.sleep(10);
                            mTimeTextView.setText(tv);
                        }
                }catch (InterruptedException e){
                   /* e.printStackTrace();
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                           record();
                        }
                    });
                    return;*/
                }
            }

        });

       /* array = MainActivity.getStringArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON);
        if (array != null) {
            for (String value : array) {

            }
        }*/


       /* Timeinfo timeinfo = new Timeinfo(h1, tv);
        timeinfo.Load(this);
        String time = timeinfo.time;
        String keep = timeinfo.keep;
        mRecordTextView.setText("사용날짜 : " + time + '\n' + "사용시간 : " + keep);*/

        isRunning = true;
        thread.start();

        // 메인 액티비티에서 결과값 받아와서 화면에 출력

        //  mTimeTextView.setText("");

      //  mRecordTextView.setText("");


      //  mRecordTextView.setText(mRecordTextView.getText() + mTimeTextView.getText().toString() + "\n");

      //  timeThread.interrupt();
    }

    public void record () {
        TaskModel taskModel = new TaskModel("확인시간 : " + getDate(), "사용시간 : " + tv );
        taskList2.add(taskModel);
        PrefConfig.writeListInPref(getApplicationContext(), taskList2);
        adapter.setTaskModelList(taskList2);
    }

    @Override
    protected void onStop() {
        super.onStop();
        record();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        taskList2.remove(taskList2.size() - 1);
        PrefConfig.writeListInPref(getApplicationContext(), taskList2);
        adapter.setTaskModelList(taskList2);

    }

    private String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        Date date = new Date();
        return String.valueOf(simpleDateFormat.format(date));
    }

    static void setStringArrayPref(Context context, String key, ArrayList<String> values) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();

        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }

        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }

        editor.apply();
    }

    static ArrayList<String> getStringArrayPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList urls = new ArrayList();

        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);

                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }


}
