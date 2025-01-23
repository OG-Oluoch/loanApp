package com.security.loanApp.controller;

import com.security.loanApp.model.LoginRequest;
import com.security.loanApp.model.Users;
import com.security.loanApp.service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {

    @Autowired
    private  UsersService usersService;

    public UsersController(UsersService usersService){
        this.usersService=usersService;
    }

    @GetMapping("/users")
    public List<Users> getUsers(){return usersService.getUsers();}

    @GetMapping("/user/{id}")
    public Users getUser(@PathVariable("id") Long id){ return usersService.getUser(id);}

    @PutMapping("/user/{id}")
    public Users updateUser(@RequestBody()Users users,@PathVariable("id")Long id){
        return usersService.updateUser(users);
    }

    @PostMapping("/register")
    public ResponseEntity<Users> newUser(@RequestBody() Users users){
        Users newUser=usersService.addUser(users);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id")Long id){usersService.deleteUser(id);}


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpSession httpSession) {
        try {
            boolean isAuthenticated = usersService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

            if (isAuthenticated) {
                // Store username in session on successful authentication
                httpSession.setAttribute("user", loginRequest.getUsername());
                return ResponseEntity.ok("Login was successful");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // Log unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

}
