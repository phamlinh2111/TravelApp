package com.example.travelapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "TravelData.db", null, 19);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Province
        db.execSQL("CREATE TABLE Province (" +
                "id_province INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL)");

        // Tạo bảng Places
        db.execSQL("CREATE TABLE Places (" +
                "id_place INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_province INTEGER, " +
                "Name TEXT, " +
                "Rate REAL, " +
                "Description TEXT, " +
                "Ticket_price INTEGER, " +
                "Time TEXT, " +
                "Phone TEXT, " +
                "Type TEXT, " +
                "FOREIGN KEY (id_province) REFERENCES Province(id_province))");

        // Tạo bảng Image
        db.execSQL("CREATE TABLE Image (" +
                "id_image INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_place INTEGER, " +
                "url TEXT, " +
                "caption TEXT, " +
                "FOREIGN KEY (id_place) REFERENCES Places(id_place))");

        // Tạo bảng Notice
        db.execSQL("CREATE TABLE Notice (" +
                "id_notice INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_place INTEGER, " +
                "notice_content TEXT, " +
                "send_time TEXT, " +
                "title TEXT, " +
                "FOREIGN KEY (id_place) REFERENCES Places(id_place))");

        // Tạo bảng User
        db.execSQL("CREATE TABLE User (" +
                "id_user INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "email TEXT)");

        // Tạo bảng Admin
        db.execSQL("CREATE TABLE Admin (" +
                "id_admin INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT UNIQUE, " +
                "sdt TEXT, " +
                "password TEXT NOT NULL)");

        // Tạo bảng QuanLy
        db.execSQL("CREATE TABLE QuanLy (" +
                "id_admin INTEGER, " +
                "id_user INTEGER, " +
                "PRIMARY KEY (id_admin, id_user), " +
                "FOREIGN KEY (id_admin) REFERENCES Admin(id_admin), " +
                "FOREIGN KEY (id_user) REFERENCES User(id_user))");

        // Tạo bảng Comment
        db.execSQL("CREATE TABLE Comment (" +
                "id_comment INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_user INTEGER, " +
                "id_place INTEGER, " +
                "Rate REAL, " +
                "Content TEXT, " +
                "FOREIGN KEY (id_user) REFERENCES User(id_user), " +
                "FOREIGN KEY (id_place) REFERENCES Places(id_place))");

        // Tạo bảng Visited
        db.execSQL("CREATE TABLE Visited (" +
                "id_visited INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_user INTEGER, " +
                "id_place INTEGER, " +
                "date TEXT, " +
                "FOREIGN KEY (id_user) REFERENCES User(id_user), " +
                "FOREIGN KEY (id_place) REFERENCES Places(id_place))");

        // Tạo bảng MuaVe
        db.execSQL("CREATE TABLE MuaVe (" +
                "id_mua_ve INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_user INTEGER, " +
                "id_place INTEGER, " +
                "ngay_mua TEXT, " +
                "FOREIGN KEY (id_user) REFERENCES User(id_user), " +
                "FOREIGN KEY (id_place) REFERENCES Places(id_place))");

        // Tạo bảng Favorite
        db.execSQL("CREATE TABLE Favorite (" +
                "id_favorite INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_user INTEGER, " +
                "id_place INTEGER, " +
                "ngay_du_dinh_di TEXT, " +
                "FOREIGN KEY (id_user) REFERENCES User(id_user), " +
                "FOREIGN KEY (id_place) REFERENCES Places(id_place))");


        db.execSQL("INSERT INTO Province (id_province, name) VALUES (1, 'Hà Nội')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (2, 'Hồ Chí Minh')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (3, 'Đà Nẵng')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (4, 'Hải Phòng')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (5, 'Cần Thơ')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (6, 'An Giang')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (7, 'Bà Rịa - Vũng Tàu')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (8, 'Bắc Giang')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (9, 'Bắc Kạn')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (10, 'Bạc Liêu')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (11, 'Bắc Ninh')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (12, 'Bến Tre')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (13, 'Bình Dương')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (14, 'Bình Định')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (15, 'Bình Phước')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (16, 'Bình Thuận')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (17, 'Cao Bằng')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (18, 'Cà Mau')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (19, 'Đắk Lắk')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (20, 'Đắk Nông')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (21, 'Điện Biên')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (22, 'Đồng Nai')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (23, 'Đồng Tháp')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (24, 'Gia Lai')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (25, 'Hà Giang')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (26, 'Hà Nam')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (27, 'Hà Tĩnh')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (28, 'Hòa Bình')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (29, 'Hưng Yên')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (30, 'Khánh Hòa')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (31, 'Kiên Giang')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (32, 'Kon Tum')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (33, 'Lai Châu')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (34, 'Lâm Đồng')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (35, 'Lạng Sơn')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (36, 'Lào Cai')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (37, 'Long An')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (38, 'Nam Định')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (39, 'Nghệ An')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (40, 'Ninh Bình')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (41, 'Ninh Thuận')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (42, 'Phú Thọ')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (43, 'Phú Yên')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (44, 'Quảng Bình')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (45, 'Quảng Nam')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (46, 'Quảng Ngãi')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (47, 'Quảng Ninh')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (48, 'Quảng Trị')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (49, 'Sóc Trăng')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (50, 'Sơn La')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (51, 'Tây Ninh')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (52, 'Thái Bình')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (53, 'Thái Nguyên')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (54, 'Thanh Hóa')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (55, 'Thừa Thiên Huế')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (56, 'Tiền Giang')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (57, 'Trà Vinh')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (58, 'Tuyên Quang')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (59, 'Vĩnh Long')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (60, 'Vĩnh Phúc')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (61, 'Yên Bái')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (62, 'Hậu Giang')");
        db.execSQL("INSERT INTO Province (id_province, name) VALUES (63, 'Điện Biên')");

        // Câu lệnh INSERT có giá trị cho id_place
        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type) VALUES " +
                "(2, 'Chợ Bến Thành', 4.5, 'Chợ Bến Thành là biểu tượng văn hóa, thương mại lâu đời của TP. Hồ Chí Minh, nổi tiếng với các gian hàng đặc sản, quà lưu niệm và ẩm thực phong phú.', 50000, '08:00 - 18:00', '0901234567', 'Shopping')");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type) VALUES " +
                "(1, 'Hồ Hoàn Kiếm', 4.7, 'Hồ Hoàn Kiếm là trái tim của Hà Nội, gắn liền với truyền thuyết vua Lê Lợi trả gươm, bao quanh bởi các công trình cổ kính và không gian tản bộ lý tưởng.', 0, '24/7', '0902345678', 'Sightseeing')");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type) VALUES " +
                "(3, 'Bà Nà Hills', 4.8, 'Bà Nà Hills là khu du lịch nổi tiếng với khí hậu mát mẻ quanh năm, sở hữu Cầu Vàng độc đáo, làng Pháp cổ kính, và hệ thống cáp treo đạt kỷ lục thế giới.', 700000, '08:00 - 20:00', '0903456789', 'Amusement')");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type) VALUES " +
                "(1, 'Văn Miếu Quốc Tử Giám', 4.6, 'Văn Miếu là trường đại học đầu tiên của Việt Nam, nơi lưu giữ giá trị lịch sử giáo dục và là địa điểm lý tưởng để tìm hiểu về Nho học và kiến trúc cổ.', 30000, '07:30 - 17:30', '0904567890', 'Cultural')");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type) VALUES " +
                "(2, 'Nhà thờ Đức Bà', 4.4, 'Nhà thờ Đức Bà là công trình kiến trúc cổ điển mang phong cách Pháp, tọa lạc giữa trung tâm Quận 1, là điểm đến thu hút du khách và tín đồ Công giáo.', 0, '06:00 - 19:00', '0905678901', 'Sightseeing')");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type) VALUES " +
                "(3, 'Ngũ Hành Sơn', 4.3, 'Ngũ Hành Sơn gồm 5 ngọn núi đá vôi đại diện cho Ngũ Hành, nổi bật với các hang động, chùa chiền và cảnh quan thiên nhiên tuyệt đẹp tại Đà Nẵng.', 40000, '07:00 - 17:00', '0906789012', 'Nature')");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type) VALUES " +
                "(2, 'Suối Tiên', 4.0, 'Suối Tiên là công viên văn hóa nổi tiếng với mô hình kết hợp giữa vui chơi giải trí và truyền thuyết dân gian, phù hợp cho cả gia đình và trẻ em.', 60000, '08:00 - 17:00', '0907890123', 'Amusement')");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type) VALUES " +
                "(47, 'Vịnh Hạ Long', 4.9, 'Vịnh Hạ Long là di sản thiên nhiên thế giới được UNESCO công nhận, nổi bật với hàng nghìn đảo đá vôi kỳ vĩ và hệ sinh thái biển phong phú.', 250000, '07:00 - 18:00', '0908901234', 'Nature')");

        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES " +
                "(1, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747449315/cho-ben-thanh-2_iskx3p.jpg', 'Chợ Bến Thành tại TP Hồ Chí Minh')");

        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES " +
                "(1, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747463591/ben-thanh_xfehfb.jpg', 'Chợ Bến Thành tại TP Hồ Chí Minh')");

        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES " +
                "(1, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747463312/hinh-anh-cho-ben-thanh_mjjpz8.jpg', 'Chợ Bến Thành tại TP Hồ Chí Minh')");

        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES " +
                "(2, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747463242/anh-Ho-Hoan-Kiem-ben-nhanh-phuong-do-1_hjpkgv.jpg', 'Hồ Hoàn Kiếm tại Hà Nội')");

        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES " +
                "(2, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747463246/ho-hoan-kiem_xj3545.jpg', 'Hồ Hoàn Kiếm tại Hà Nội')");

        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES " +
                "(2, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747463237/ho-hoan-kiem-ve-dem-tuyet-dep1_ivciej.jpg', 'Hồ Hoàn Kiếm tại Hà Nội')");

        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES " +
                "(3, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747463514/sun-world-ba-na-hills-ivivu37-1_si6xoo.jpg', 'Bà Nà Hills tại Đà Nẵng')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES " +
                "(3, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747463297/ba-na-hills-longphutravel-1_a656y2.jpg', 'Bà Nà Hills tại Đà Nẵng')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES " +
                "(3, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747463301/11-toan-canh-ba-na_jhlw9q.jpg', 'Bà Nà Hills tại Đà Nẵng')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES " +
                "(4, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747462621/bai-duong-van-mieu-quoc-tu-giam_cjbokq.jpg', 'Văn miếu quốc tử giám')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES " +
                "(5, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747463470/nha_tho_duc_ba_05_vlnl78.jpg', 'Nhà thờ Đức Bà')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES " +
                "(6, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747463211/Marble-Mountains_copertina_sxcmki.jpg', 'Ngũ Hành Sơn')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES " +
                "(7, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747463391/khu_du_lich_suoi_tien_qwevwo.jpg', 'Suối Tiên')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES " +
                "(8, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747463441/ha-long_hhj2si.jpg', 'Vịnh Hạ Long')");

        db.execSQL("INSERT INTO User (username, password, email) VALUES " +
                "('user1', '123456', 'user1@example.com')," +
                "('user2', 'abcdef', 'user2@example.com')," +
                "('user3', 'pass789', 'user3@example.com')");

        // Thêm dữ liệu mẫu cho Admin
        db.execSQL("INSERT INTO Admin (name, sdt, password) VALUES " +
                "('admin1', '0909123456', 'admin123')," +
                "('admin2', '0911222333', 'secure456')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DBHelper", "onUpgrade called from " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS Favorite");
        db.execSQL("DROP TABLE IF EXISTS MuaVe");
        db.execSQL("DROP TABLE IF EXISTS Visited");
        db.execSQL("DROP TABLE IF EXISTS Comment");
        db.execSQL("DROP TABLE IF EXISTS QuanLy");
        db.execSQL("DROP TABLE IF EXISTS Admin");
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Notice");
        db.execSQL("DROP TABLE IF EXISTS Image");
        db.execSQL("DROP TABLE IF EXISTS Places");
        db.execSQL("DROP TABLE IF EXISTS Province");
        onCreate(db); // gọi lại để tạo mới toàn bộ
    }


    public long addPlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_province", place.getIdProvince());
        values.put("Name", place.getName());
        values.put("Rate", place.getRate());
        values.put("Description", place.getDescription());
        values.put("Ticket_price", place.getTicketPrice());
        values.put("Time", place.getTime());
        values.put("Phone", place.getPhone());
        values.put("Type", place.getType());

        long result = db.insert("Places", null, values);
        return result;
    }


    public List<Place> getAllPlaces() {
        List<Place> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Places", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Place(
                        cursor.getInt(0), // id_place
                        cursor.getInt(1), // id_province
                        cursor.getString(2), // Name
                        cursor.getDouble(3), // Rate
                        cursor.getString(4), // Description
                        cursor.getInt(5), // Ticket_price
                        cursor.getString(6), // Time
                        cursor.getString(7), // Phone
                        cursor.getString(8)  // Type
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public Place getPlaceById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Places WHERE id_place = ?", new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            Place place = new Place(
                    cursor.getInt(0), // id_place
                    cursor.getInt(1), // id_province
                    cursor.getString(2), // Name
                    cursor.getDouble(3), // Rate
                    cursor.getString(4), // Description
                    cursor.getInt(5), // Ticket_price
                    cursor.getString(6), // Time
                    cursor.getString(7), // Phone
                    cursor.getString(8)  // Type
            );
            cursor.close();
            return place;
        }

        cursor.close();
        return null;
    }



    public List<Province> getAllProvinces() {
        List<Province> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Province", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Province(
                        cursor.getInt(0), // id_province
                        cursor.getString(1) // name
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<Image> getAllImages() {
        List<Image> imageList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Image", null);
        if (cursor.moveToFirst()) {
            do {
                imageList.add(new Image(
                        cursor.getInt(0), // id
                        cursor.getInt(1),
                        cursor.getString(2), // URL
                        cursor.getString(3) // Description
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return imageList;
    }


    // Kiểm tra người dùng có phải admin không từ bảng Admin
    public boolean isAdmin(String name, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Kiểm tra người dùng có tồn tại trong bảng Admin
        Cursor cursor = db.rawQuery("SELECT * FROM Admin WHERE name = ? AND password = ?", new String[]{name, password});
        boolean isAdmin = cursor.getCount() > 0;
        cursor.close();
        return isAdmin;
    }

    public boolean addImage(int placeId, String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_place", placeId);
        values.put("url", url);

        long result = db.insert("Image", null, values);
        return result != -1;
    }

    public void deletePlace(int idPlace) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // xóa ảnh liên quan
            db.delete("Image", "id_place=?", new String[]{String.valueOf(idPlace)});
            // xóa place
            db.delete("Places", "id_place=?", new String[]{String.valueOf(idPlace)});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }


    public int updatePlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", place.getName());
        values.put("id_province", place.getIdProvince());
        values.put("Ticket_price", place.getTicketPrice());
        values.put("Time", place.getTime());
        values.put("Description", place.getDescription());
        values.put("Phone", place.getPhone());
        values.put("Type", place.getType());
        values.put("Rate", place.getRate());
        return db.update("Places", values, "id_place=?", new String[]{String.valueOf(place.getIdPlace())});
    }

    public void deleteImagesForPlace(int placeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Image", "id_place=?", new String[]{String.valueOf(placeId)});
    }

    public List<String> getImagesForPlace(int placeId) {
        List<String> imageUrls = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT url FROM Image WHERE id_place = ?", new String[]{String.valueOf(placeId)});
        if (cursor.moveToFirst()) {
            do {
                imageUrls.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return imageUrls;
    }

    public void deleteImageByLink(String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Image", "url = ?", new String[]{url});
        db.close();
    }


}
