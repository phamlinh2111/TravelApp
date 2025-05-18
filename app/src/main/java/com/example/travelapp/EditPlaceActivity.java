package com.example.travelapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import org.json.JSONObject;
import java.io.*;
import java.util.*;
import okhttp3.*;

public class EditPlaceActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_IMAGES = 100;
    private static final String CLOUD_NAME = "dkfzkpsmn";
    private static final String UPLOAD_PRESET = "travel_upload_preset";

    EditText edtName, edtDescription, edtTicketPrice, edtRate, edtTime, edtPhone, edtType;
    Spinner spinnerProvince;
    Button btnSave, btnSelectImages;
    DBHelper dbHelper;
    List<Province> provinceList;
    List<Uri> selectedImageUris = new ArrayList<>();
    List<String> oldImageLinks = new ArrayList<>();
    List<String> oldImageLinksToKeep = new ArrayList<>();

    Place placeToEdit;
    LinearLayout layoutSelectedImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_place);

        edtName = findViewById(R.id.edtPlaceName);
        edtDescription = findViewById(R.id.edtDescription);
        edtTicketPrice = findViewById(R.id.edtTicketPrice);
        edtRate = findViewById(R.id.edtRate);
        edtTime = findViewById(R.id.edtTime);
        edtPhone = findViewById(R.id.edtPhone);
        edtType = findViewById(R.id.edtType);
        layoutSelectedImages = findViewById(R.id.layoutSelectedImages);
        spinnerProvince = findViewById(R.id.spinnerProvince);
        btnSave = findViewById(R.id.btnSave);
        btnSelectImages = findViewById(R.id.btnSelectImages);

        dbHelper = new DBHelper(this);
        provinceList = dbHelper.getAllProvinces();

        int placeId = getIntent().getIntExtra("placeId", -1);
        placeToEdit = dbHelper.getPlaceById(placeId);

        edtName.setText(placeToEdit.getName());
        edtDescription.setText(placeToEdit.getDescription());
        edtTicketPrice.setText(String.valueOf(placeToEdit.getTicketPrice()));
        edtRate.setText(String.valueOf(placeToEdit.getRate()));
        edtTime.setText(placeToEdit.getTime());
        edtPhone.setText(placeToEdit.getPhone());
        edtType.setText(placeToEdit.getType());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        for (Province p : provinceList) {
            adapter.add(p.getName());
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(adapter);

        for (int i = 0; i < provinceList.size(); i++) {
            if (provinceList.get(i).getId() == placeToEdit.getIdProvince()) {
                spinnerProvince.setSelection(i);
                break;
            }
        }

        oldImageLinks = dbHelper.getImagesForPlace(placeToEdit.getIdPlace());
        oldImageLinksToKeep.addAll(oldImageLinks);
        updateImageLayout();

        btnSelectImages.setOnClickListener(v -> {
            Intent intentPick = new Intent(Intent.ACTION_GET_CONTENT);
            intentPick.setType("image/*");
            intentPick.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intentPick, "Chọn ảnh"), REQUEST_CODE_PICK_IMAGES);
        });

        btnSave.setOnClickListener(view -> {
            String name = edtName.getText().toString().trim();
            String desc = edtDescription.getText().toString().trim();
            String ticketStr = edtTicketPrice.getText().toString().trim();
            String rateStr = edtRate.getText().toString().trim();
            String time = edtTime.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String type = edtType.getText().toString().trim();

            if (name.isEmpty() || desc.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên và mô tả!", Toast.LENGTH_SHORT).show();
                return;
            }

            int ticket = ticketStr.isEmpty() ? 0 : Integer.parseInt(ticketStr);
            double rate = rateStr.isEmpty() ? 0 : Double.parseDouble(rateStr);
            int provinceId = provinceList.get(spinnerProvince.getSelectedItemPosition()).getId();

            placeToEdit.setName(name);
            placeToEdit.setDescription(desc);
            placeToEdit.setTicketPrice(ticket);
            placeToEdit.setRate(rate);
            placeToEdit.setTime(time);
            placeToEdit.setPhone(phone);
            placeToEdit.setType(type);
            placeToEdit.setIdProvince(provinceId);

            int success = dbHelper.updatePlace(placeToEdit);

            if (success > 0) {
                // Xóa ảnh cũ đã bị gỡ bỏ
                for (String oldLink : oldImageLinks) {
                    if (!oldImageLinksToKeep.contains(oldLink)) {
                        dbHelper.deleteImageByLink(oldLink); // Bạn cần thêm hàm này trong DBHelper
                    }
                }

                if (selectedImageUris.isEmpty()) {
                    Toast.makeText(this, "Đã cập nhật địa điểm!", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                for (Uri uri : selectedImageUris) {
                    uploadImageToCloudinary(uri, (link) -> {
                        dbHelper.addImage(placeToEdit.getIdPlace(), link);
                        Toast.makeText(this, "Đã cập nhật địa điểm và ảnh!", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }
            } else {
                Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
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
            updateImageLayout();
        }
    }

    private void updateImageLayout() {
        layoutSelectedImages.removeAllViews();
        for (String link : oldImageLinksToKeep) {
            layoutSelectedImages.addView(createImageWithRemoveX(null, link));
        }
        for (Uri uri : selectedImageUris) {
            layoutSelectedImages.addView(createImageWithRemoveX(uri, null));
        }
    }

    private View createImageWithRemoveX(Uri uri, String link) {
        FrameLayout frameLayout = new FrameLayout(this);
        FrameLayout.LayoutParams imageParams = new FrameLayout.LayoutParams(300, 300);
        imageParams.setMargins(8, 8, 8, 8);

        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(imageParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        if (uri != null) {
            Glide.with(this).load(uri).into(imageView);
        } else if (link != null) {
            Glide.with(this).load(link).into(imageView);
        }

        ImageView btnRemove = new ImageView(this);
        FrameLayout.LayoutParams btnParams = new FrameLayout.LayoutParams(80, 90);
        btnParams.gravity = Gravity.END | Gravity.TOP;
        btnParams.setMargins(8, 8, 8, 8);
        btnRemove.setLayoutParams(btnParams);
        btnRemove.setImageResource(R.drawable.close);

        // Bo tròn nút X
        GradientDrawable circleBg = new GradientDrawable();
        circleBg.setColor(0xFFFFFFFF);
        circleBg.setShape(GradientDrawable.OVAL);
        circleBg.setStroke(2, 0xFFAAAAAA);

        btnRemove.setBackground(circleBg);
        btnRemove.setPadding(4, 4, 4, 4);
        btnRemove.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        btnRemove.setElevation(8f);

        btnRemove.setOnClickListener(v -> {
            if (uri != null) {
                selectedImageUris.remove(uri);
            } else if (link != null) {
                oldImageLinksToKeep.remove(link);
            }
            updateImageLayout();
        });

        frameLayout.addView(imageView);
        frameLayout.addView(btnRemove);
        return frameLayout;
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
