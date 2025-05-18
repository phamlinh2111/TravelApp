package com.example.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class PlaceDetailActivity extends AppCompatActivity {
    TextView txtName, txtProvince, txtTicket, txtTime;
    TextView txtRate, txtDescription, txtPhone, txtType;
    ViewPager2 viewPagerImages;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable autoScrollRunnable;
    private ArrayList<String> imageList;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        txtName = findViewById(R.id.txtDetailName);
        txtProvince = findViewById(R.id.txtDetailProvince);
        txtTicket = findViewById(R.id.txtDetailTicket);
        txtTime = findViewById(R.id.txtDetailTime);
        txtRate = findViewById(R.id.txtDetailRate);
        txtDescription = findViewById(R.id.txtDetailDescription);
        txtPhone = findViewById(R.id.txtDetailPhone);
        txtType = findViewById(R.id.txtDetailType);
        viewPagerImages = findViewById(R.id.viewPagerImages);

        Intent intent = getIntent();
        int idPlace = intent.getIntExtra("id_place", -1);
        String name = intent.getStringExtra("name");
        String province = intent.getStringExtra("province");
        int ticket = intent.getIntExtra("ticket", 0);
        String time = intent.getStringExtra("time");
        double rate = intent.getDoubleExtra("rate", 0.0);
        String description = intent.getStringExtra("description");
        String phone = intent.getStringExtra("phone");
        String type = intent.getStringExtra("type");

        txtName.setText(name);
        txtProvince.setText("Tỉnh: " + province);
        txtTicket.setText("Giá vé: " + ticket + " VND");
        txtTime.setText("Thời gian: " + time);
        txtRate.setText("Đánh giá: " + rate);
        txtDescription.setText("Mô tả: " + description);
        txtPhone.setText("Liên hệ: " + phone);
        txtType.setText("Loại hình: " + type);


        DBHelper dbHelper = new DBHelper(this);
        imageList = new ArrayList<>(dbHelper.getImagesForPlace(idPlace));

        ImageAdapter adapter = new ImageAdapter(this, imageList);
        viewPagerImages.setAdapter(adapter);

        // Tự động chuyển ảnh mỗi 3s
        autoScrollRunnable = new Runnable() {
            @Override
            public void run() {
                if (imageList.size() > 0) {
                    currentPage = (currentPage + 1) % imageList.size();
                    viewPagerImages.setCurrentItem(currentPage, true);
                    handler.postDelayed(this, 3000);
                }
            }
        };
        handler.postDelayed(autoScrollRunnable, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(autoScrollRunnable);
    }
}
