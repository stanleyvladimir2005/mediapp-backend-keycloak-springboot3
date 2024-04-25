package com.mitocode.controller;

import com.mitocode.security.KeyCloakConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/mail")
public class MailController {

    //KeyCloak
    @PostMapping(value = "/sendMail", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Integer> sendMailKeycloak(@RequestBody String username) {
        var usersResource = KeyCloakConfig.getInstance().realm(KeyCloakConfig.realm).users();
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


    // KeyCloak
    @PostMapping("/logout")
    public void logout(@RequestBody String username) {
        var usersResource = KeyCloakConfig.getInstance().realm(KeyCloakConfig.realm).users();
        var user = usersResource.search(username, true).getFirst();
        usersResource.get(user.getId()).logout();

        //Cerrar sesion al iniciar y luego poder iniciar, con eso limito a 1 sesion activa, es decir mato a todos para permitir al nuevo
        //RealmResource realmResource = KeyCloakConfig.getInstance("").realm(KeyCloakConfig.realm).clients().get("mediapp-backend").getUserSessions(firstResult, maxResults)
    }
}