package com.example.travelapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.os.Bundle;
import android.provider.Settings;

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
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        try {
            if (isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (location == null && isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public boolean canGetLocation() {
        return isProviderEnabled(LocationManager.GPS_PROVIDER) || isProviderEnabled(LocationManager.NETWORK_PROVIDER);
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
        return location != null ? latitude : 0.0;
    }

    public double getLongitude() {
        return location != null ? longitude : 0.0;
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
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}
        public void onProviderEnabled(String provider) {}
        public void onProviderDisabled(String provider) {}
    };
}
