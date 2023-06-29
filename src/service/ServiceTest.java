package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.entity.Translation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class ServiceTest {

    @Mock
    private TranslateRepository translateRepository;

    @Mock
    private GoogleService googleService;

    private Service service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        service = new Service(translateRepository, googleService);
    }

    @Test
    public void testTranslate_ExistingTranslation() throws Exception {
        // Arrange
        String orgText = "Hello";
        String source = "en";
        String dest = "fr";
        Translation existingTranslation = new Translation(orgText, source, dest, "Bonjour");
        when(translateRepository.getTranslation(orgText, source, dest)).thenReturn(existingTranslation);

        // Act
        Translation result = service.translate(orgText, source, dest);

        // Assert
        assertEquals(existingTranslation, result);
        verify(translateRepository, times(1)).getTranslation(orgText, source, dest);
        verify(googleService, never()).translate(anyString(), anyString(), anyString());
        verify(translateRepository, never()).insertTranslation(any(Translation.class));
    }

    @Test
    public void testTranslate_NewTranslation() throws Exception {
        // Arrange
        String orgText = "Hello";
        String source = "en";
        String dest = "fr";
        Translation newTranslation = new Translation(orgText, source, dest, "Bonjour");
        when(translateRepository.getTranslation(orgText, source, dest)).thenReturn(null);
        when(googleService.translate(orgText, source, dest)).thenReturn(newTranslation);

        // Act
        Translation result = service.translate(orgText, source, dest);

        // Assert
        assertEquals(newTranslation, result);
        verify(translateRepository, times(1)).getTranslation(orgText, source, dest);
        verify(googleService, times(1)).translate(orgText, source, dest);
        verify(translateRepository, times(1)).insertTranslation(newTranslation);
    }

    @Test
    public void testFetchHistories() {
        // Arrange
        List<Translation> histories = new ArrayList<>();
        when(translateRepository.findHistories()).thenReturn(histories);

        // Act
        List<Translation> result = service.fetchHistories();

        // Assert
        assertEquals(histories, result);
        verify(translateRepository, times(1)).findHistories();
    }
}
