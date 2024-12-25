package com.project.power_up_fitness_backend.Controller;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.power_up_fitness_backend.Modals.User;
import com.project.power_up_fitness_backend.Modals.LoginRequest;
import com.project.power_up_fitness_backend.Service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/User")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService=userService;

    }

    @PostMapping
    public ResponseEntity addUser(@RequestBody User user) {

        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody User user) {
        userService.updateUser(user);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{name}")
    public ResponseEntity getUserByName(@PathVariable String name) {
        return ResponseEntity.ok(userService.getUserByName(name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable String id)
    {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest.getUserName());
        String name = loginRequest.getUserName();
        String passphrase = loginRequest.getPassphrase();

        // Find the user by name
        User user = userService.getUserByName(name);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found.");
        }

        // Verify the passphrase
        boolean isPassphraseValid = passphrase.equals(user.getPassphrase());
        if (!isPassphraseValid) {
            return ResponseEntity.status(401).body("Invalid passphrase.");
        }

        // Return user data (excluding sensitive information)
        User response = new User(user.getId(),user.getuserName(), user.getName(), user.getemailID(),user.getimageURL(),user.getPassphrase());
        System.out.println(user.getName());
        return ResponseEntity.ok(response);
    }
    

}
