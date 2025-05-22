package com.example.travelapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    RecyclerView recyclerView;
    private PlaceAdapter adapter;
    private List<Place> placeList;
    private List<Province> provinces;
    private List<Image> imageList;

    private String loggedInUsername;
    private String loggedInPassword;
    private boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
        // Ẩn luôn action bar nếu còn
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        loggedInUsername = preferences.getString("username", "");
        loggedInPassword = preferences.getString("password", "");
        this.isAdmin = preferences.getBoolean("isAdmin", false);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        placeList = dbHelper.getAllPlaces();
        provinces = dbHelper.getAllProvinces();
        imageList = dbHelper.getAllImages();

        adapter = new PlaceAdapter(this, placeList, provinces, imageList, isAdmin,
                new PlaceAdapter.OnPlaceActionListener() {
                    @Override
                    public void onEditPlace(Place place) {
                        Intent intent = new Intent(MainActivity.this, EditPlaceActivity.class);
                        intent.putExtra("placeId", place.getIdPlace());
                        startActivity(intent);
                    }

                    @Override
                    public void onDeletePlace(Place place) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Xác nhận xóa")
                                .setMessage("Bạn có chắc chắn muốn xóa địa điểm này?")
                                .setPositiveButton("Xóa", (dialog, which) -> {
                                    dbHelper.deletePlace(place.getIdPlace());
                                    reloadData();
                                })
                                .setNegativeButton("Hủy", null)
                                .show();
                    }

                });

        recyclerView.setAdapter(adapter);

        EditText searchEditText = findViewById(R.id.editSearch);
        Button searchButton = findViewById(R.id.btnSearch);
        searchButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString().trim();
            adapter.filter(query);
        });

        ImageView imgFilter = findViewById(R.id.iconFilter);
        imgFilter.setOnClickListener(v -> showSortFilterDialog());

        Button btnAdd = findViewById(R.id.btnAdd);
        if (isAdmin) {
            btnAdd.setVisibility(View.VISIBLE);
        } else {
            btnAdd.setVisibility(View.GONE);
        }

        btnAdd.setOnClickListener(v -> {
            Intent addIntent = new Intent(MainActivity.this, AddPlaceActivity.class);
            startActivity(addIntent);
        });

        Button btnMap = findViewById(R.id.btnMap);
        btnMap.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
        });


        ImageView iconLogout = findViewById(R.id.iconLogout);
        iconLogout.setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Đăng xuất")
                    .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                    .setPositiveButton("Đăng xuất", (dialog, which) -> {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();

                        Intent logout_intent = new Intent(MainActivity.this, LoginActivity.class);
                        logout_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logout_intent);
                        finish();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadData();
    }

    private void reloadData() {
        List<Place> updatedPlaces = dbHelper.getAllPlaces();
        List<Province> updatedProvinces = dbHelper.getAllProvinces();
        List<Image> updatedImages = dbHelper.getAllImages();
        adapter.updateData(updatedPlaces, updatedProvinces, updatedImages);
    }

    private String getProvinceNameById(int idProvince) {
        for (Province province : provinces) {
            if (province.getId() == idProvince) {
                return province.getName();
            }
        }
        return "";
    }

    private void showSortFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sắp xếp / Lọc");

        View view = getLayoutInflater().inflate(R.layout.dialog_sort_filter, null);
        RadioGroup radioGroup = view.findViewById(R.id.radioGroupSort);
        Spinner spinnerProvince = view.findViewById(R.id.spinnerProvince);

        List<String> provinceList = new ArrayList<>();
        for (Province province : provinces) {
            provinceList.add(province.getName());
        }

        Collections.sort(provinceList);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, provinceList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(spinnerAdapter);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            spinnerProvince.setVisibility(checkedId == R.id.radioSortProvince ? View.VISIBLE : View.GONE);
        });

        builder.setView(view);
        builder.setPositiveButton("Áp dụng", (dialog, which) -> {
            int checkedId = radioGroup.getCheckedRadioButtonId();
            List<Place> modifiedList = new ArrayList<>(placeList);

            if (checkedId == R.id.radioSortName) {
                modifiedList.sort(Comparator.comparing(Place::getName));
            } else if (checkedId == R.id.radioSortRate) {
                modifiedList.sort((p1, p2) -> Double.compare(p2.getRate(), p1.getRate()));
            } else if (checkedId == R.id.radioSortPrice) {
                modifiedList.sort((p1, p2) -> Integer.compare(p2.getTicketPrice(), p1.getTicketPrice()));
            } else if (checkedId == R.id.radioSortProvince) {
                String selectedProvince = spinnerProvince.getSelectedItem().toString();
                modifiedList.removeIf(place -> !getProvinceNameById(place.getIdProvince()).equalsIgnoreCase(selectedProvince));
            }

            adapter.updateList(modifiedList);
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }
}
