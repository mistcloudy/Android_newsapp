package com.News.ns2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//import com.prolificinteractive.materialcalendarview.*;
//import com.prolificinteractive.materialcalendarview.spans.DotSpan;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link notice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class notice extends Fragment implements View.OnClickListener {
    private View view;
  /*  TextView selectday;
    MaterialCalendarView calendarView;
    HashMap<CalendarDay,List<RecyclerItem>> map = new HashMap<>();
    RecyclerView rv;
    RecyclerView rv1;
    Parcelable rvstates;
    ItemTouchHelper helper;
    RecyclerAdapter adapter;
    RecyclerAdapter adapter1;  */

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public notice() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment norice.
     */
    // TODO: Rename and change types and number of parameters
    public static notice newInstance(String param1, String param2) {
        notice fragment = new notice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_notice, container, false);

        Button onButtonClickedInputRecord = (Button) root.findViewById(R.id.InputRecord);
        Button onButtonClickedInputRecord2 = (Button) root.findViewById(R.id.InputRecord2);
        Button onButtonClickedInputRecord3 = (Button) root.findViewById(R.id.InputRecord3);
        onButtonClickedInputRecord.setOnClickListener(this);
        onButtonClickedInputRecord2.setOnClickListener(this);
        onButtonClickedInputRecord3.setOnClickListener(this);

        Button onButtonClickedInputRecord4 = (Button) root.findViewById(R.id.InputRecord4);
        onButtonClickedInputRecord4.setOnClickListener(this);
        Button onButtonClickedInputRecord5 = (Button) root.findViewById(R.id.InputRecord5);
        onButtonClickedInputRecord5.setOnClickListener(this);
        Button onButtonClickedInputRecord6 = (Button) root.findViewById(R.id.InputRecord6);
        onButtonClickedInputRecord6.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;

        switch(b.getId()) {
            //id에 따라서 다른 구현을 한다.
            case R.id.InputRecord:
                //버튼 클릭시 아래 구현이 실행된다.
                System.out.println("InputRecord clicked");
                getActivity().startActivity(new Intent(getActivity(), MemoActivity.class));
                break;

            case R.id.InputRecord2:
                //버튼 클릭시 아래 구현이 실행된다.
                System.out.println("InputRecord2 clicked");
                getActivity().startActivity(new Intent(getActivity(), parsingActivity.class));
                break;

            case R.id.InputRecord3:
                //버튼 클릭시 아래 구현이 실행된다.
                System.out.println("InputRecord3 clicked");
                getActivity().startActivity(new Intent(getActivity(), TimerActivity.class));
                break;

            case R.id.InputRecord4:
                //버튼 클릭시 아래 구현이 실행된다.
                System.out.println("InputRecord4 clicked");
                getActivity().startActivity(new Intent(getActivity(), TranslateActivity.class));
                break;

            case R.id.InputRecord5:
                //버튼 클릭시 아래 구현이 실행된다.
                System.out.println("InputRecord5 clicked");
                getActivity().startActivity(new Intent(getActivity(), EnTranslateActivity.class));
                break;

            case R.id.InputRecord6:
                //버튼 클릭시 아래 구현이 실행된다.
                System.out.println("InputRecord6 clicked");
                getActivity().startActivity(new Intent(getActivity(), UtubeActivity.class));
                break;

        }
    }


}