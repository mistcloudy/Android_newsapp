package com.News.ns2;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNV;
   // issue issue = new issue();
    ListView listview = null ;

    private Thread timeThread = null;
    private Boolean isRunning = true;
    static String tv;
    private IMyCounterService binder;
    boolean login = true;

    static ArrayList<String> array = new ArrayList<>();

    // ArrayList -> Json으로 변환
    private static final String SETTINGS_PLAYER_JSON = "settings_item_json";

    static ArrayList<TimeRecord> TimeRecords ;
    static String h1;


    long hour;
    long min;
    long second ;
   /* private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = IMyCounterService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };*/

   // private intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handleIntent(getIntent());

        timeThread = new Thread(new timeThread());
        timeThread.start();
        //스레드 시작


        //  mRecordTextView.setText("");
      //  timeThread.interrupt();
        array = getStringArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        Date date = new Date();
        String currDateStr = simpleDateFormat.format(date);
        h1 = currDateStr;

        mBottomNV = findViewById(R.id.nav_view);
        mBottomNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() { //NavigationItemSelecte
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                BottomNavigate(menuItem.getItemId());


                return true;
            }
        });
        mBottomNV.setSelectedItemId(R.id.navigation_issue);
       /*Button btn = (Button)findViewById(R.id.loginbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent( MainActivity.this, LoginActivity.class );
                startActivity(intent1);
            }
        });*/



       /* final String[] items = {"로그인", "회원가입"} ;
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items) ;

        listview = (ListView) findViewById(R.id.drawer_menulist) ;
        listview.setAdapter(adapter) ;

        listview.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                TextView contentTextview = (TextView) findViewById(R.id.drawer_content);

                switch (position) {
                    case 0: // WHITE
                        Intent intent1 = new Intent( MainActivity.this, LoginActivity.class );
                        startActivity( intent1 );
                       /* contentTextview.setBackgroundColor(Color.rgb(0xFF, 0xFF, 0xFF));
                        contentTextview.setTextColor(Color.rgb(0x00, 0x00, 0x00));
                        contentTextview.setText("로그인");*/
                  /*      break;
                    case 1: // RED
                        Intent intent2 = new Intent( MainActivity.this, RegisterActivity.class );
                        startActivity( intent2 );

                        /*contentTextview.setBackgroundColor(Color.rgb(0xFF, 0x00, 0x00));
                        contentTextview.setTextColor(Color.rgb(0xFF, 0xFF, 0xFF));
                        contentTextview.setText("회원가입");*/
                    //    break;
                                                   /* case 2: // GREEN
                                                        contentTextview.setBackgroundColor(Color.rgb(0x00, 0xFF, 0x00));
                                                        contentTextview.setTextColor(Color.rgb(0x00, 0x00, 0x00));
                                                        contentTextview.setText("GREEN");
                                                        break;
                                                    case 3: // BLUE
                                                        contentTextview.setBackgroundColor(Color.rgb(0x00, 0x00, 0xFF));
                                                        contentTextview.setTextColor(Color.rgb(0xFF, 0xFF, 0xFF));
                                                        contentTextview.setText("BLUE");
                                                        break;
                                                    case 4: // BLACK
                                                        contentTextview.setBackgroundColor(Color.rgb(0x00, 0x00, 0x00));
                                                        contentTextview.setTextColor(Color.rgb(0xFF, 0xFF, 0xFF));
                                                        contentTextview.setText("BLACK");
                                                        break;*/
           /*     }
            }
        });
        listview.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer) ;

                drawer.closeDrawer(Gravity.LEFT) ;
            }
        });
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();*/


    }




    private void setStringArrayPref(Context context, String key, ArrayList<String> values) {

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

    private ArrayList getStringArrayPref(Context context, String key) {

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


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

            menu.findItem(R.id.loginbtn).setVisible(false);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.loginbtn) {
            login = false;
            Intent intent1 = new Intent( MainActivity.this, LoginActivity.class );
            startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }


    private void BottomNavigate(int id) {  //BottomNavigation 페이지 변경
        String tag = String.valueOf(id);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            if (id == R.id.navigation_issue) {
                fragment = new issue();
            } else if (id == R.id.navigation_search){
                fragment = new search();
            }else if (id == R.id.navigation_notice) {
                fragment = new notice();
            }

            fragmentTransaction.add(R.id.content_layout, fragment, tag);
        } else {
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNow();


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeThread.interrupt();
        String currDateStr = h1;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        Date date = new Date();
        String inputDm = simpleDateFormat.format(date);

        Date inputDate = null;
        try {
            inputDate = simpleDateFormat.parse(inputDm);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date currDate = null;
        try {
            currDate = simpleDateFormat.parse(currDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        hour = (inputDate.getTime() - currDate.getTime()) / (60 * 60 * 1000);
        min = (inputDate.getTime() - currDate.getTime()) / (60 * 1000);
        second = (inputDate.getTime() - currDate.getTime()) / (1000);

        // 앱 종료할 떄 스레드 종료
       // String str = Long.toString(hour) + Long.toString(min) + Long.toString(second) ;

            array.add(h1);
            array.add(tv);
        setStringArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON, array);

        Timeinfo timeinfo = new Timeinfo(h1, tv);
        timeinfo.Save(this);
    }

    private final MyHandler mHandler = new MyHandler(this);
    //핸들러 선언

    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;
        public MyHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg){
            MainActivity activity = mActivity.get();
            if (activity != null){

                activity.handleMessage(msg);
            }
        }
    }
    //메인 액티비티에 핸들러 적용

    private void handleMessage(Message msg) {
        int mSec = msg.arg1 % 100;
        int sec = (msg.arg1 / 100) % 60;
        int min = (msg.arg1 / 100) / 60;
        int hour = (msg.arg1 / 100) / 360;
        //1000이 1초 1000*60 은 1분 1000*60*10은 10분 1000*60*60은 한시간

         String result = String.format("%02d:%02d:%02d:%02d", hour, min, sec, mSec);

        tv = result;
    }
    // 결과값 String tv에 저장


    public class timeThread implements Runnable {
        @Override
        public void run() {
            int i = 0;

            while (true) {
                while (isRunning) { //일시정지를 누르면 멈춤
                    Message msg = new Message();
                    msg.arg1 = i++;
                    mHandler.sendMessage(msg);

                    // 핸들러에 메세지 전달
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                             //   mTimeTextView.setText("");
                                 tv = "00:00:00:00";
                            }
                        });
                        return; // 인터럽트 받을 경우 return
                    }
                }
            }
        }
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//뒤로가기 버튼 이벤트
        if ((keyCode == KeyEvent.KEYCODE_BACK) && issue.myWebView1.canGoBack()) {//웹뷰에서 뒤로가기 버튼을 누르면 뒤로가짐
            issue.myWebView1.goBack();
            return true;
        }
        if ((keyCode == KeyEvent.KEYCODE_BACK) && issue.myWebView2.canGoBack()) {//웹뷰에서 뒤로가기 버튼을 누르면 뒤로가짐
            issue.myWebView2.goBack();
            return true;
        }
        if ((keyCode == KeyEvent.KEYCODE_BACK) && issue.myWebView3.canGoBack()) {//웹뷰에서 뒤로가기 버튼을 누르면 뒤로가짐
            issue.myWebView3.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

}