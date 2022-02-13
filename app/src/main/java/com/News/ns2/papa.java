package com.News.ns2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class papa extends AppCompatActivity {

    EditText inContent_et;
    TextView outContent_tv;
    Button setInLng_btn;
    Button setOutLng_btn;

    LinearLayout setInLngList;
    LinearLayout setOutLngList;

    Animation inputTranslateUp;
    Animation inputTranslateDown;
    Animation outputTranslateUp;
    Animation outputTranslateDown;

    boolean inputIsShown = false;
    boolean outputIsShown = false;

    String inputLng;
    String outputLng;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.papago_xml);

        if(ApiHelper.requestQueue != null) {
            ApiHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        inContent_et = findViewById(R.id.inContent_et);
        outContent_tv = findViewById(R.id.outContent_tv);

        setInLngList = findViewById(R.id.setInLnglist);
        setOutLngList = findViewById(R.id.setOutLnglist);

        inputTranslateUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_up);
        inputTranslateUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setInLng_btn.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        inputTranslateDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_down);

        outputTranslateUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_up);
        outputTranslateUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setInLng_btn.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        outputTranslateDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_down);

        setInLng_btn = findViewById(R.id.setInLng_btn);
        setInLng_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputIsShown == true) {
                    setInLngList.startAnimation(inputTranslateUp);
                } else {
                    setInLngList.startAnimation(inputTranslateDown);
                    setInLngList.setVisibility(View.VISIBLE);
                }
                inputIsShown = !inputIsShown;
            }
        });

        final Button inKo_btn = findViewById(R.id.inKo_btn);
        inKo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInLngList.startAnimation(inputTranslateUp);
                setInLng_btn.setText(inKo_btn.getText().toString());
                inputIsShown = false;
            }
        });
        final Button inEn_btn = findViewById(R.id.inEn_btn);
        inEn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInLngList.startAnimation(inputTranslateUp);
                setInLng_btn.setText(inEn_btn.getText().toString());
                inputIsShown = false;
            }
        });
        final Button inCn_btn = findViewById(R.id.inCn_btn);
        inCn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInLngList.startAnimation(inputTranslateUp);
                setInLng_btn.setText(inCn_btn.getText().toString());
                inputIsShown = false;
            }
        });
        final Button inTw_btn = findViewById(R.id.inTw_btn);
        inTw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInLngList.startAnimation(inputTranslateUp);
                setInLng_btn.setText(inTw_btn.getText().toString());
                inputIsShown = false;
            }
        });
        final Button inEs_btn = findViewById(R.id.inEs_btn);
        inEs_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInLngList.startAnimation(inputTranslateUp);
                setInLng_btn.setText(inEs_btn.getText().toString());
                inputIsShown = false;
            }
        });
        final Button inFr_btn = findViewById(R.id.inFr_btn);
        inFr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInLngList.startAnimation(inputTranslateUp);
                setInLng_btn.setText(inFr_btn.getText().toString());
                inputIsShown = false;
            }
        });
        final Button inVi_btn = findViewById(R.id.inVi_btn);
        inVi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInLngList.startAnimation(inputTranslateUp);
                setInLng_btn.setText(inVi_btn.getText().toString());
                inputIsShown = false;
            }
        });
        final Button inTh_btn = findViewById(R.id.inTh_btn);
        inTh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInLngList.startAnimation(inputTranslateUp);
                setInLng_btn.setText(inTh_btn.getText().toString());
                inputIsShown = false;
            }
        });
        final Button inIn_btn = findViewById(R.id.inIn_btn);
        inIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInLngList.startAnimation(inputTranslateUp);
                setInLng_btn.setText(inIn_btn.getText().toString());
                inputIsShown = false;
            }
        });


        setOutLng_btn = findViewById(R.id.setOutLng_btn);
        setOutLng_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (outputIsShown == true) {
                    setOutLngList.startAnimation(outputTranslateUp);
                } else {
                    setOutLngList.startAnimation(outputTranslateDown);
                    setOutLngList.setVisibility(View.VISIBLE);
                }
                outputIsShown = !outputIsShown;
            }
        });

        final Button outKo_btn = findViewById(R.id.outKo_btn);
        outKo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOutLngList.startAnimation(outputTranslateUp);
                setOutLng_btn.setText(outKo_btn.getText().toString());
                outputIsShown = false;
            }
        });
        final Button outEn_btn = findViewById(R.id.outEn_btn);
        outEn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOutLngList.startAnimation(outputTranslateUp);
                setOutLng_btn.setText(outEn_btn.getText().toString());
                outputIsShown = false;
            }
        });
        final Button outCn_btn = findViewById(R.id.outCn_btn);
        outCn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOutLngList.startAnimation(outputTranslateUp);
                setOutLng_btn.setText(outCn_btn.getText().toString());
                outputIsShown = false;
            }
        });
        final Button outTw_btn = findViewById(R.id.outTw_btn);
        outTw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOutLngList.startAnimation(outputTranslateUp);
                setOutLng_btn.setText(outTw_btn.getText().toString());
                outputIsShown = false;
            }
        });
        final Button outEs_btn = findViewById(R.id.outEs_btn);
        outEs_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOutLngList.startAnimation(outputTranslateUp);
                setOutLng_btn.setText(outEs_btn.getText().toString());
                outputIsShown = false;
            }
        });
        final Button outFr_btn = findViewById(R.id.outFr_btn);
        outFr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOutLngList.startAnimation(outputTranslateUp);
                setOutLng_btn.setText(outFr_btn.getText().toString());
                outputIsShown = false;
            }
        });
        final Button outVi_btn = findViewById(R.id.outVi_btn);
        outVi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOutLngList.startAnimation(outputTranslateUp);
                setOutLng_btn.setText(outVi_btn.getText().toString());
                outputIsShown = false;
            }
        });
        final Button outTh_btn = findViewById(R.id.outTh_btn);
        outTh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOutLngList.startAnimation(outputTranslateUp);
                setOutLng_btn.setText(outTh_btn.getText().toString());
                outputIsShown = false;
            }
        });
        final Button outIn_btn = findViewById(R.id.outIn_btn);
        outIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOutLngList.startAnimation(outputTranslateUp);
                setOutLng_btn.setText(outIn_btn.getText().toString());
                outputIsShown = false;
            }
        });


        Button translator_btn = findViewById(R.id.translator_btn);
        translator_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });
    }


    public void sendRequest() {

        if (setInLng_btn.getText().toString().equals("한국어")) {
            inputLng = "ko";
        } else if (setInLng_btn.getText().toString().equals("영어")) {
            inputLng = "en";
        } else if (setInLng_btn.getText().toString().equals("중국어 간체")) {
            inputLng = "zh-CN";
        } else if (setInLng_btn.getText().toString().equals("중국어 번체")) {
            inputLng = "zh=TW";
        } else if (setInLng_btn.getText().toString().equals("스페인어")) {
            inputLng = "es";
        } else if (setInLng_btn.getText().toString().equals("프랑스어")) {
            inputLng = "fr";
        } else if (setInLng_btn.getText().toString().equals("베트남어")) {
            inputLng = "vi";
        } else if (setInLng_btn.getText().toString().equals("태국")) {
            inputLng = "th";
        } else if (setInLng_btn.getText().toString().equals("인도네시아어")) {
            inputLng = "id";
        }

        if (setOutLng_btn.getText().toString().equals("한국어")) {
            outputLng = "ko";
        } else if (setInLng_btn.getText().toString().equals("영어")) {
            outputLng = "en";
        } else if (setInLng_btn.getText().toString().equals("중국어 간체")) {
            outputLng = "zh-CN";
        } else if (setInLng_btn.getText().toString().equals("중국어 번체")) {
            outputLng = "zh=TW";
        } else if (setInLng_btn.getText().toString().equals("스페인어")) {
            outputLng = "es";
        } else if (setInLng_btn.getText().toString().equals("프랑스어")) {
            outputLng = "fr";
        } else if (setInLng_btn.getText().toString().equals("베트남어")) {
            outputLng = "vi";
        } else if (setInLng_btn.getText().toString().equals("태국")) {
            outputLng = "th";
        } else if (setInLng_btn.getText().toString().equals("인도네시아어")) {
            outputLng = "id";
        }

        Request request = new StringRequest(
                Request.Method.POST,
                ApiHelper.host,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String body = null;
                        try {
                            body = new String(error.networkResponse.data, "UTF-8");
                            Log.d("papa", body);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("X-Naver-Client-Id", ApiHelper.clientId);
                params.put("X-Naver-Client-Secret", ApiHelper.clientSecret);
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("source", inputLng);
                params.put("target", outputLng);
                params.put("text", inContent_et.getText().toString());
                return params;
            }
        };
        request.setShouldCache(false);
        ApiHelper.requestQueue.add(request);

    }

    public void processResponse(String response) {
        Gson gson = new Gson();
        TranslationMessage translationMessage = gson.fromJson(response, TranslationMessage.class);
       if (translationMessage != null){
           outContent_tv.setText(translationMessage.message.result.translatedText);
       }
    }





}

