package com.News.ns2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import android.os.Handler;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.News.ns2.Recycler.ItemObject;
import com.News.ns2.Recycler.MyAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class parsingActivity extends Activity {

    ProgressBar bar;
    TextView tv_notification;
    private TextView tv;
    boolean isRunning;
    Handler handler;
    MRunnable runnable;
    private RecyclerView recyclerView;
    private ArrayList<ItemObject> list = new ArrayList();

    String[] mArr = {"ZUM NOW 이슈 검색어", "키자드 - 실시간 검색어", "ZUM HOT 인기 검색어", "d"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsing);

       // new RealTask().execute();
        bar = (ProgressBar) findViewById(R.id.progress);

        handler = new Handler();
        runnable = new MRunnable();
        tv_notification =(TextView) findViewById(R.id.tv_notification);

        isRunning = true;


        bar.setProgress(0);

        new RealTask2().execute();
        tv_notification.setText(mArr[0]);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(isRunning){
                        for(int i = 0; i< 20 && isRunning; i++){
                            Thread.sleep(1500);

                            handler.post(runnable);
                        }
                    }

                }catch (Exception e){
                    Log.e("parsingActivity", "Exception thread", e);
                }
            }
        });

        isRunning = true;
        thread.start();

        recyclerView = (RecyclerView) findViewById(R.id.parsingRecyclerView);

    }


        class RealTask extends AsyncTask<Void, Void, Void> {

            private ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //진행다일로그 시작
                progressDialog = new ProgressDialog(parsingActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("잠시 기다려 주세요.");
                progressDialog.show();

            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Document doc = Jsoup.connect("https://keyzard.org/realtimekeyword").get();
                    Elements mElementDataSize = doc.select("table[class=table  table-hover table-striped ]").select("tr"); //필요한 녀석만 꼬집어서 지정
                    int mElementSize = mElementDataSize.size(); //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하나깐.

                    for (Element elem : mElementDataSize) {


                      //  Element rElem = elem.select("dl[class=info_txt1] dt").next().first();
                      //  String my_release = rElem.select("dd").text();
                        String my_title = elem.select("span[class=realtimeKeyRank]").text();
                        String my_release = elem.select("td[class=ellipsis100] a").text();
                      /*  Element rElem = elem.select("div [class=cont]").next().first();
                        String my_title =  rElem.select("span[class=num]").text();
                        String my_release = rElem.select("span[class=word]").text();*/


                        //Log.d("test", "test" + mTitle);
                        //ArrayList에 계속 추가한다.
                        list.add(new ItemObject(my_title, my_release));

                    }

                    Log.d("debug :", "List " + mElementDataSize);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                MyAdapter myAdapter = new MyAdapter(list);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myAdapter);

                progressDialog.dismiss();
            }
        }


    class RealTask2 extends AsyncTask<Void, Void, Void> {

            private ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //진행다일로그 시작
                progressDialog = new ProgressDialog(parsingActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("잠시 기다려 주세요.");
                progressDialog.show();

            }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect("http://issue.zum.com").get();
                Elements mElementDataSize = doc.select("div [id=issueKeywordList]").select("div"); //필요한 녀석만 꼬집어서 지정
                int mElementSize = mElementDataSize.size(); //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하나깐.

                for (Element elem : mElementDataSize) {


                    //  Element rElem = elem.select("dl[class=info_txt1] dt").next().first();
                    //  String my_release = rElem.select("dd").text();

                     /*  Element rElem = elem.select("ul [id=issueKeywordList] li").next().first();
                        String my_title =  rElem.select(".num").text();
                        String my_release = rElem.select(".span").text();*/


                   String my_title = elem.select(".num").text();
                   String my_release = elem.select(".word").text();

                    //Log.d("test", "test" + mTitle);
                    //ArrayList에 계속 추가한다.
                    list.add(new ItemObject(my_title, my_release));

                }

                Log.d("debug :", "List " + mElementDataSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            MyAdapter myAdapter = new MyAdapter(list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myAdapter);

            progressDialog.dismiss();
        }
    }



    public class MRunnable implements Runnable {
    @Override
    public void run() {
        //int i=0;
        MyAdapter myAdapter = new MyAdapter(list);
        bar.incrementProgressBy(5);

        if(bar.getProgress() <=(bar.getMax()/4)){
           if(bar.getProgress() ==(bar.getMax()/4)) {
               myAdapter.clear();
               new RealTask().execute();
               tv_notification.setText(mArr[1]);
           }
        }else if(bar.getProgress() <=bar.getMax()*2/4){
            if(bar.getProgress() ==bar.getMax()*2/4) {
                myAdapter.clear();
                new RealTask2().execute();
                tv_notification.setText(mArr[0]);
            }
        }else if(bar.getProgress() <=bar.getMax()*3/4){
            if(bar.getProgress() ==bar.getMax()*3/4) {
                myAdapter.clear();
                new RealTask().execute();
                tv_notification.setText(mArr[1]);
            }
        }else if(bar.getProgress() <=bar.getMax()-1){
            //if(bar.getProgress() == bar.getMax()-1) {

            //}
        }else{
            //tv_notification.setText(mArr[3]);
            bar.setProgress(0);
            myAdapter.clear();
            new RealTask2().execute();
            tv_notification.setText(mArr[0]);
        }


    }
}

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        isRunning=true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        isRunning = false;
    }

    @Override
    protected void onStop() {
        super.onStop();

        isRunning = false;

    }

    @Override
    protected void onPause() {
        super.onPause();

        isRunning = false;
    }

/*










   */

    }
