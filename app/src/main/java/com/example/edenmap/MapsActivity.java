package com.example.edenmap;

import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private static final float minZoom = (float)16.00;

    private static final LatLng EDENNATUREPARKCENTER = new LatLng(7.032594, 125.398411);
    private static final LatLng EDENENTRANCE = new LatLng(7.032614, 125.398446);
    private static final LatLng EDENFISHINGVILL = new LatLng(7.030994, 125.399074);
    private static final LatLng EDENCHAPEL = new LatLng(7.032927, 125.398365);
    private static final LatLng EDENPOOL = new LatLng(7.031371, 125.395713);
    private static final LatLng EDENBUTTERFLY = new LatLng(7.031474, 125.396214);
    private static final LatLng EDENHERBGARDEN = new LatLng(7.025600, 125.406347);

    private LatLngBounds edenNatureParkBound = new LatLngBounds(
            new LatLng(EDENNATUREPARKCENTER.latitude - 0.003, EDENNATUREPARKCENTER.longitude - 0.003),
            new LatLng(EDENNATUREPARKCENTER.latitude + 0.005, EDENNATUREPARKCENTER.longitude + 0.005));

    private GoogleMap mMap;

    //Markers
    private Marker mEdenEntranceMarker;
    private Marker mEdenFishingVillageMarker;
    private Marker mEdenChapelMarker;
    private Marker mEdenPoolMarker;
    private Marker mEdenButterflyMarker;
    private Marker mEdenHerbsGardenMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        addMarkersToMap();

        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));

            if(!success) {
                android.util.Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            android.util.Log.e(TAG, "Can't find style. Error: ", e);
        }

        googleMap.setBuildingsEnabled(true);
        googleMap.setMinZoomPreference(minZoom);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(edenNatureParkBound.getCenter(),10));
        googleMap.setLatLngBoundsForCameraTarget(edenNatureParkBound);

        /*
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
    }

    private void addMarkersToMap() {
        mEdenEntranceMarker = mMap.addMarker(new MarkerOptions()
                        .position(EDENENTRANCE)
                        .title("Main Entrance")
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.eden_logo_round)));

        mEdenFishingVillageMarker = mMap.addMarker(new MarkerOptions()
                .position(EDENFISHINGVILL)
                .title("Fishing Village")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.fish_icon_round)));

        mEdenChapelMarker = mMap.addMarker(new MarkerOptions()
                .position(EDENCHAPEL)
                .title("St. Michael the Archangel Chapel")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.chapel_icon_round)));

        mEdenPoolMarker = mMap.addMarker(new MarkerOptions()
                .position(EDENPOOL)
                .title("Swimming Pool")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.pool_icon_round)));

        mEdenButterflyMarker = mMap.addMarker(new MarkerOptions()
                .position(EDENBUTTERFLY)
                .title("Butterfly Garden")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.butterfly_icon_round)));

        mEdenHerbsGardenMarker = mMap.addMarker(new MarkerOptions()
                .position(EDENHERBGARDEN)
                .title("Herb Garden")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.herbs_icon_round)));
    }
}
