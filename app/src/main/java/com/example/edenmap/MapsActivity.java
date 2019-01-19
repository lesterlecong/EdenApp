package com.example.edenmap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Arrays;
import java.util.List;

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
    private static final LatLng EDENLOLASGARDEN = new LatLng(7.025649, 125.406002);
    private static final LatLng EDENVISTARESTAURANT = new LatLng(7.030130, 125.398333);

    private static final LatLng EDENILOVEEDEN = new LatLng(7.030200, 125.397560);

    private static final LatLng EDENFRONTOFFICE = new LatLng(7.029683, 125.400436);
    private static final LatLng EDENFRCLINIC = new LatLng(7.029688, 125.400521);
    private static final LatLng EDENCYPRESSHALL = new LatLng(7.029412, 125.400567);

    //Rooms
    private static final LatLng EDENBEGONIAROOMS = new LatLng(7.029612, 125.400667);
    private static final LatLng EDENVISTACOTTAGES = new LatLng(7.029086, 125.399105);
    private static final LatLng EDENCAMELLAROOMS = new LatLng(7.030013, 125.397181);

    private static final int PATTERNDASHLENGTHPIXEL = 20;
    private static final int PATTERNGAPLENGTHPIXEL = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem DASH = new Dash(PATTERNDASHLENGTHPIXEL);
    private static final PatternItem GAP = new Gap(PATTERNGAPLENGTHPIXEL);
    private static final List<PatternItem> PATTERNPOLYGONALPHA = Arrays.asList(GAP, DASH);

    private LatLngBounds edenNatureParkBound = new LatLngBounds(
            new LatLng(EDENNATUREPARKCENTER.latitude - 0.009, EDENNATUREPARKCENTER.longitude - 0.009),
            new LatLng(EDENNATUREPARKCENTER.latitude + 0.009, EDENNATUREPARKCENTER.longitude + 0.009));

    private GoogleMap mMap;

    //Markers
    private Marker mEdenEntranceMarker;
    private Marker mEdenFishingVillageMarker;
    private Marker mEdenChapelMarker;
    private Marker mEdenPoolMarker;
    private Marker mEdenButterflyMarker;
    private Marker mEdenHerbsGardenMarker;
    private Marker mEdenLolasGardenMarker;
    private Marker mEdenVistaRestaurantMarker;

    private Polyline line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        android.util.Log.i("INFO","onMapReady");

        mMap = googleMap;

        addMarkersToMap();
        drawMountainTrail();

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
                .snippet("Daily:\t8:00 AM - 5:00 PM")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.fish_icon_round)));

        mEdenChapelMarker = mMap.addMarker(new MarkerOptions()
                .position(EDENCHAPEL)
                .title("St. Michael the Archangel Chapel")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.chapel_icon_round)));

        mEdenPoolMarker = mMap.addMarker(new MarkerOptions()
                .position(EDENPOOL)
                .title("Swimming Pool")
                .snippet("Sun-Fri:\t8:00 AM - 4:00 PM\nSat:\t\t\t\t8:00 AM - 5:00 PM")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.pool_icon_round)));

        mEdenButterflyMarker = mMap.addMarker(new MarkerOptions()
                .position(EDENBUTTERFLY)
                .title("Butterfly Garden")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.butterfly_icon_round)));

        mEdenHerbsGardenMarker = mMap.addMarker(new MarkerOptions()
                .position(EDENHERBGARDEN)
                .title("Herb Garden")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.herbs_icon_round)));

        mEdenLolasGardenMarker = mMap.addMarker(new MarkerOptions()
                .position(EDENLOLASGARDEN)
                .title("Lolas Garden")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.garden_icon_round)));

        mEdenVistaRestaurantMarker = mMap.addMarker(new MarkerOptions()
                .position(EDENVISTARESTAURANT)
                .title("Vista Restaurant")
                .snippet("Breakfast:\t\t6:30 AM - 9:00 AM\nLunch:\t\t\t\t11:30 AM - 2:00 PM\nDinner:\t\t\t\t\t6:00 PM - 8:30 PM")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.food_icon_round)));

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public android.view.View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public android.view.View getInfoContents(Marker marker) {
                Context context = getApplicationContext();
                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(context);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(context);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
    }

    private void drawMountainTrail(){
        line = mMap.addPolyline(new PolylineOptions()
            .add(EDENCHAPEL, EDENBUTTERFLY, EDENENTRANCE)
            .width(5)
            .color(Color.BLUE)
            .pattern(PATTERNPOLYGONALPHA)
            );
        line.setVisible(true);
    }



}
