package com.mitocode.controller;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final Keycloak keycloak;

    @Value("${keycloak.admin.realm}")
    private String realm;

    @PostMapping(value = "/sendMail", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Integer> sendMailKeycloak(@RequestBody String username) {
        var usersResource = keycloak.realm(realm).users();
        var lista = usersResource.search(username, true);
        var rpta = lista.isEmpty();
        if (!rpta) {
            //Si lista no vacia, significa que usuario existe, entonces enviar correo
            var user = lista.getFirst();
            usersResource.get(user.getId()).executeActionsEmail(List.of("UPDATE_PASSWORD")); //.resetPasswordEmail();
            return new ResponseEntity<>(1, HttpStatus.OK);
        }
        return new ResponseEntity<>(0, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody String username) {
        var usersResource = keycloak.realm(realm).users();
        var lista = usersResource.search(username, true);
        if (lista.isEmpty()) {
            return;
        }
        var user = lista.getFirst();
        usersResource.get(user.getId()).logout();
    }
}
