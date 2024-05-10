package org.launchcode.expense_tracker.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.launchcode.expense_tracker.configure.UserAuthProvider;
import org.launchcode.expense_tracker.models.CredentialsDto;
import org.launchcode.expense_tracker.models.SignUpDto;
import org.launchcode.expense_tracker.models.UserDto;
import org.launchcode.expense_tracker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.logging.Logger;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private static final Logger logger = Logger.getLogger(AuthenticationController.class.getName());

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;


    @PostMapping("/api/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        UserDto userDto = userService.login(credentialsDto);
        String token = userAuthProvider.createToken(userDto);
        userDto.setToken(token);
        logger.info(token);
        return ResponseEntity.ok().body(userDto);
    }


    @PostMapping("/api/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
        logger.info("Received a request: {}");
        System.out.println("Received registration request for user: " + user);
        UserDto createUser = userService.register(user);
        return ResponseEntity.created(URI.create("/users/" + createUser.getId())).body(createUser);
    }

    @PostMapping("/api/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/api/logout-success")
    public String logoutSuccess() {

        return "Logout successful!";
    }


}