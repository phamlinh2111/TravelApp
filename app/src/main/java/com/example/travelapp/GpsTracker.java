package com.example.travelapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;

public class GpsTracker {

    private final Context context;
    private final LocationManager locationManager;
    private Location location;
    private double latitude;
    private double longitude;

    public GpsTracker(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        getLocation();
    }

    public Location getLocation() {
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }

            if (isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1, locationListener);
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Log.d("GPS", "Lat: " + latitude + " - Lon: " + longitude);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public boolean canGetLocation() {
        return isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void showSettingsAlert() {
        new AlertDialog.Builder(context)
                .setTitle("GPS không bật")
                .setMessage("Bạn có muốn vào cài đặt để bật GPS?")
                .setPositiveButton("Cài đặt", (dialog, which) ->
                        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton("Hủy", (dialog, which) -> dialog.cancel())
                .show();
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    private boolean isProviderEnabled(String provider) {
        return locationManager.isProviderEnabled(provider);
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location loc) {
            location = loc;
            latitude = loc.getLatitude();
            longitude = loc.getLongitude();
            Log.d("GPS", "onLocationChanged - Lat: " + latitude + ", Lon: " + longitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
        @Override
        public void onProviderEnabled(String provider) {}
        @Override
        public void onProviderDisabled(String provider) {}
    };
}
