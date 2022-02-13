package com.News.ns2;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class NewsActivity extends AppCompatActivity {

    EditText searchEt;
    ImageView searchIb;
    Spinner newsSpinner;
    Button newsRecordBtn;


    String category = "";
    String[] spinnerArray = new String[]{"카테고리를 선택해주세요.","news", "book", "cafearticle", "kin"};
    String[] koreaArray = new String[]{"카테고리를 선택해주세요.","뉴스", "책", "네이버 카페", "지식 IN"};

    StringBuffer response;
    String naverHtml;

    RecyclerView mRecylerView = null;
    RecyclerNewsSearchAdapter mAdapter = null;
    private ArrayList<NewsRecyclerItem> mlist = new ArrayList<NewsRecyclerItem>();

    int position = -1;
    int newsSize = 0;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            Bundle bun = msg.getData();
            naverHtml = bun.getString("NAVER_HTML");

            naverHtml = naverHtml.replaceAll("<b>","");
            naverHtml = naverHtml.replaceAll("</b>","");
            naverHtml = naverHtml.replaceAll("<", "<");
            naverHtml = naverHtml.replaceAll(">", ">");
            naverHtml = naverHtml.replaceAll("&", "&");
            //naverHtml = naverHtml.replaceAll("\"", "");

            try {

                JSONObject jsonObject = new JSONObject(naverHtml);
                JSONArray newsArray = jsonObject.getJSONArray("items");

                mlist.clear();

                for(int i = 0; i < newsArray.length(); i++)
                {
                    JSONObject newsObject = newsArray.getJSONObject(i);


                    NewsRecyclerItem item = new NewsRecyclerItem();


                    item.setTitle(newsObject.getString("title"));
                    item.setDescription(newsObject.getString("description"));
                    item.setLink(newsObject.getString("link"));


                   // item.getTitle().replaceAll("\"", "");
                    mlist.add(item);
                }

                Log.v("main mAdapter","notifyDataSetChanged 호출");
                mAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsactivity);

        mRecylerView = findViewById(R.id.newsRecyclerView1);
        Toolbar tb = findViewById(R.id.app_toolbar1);
        searchEt = findViewById(R.id.searchEt1);
        searchIb = findViewById(R.id.searchIb1);
        newsSpinner = findViewById(R.id.newsSpinner1);
       // newsRecordBtn = findViewById(R.id.newsRecordBtn1);

        setSupportActionBar(tb);

        ActionBar ab = getSupportActionBar();

        ab.setTitle("NAVER 검색");

        Log.v("news Adapter 선언","RecyclerNewsSearchAdapter 선언");
        mAdapter = new RecyclerNewsSearchAdapter(mlist);

        Log.v("news mRecylerView","setAdapter");
        mRecylerView.setAdapter(mAdapter);

        Log.v("news mRecylerView","setLayoutManager");
        // 리사이클러뷰에 LinearLayoutManager 지정. (미지정: vertical)
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));


        Log.v("main mAdapter","notifyDataSetChanged 호출");
        mAdapter.notifyDataSetChanged();

        //아이템 클릭 리스너 => 기사보기, 스크랩 하기 버튼이 있는 다이얼로그 생성
        mAdapter.setOnItemClickListener(new RecyclerNewsSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                position = pos;

                AlertDialog.Builder builder = new AlertDialog.Builder(NewsActivity.this);

                builder.setTitle("기사 정보");
                        //.setMessage("기사 스크랩을 원하시면, 기사 스크랩 하기 버튼을 클릭해주세요.");

                builder.setPositiveButton("기사보기", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getApplicationContext(), "기사보기 Click", Toast.LENGTH_SHORT).show();
                     //   mlist.get(pos);
                    }
                });

              /*  builder.setNegativeButton("기사 스크랩", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getApplicationContext(), "스크랩 하였습니다.", Toast.LENGTH_SHORT).show();


                        if (mlist != null) {
                            Log.v("뉴스 기사 쉐어드 저장", "뉴스 기사 쉐어드 저장");
                            pref = getSharedPreferences("NewsPref", NewsActivity.MODE_PRIVATE);
                            editor = pref.edit();

                            //쉐어드에 저장한 뉴스 기사 전체 개수를 가져온다.
                            newsSize = pref.getInt("newsSize", 0);


                            String recordNewsCategory = "뉴스";

                            if(category.equals("news"))
                            {
                                recordNewsCategory = "뉴스";
                            }
                            else if(category.equals("book"))
                            {
                                recordNewsCategory = "책";
                            }
                            else if(category.equals("cafearticle"))
                            {
                                recordNewsCategory = "네이버 카페";
                            }
                            else if(category.equals("kin"))
                            {
                                recordNewsCategory = "지식 IN";
                            }

                            //item에 저장되어 있는 뉴스기사를 받아와서 쉐어드에 저장.
                            editor.putString(String.valueOf(newsSize), mlist.get(position).toString() +",,," + recordNewsCategory);

                            newsSize += 1;

                            editor.putInt("newsSize", newsSize);

                            editor.commit();


                        }
                    }
                });*/

                //얼럿다이얼로그 생성 및 보여주는 코드
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        ArrayAdapter myAdapter = new ArrayAdapter(NewsActivity.this, android.R.layout.simple_spinner_dropdown_item, koreaArray);

        newsSpinner.setAdapter(myAdapter);

        newsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if(!koreaArray[position].equals("카테고리를 선택해주세요."))
                {
                    Toast.makeText(getApplicationContext(), koreaArray[position] + " 선택",
                            Toast.LENGTH_SHORT).show();

                    category = spinnerArray[position];

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "카테고리를 선택해주세요.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //검색
        searchIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchNews(category, String.valueOf(searchEt.getText()));
            }
        });


        //저장한 뉴스 스크랩 보기
     /*   newsRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/




    }


    private void SearchNews(final String _category, final String searchWord)
    {

        if(_category.equals("") && _category != null)
        {
            Toast.makeText(getApplicationContext(), "카테고리를 선택해주세요.",
                    Toast.LENGTH_SHORT).show();
        }
        else if(searchWord.equals("") && searchWord != null)
        {
            Toast.makeText(getApplicationContext(), "검색어를 입력해주세요.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            new Thread() {
                @Override
                public void run() {

                    String clientId = "FfxDIY37wNPwLonzM9Tm";//애플리케이션 클라이언트 아이디값";
                    String clientSecret = "ShDiNdNRWW";//애플리케이션 클라이언트 시크릿값";
                    try {
                        String text = URLEncoder.encode(searchWord, "UTF-8");
                        String apiURL = "https://openapi.naver.com/v1/search/" + _category + "?query=" + text +"&display=20"; // json 결과
                        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과
                        URL url = new URL(apiURL);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        con.setRequestProperty("X-Naver-Client-Id", clientId);
                        con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                        int responseCode = con.getResponseCode();
                        BufferedReader br;
                        if (responseCode == 200) { // 정상 호출
                            br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                        } else {  // 에러 발생
                            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                        }
                        String inputLine;
                        response = new StringBuffer();
                        while ((inputLine = br.readLine()) != null) {
                            response.append(inputLine);
                            response.append("\n");
                        }
                        br.close();

                        String naverHtml = response.toString();

                        Bundle bun = new Bundle();
                        bun.putString("NAVER_HTML", naverHtml);
                        Message msg = handler.obtainMessage();
                        msg.setData(bun);
                        handler.sendMessage(msg);

                        //testText.setText(response.toString());
                        //System.out.println(response.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }

    }


}


