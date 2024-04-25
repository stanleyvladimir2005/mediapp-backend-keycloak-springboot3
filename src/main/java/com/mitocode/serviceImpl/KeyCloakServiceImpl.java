package com.mitocode.serviceImpl;

import java.util.Collections;
import com.mitocode.service.IKeyCloakService;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import com.mitocode.security.KeyCloakConfig;
import com.mitocode.model.User;

@Service
public class KeyCloakServiceImpl implements IKeyCloakService {

    @Override
    public boolean addUser(User user) {
        var realmResource = KeyCloakConfig.getInstance().realm(KeyCloakConfig.realm);
        var usersResource = realmResource.users();
        var lista = usersResource.search(user.getUsername(), true);
        var rpta = lista.isEmpty();

        if (rpta) {
            //Si lista vacia, significa que usuario no existe, entonces crearlo
            var ur = new UserRepresentation();
            ur.setUsername(user.getUsername());
            ur.setCredentials(Collections.singletonList(generarPassword(user.getPassword())));
            ur.setFirstName("TEST");
            ur.setLastName("TEST");
            ur.setEmail(user.getUsername());
            ur.setEnabled(true);
            ur.setEmailVerified(true);
            var response = usersResource.create(ur);
            var userId = CreatedResponseUtil.getCreatedId(response);

            //Agregar un rol por defecto para que funcione las opciones de menu
            var rr = realmResource.roles().get("USER").toRepresentation();
            usersResource.get(userId).roles().realmLevel().add(Collections.singletonList(rr));
        }
        return rpta;
    }

    private CredentialRepresentation generarPassword(String password) {
        var credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        return credential;
    }
}