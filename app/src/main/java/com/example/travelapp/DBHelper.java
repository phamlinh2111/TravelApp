package com.example.travelapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "TravelData.db", null, 25);
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
                "Latitude REAL, " +
                "Longitude REAL, " +
                "FOREIGN KEY (id_province) REFERENCES Province(id_province))");


        // Tạo bảng Image
        db.execSQL("CREATE TABLE Image (" +
                "id_image INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_place INTEGER, " +
                "url TEXT, " +
                "caption TEXT, " +
                "FOREIGN KEY (id_place) REFERENCES Places(id_place))");

        // Tạo bảng User
        db.execSQL("CREATE TABLE User (" +
                "id_user INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "phone TEXT)");

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

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(2, 'Chợ Bến Thành', 4.5, 'Chợ Bến Thành là biểu tượng văn hóa, thương mại lâu đời của TP. Hồ Chí Minh, nổi tiếng với các gian hàng đặc sản, quà lưu niệm và ẩm thực phong phú.', 50000, '08:00 - 18:00', '0901234567', 'Mua sắm', 10.7721, 106.6983)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(1, 'Hồ Hoàn Kiếm', 4.7, 'Hồ Hoàn Kiếm là trái tim của Hà Nội, gắn liền với truyền thuyết vua Lê Lợi trả gươm, bao quanh bởi các công trình cổ kính và không gian tản bộ lý tưởng.', 0, '24/7', '0902345678', 'Tham quan', 21.0285, 105.8542)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(3, 'Bà Nà Hills', 4.8, 'Bà Nà Hills là khu du lịch nổi tiếng với khí hậu mát mẻ quanh năm, sở hữu Cầu Vàng độc đáo, làng Pháp cổ kính, và hệ thống cáp treo đạt kỷ lục thế giới.', 700000, '08:00 - 20:00', '0903456789', 'Giải trí', 15.9957, 107.9964)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(1, 'Văn Miếu Quốc Tử Giám', 4.6, 'Văn Miếu là trường đại học đầu tiên của Việt Nam, nơi lưu giữ giá trị lịch sử giáo dục và là địa điểm lý tưởng để tìm hiểu về Nho học và kiến trúc cổ.', 30000, '07:30 - 17:30', '0904567890', 'Văn hóa', 21.0283, 105.8350)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(2, 'Nhà thờ Đức Bà', 4.4, 'Nhà thờ Đức Bà là công trình kiến trúc cổ điển mang phong cách Pháp, tọa lạc giữa trung tâm Quận 1, là điểm đến thu hút du khách và tín đồ Công giáo.', 0, '06:00 - 19:00', '0905678901', 'Tham quan', 10.7798, 106.6992)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(3, 'Ngũ Hành Sơn', 4.3, 'Ngũ Hành Sơn gồm 5 ngọn núi đá vôi đại diện cho Ngũ Hành, nổi bật với các hang động, chùa chiền và cảnh quan thiên nhiên tuyệt đẹp tại Đà Nẵng.', 40000, '07:00 - 17:00', '0906789012', 'Thiên nhiên', 16.0022, 108.2630)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(2, 'Suối Tiên', 4.0, 'Suối Tiên là công viên văn hóa nổi tiếng với mô hình kết hợp giữa vui chơi giải trí và truyền thuyết dân gian, phù hợp cho cả gia đình và trẻ em.', 60000, '08:00 - 17:00', '0907890123', 'Giải trí', 10.8411, 106.8165)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(47, 'Vịnh Hạ Long', 4.9, 'Vịnh Hạ Long là di sản thiên nhiên thế giới được UNESCO công nhận, nổi bật với hàng nghìn đảo đá vôi kỳ vĩ và hệ sinh thái biển phong phú.', 250000, '07:00 - 18:00', '0908901234', 'Thiên nhiên', 20.9101, 107.1839)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(36, 'Sa Pa', 4.7, 'Sa Pa nổi tiếng với cảnh núi non hùng vĩ, ruộng bậc thang và văn hóa dân tộc thiểu số đặc sắc.', 0, '24/7', '02143812345', 'Thiên nhiên', 22.3402, 103.8448)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(55, 'Đại Nội Huế', 4.6, 'Đại Nội là quần thể di tích cung đình nhà Nguyễn, mang giá trị lịch sử và kiến trúc độc đáo.', 150000, '07:00 - 17:30', '02343845678', 'Văn hóa', 16.4637, 107.5909)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(44, 'Động Phong Nha', 4.8, 'Một trong những hang động đẹp nhất thế giới với thạch nhũ kỳ ảo, trong vườn quốc gia Phong Nha - Kẻ Bàng.', 150000, '07:00 - 16:30', '02323645678', 'Thiên nhiên', 17.5739, 106.2876)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(40, 'Khu du lịch Tràng An', 4.9, 'Di sản thiên nhiên và văn hóa thế giới nổi bật với cảnh sông núi kỳ vĩ, hệ thống hang động phong phú.', 250000, '07:00 - 17:00', '02293645678', 'Thiên nhiên', 20.2505, 105.9366)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(30, 'VinWonders Nha Trang', 4.5, 'Khu vui chơi giải trí hiện đại nằm trên đảo Hòn Tre, nổi bật với cáp treo vượt biển dài nhất Việt Nam.', 880000, '08:30 - 21:00', '02583876543', 'Giải trí', 12.2260, 109.2802)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(34, 'Thung lũng Tình Yêu', 4.4, 'Khu du lịch thơ mộng tại Đà Lạt với hồ nước, vườn hoa và phong cảnh lãng mạn.', 25000, '07:00 - 17:00', '02633876567', 'Tham quan', 12.0021, 108.0123)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(46, 'Đảo Lý Sơn', 4.5, 'Huyện đảo tiền tiêu với biển xanh và di tích lịch sử.', 0, '24/7', '', 'Thiên nhiên', 15.3833, 109.1167)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(43, 'Gành Đá Dĩa', 4.6, 'Danh thắng với cấu trúc đá độc đáo hình lục giác.', 0, '06:00 - 18:00', '', 'Thiên nhiên', 13.9705, 109.2785)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(11, 'Đền Đô', 4.4, 'Nơi thờ 8 vị vua triều Lý, di tích quốc gia đặc biệt.', 20000, '07:00 - 18:00', '', 'Văn hóa', 21.1542, 105.9953)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(60, 'Tam Đảo', 4.7, 'Thị trấn trong mây, khí hậu mát mẻ quanh năm.', 0, '24/7', '', 'Thiên nhiên', 21.4556, 105.5944)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(49, 'Chùa Dơi', 4.3, 'Ngôi chùa cổ nổi tiếng với đàn dơi hàng nghìn con.', 0, '06:00 - 18:00', '', 'Văn hóa', 9.6034, 105.9593)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(63, 'Chiến trường Điện Biên Phủ', 4.8, 'Di tích chiến thắng lịch sử năm 1954.', 30000, '07:00 - 17:00', '', 'Lịch sử', 21.386, 103.023)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(24, 'Biển Hồ', 4.5, 'Hồ nước ngọt giữa núi rừng Gia Lai, còn gọi là hồ T’Nưng.', 0, '24/7', '', 'Thiên nhiên', 13.9986, 108.0322)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(25, 'Đèo Mã Pì Lèng', 4.9, 'Một trong tứ đại đỉnh đèo Việt Nam, hùng vĩ và hiểm trở.', 0, '24/7', '', 'Thiên nhiên', 23.2035, 105.4145)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(22, 'Khu du lịch Bửu Long', 4.2, 'Khu sinh thái nổi bật với núi đá và hồ nước.', 120000, '07:00 - 17:00', '', 'Giải trí', 10.9514, 106.8282)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(13, 'Đại Nam Văn Hiến', 4.3, 'Khu du lịch tổng hợp lớn nhất Việt Nam.', 200000, '08:00 - 17:00', '', 'Giải trí', 11.0896, 106.6626)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(51, 'Núi Bà Đen', 4.6, 'Nóc nhà Nam Bộ với cáp treo và chùa Bà.', 0, '06:00 - 18:00', '', 'Thiên nhiên', 11.3604, 106.1389)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(37, 'Làng nổi Tân Lập', 4.4, 'Khám phá rừng tràm bằng xuồng hoặc đi bộ qua cầu.', 100000, '07:00 - 17:00', '', 'Thiên nhiên', 10.5461, 105.7702)");

        db.execSQL("INSERT INTO Places (id_province, Name, Rate, Description, Ticket_price, Time, Phone, Type, Latitude, Longitude) VALUES " +
                "(56, 'Chợ nổi Cái Bè', 4.2, 'Chợ nổi nổi tiếng trên sông Tiền, hoạt động từ sáng sớm.', 0, '05:00 - 10:00', '', 'Văn hóa', 10.4071, 105.9575)");


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
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES (9, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747712859/sapa_qlp7dv.jpg', 'Sa Pa')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES (10, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747712915/Dai-Noi-Hue-2_upnyiy.jpg', 'Đại Nội Huế')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES (11, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747712957/%C4%91%E1%BB%99ng-phong-nha-1_zvtnp2.jpg', 'Động Phong Nha')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES (12, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747713004/trang-an-ninh-binh_p66g08.jpg', 'Tràng An')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES (13, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747713091/vinwonders-nha-trang-2_bzqmtm.jpg', 'VinWonders Nha Trang')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES (14, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747713095/thung_lung_t%C3%ACnh_yeu_tgroup_travel_3_xsg0dx.jpg', 'Thung lũng Tình Yêu')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES (15, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747713335/dao-ly-son-banner_f9t2lp.jpg', 'Đảo Lý Sơn')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES (16, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747713290/ghenh_da_dia_cg8xgr.jpg', 'Gành Đá Dĩa')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES (17, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747713477/dendo_nrqgbs.jpg', 'Đền Đô')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES (18, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747713567/tam-dao_by0udq.jpg', 'Tam Đảo')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES (19, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747713567/chua-doi_wvs7e2.jpg', 'Chùa Dơi')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES (20, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747713702/dien-bien-phu_s59bvq.jpg', 'Chiến trường Điện Biên Phủ')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES (21, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747713687/bien-ho_rt4yhv.jpg', 'Biển Hồ')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES (22, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747713687/Ma-Pi-Leng_d4vcvu.jpg', 'Đèo Mã Pì Lèng')");

        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES (23, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747713897/buu-long-1_tgnrru.jpg', 'Khu du lịch Bửu Long')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES (24, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747713896/%C4%90%E1%BA%A1i-Nam-V%C4%83n-hi%E1%BA%BFn_ygul8b.jpg', 'Đại Nam Văn Hiến')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES (25, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747713897/nui-ba-den-tay-ninh-2_ilpz2j.jpg', 'Núi Bà Đen')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES (26, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747713898/langnoitanlaplongan01_cguntx.jpg', 'Làng nổi Tân Lập')");
        db.execSQL("INSERT INTO Image (id_place, url, caption) VALUES (27, 'https://res.cloudinary.com/dkfzkpsmn/image/upload/v1747713990/Cho-Noi-Cai-Be-Tien-_u9pnu4.jpg', 'Chợ nổi Cái Bè')");

        db.execSQL("INSERT INTO User (username, password, phone) VALUES " +
                "('user1', '123456', '0123')," +
                "('user2', 'abcdef', '4567')," +
                "('user3', 'pass789', '8910')");

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
        values.put("Latitude", place.getLatitude());
        values.put("Longitude", place.getLongitude());

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
                        cursor.getInt(0),  // id_place
                        cursor.getInt(1),  // id_province
                        cursor.getString(2),  // Name
                        cursor.getDouble(3),  // Rate
                        cursor.getString(4),  // Description
                        cursor.getInt(5),  // Ticket_price
                        cursor.getString(6),  // Time
                        cursor.getString(7),  // Phone
                        cursor.getString(8),  // Type
                        cursor.getDouble(9),  // Latitude
                        cursor.getDouble(10) // Longitude
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
                    cursor.getInt(0),  // id_place
                    cursor.getInt(1),  // id_province
                    cursor.getString(2),  // Name
                    cursor.getDouble(3),  // Rate
                    cursor.getString(4),  // Description
                    cursor.getInt(5),  // Ticket_price
                    cursor.getString(6),  // Time
                    cursor.getString(7),  // Phone
                    cursor.getString(8),  // Type
                    cursor.getDouble(9),  // Latitude
                    cursor.getDouble(10) // Longitude
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
        values.put("Latitude", place.getLatitude());
        values.put("Longitude", place.getLongitude());

        return db.update("Places", values, "id_place=?", new String[]{String.valueOf(place.getIdPlace())});
    }

    public Province getProvinceById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Province province = null;

        Cursor cursor = db.rawQuery("SELECT * FROM Province WHERE id_province = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            province = new Province(id, name);
        }

        cursor.close();
        db.close();
        return province;
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

    public boolean registerUser(String username, String password, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (checkUsernameExists(username)) {
            return false;
        }

        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("phone", phone);

        long result = db.insert("User", null, values);
        return result != -1;
    }

    // Kiểm tra người dùng thường
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE username = ? AND password = ?", new String[]{username, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    // Kiểm tra admin
    public boolean checkAdmin(String name, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Admin WHERE name = ? AND password = ?", new String[]{name, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }


    public boolean checkUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE username=?", new String[]{username});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

}

