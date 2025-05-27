package com.example.travelapp;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private DBHelper dbHelper;
    private IMapController mapController;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private GpsTracker gpsTracker;
    private List<Marker> originalMarkers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().setUserAgentValue(getPackageName());
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapController = mapView.getController();
        mapController.setZoom(12.0);

        dbHelper = new DBHelper(this);
        List<Place> places = dbHelper.getAllPlaces();

        if (!places.isEmpty()) {
            Place firstPlace = places.get(0);
            GeoPoint centerPoint = new GeoPoint(firstPlace.getLatitude(), firstPlace.getLongitude());
            mapController.setCenter(centerPoint);

            for (Place place : places) {
                double lat = place.getLatitude();
                double lon = place.getLongitude();
                if (lat == 0 && lon == 0) continue;
                GeoPoint point = new GeoPoint(lat, lon);
                Marker marker = new Marker(mapView);
                marker.setPosition(point);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                marker.setIcon(ContextCompat.getDrawable(this, R.drawable.home_pin));
                marker.setTitle(place.getName());

                marker.setOnMarkerClickListener((m, v) -> {
                    showPlaceDialog(place);
                    return true;
                });

                mapView.getOverlays().add(marker);
                originalMarkers.add(marker);
            }
        }

        findViewById(R.id.btnFindNearby).setOnClickListener(v -> getNearestPlacesAndShowDialog());
    }

    private void getNearestPlacesAndShowDialog() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
            return;
        }

        gpsTracker = new GpsTracker(this);
        if (gpsTracker.canGetLocation()) {
            double userLat = gpsTracker.getLatitude();
            double userLon = gpsTracker.getLongitude();

            Log.d("USER_LOCATION", "Lat: " + userLat + ", Lon: " + userLon);
            GeoPoint userPoint = new GeoPoint(userLat, userLon);
            Marker userMarker = new Marker(mapView);
            userMarker.setPosition(userPoint);
            userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            userMarker.setIcon(ContextCompat.getDrawable(this, R.drawable.person_pin));
            userMarker.setTitle("Vị trí của bạn");
            mapView.getOverlays().add(userMarker);
            mapController.setCenter(userPoint);
            mapView.invalidate();

            // 🔍 Tìm địa điểm gần
            List<Place> allPlaces = dbHelper.getAllPlaces();
            List<Place> nearbyPlaces = new ArrayList<>();

            for (Place place : allPlaces) {
                double distance = getDistanceInKm(userLat, userLon, place.getLatitude(), place.getLongitude());
                Log.d("NEARBY", "Địa điểm: " + place.getName() +
                        ", cách bạn: " + distance + " km");
                if (distance < 15) {
                    nearbyPlaces.add(place);
                }
            }

            if (nearbyPlaces.isEmpty()) {
                Toast.makeText(this, "Không tìm thấy địa điểm nào trong bán kính 15km!", Toast.LENGTH_SHORT).show();
            } else {
                showNearbyPlacesDialog(nearbyPlaces);
            }
        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    private void showNearbyPlacesDialog(List<Place> places) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_nearby_places);

        LinearLayout layout = dialog.findViewById(R.id.layoutNearbyPlaces);
        layout.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(this);

        for (Place place : places) {
            LinearLayout itemView = (LinearLayout) inflater.inflate(R.layout.item_place, null);

            ImageView imgPlace = itemView.findViewById(R.id.imgPlace);
            TextView txtName = itemView.findViewById(R.id.txtName);
            TextView txtProvince = itemView.findViewById(R.id.txtProvince);
            TextView txtTicketPrice = itemView.findViewById(R.id.txtTicketPrice);
            TextView txtTime = itemView.findViewById(R.id.txtTime);

            txtName.setText(place.getName());
            txtTicketPrice.setText("Giá vé: " + place.getTicketPrice() + " VND");
            txtTime.setText("Mở cửa: " + place.getTime());

            Province province = dbHelper.getProvinceById(place.getIdProvince());
            if (province != null) txtProvince.setText(province.getName());

            List<String> images = dbHelper.getImagesForPlace(place.getIdPlace());
            if (!images.isEmpty()) {
                Glide.with(this).load(images.get(0)).into(imgPlace);
            }
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(MapActivity.this, PlaceDetailActivity.class);
                intent.putExtra("id_place", place.getIdPlace());
                intent.putExtra("name", place.getName());
                intent.putExtra("ticket", place.getTicketPrice());
                intent.putExtra("time", place.getTime());
                intent.putExtra("rate", place.getRate());
                intent.putExtra("description", place.getDescription());
                intent.putExtra("phone", place.getPhone());
                intent.putExtra("type", place.getType());

                Province placeProvince = dbHelper.getProvinceById(place.getIdProvince());
                String provinceName = (placeProvince != null) ? placeProvince.getName() : "Không rõ";
                intent.putExtra("province", provinceName);

                List<String> placeImages = dbHelper.getImagesForPlace(place.getIdPlace());
                String imageUrl = (!placeImages.isEmpty()) ? placeImages.get(0) : "";
                intent.putExtra("image", imageUrl);

                startActivity(intent);
                dialog.dismiss();
            });

            layout.addView(itemView);
        }

        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(layoutParams);
        }

        dialog.show();
    }

    private void showPlaceDialog(Place place) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.item_place);

        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(layoutParams);
        }

        ImageView imgPlace = dialog.findViewById(R.id.imgPlace);
        TextView txtName = dialog.findViewById(R.id.txtName);
        TextView txtProvince = dialog.findViewById(R.id.txtProvince);
        TextView txtTicketPrice = dialog.findViewById(R.id.txtTicketPrice);
        TextView txtTime = dialog.findViewById(R.id.txtTime);

        txtName.setText(place.getName());
        txtTicketPrice.setText("Giá vé: " + place.getTicketPrice() + " VND");
        txtTime.setText("Mở cửa: " + place.getTime());

        Province province = dbHelper.getProvinceById(place.getIdProvince());
        String provinceName =province.getName();
        txtProvince.setText(provinceName);

        List<String> imageLinks = dbHelper.getImagesForPlace(place.getIdPlace());
        String imageUrl = (!imageLinks.isEmpty()) ? imageLinks.get(0) : "";

        Glide.with(this).load(imageUrl).into(imgPlace);

        dialog.findViewById(R.id.rootItemPlace).setOnClickListener(v -> {
            Intent intent = new Intent(MapActivity.this, PlaceDetailActivity.class);
            intent.putExtra("id_place", place.getIdPlace());
            intent.putExtra("name", place.getName());
            intent.putExtra("province", provinceName);
            intent.putExtra("ticket", place.getTicketPrice());
            intent.putExtra("time", place.getTime());
            intent.putExtra("rate", place.getRate());
            intent.putExtra("description", place.getDescription());
            intent.putExtra("phone", place.getPhone());
            intent.putExtra("type", place.getType());
            intent.putExtra("image", imageUrl);
            startActivity(intent);
            dialog.dismiss();
        });

        dialog.show();
    }


    private double getDistanceInKm(double lat1, double lon1, double lat2, double lon2) {
        float[] results = new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        return results[0] / 1000.0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
}