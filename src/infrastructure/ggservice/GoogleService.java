package infrastructure.ggservice;

import service.entity.Translation;

import java.util.HashSet;
import java.util.Hashtable;

public class GoogleService implements service.GoogleService {
    // TODO: Connect to Google Service
    private Hashtable<String, String> vnToEnTable;
    private Hashtable<String, String> enToVnTable;

    public GoogleService() {
        vnToEnTable = new Hashtable<>();
        enToVnTable = new Hashtable<>();
        initializeTranslationTables();
    }

    private void initializeTranslationTables() {
        // Hashtable dịch từ tiếng Việt sang tiếng Anh
        vnToEnTable.put("chào", "hello");
        vnToEnTable.put("tạm biệt", "goodbye");
        vnToEnTable.put("cảm ơn", "thank you");
        vnToEnTable.put("xin lỗi", "sorry");
        vnToEnTable.put("người", "person");
        vnToEnTable.put("quốc gia", "country");
        vnToEnTable.put("ngôn ngữ", "language");
        vnToEnTable.put("động vật", "animal");
        vnToEnTable.put("thức ăn", "food");
        vnToEnTable.put("màu sắc", "color");
        vnToEnTable.put("sách", "book");
        vnToEnTable.put("bút", "pen");
        vnToEnTable.put("máy tính", "computer");
        vnToEnTable.put("điện thoại", "phone");
        vnToEnTable.put("xe hơi", "car");
        vnToEnTable.put("bảo vệ", "security");
        vnToEnTable.put("trường học", "school");
        vnToEnTable.put("hành trình", "journey");
        vnToEnTable.put("máy ảnh", "camera");
        vnToEnTable.put("nhạc", "music");
        vnToEnTable.put("nhà", "house");
        vnToEnTable.put("nước", "water");
        vnToEnTable.put("mặt trời", "sun");
        vnToEnTable.put("mặt trăng", "moon");
        vnToEnTable.put("cây", "tree");
        vnToEnTable.put("hoa", "flower");
        vnToEnTable.put("trái cây", "fruit");
        vnToEnTable.put("động từ", "verb");
        vnToEnTable.put("tính từ", "adjective");
        vnToEnTable.put("thực phẩm", "food");
        vnToEnTable.put("giáo viên", "teacher");
        vnToEnTable.put("học sinh", "student");
        vnToEnTable.put("đội tuyển", "team");
        vnToEnTable.put("nhóm", "group");
        vnToEnTable.put("thành phố", "city");
        vnToEnTable.put("biển", "ocean");
        vnToEnTable.put("hồ", "lake");
        vnToEnTable.put("sông", "river");
        vnToEnTable.put("núi", "mountain");
        vnToEnTable.put("đồng cỏ", "meadow");
        vnToEnTable.put("chó", "dog");
        vnToEnTable.put("mèo", "cat");
        vnToEnTable.put("con gà", "chicken");
        vnToEnTable.put("con lợn", "pig");
        vnToEnTable.put("con cừu", "sheep");
        vnToEnTable.put("con bò", "cow");
        vnToEnTable.put("con ngựa", "horse");
        vnToEnTable.put("con vịt", "duck");
        vnToEnTable.put("con gấu", "bear");
        vnToEnTable.put("con cá", "fish");
        vnToEnTable.put("con chim", "bird");

        // Hashtable dịch từ tiếng Anh sang tiếng Việt
        for (String vnWord : vnToEnTable.keySet()) {
            String enWord = vnToEnTable.get(vnWord);
            enToVnTable.put(enWord, vnWord);
        }
    }

    @Override
    public Translation translate(String orgText, String source, String dest) {
        HashSet validCountries = new HashSet<>();
        validCountries.add("vn");
        validCountries.add("en");
        if (!validCountries.contains(source.toLowerCase()) || !validCountries.contains(dest.toLowerCase())) {
            throw new IllegalArgumentException("Nguồn hoặc đích không hợp lệ");
        }
        if (source.toLowerCase().equals("vn")) {
            return new Translation(orgText, source, dest, translateFromVnToEn(orgText.toLowerCase()));
        }
        return new Translation(orgText, source, dest, translateFromEnToVn(orgText.toLowerCase()));
    }

    public String translateFromVnToEn(String vnWord) {
        if (vnToEnTable.containsKey(vnWord.toLowerCase())) {
            return vnToEnTable.get(vnWord);
        } else {
            throw new IllegalArgumentException("Không tìm thấy dịch nghĩa cho từ này");
        }
    }

    public String translateFromEnToVn(String enWord) {
        if (enToVnTable.containsKey(enWord.toLowerCase())) {
            return enToVnTable.get(enWord);
        } else {
            throw new IllegalArgumentException("Không tìm thấy dịch nghĩa cho từ này");
        }
    }
}
