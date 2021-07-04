package com.edith.divisaviyafinalproject.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.edith.divisaviyafinalproject.Adapters.ProductAdapter;
import com.edith.divisaviyafinalproject.Details.ArraySets;
import com.edith.divisaviyafinalproject.Details.ProductInformation;
import com.edith.divisaviyafinalproject.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class CategoryProductFragment extends Fragment {

    View view;
    ViewFlipper viewFlipper;
    ImageView next, previous;
    ImageView vegetable, fruits, food, bakery, sweets, spices, meat, crafts;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<ProductInformation> arrayListProduct = new ArrayList<>();
    ProductAdapter adapter;

    ArraySets arraySets = new ArraySets();

    RecyclerView productList;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public CategoryProductFragment() {
        // Required empty public constructor
    }

    public static CategoryProductFragment newInstance(String param1, String param2) {
        CategoryProductFragment fragment = new CategoryProductFragment();
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
        view = inflater.inflate(R.layout.fragment_category_product, container, false);
        viewFlipper = view.findViewById(R.id.product_flipper);
        productList = view.findViewById(R.id.pList);
        next = view.findViewById(R.id.next);
        previous = view.findViewById(R.id.prev);

        vegetable = view.findViewById(R.id.vegetable);
        fruits = view.findViewById(R.id.fruit);
        food = view.findViewById(R.id.food);
        bakery = view.findViewById(R.id.bakery);
        sweets = view.findViewById(R.id.sweets);
        spices = view.findViewById(R.id.spices);
        meat = view.findViewById(R.id.meat);
        crafts = view.findViewById(R.id.crafts);


        vegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search("Vegetable");
            }
        });
        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search("Fruits");
            }
        });
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search("Food");
            }
        });
        bakery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search("Bakery");
            }
        });
        sweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search("Sweets");
            }
        });
        spices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search("Spices");
            }
        });
        meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search("Meat Diet");
            }
        });
        crafts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search("Crafts");
            }
        });

        //-------------flipper next-----------------------------------------------------------------
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setInAnimation(getContext(), R.anim.slide_in_right);
                viewFlipper.setOutAnimation(getContext(), R.anim.slide_out_left);
                viewFlipper.showPrevious();
            }
        });
        //-------------flipper previous-------------------------------------------------------------
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
                viewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
                viewFlipper.showNext();
            }
        });
        //------------------------------------------------------------------------------------------

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        productList.setLayoutManager(gridLayoutManager);

        return view;
    }

    public void search(String category){
        arrayListProduct.clear();

        SharedPreferences userPreferences = getActivity().getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        String USER_ID = userPreferences.getString("USER_TELEPHONE", "");
        String USER_STATE = userPreferences.getString("USER_STATE", "");
        Log.d("userState--->", USER_STATE);

        CollectionReference reference = db.collection("Products");

        reference
                .whereEqualTo("userState",USER_STATE)
                .whereEqualTo("productCategory",category)
                .orderBy("userRate", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            return;
                        }

                        arrayListProduct.clear();
                        for (QueryDocumentSnapshot documentSnapshot: value){
                            ProductInformation information = documentSnapshot.toObject(ProductInformation.class);
                            arrayListProduct.add(information);
                        }
                        adapter = new ProductAdapter(getContext(), arrayListProduct);
                        productList.setAdapter(adapter);
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();

        arrayListProduct.clear();

        SharedPreferences userPreferences = getActivity().getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        String USER_ID = userPreferences.getString("USER_TELEPHONE", "");
        String USER_STATE = userPreferences.getString("USER_STATE", "");
        Log.d("userState--->", USER_STATE);

        CollectionReference reference = db.collection("Products");

        reference
                .whereEqualTo("userState",USER_STATE)
                .orderBy("userRate", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            return;
                        }

                        arrayListProduct.clear();
                        for (QueryDocumentSnapshot documentSnapshot: value){
                            ProductInformation information = documentSnapshot.toObject(ProductInformation.class);
                            arrayListProduct.add(information);
                        }
                        adapter = new ProductAdapter(getContext(), arrayListProduct);
                        productList.setAdapter(adapter);
                    }
                });
    }
}