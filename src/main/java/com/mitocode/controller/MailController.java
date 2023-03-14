package com.mitocode.controller;

import com.mitocode.security.KeyCloakConfig;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
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
    public ResponseEntity<Integer> sendMailKeycloak(@RequestBody String username) throws Exception {
        UsersResource usersResource = KeyCloakConfig.getInstance().realm(KeyCloakConfig.realm).users();
        List<UserRepresentation> lista = usersResource.search(username, true);
        boolean rpta = lista.isEmpty();

        if (!rpta) {
            //Si lista no vacia, significa que usuario existe, entonces enviar correo
            UserRepresentation user = lista.get(0);
            usersResource.get(user.getId()).executeActionsEmail(Arrays.asList("UPDATE_PASSWORD")); //.resetPasswordEmail();
            return new ResponseEntity<>(1, HttpStatus.OK);
        }
        return new ResponseEntity<>(0, HttpStatus.OK);
    }


    // KeyCloak
    @PostMapping("/logout")
    public void logout(@RequestBody String username) {
        UsersResource usersResource = KeyCloakConfig.getInstance().realm(KeyCloakConfig.realm).users();
        UserRepresentation user = usersResource.search(username, true).get(0);
        usersResource.get(user.getId()).logout();

        //Cerrar sesion al iniciar y luego poder iniciar, con eso limito a 1 sesion activa, es decir mato a todos para permitir al nuevo
        //RealmResource realmResource = KeyCloakConfig.getInstance("").realm(KeyCloakConfig.realm).clients().get("mediapp-backend").getUserSessions(firstResult, maxResults)
    }

}