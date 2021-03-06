package com.News.ns2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link search#newInstance} factory method to
 * create an instance of this fragment.
 */
public class search extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public search() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment search.
     */
    // TODO: Rename and change types and number of parameters
    public static search newInstance(String param1, String param2) {
        search fragment = new search();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public WebView myWebView11;
    public WebView myWebView22;
    public WebView myWebView33;
    public WebView myWebView44;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs2) ; // ??? ???????????? ??????
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // ??? ???????????? ????????? ??????
            @Override
            public void onTabSelected(TabLayout.Tab tab) { // ??? ????????? ??????
                int pos = tab.getPosition() ; // ?????? ????????????
                changeView(pos) ; // ?????? ?????? ?????????
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {  // ?????? ???????????? ?????? ??????
                // do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { // ?????? ?????? ????????? ??????
                // do nothing
            }
        }) ;

        myWebView11 = (WebView) getActivity().findViewById(R.id.webview11); // ?????? ??????
        myWebView11.loadUrl("https://www.google.com/webhp?hl=ko&sa=X&ved=0ahUKEwitstqzppzwAhWRvZQKHTjuDQgQPAgI"); // ?????? ??????
        myWebView11.setWebChromeClient(new WebChromeClient()); // ?????? ???????????? ??????
        myWebView11.setWebViewClient(new search.WebViewClientClass()); // ?????? ?????????????????? ??????

       /* SearchView msearchview = (SearchView) getActivity().findViewById(R.id.searchView);
        msearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                // ???????????? ????????? ??????
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // ???????????? ???????????? ?????? ??? ??????
                return false;
            }
        });*/

    }

    private void changeView(int pos) {
        myWebView11 = (WebView) getActivity().findViewById(R.id.webview11);
        myWebView11.loadUrl("https://www.google.com/webhp?hl=ko&sa=X&ved=0ahUKEwitstqzppzwAhWRvZQKHTjuDQgQPAgI");
        myWebView11.setWebChromeClient(new WebChromeClient());
        myWebView11.setWebViewClient(new search.WebViewClientClass());

        myWebView22 = (WebView) getActivity().findViewById(R.id.webview22);
        myWebView22.loadUrl("https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=");
        myWebView22.setWebChromeClient(new WebChromeClient());
        myWebView22.setWebViewClient(new search.WebViewClientClass());

        myWebView33 = (WebView) getActivity().findViewById(R.id.webview33);https://
        myWebView33.loadUrl("namu.wiki/w/????????????:??????");
        myWebView33.setWebChromeClient(new WebChromeClient());
        myWebView33.setWebViewClient(new search.WebViewClientClass());

        myWebView44 = (WebView) getActivity().findViewById(R.id.webview44);
        myWebView44.loadUrl("https://ko.wikipedia.org/w/index.php?title=??????:??????&search=");
        myWebView44.setWebChromeClient(new WebChromeClient());
        myWebView44.setWebViewClient(new search.WebViewClientClass());


        switch (pos) { // ??? ????????? ???????????? ??????
            case 0 :
                myWebView11.setVisibility(View.VISIBLE) ;
                myWebView22.setVisibility(View.INVISIBLE) ;
                myWebView33.setVisibility(View.INVISIBLE) ;
                myWebView44.setVisibility(View.INVISIBLE) ;
                break ;
            case 1 :
                myWebView11.setVisibility(View.INVISIBLE) ;
                myWebView22.setVisibility(View.VISIBLE) ;
                myWebView33.setVisibility(View.INVISIBLE) ;
                myWebView44.setVisibility(View.INVISIBLE) ;
                break ;
            case 2 :
                myWebView11.setVisibility(View.INVISIBLE) ;
                myWebView22.setVisibility(View.INVISIBLE) ;
                myWebView33.setVisibility(View.VISIBLE) ;
                myWebView44.setVisibility(View.INVISIBLE) ;
                break ;
            case 3 :
                myWebView11.setVisibility(View.INVISIBLE) ;
                myWebView22.setVisibility(View.INVISIBLE) ;
                myWebView33.setVisibility(View.INVISIBLE) ;
                myWebView44.setVisibility(View.VISIBLE) ;
                break ;
        }
    }

    private class WebViewClientClass extends WebViewClient { //?????? ??? ????????? ??????
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("check URL",url);
            view.loadUrl(url);
            return true;
        }
    }
}