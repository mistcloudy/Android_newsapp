package com.News.ns2;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.News.ns2.api.ApiClient;
import com.News.ns2.api.ApiInterface;
import com.News.ns2.models.Articles;
import com.News.ns2.models.News;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Newsapi extends AppCompatActivity implements  SwipeRefreshLayout.OnRefreshListener {

    public static final String API_KEY = "621ebc965b8848d7a5f65371d1486d99"; // news api 키코드 (본인 계정)
    private RecyclerView recyclerView;
    private List<Articles> articles = new ArrayList<>();
    private Adapter adapter;
    private final String TAG =  Newsapi.class.getSimpleName();
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout errorLayout;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;

    // 필요한 레이아웃 및 뷰 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsapi);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        // swiperefreshlayout은 위로 당겨서 새로고침하는 레이아웃

        recyclerView = findViewById(R.id.newsapiRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Newsapi.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        // 리사이클러뷰 적용

        onLoadingSwipeRefresh("");

        errorLayout = findViewById(R.id.errorLayout);
        errorImage = findViewById(R.id.errorImage);
        errorTitle = findViewById(R.id.errorTitle);
        errorMessage = findViewById(R.id.errorMessage);
        btnRetry = findViewById(R.id.btnRetry);

        //에러메세지 적용, 에러메세지는 인터넷 연결이 안 될때 출력
    }

    private void onLoadingSwipeRefresh(String keyword) {
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadJson(keyword);
                    }
                }
        );
    }
    // swiperefresh 호출결과 -> loadjson 을 다시 출력

    public void LoadJson(final String keyword) {

        errorLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);
        //에러 레이아웃은 안보이게, swiperefresh는 작동하게 적용

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        // 작성해두었던 apiinterface 클래스 불러옴

        String country = Utils.getCountry();
        String language = Utils.getLanguage();
        //Utils에 만들어 두었던 국가, 언어 데이터 불러옴

        Call<News> call;
        //콜백하기 위해 선언
        if (keyword.length() > 0 ){
            call = apiInterface.getNewsSearch(keyword, language, "publishedAt", API_KEY);
        } else {
            call = apiInterface.getNews(country, API_KEY);
        }

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NotNull Call<News> call, @NotNull Response<News> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {

                    if (!articles.isEmpty()) {
                        articles.clear();
                    }

                    articles = response.body().getArticles(); // 추출할 태그의 데이터들을 불러옴
                    adapter = new Adapter(articles, Newsapi.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    //추출한 데이터를 리사이클러뷰에 저장
                    initListener();


                    swipeRefreshLayout.setRefreshing(false);


                } else {   // 오류가 났을때 출력

                    swipeRefreshLayout.setRefreshing(false);

                    String errorCode;
                    switch (response.code()) {
                        case 404:
                            errorCode = "404 찾지 못했습니다";
                            break;
                        case 500:
                            errorCode = "500 서버가 없습니다";
                            break;
                        default:
                            errorCode = "알 수 없는 에러";
                            break;
                    } //에러가 났을때 나오는 텍스트

                    showErrorMessage(
                            R.drawable.no_result,
                            "결과 없음",
                            "다시 시도해주세요!\n" +
                                    errorCode);

                }

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

                swipeRefreshLayout.setRefreshing(false);
                showErrorMessage(
                        R.drawable.oops,
                        "이런..",
                        "네트워크 연결에 실패했습니다. 다시 시도해주세요.\n"+
                                t.toString());
            }
        });

    }

            private void showErrorMessage(int imageView, String title, String message){

                if (errorLayout.getVisibility() == View.GONE) {
                    errorLayout.setVisibility(View.VISIBLE);
                }

                errorImage.setImageResource(imageView);
                errorTitle.setText(title);
                errorMessage.setText(message);

                btnRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onLoadingSwipeRefresh("");
                    }
                });

            }


            //이 부분은 뉴스 목록에서 뉴스를 선택했을때 뉴스기사를 확인 할 수 있게 함.
            private void initListener() {
                adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ImageView imageView = view.findViewById(R.id.img);
                        //뉴스디테일액티비티에 데이터 전달
                        Intent intent = new Intent(Newsapi.this, NewsDetailActivity.class);

                        //태그 데이터들을 전달
                        Articles article = articles.get(position);
                        intent.putExtra("url", article.getUrl());
                        intent.putExtra("title", article.getTitle());
                        intent.putExtra("img",  article.getUrlToImage());
                        intent.putExtra("date",  article.getPublishedAt());
                        intent.putExtra("source",  article.getSource().getName());
                        intent.putExtra("author",  article.getAuthor());


                        // 이미지뷰와 이미지뷰 이름을 전달
                        Pair<View, String> pair = Pair.create((View)imageView, ViewCompat.getTransitionName(imageView));
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                Newsapi.this,
                                pair
                        );


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            startActivity(intent, optionsCompat.toBundle());
                        }else {
                            startActivity(intent);
                        }

                    }
                });
            }

            // 뉴스 검색을 할 수 있는 기능입니다.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_newsapi, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("최근 뉴스를 찾습니다.");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 1){
                    onLoadingSwipeRefresh(query);
                }
                else {
                    Toast.makeText(Newsapi.this, "글자 수는 2개 이상이어야 합니다", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }

    @Override
    public void onRefresh() {
        LoadJson("");
    }


}

