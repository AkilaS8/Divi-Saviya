package com.edith.divisaviyafinalproject.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;

import com.edith.divisaviyafinalproject.Adapters.ProductAdapter;
import com.edith.divisaviyafinalproject.Adapters.ServiceAdapter;
import com.edith.divisaviyafinalproject.Details.ArraySets;
import com.edith.divisaviyafinalproject.Details.ProductInformation;
import com.edith.divisaviyafinalproject.Details.ServiceInformation;
import com.google.android.gms.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.edith.divisaviyafinalproject.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentUserLocationMarker;
    private static final int requestUserLocationCode = 99;
    MarkerOptions MyCurrentMarker = new MarkerOptions();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<ProductInformation> arrayListProduct = new ArrayList<>();
    ArrayList<ServiceInformation> arrayListService = new ArrayList<>();
    ProductAdapter adapterProduct;
    ServiceAdapter adapterService;

    AutoCompleteTextView searchQuery;
    ImageView searchBtn;
    ViewFlipper viewFlipper;
    ImageView next, previous;

    ImageView pumpkin, mango, egg, tomato, hire, cocount, lemon, banana, tumeric;

    ArraySets arraySets = new ArraySets();

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);

            }
        }
    };

    private synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getActivity()).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        googleApiClient.connect();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        searchQuery = view.findViewById(R.id.auto_search_editText);
        searchBtn = view.findViewById(R.id.search_btn);
        viewFlipper = view.findViewById(R.id.map_flipper);
        previous = view.findViewById(R.id.map_prev);
        next = view.findViewById(R.id.map_next);

        pumpkin = view.findViewById(R.id.pumpkin);
        mango = view.findViewById(R.id.mango);
        egg = view.findViewById(R.id.egg);
        tomato = view.findViewById(R.id.tomato);
        hire = view.findViewById(R.id.hire);
        cocount = view.findViewById(R.id.coconut);
        lemon = view.findViewById(R.id.lemon);
        banana = view.findViewById(R.id.banana);
        tumeric = view.findViewById(R.id.tumeric);

        //------------------------------------------------------------------------------------------
        //------------------- flipper images -------------------------------------------------------
        pumpkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuery.setText("");
                searchQuery.setText("pumpkin");
            }
        });
        mango.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuery.setText("");
                searchQuery.setText("mango");
            }
        });
        egg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuery.setText("");
                searchQuery.setText("egg");
            }
        });
        tomato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuery.setText("");
                searchQuery.setText("tomato");
            }
        });
        hire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuery.setText("");
                searchQuery.setText("hire");
            }
        });
        cocount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuery.setText("");
                searchQuery.setText("coconut");
            }
        });
        lemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuery.setText("");
                searchQuery.setText("lemon");
            }
        });
        banana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuery.setText("");
                searchQuery.setText("banana");
            }
        });
        tumeric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuery.setText("");
                searchQuery.setText("turmeric");
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

        //-----------Auto Complete text View -------------------------------------------------------
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, arraySets.search);
        searchQuery.setAdapter(adapter);
        //------------------------------------------------------------------------------------------

        //--------------Searching-------------------------------------------------------------------

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchingTAG = searchQuery.getText().toString();
                if (searchingTAG.isEmpty()){
                    Toast.makeText(getContext(), "Type what you want", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("Search------>",searchingTAG);
                    searchData(searchingTAG);
                }
            }
        });
        //------------------------------------------------------------------------------------------

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkUserLocationPermission();
        }

        return view;
    }

    private void searchData(String searchingTAG) {
        mMap.clear();
        searchProduct(searchingTAG);
        searchService(searchingTAG);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        //-------Change Location button position ---------------------------------------------------
        View myLocationButton = mapFragment.getView().findViewWithTag("GoogleMapMyLocationButton");
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) myLocationButton.getLayoutParams();
        //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.setMargins(0, 300, 20, 0);
        myLocationButton.setLayoutParams(params);
        //------------------------------------------------------------------------------------------
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        lastLocation = location;

        if (currentUserLocationMarker != null) {
            currentUserLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        Log.d("Latitude", String.valueOf(location.getLatitude()));
        Log.d("Longitude", String.valueOf(location.getLongitude()));

        MyCurrentMarker.position(latLng);
        MyCurrentMarker.title("I am here");
        MyCurrentMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        currentUserLocationMarker = mMap.addMarker(MyCurrentMarker);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, (com.google.android.gms.location.LocationListener) this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        startProducts();
        startServices();

    }

    public void startProducts(){
        SharedPreferences userPreferences = getActivity().getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        String USER_STATE = userPreferences.getString("USER_STATE", "");

        arrayListProduct.clear();

        CollectionReference reference = db.collection("Products");
        reference.whereEqualTo("userState",USER_STATE)
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

                            Double latitude = information.getUserLat();
                            Double longitude = information.getUserLng();

                            LatLng location = new LatLng((Double)latitude, (Double)longitude);

                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(location);
                            markerOptions.title(information.getProductName());
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                            mMap.addMarker(markerOptions);
                        }
                    }
                });
    }

    public void startServices(){
        SharedPreferences userPreferences = getActivity().getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        String USER_STATE = userPreferences.getString("USER_STATE", "");

        arrayListService.clear();

        CollectionReference referenceS = db.collection("Services");
        referenceS.whereEqualTo("userState",USER_STATE)
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

                            Double latitude = information.getUserLat();
                            Double longitude = information.getUserLng();

                            LatLng location = new LatLng((Double)latitude, (Double)longitude);

                            MarkerOptions markerOptions1 = new MarkerOptions();
                            markerOptions1.position(location);
                            markerOptions1.title(information.getServiceTopic());
                            markerOptions1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                            mMap.addMarker(markerOptions1);
                        }
                    }
                });
    }

    public void searchProduct(String searchingTAG){
        SharedPreferences userPreferences = getActivity().getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        String USER_STATE = userPreferences.getString("USER_STATE", "");

        arrayListProduct.clear();

        CollectionReference reference = db.collection("Products");
        reference.whereEqualTo("userState",USER_STATE)
                .whereEqualTo("productName",searchingTAG)
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

                            Double latitude = information.getUserLat();
                            Double longitude = information.getUserLng();

                            LatLng location = new LatLng((Double)latitude, (Double)longitude);

                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(location);
                            markerOptions.title(information.getProductName());
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                            mMap.addMarker(markerOptions);
                        }
                    }
                });
    }

    public void searchService(String searchingTAG){
        SharedPreferences userPreferences = getActivity().getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        String USER_STATE = userPreferences.getString("USER_STATE", "");

        arrayListService.clear();

        CollectionReference reference = db.collection("Services");
        reference.whereEqualTo("userState",USER_STATE)
                .whereEqualTo("serviceTopic",searchingTAG)
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

                            Double latitude = information.getUserLat();
                            Double longitude = information.getUserLng();

                            LatLng location = new LatLng((Double)latitude, (Double)longitude);

                            MarkerOptions markerOptions1 = new MarkerOptions();
                            markerOptions1.position(location);
                            markerOptions1.title(information.getServiceTopic());
                            markerOptions1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                            mMap.addMarker(markerOptions1);
                        }
                    }
                });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, (com.google.android.gms.location.LocationListener) this);
        }
    }

    public boolean checkUserLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestUserLocationCode);

            } else {

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestUserLocationCode);
            }

            return false;

        } else {

            return true;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case requestUserLocationCode:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (googleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(getActivity(), "Permission Denied...", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}