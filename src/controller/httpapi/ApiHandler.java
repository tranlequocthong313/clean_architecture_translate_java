package controller.httpapi;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import service.TranslateUseCase;
import service.entity.Translation;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ApiHandler {
    private TranslateUseCase translateUseCase;

    public ApiHandler(TranslateUseCase useCase) {
        translateUseCase = useCase;
    }

    public void startServer() throws IOException {
        int port = 3000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        setupRoute(server);
        server.start();
        System.out.println("Server started on port " + port);
    }

    private void setupRoute(HttpServer server) {
        server.createContext("/translate", translateHandler);
    }

    private HttpHandler translateHandler = new HttpHandler() {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                String query = exchange.getRequestURI().getQuery();

                // Parse the query to get original text, source, and destination
                Map<String, String> queryParams = parseQueryParams(query);
                String orgText = URLDecoder.decode(queryParams.get("original_text"), StandardCharsets.UTF_8);
                String source = queryParams.get("source");
                String dest = queryParams.get("destination");

                try {
                    Translation translation = translateUseCase.translate(orgText, source, dest);
                    String response = translation.resultText;
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    OutputStream outputStream = exchange.getResponseBody();
                    outputStream.write(response.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    String response = e.getMessage();
                    exchange.sendResponseHeaders(404, response.getBytes().length);
                    OutputStream outputStream = exchange.getResponseBody();
                    outputStream.write(response.getBytes());
                    outputStream.close();
                    e.printStackTrace();
                }
            } else {
                exchange.sendResponseHeaders(405, 0); // Method Not Allowed
            }
        }
    };

    private Map<String, String> parseQueryParams(String query) {
        Map<String, String> queryParams = new HashMap<>();

        if (query != null && !query.isEmpty()) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];
                    queryParams.put(key, value);
                }
            }
        }

        return queryParams;
    }
}
