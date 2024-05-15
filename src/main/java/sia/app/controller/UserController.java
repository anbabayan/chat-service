package sia.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sia.app.model.InputDTO;
import sia.app.model.LoginInfoDTO;
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
    @CrossOrigin
    public ResponseEntity<User> loginUser(@RequestBody LoginInfoDTO loginInfo) {
        final User user = userService.loginUser(loginInfo.getLogin(), loginInfo.getPassword());
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }


    @PostMapping("/sendRequest")
    @CrossOrigin
    public ResponseEntity<String> sendRequest(@RequestBody InputDTO inputDTO,
                                              @RequestParam Boolean isPremium) {
        if (inputDTO == null || isPremium == null || inputDTO.getInputText().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return userService.sendRequest(inputDTO.getInputText(), isPremium);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}
