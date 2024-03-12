package org.launchcode.expense_tracker.controllers;

import org.launchcode.expense_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class CheckUniqueController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/checkUnique")
    public Map<String, Object> checkUnique(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String email = request.get("email");

        boolean usernameExists = userRepository.existsByUsername(username);
        boolean emailExists = userRepository.existsByEmail(email);

        Map<String, Object> response = new HashMap<>();
        response.put("usernameExists", usernameExists);
        response.put("emailExists", emailExists);

        return response;
    }

}
