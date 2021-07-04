package com.edith.divisaviyafinalproject.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ServicesFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<ServiceInformation> arrayListService = new ArrayList<>();
    ServiceAdapter adapter;
    ArraySets arraySets = new ArraySets();

    RecyclerView serviceList;
    View view;
    ImageView searchQuery;
    AutoCompleteTextView searchText;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ServicesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ServicesFragment newInstance(String param1, String param2) {
        ServicesFragment fragment = new ServicesFragment();
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
        view = inflater.inflate(R.layout.fragment_services, container, false);

        serviceList = view.findViewById(R.id.sList);
        searchQuery = view.findViewById(R.id.service_search_btn);
        searchText = view.findViewById(R.id.auto_search_editText);

        //-----------Auto Complete text View -------------------------------------------------------
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, arraySets.search);
        searchText.setAdapter(adapter);
        //------------------------------------------------------------------------------------------

        //--------------Searching-------------------------------------------------------------------

        searchQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchingTAG = searchText.getText().toString();
                if (searchingTAG.isEmpty()){
                    Toast.makeText(getContext(), "Type what you want", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("Search------>",searchingTAG);
                    searchData(searchingTAG);
                }
            }
        });
        //------------------------------------------------------------------------------------------

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        serviceList.setLayoutManager(linearLayoutManager);

        return view;
    }

    private void searchData(String searchingTAG) {
        SharedPreferences userPreferences = getActivity().getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        String USER_STATE = userPreferences.getString("USER_STATE", "");


        arrayListService.clear();
        CollectionReference searching = db.collection("Services");

        //TODO: Searching part
        searching.whereEqualTo("userState",USER_STATE)
                .whereEqualTo("serviceTopic", searchingTAG)
                .orderBy("userRate", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            return;
                        }
                        arrayListService.clear();
                        for (QueryDocumentSnapshot documentSnapshot: value){
                            ServiceInformation information = documentSnapshot.toObject(ServiceInformation.class);
                            arrayListService.add(information);
                        }
                        adapter = new ServiceAdapter(getContext(), arrayListService);
                        serviceList.setAdapter(adapter);
                    }
                });

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
}