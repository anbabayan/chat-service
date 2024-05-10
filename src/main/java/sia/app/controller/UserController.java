package sia.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sia.app.model.User;
import sia.app.service.UserService;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("login")
    public ResponseEntity<User> loginUser(@RequestParam String login,
                                          @RequestParam String password) {
        final User user = userService.loginUser(login, password);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }


    @PostMapping("/sendRequest")
    public ResponseEntity<String> sendRequest(@RequestParam String inputText,
                                              @RequestParam Boolean isPremium) {
        if (inputText == null || isPremium == null || inputText.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return userService.sendRequest(inputText, isPremium);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}
