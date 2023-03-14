package com.mitocode.controller;

import com.mitocode.model.User;
import com.mitocode.serviceImpl.KeyCloakServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
public class TokenController {

    private final KeyCloakServiceImpl keycloakService;

    @PostMapping(value = "/user/add")
    public ResponseEntity<Boolean> createUser(@RequestBody User user) throws Exception {
        boolean rpta = keycloakService.addUser(user);
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }
}