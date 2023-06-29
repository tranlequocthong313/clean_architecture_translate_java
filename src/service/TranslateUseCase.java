package service;

import service.entity.Translation;

import java.util.List;

public interface TranslateUseCase {
    Translation translate(String orgText, String source, String dest) throws Exception;

    List<Translation> fetchHistories();
}
