package com.News.ns2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.POST;

public class NaverTranslateApiActivity extends AppCompatActivity  implements Callback<NaverTranslateApiActivity.Result> {

    private EditText mTextEditText;
    private Spinner mStartLangSpinner;
    private Spinner mEndLangSpinner;
    private TextView mResultTextView;

    public static final String BASE_URL = "https://openapi.naver.com/v1/papago/n2mt/";

    public static class Result {
        public String translatedText;
        public String srcLangType;
        public String tarLangType;
    }

    interface NaverTranslateApi {
        @POST("/")
        Call<Result> getTraslatedText(@Field("X-Naver-Client-Id") String id,
                                      @Field("X-Naver-Client-Secret") String secret,
                                      @Field("source") String source,
                                      @Field("target") String target,
                                      @Field("text") String text);

    }


    private NaverTranslateApi mNaverTranslateApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver_translate_api);

        mTextEditText = (EditText) findViewById(R.id.text_edit);
        mStartLangSpinner = (Spinner) findViewById(R.id.start_lang_spinner);
        mEndLangSpinner = (Spinner) findViewById(R.id.end_lang_spinner);
        mResultTextView = (TextView) findViewById(R.id.result_text);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mNaverTranslateApi = retrofit.create(NaverTranslateApi.class);
    }

    public void onClick(View view) {
        String text = mTextEditText.getText().toString();
        String srcLangType = (String) mStartLangSpinner.getSelectedItem();
        String tarLangType = (String) mEndLangSpinner.getSelectedItem();

        Log.d("Naver", "onClick: " + srcLangType + ", " +tarLangType);

        Call<Result> call = mNaverTranslateApi.getTraslatedText("EuX3QDI4Y3ErrCji4GgS",
                "IrtIAFblKc",
                srcLangType,
                tarLangType,
                text);

        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Result> call, Response<Result> response) {
        mResultTextView.setText(response.body().translatedText);
    }

    @Override
    public void onFailure(Call<Result> call, Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

}