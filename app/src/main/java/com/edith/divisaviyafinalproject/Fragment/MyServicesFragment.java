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
import com.edith.divisaviyafinalproject.Activities.AddService;
import com.edith.divisaviyafinalproject.Adapters.MyProductAdapter;
import com.edith.divisaviyafinalproject.Adapters.MyServiceAdapter;
import com.edith.divisaviyafinalproject.Details.ProductInformation;
import com.edith.divisaviyafinalproject.Details.ServiceInformation;
import com.edith.divisaviyafinalproject.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MyServicesFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<ServiceInformation> arrayListServices = new ArrayList<>();
    MyServiceAdapter adapter;

    RecyclerView myServices;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;
    LottieAnimationView serviceButton;

    public MyServicesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MyServicesFragment newInstance(String param1, String param2) {
        MyServicesFragment fragment = new MyServicesFragment();
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
        view = inflater.inflate(R.layout.fragment_my_services, container, false);
        serviceButton = view.findViewById(R.id.gotoService);
        myServices = view.findViewById(R.id.myList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        myServices.setLayoutManager(linearLayoutManager);

        serviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddService.class));
            }
        });

        arrayListServices.clear();

        SharedPreferences userPreferences = getActivity().getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        String USER_ID = userPreferences.getString("USER_TELEPHONE", "");

        CollectionReference reference = db.collection("MyStore/" + USER_ID + "/MyServices");

        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    return;
                }

                for (QueryDocumentSnapshot documentSnapshot: value){
                    ServiceInformation information = documentSnapshot.toObject(ServiceInformation.class);
                    arrayListServices.add(information);
                }
                adapter = new MyServiceAdapter(getContext(), arrayListServices);
                myServices.setAdapter(adapter);

            }
        });

        return view;
    }
}