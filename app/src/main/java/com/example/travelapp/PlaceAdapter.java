package com.example.travelapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private Context context;
    private List<Place> placeList;
    private List<Place> placeListFull;
    private List<Province> provinces;
    private List<Image> imageList;
    private OnPlaceActionListener listener;

    public PlaceAdapter(Context context, List<Place> places, List<Province> provinces, List<Image> images, OnPlaceActionListener listener) {
        this.context = context;
        this.placeList = places;
        this.placeListFull = new ArrayList<>(places);
        this.provinces = provinces;
        this.imageList = images;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = placeList.get(position);
        holder.tvName.setText(place.getName());

        // Tìm tên tỉnh theo idProvince
        String provinceName = "";
        for (Province p : provinces) {
            if (p.getId() == place.getIdProvince()) {
                provinceName = p.getName();
                break;
            }
        }

        // Lấy ảnh từ imageList dựa trên idPlace
        String imageUrl = getImageUrlForPlace(place.getIdPlace());
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .into(holder.imgPlace);
        }

        holder.tvProvince.setText(provinceName);
        holder.tvTicketPrice.setText("Giá vé: " + place.getTicketPrice() + " VND");
        holder.tvTime.setText("Thời gian: " + place.getTime());

        // Click item -> Mở chi tiết
        String finalProvinceName = provinceName;
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), PlaceDetailActivity.class);
            intent.putExtra("id_place", place.getIdPlace());
            intent.putExtra("name", place.getName());
            intent.putExtra("province", finalProvinceName);
            intent.putExtra("ticket", (int) place.getTicketPrice());
            intent.putExtra("time", place.getTime());
            intent.putExtra("image", imageUrl);
            intent.putExtra("rate", place.getRate());
            intent.putExtra("description", place.getDescription());
            intent.putExtra("phone", place.getPhone());
            intent.putExtra("type", place.getType());
            v.getContext().startActivity(intent);
        });

        // Hiển thị popup menu (Sửa/Xóa)
        holder.imgMore.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, holder.imgMore);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_place_options, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                if (listener == null) return false;

                Place selectedPlace = placeList.get(holder.getAdapterPosition());
                int id = item.getItemId();

                if (id == R.id.menu_edit) {
                    listener.onEditPlace(selectedPlace);
                    return true;
                } else if (id == R.id.menu_delete) {
                    listener.onDeletePlace(selectedPlace);
                    return true;
                }

                return false;
            });

            popup.show();
        });
    }

    // Lấy đường dẫn ảnh từ idPlace
    private String getImageUrlForPlace(int idPlace) {
        for (Image image : imageList) {
            if (image.getIdPlace() == idPlace) {
                return image.getUrl();
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    // Lọc theo tên địa điểm
    public void filter(String query) {
        if (query.isEmpty()) {
            placeList.clear();
            placeList.addAll(placeListFull);
        } else {
            List<Place> filteredList = new ArrayList<>();
            for (Place place : placeListFull) {
                if (place.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(place);
                }
            }
            placeList.clear();
            placeList.addAll(filteredList);
        }
        notifyDataSetChanged();
    }

    // Cập nhật danh sách mới
    public void updateList(List<Place> newList) {
        this.placeList = newList;
        this.placeListFull = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    public void updateData(List<Place> newPlaces, List<Province> newProvinces, List<Image> newImages) {
        this.placeList = newPlaces;
        this.placeListFull = new ArrayList<>(newPlaces);
        this.provinces = newProvinces;
        this.imageList = newImages;
        notifyDataSetChanged();
    }

    // ViewHolder chứa các thành phần hiển thị
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvProvince, tvTicketPrice, tvTime;
        ImageView imgPlace, imgMore;

        public ViewHolder(View itemView) {
            super(itemView);
            imgPlace = itemView.findViewById(R.id.imgPlace);
            tvName = itemView.findViewById(R.id.txtName);
            tvProvince = itemView.findViewById(R.id.txtProvince);
            tvTicketPrice = itemView.findViewById(R.id.txtTicketPrice);
            tvTime = itemView.findViewById(R.id.txtTime);
            imgMore = itemView.findViewById(R.id.imgMore); // Icon 3 chấm
        }
    }

    // Interface callback cho hành động Sửa/Xóa
    public interface OnPlaceActionListener {
        void onEditPlace(Place place);
        void onDeletePlace(Place place);
    }


}
