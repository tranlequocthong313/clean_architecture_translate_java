package service;

import service.entity.Translation;

import java.util.List;

public interface GoogleService {
    Translation translate(String orgText, String source, String dest);
}
