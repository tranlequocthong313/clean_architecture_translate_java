package infrastructure.mysql;

import service.TranslateRepository;
import service.entity.Translation;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

public class MySQLDatabase implements TranslateRepository {
    // TODO: Connect to DB
    Hashtable<String, Translation> enToVnDb = new Hashtable<>();
    Hashtable<String, Translation> vnToEnDb = new Hashtable<>();
    HashSet validCountries = new HashSet<>();

    public MySQLDatabase() {
        validCountries.add("vn");
        validCountries.add("en");
    }

    @Override
    public Translation getTranslation(String orgText, String source, String dest) {
        if (!validCountries.contains(source.toLowerCase()) || !validCountries.contains(dest.toLowerCase())) {
            return null;
        }
        if (source.toLowerCase().equals("vn")) {
            return vnToEnDb.get(orgText.toLowerCase());
        }
        return enToVnDb.get(orgText.toLowerCase());
    }

    @Override
    public List<Translation> findHistories() {
        return null;
    }

    @Override
    public void insertTranslation(Translation translation) {
        var source = translation.source.toLowerCase();
        var dest = translation.destination.toLowerCase();
        if (!validCountries.contains(source) || !validCountries.contains(dest)) {
            return;
        }
        if (source.equals("vn")) {
            vnToEnDb.put(translation.originalText.toLowerCase(), translation);
            return;
        }
        enToVnDb.put(translation.originalText.toLowerCase(), translation);
    }
}
