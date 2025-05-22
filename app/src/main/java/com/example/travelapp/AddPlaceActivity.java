package com.example.travelapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.*;
import android.view.ViewGroup;
import android.view.View;

import com.bumptech.glide.Glide;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.util.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.*;

import org.json.JSONObject;

public class AddPlaceActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_IMAGES = 100;
    private static final String CLOUD_NAME = "dkfzkpsmn";
    private static final String UPLOAD_PRESET = "travel_upload_preset";

    EditText edtName, edtDescription, edtTicketPrice, edtRate, edtTime, edtPhone, edtType, edtLatitude, edtLongitude;
    Spinner spinnerProvince;
    Button btnSave, btnSelectImages;
    DBHelper dbHelper;
    List<Province> provinceList;
    Set<Uri> selectedImageUris = new LinkedHashSet<>();
    LinearLayout layoutSelectedImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        edtName = findViewById(R.id.edtPlaceName);
        edtDescription = findViewById(R.id.edtDescription);
        edtTicketPrice = findViewById(R.id.edtTicketPrice);
        edtRate = findViewById(R.id.edtRate);
        edtTime = findViewById(R.id.edtTime);
        edtPhone = findViewById(R.id.edtPhone);
        edtType = findViewById(R.id.edtType);
        edtLatitude = findViewById(R.id.edtLatitude);
        edtLongitude = findViewById(R.id.edtLongitude);
        spinnerProvince = findViewById(R.id.spinnerProvince);
        btnSave = findViewById(R.id.btnSave);
        btnSelectImages = findViewById(R.id.btnSelectImages);
        layoutSelectedImages = findViewById(R.id.layoutSelectedImages);

        dbHelper = new DBHelper(this);
        provinceList = dbHelper.getAllProvinces();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        for (Province p : provinceList) {
            adapter.add(p.getName());
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(adapter);

        btnSelectImages.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), REQUEST_CODE_PICK_IMAGES);
        });

        btnSave.setOnClickListener(view -> {
            String name = edtName.getText().toString().trim();
            String desc = edtDescription.getText().toString().trim();
            String ticketStr = edtTicketPrice.getText().toString().trim();
            String rateStr = edtRate.getText().toString().trim();
            String time = edtTime.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String type = edtType.getText().toString().trim();
            String latStr = edtLatitude.getText().toString().trim();
            String lngStr = edtLongitude.getText().toString().trim();

            double latitude = latStr.isEmpty() ? 0 : Double.parseDouble(latStr);
            double longitude = lngStr.isEmpty() ? 0 : Double.parseDouble(lngStr);


            if (name.isEmpty() || desc.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên và mô tả!", Toast.LENGTH_SHORT).show();
                return;
            }

            int ticket = ticketStr.isEmpty() ? 0 : Integer.parseInt(ticketStr);
            double rate = rateStr.isEmpty() ? 0 : Double.parseDouble(rateStr);
            int provinceId = provinceList.get(spinnerProvince.getSelectedItemPosition()).getId();

            Place place = new Place(0, provinceId, name, rate, desc, ticket, time, phone, type, latitude, longitude);
            long placeId = dbHelper.addPlace(place);

            if (placeId != -1) {
                if (selectedImageUris.isEmpty()) {
                    Toast.makeText(this, "Đã thêm địa điểm!", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                for (Uri uri : selectedImageUris) {
                    uploadImageToCloudinary(uri, (link) -> {
                        dbHelper.addImage((int) placeId, link);
                        Toast.makeText(this, "Đã thêm địa điểm và ảnh!", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }

            } else {
                Toast.makeText(this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_IMAGES && resultCode == Activity.RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    selectedImageUris.add(data.getClipData().getItemAt(i).getUri());
                }
            } else if (data.getData() != null) {
                selectedImageUris.add(data.getData());
            }

            Toast.makeText(this, selectedImageUris.size() + " ảnh được chọn", Toast.LENGTH_SHORT).show();
            displaySelectedImages();
        }
    }

    private void displaySelectedImages() {
        layoutSelectedImages.removeAllViews();

        for (Uri uri : selectedImageUris) {
            FrameLayout frameLayout = new FrameLayout(this);
            LinearLayout.LayoutParams frameParams = new LinearLayout.LayoutParams(300, 300);
            frameParams.setMargins(16, 8, 16, 8);
            frameLayout.setLayoutParams(frameParams);

            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
            ));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(this).load(uri).into(imageView);

            ImageButton btnRemove = new ImageButton(this);
            FrameLayout.LayoutParams btnParams = new FrameLayout.LayoutParams(80, 80);
            btnParams.gravity = Gravity.END | Gravity.TOP;
            btnParams.setMargins(8, 8, 8, 8);
            btnRemove.setLayoutParams(btnParams);

            // Nút bo tròn
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(0xFFFFFFFF); // màu nền trắng
            bg.setShape(GradientDrawable.OVAL);
            bg.setStroke(2, 0xFFAAAAAA); // viền xám

            btnRemove.setBackground(bg);
            btnRemove.setPadding(10, 10, 10, 10);
            btnRemove.setImageResource(R.drawable.close);
            btnRemove.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            btnRemove.setElevation(8f);

            btnRemove.setOnClickListener(v -> {
                selectedImageUris.remove(uri);
                displaySelectedImages();
            });

            frameLayout.addView(imageView);
            frameLayout.addView(btnRemove);

            layoutSelectedImages.addView(frameLayout);
        }
    }


    private void uploadImageToCloudinary(Uri uri, OnImageUploadedListener listener) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            byte[] imageBytes = getBytes(inputStream);

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", "image.jpg",
                            RequestBody.create(imageBytes, MediaType.parse("image/*")))
                    .addFormDataPart("upload_preset", UPLOAD_PRESET)
                    .build();

            Request request = new Request.Builder()
                    .url("https://api.cloudinary.com/v1_1/" + CLOUD_NAME + "/image/upload")
                    .post(requestBody)
                    .build();

            OkHttpClient client = new OkHttpClient();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseStr = response.body().string();
                    try {
                        JSONObject json = new JSONObject(responseStr);
                        String imageUrl = json.getString("secure_url");
                        runOnUiThread(() -> listener.onUploaded(imageUrl));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[4096];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }

    interface OnImageUploadedListener {
        void onUploaded(String link);
    }
}
