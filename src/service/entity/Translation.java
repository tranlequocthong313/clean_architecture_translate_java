package service.entity;

public class Translation {
    public String originalText;
    public String source;
    public String destination;
    public String resultText;

    public Translation(String originalText, String source, String destination, String resultText) {
        this.originalText = originalText;
        this.source = source;
        this.destination = destination;
        this.resultText = resultText;
    }
}
