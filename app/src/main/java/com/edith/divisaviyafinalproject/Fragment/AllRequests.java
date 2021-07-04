package com.edith.divisaviyafinalproject.Fragment;

import android.content.Context;
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
import com.edith.divisaviyafinalproject.Adapters.MyRequestAdapter;
import com.edith.divisaviyafinalproject.Adapters.RequestAdapter;
import com.edith.divisaviyafinalproject.Details.RequestInformation;
import com.edith.divisaviyafinalproject.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class AllRequests extends Fragment {

    View view;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<RequestInformation> arrayListRequest = new ArrayList<>();
    RequestAdapter adapter;

    RecyclerView Requests;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AllRequests() {
        // Required empty public constructor
    }

    public static AllRequests newInstance(String param1, String param2) {
        AllRequests fragment = new AllRequests();
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
        view = inflater.inflate(R.layout.fragment_all_requests, container, false);
        Requests = view.findViewById(R.id.myList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        Requests.setLayoutManager(linearLayoutManager);

        arrayListRequest.clear();

        CollectionReference reference = db.collection("Requests");

        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    return;
                }

                for (QueryDocumentSnapshot documentSnapshot: value){
                    RequestInformation information = documentSnapshot.toObject(RequestInformation.class);
                    arrayListRequest.add(information);
                }
                adapter = new RequestAdapter(getContext(), arrayListRequest);
                Requests.setAdapter(adapter);
            }
        });
        return view;
    }
}