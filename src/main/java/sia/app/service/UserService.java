package sia.app.service;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sia.app.model.User;
import sia.app.repository.UserRepository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Service
public class UserService {
    private static final String PREMIUM_URI = "https://b044-37-252-89-113.ngrok-free.app/generate";
    private static final String FREE_URI = "https://4cda-46-71-255-102.ngrok-free.app/generate";

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User loginUser(String login, String password) {
        final User user = userRepository.findByLoginAndPassword(login, password);
        // Check if user exists and if password matches
        if (user != null && user.getPassword().equals(password)) {
            return user; // Authentication successful
        } else {
            return null; // Authentication failed
        }
    }

    public ResponseEntity<String> sendRequest(String inputText, Boolean isPremium) throws URISyntaxException, IOException, InterruptedException {
        URI uri = isPremium ? new URI(PREMIUM_URI) : new URI(FREE_URI);

        java.net.http.HttpRequest httpRequest = java.net.http.HttpRequest.newBuilder()
                .uri(uri)
                .header(CONTENT_TYPE, String.valueOf(MediaType.APPLICATION_JSON))
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString("{\"text\": \"" + inputText + "\"}"))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return ResponseEntity.ok(response.body());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }}