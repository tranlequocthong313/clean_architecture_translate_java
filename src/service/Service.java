package service;

import service.entity.Translation;

import java.util.List;
import java.util.concurrent.Executors;

public class Service implements TranslateUseCase {
    private TranslateRepository translateRepository;
    private GoogleService googleService;

    public Service(TranslateRepository repository, GoogleService ggService) {
        translateRepository = repository;
        googleService = ggService;
    }

    @Override
    public Translation translate(String orgText, String source, String dest) {
        var translation = translateRepository.getTranslation(orgText, source, dest);
        if (translation == null) {
            System.out.println("GG");
            translation = googleService.translate(orgText, source, dest);

            Translation finalTranslation = translation;
            Executors.newCachedThreadPool().submit(() -> {
                // Caching new translation
                translateRepository.insertTranslation(finalTranslation);
            });
        } else {
            System.out.println("DB");
        }
        return translation;
    }

    @Override
    public List<Translation> fetchHistories() {
        return translateRepository.findHistories();
    }
}

