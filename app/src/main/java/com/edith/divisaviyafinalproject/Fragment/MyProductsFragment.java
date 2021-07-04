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
import com.edith.divisaviyafinalproject.Activities.MyShop;
import com.edith.divisaviyafinalproject.Adapters.MyProductAdapter;
import com.edith.divisaviyafinalproject.Details.ProductInformation;
import com.edith.divisaviyafinalproject.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MyProductsFragment extends Fragment{

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<ProductInformation> arrayListMyProducts = new ArrayList<>();
    MyProductAdapter adapter;

    RecyclerView myProducts;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;
    LottieAnimationView productButton;

    public MyProductsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MyProductsFragment newInstance(String param1, String param2) {
        MyProductsFragment fragment = new MyProductsFragment();
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
        view = inflater.inflate(R.layout.fragment_my_products, container, false);
        productButton = view.findViewById(R.id.gotoProduct);
        myProducts = view.findViewById(R.id.myList);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        myProducts.setLayoutManager(linearLayoutManager);

        productButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AddProduct.class));
                getActivity().finish();
            }
        });

        arrayListMyProducts.clear();
        SharedPreferences userPreferences = getActivity().getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        String USER_ID = userPreferences.getString("USER_TELEPHONE", "");

        CollectionReference reference = db.collection("MyStore/" + USER_ID + "/MyProducts");

        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    return;
                }

                for (QueryDocumentSnapshot documentSnapshot: value){
                    ProductInformation information = documentSnapshot.toObject(ProductInformation.class);
                    arrayListMyProducts.add(information);
                }
                adapter = new MyProductAdapter(getContext(), arrayListMyProducts);
                myProducts.setAdapter(adapter);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }


}