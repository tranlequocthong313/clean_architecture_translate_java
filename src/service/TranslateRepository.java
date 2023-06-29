package service;

import service.entity.Translation;

import java.util.List;

public interface TranslateRepository {
    Translation getTranslation(String orgText, String source, String dest);

    List<Translation> findHistories();

    void insertTranslation(Translation translation);
}
