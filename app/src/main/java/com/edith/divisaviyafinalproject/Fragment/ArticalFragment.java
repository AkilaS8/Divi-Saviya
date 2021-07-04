package com.edith.divisaviyafinalproject.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edith.divisaviyafinalproject.Activities.ArticleViewer;
import com.edith.divisaviyafinalproject.R;

public class ArticalFragment extends Fragment {

    View view;
    CardView a1, a2, a3, a4, a5, a6, a7;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ArticalFragment() {
        // Required empty public constructor
    }

    public static ArticalFragment newInstance(String param1, String param2) {
        ArticalFragment fragment = new ArticalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        view = inflater.inflate(R.layout.fragment_artical, container, false);

        a1 = view.findViewById(R.id.a1);
        a2 = view.findViewById(R.id.a2);
        a3 = view.findViewById(R.id.a3);
        a4 = view.findViewById(R.id.a4);
        a5 = view.findViewById(R.id.a5);
        a6 = view.findViewById(R.id.a6);
        a7 = view.findViewById(R.id.a7);

        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ArticleViewer.class);
                intent.putExtra("aTopic", "එළවලු වගාව");
                intent.putExtra("aUrl","https://doa.gov.lk/images/gewathu/VegetableBook_2019.pdf");
                startActivity(intent);
            }
        });

        return view;
    }
}