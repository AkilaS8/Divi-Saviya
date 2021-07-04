package com.edith.divisaviyafinalproject.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.edith.divisaviyafinalproject.Adapters.ServiceAdapter;
import com.edith.divisaviyafinalproject.Details.ArraySets;
import com.edith.divisaviyafinalproject.Details.ServiceInformation;
import com.edith.divisaviyafinalproject.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class CategoryServiceFragment extends Fragment {
    View view;
    ViewFlipper viewFlipper;
    ImageView next, previous;
    ImageView delivery, carpentry, repair, hire, laundry, tailor, saloon, plumbing;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<ServiceInformation> arrayListService = new ArrayList<>();
    ServiceAdapter adapter;
    ArraySets arraySets = new ArraySets();

    RecyclerView serviceList;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public CategoryServiceFragment() {
        // Required empty public constructor
    }

    public static CategoryServiceFragment newInstance(String param1, String param2) {
        CategoryServiceFragment fragment = new CategoryServiceFragment();
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
        view = inflater.inflate(R.layout.fragment_category_service, container, false);
        viewFlipper = view.findViewById(R.id.product_flipper);
        serviceList = view.findViewById(R.id.sList);
        next = view.findViewById(R.id.next);
        previous = view.findViewById(R.id.prev);

        delivery = view.findViewById(R.id.delivery);
        carpentry = view.findViewById(R.id.carpentry);
        repair = view.findViewById(R.id.repair);
        hire = view.findViewById(R.id.hire_c);
        laundry = view.findViewById(R.id.laundry);
        tailor = view.findViewById(R.id.tailor);
        saloon = view.findViewById(R.id.saloon);
        plumbing = view.findViewById(R.id.plumbing);

        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search("Delivery");
            }
        });
        carpentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search("Carpentry");
            }
        });
        repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search("Repair");
            }
        });
        hire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search("Hire");
            }
        });
        laundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search("Laundry");
            }
        });
        tailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search("Tailor");
            }
        });
        saloon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search("Saloon");
            }
        });
        plumbing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search("Plumbing");
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        serviceList.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        arrayListService.clear();

        SharedPreferences userPreferences = getActivity().getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        String USER_ID = userPreferences.getString("USER_TELEPHONE", "");
        String USER_STATE = userPreferences.getString("USER_STATE", "");
        Log.d("userState--->", USER_STATE);

        CollectionReference reference = db.collection("Services");

        reference.whereEqualTo("userState", USER_STATE).orderBy("userRate", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                arrayListService.clear();
                for (QueryDocumentSnapshot documentSnapshot : value) {
                    ServiceInformation information = documentSnapshot.toObject(ServiceInformation.class);
                    arrayListService.add(information);
                }

                adapter = new ServiceAdapter(getContext(), arrayListService);
                serviceList.setAdapter(adapter);
            }
        });

    }

    public void search(String category){
        arrayListService.clear();

        SharedPreferences userPreferences = getActivity().getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        String USER_ID = userPreferences.getString("USER_TELEPHONE", "");
        String USER_STATE = userPreferences.getString("USER_STATE", "");
        Log.d("userState--->", USER_STATE);

        CollectionReference reference = db.collection("Services");

        reference
                .whereEqualTo("userState", USER_STATE)
                .whereEqualTo("serviceCategory",category)
                .orderBy("userRate", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                arrayListService.clear();
                for (QueryDocumentSnapshot documentSnapshot : value) {
                    ServiceInformation information = documentSnapshot.toObject(ServiceInformation.class);
                    arrayListService.add(information);
                }

                adapter = new ServiceAdapter(getContext(), arrayListService);
                serviceList.setAdapter(adapter);
            }
        });
    }
}