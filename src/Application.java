import controller.httpapi.ApiHandler;
import infrastructure.mysql.MySQLDatabase;
import service.GoogleService;
import service.Service;
import service.TranslateRepository;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        TranslateRepository repository = new MySQLDatabase();
        GoogleService ggService = new infrastructure.ggservice.GoogleService();
        Service service = new Service(repository, ggService);
        ApiHandler apiHandler = new ApiHandler(service);

        apiHandler.startServer();
    }
}
