package com.mativimu.eventsappservice.domain.user;

import java.security.InvalidParameterException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mativimu.eventsappservice.security.TokenUtils;
import com.mativimu.eventsappservice.utils.Message;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all/{token}")
    public ResponseEntity<List<User>> getUsers(@PathVariable("token") String token) {
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        return ResponseEntity.ok().body(userService.getUsers());        
    }

    @GetMapping("/{email}/{token}")
    public ResponseEntity<User> getUserByEmail(
            @PathVariable("email") String email, @PathVariable("token") String token) {
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        return ResponseEntity.ok().body(userService.getUserByEmail(email));
    }

    @GetMapping("/id/{id}/{token}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id, @PathVariable("token") String token) {
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        Long userId = Long.parseLong(id);
        return ResponseEntity.ok().body(userService.getUserById((userId)));
    }

    @PostMapping("/new")
    public ResponseEntity<Message> addUser(@RequestBody User user){
        userService.addUser(
            user.getUsername(),
            user.getUserEmail(),
            user.getUserPassword(),
            user.getUserFullName(),
            user.getUserOccupation()
        );
        return ResponseEntity.ok().body(new Message("User saved"));
    }

    @DeleteMapping("/remove/{id}/{token}")
    public ResponseEntity<Message> deleteUser(@PathVariable("id") String id, @PathVariable("token") String token) {
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        Long userId = Long.parseLong(id);
        userService.deleteUser(userId);
        return ResponseEntity.ok().body(new Message("User deleted"));
    }

    @PutMapping("/set/username/{username}/on-user/{id}/{token}")
    public ResponseEntity<Message> updateUsername(@PathVariable("id") String id, 
            @PathVariable("username") String newUsername, @PathVariable("token") String token) {
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        userService.updateUsername(Long.parseLong(id), newUsername);
        return ResponseEntity.ok().body(new Message("User updated"));
    }

    @PutMapping("/set/email/{email}/on-user/{id}/{token}")
    public ResponseEntity<Message> updateUseEmail(@PathVariable("id") String id,
            @PathVariable("email") String newUserEmail, @PathVariable("token") String token) {
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        userService.updateUserEmail(Long.parseLong(id), newUserEmail);
        return ResponseEntity.ok().body(new Message("User updated"));
    }

    @PutMapping("/change/password/{token}")
    public ResponseEntity<Message> updateUserPassword(@RequestBody User user, @PathVariable("token") String token) {
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        userService.updateUserPassword(user.getUserId(), user.getUserPassword());
        return ResponseEntity.ok().body(new Message("User updated"));
    }

    @PutMapping("/set/fullname/{fullname}/on-user/{id}/{token}")
    public ResponseEntity<Message> updateUseFullName(@PathVariable("id") String id,
            @PathVariable("fullname") String newUserFullName, @PathVariable("token") String token) {
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        userService.updateUserFullName(Long.parseLong(id), newUserFullName);
        return ResponseEntity.ok().body(new Message("User updated"));
    }

    @PutMapping("/set/occupation/on-user/{id}/{occupation}/{token}")
    public ResponseEntity<Message> updateUserOccupation(@PathVariable("id") String id,
            @PathVariable("occupation") String newUserOccupation, @PathVariable("token") String token){
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        userService.updateUserOccupation(Long.parseLong(id), newUserOccupation);
        return ResponseEntity.ok().body(new Message("User updated"));
    }

}
