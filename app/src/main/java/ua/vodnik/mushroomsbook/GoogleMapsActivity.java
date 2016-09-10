package ua.vodnik.mushroomsbook;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback , GoogleMap.OnMarkerDragListener ,
        GoogleMap.OnMapClickListener , GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    SharedPreferences prefs = null;
    int locationCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);







        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMyLocationEnabled(true);


        final LatLng forest = new LatLng(50.847405, 26.024078);

        mMap.addMarker(new MarkerOptions()
                .position(forest)
                .draggable(true));

        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapLongClickListener(this);


        final LatLng Kyiv = new LatLng(50.441325, 30.538864);


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Kyiv, 15));

        mMap.animateCamera(CameraUpdateFactory.zoomIn());

        mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(forest)
                .zoom(14)
                .bearing(90)
                .tilt(40)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        prefs = getSharedPreferences("location", 0);
        locationCount = prefs.getInt("locationCount", 0);

        if (locationCount != 0) {

            String lat = "";
            String lng = "";
            for (int i = 0; i < locationCount; i++) {
                lat = prefs.getString("lat" + i, "0");
                lng = prefs.getString("lng" + i, "0");

                Toast.makeText(this, lat + "," + lng, Toast.LENGTH_LONG).show();
                double lat3 = Double.valueOf(lat).doubleValue();
                double lng3 = Double.valueOf(lng).doubleValue();
                LatLng pos = new LatLng(lat3, lng3);
                mMap.addMarker(new MarkerOptions().position(pos).draggable(true).title("Тут багато грибів :)"));
            }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.addMarker(new MarkerOptions()
                .position(latLng).draggable(true));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        locationCount++;

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lat" + Integer.toString((locationCount - 1)), Double.toString(latLng.latitude));
        editor.putString("lng" + Integer.toString((locationCount - 1)), Double.toString(latLng.longitude));
        editor.putInt("locationCount", locationCount);

        editor.commit();


    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng dragPosition = marker.getPosition();
        double dragLat = dragPosition.latitude;
        double dragLong = dragPosition.longitude;
        Log.i("info", "on drag end :" + dragLat + " dragLong :" + dragLong);
        Toast.makeText(getApplicationContext(), "Маркер позиції переміщено..!", Toast.LENGTH_LONG).show();
    }
}
