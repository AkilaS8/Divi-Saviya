package com.edith.divisaviyafinalproject.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.edith.divisaviyafinalproject.Activities.AddProduct;
import com.edith.divisaviyafinalproject.Activities.AddRequest;
import com.edith.divisaviyafinalproject.Adapters.MyProductAdapter;
import com.edith.divisaviyafinalproject.Adapters.MyRequestAdapter;
import com.edith.divisaviyafinalproject.Details.ProductInformation;
import com.edith.divisaviyafinalproject.Details.RequestInformation;
import com.edith.divisaviyafinalproject.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyRequests extends Fragment {

    View view;
    LottieAnimationView requestButton;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<RequestInformation> arrayListMyRequest = new ArrayList<>();
    MyRequestAdapter adapter;

    RecyclerView myRequests;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public MyRequests() {
        // Required empty public constructor
    }

    public static MyRequests newInstance(String param1, String param2) {
        MyRequests fragment = new MyRequests();
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
        view = inflater.inflate(R.layout.fragment_my_requests, container, false);
        requestButton = view.findViewById(R.id.gotoRequest);
        myRequests = view.findViewById(R.id.myList);

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddRequest.class));
                getActivity().finish();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        myRequests.setLayoutManager(linearLayoutManager);

        arrayListMyRequest.clear();

        SharedPreferences userPreferences = getActivity().getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        String USER_ID = userPreferences.getString("USER_TELEPHONE", "");

        CollectionReference reference = db.collection("MyStore/" + USER_ID + "/MyRequests");

        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    return;
                }

                for (QueryDocumentSnapshot documentSnapshot: value){
                    RequestInformation information = documentSnapshot.toObject(RequestInformation.class);
                    arrayListMyRequest.add(information);
                }
                adapter = new MyRequestAdapter(getContext(), arrayListMyRequest);
                myRequests.setAdapter(adapter);
            }
        });


        return view;
    }
}