package com.mitocode.serviceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.mitocode.service.IKeyCloakService;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import com.mitocode.security.KeyCloakConfig;
import com.mitocode.model.User;

import javax.ws.rs.core.Response;

@Service
public class KeyCloakServiceImpl implements IKeyCloakService {

    @Override
    public boolean addUser(User user) throws Exception{
        boolean rpta;

        RealmResource realmResource = KeyCloakConfig.getInstance().realm(KeyCloakConfig.realm);
        UsersResource usersResource = realmResource.users();

        List<UserRepresentation> lista = usersResource.search(user.getUsername(), true);
        rpta = lista.isEmpty();

        if (rpta) {
            //Si lista vacia, significa que usuario no existe, entonces crearlo
            UserRepresentation ur = new UserRepresentation();
            ur.setUsername(user.getUsername());
            ur.setCredentials(Collections.singletonList(generarPassword(user.getPassword())));
            ur.setFirstName("TEST");
            ur.setLastName("TEST");
            ur.setEmail(user.getUsername());
            ur.setEnabled(true);
            ur.setEmailVerified(true);

            Response response = usersResource.create(ur);

            String userId = CreatedResponseUtil.getCreatedId(response);

            //Agregar un rol por defecto para que funcione las opciones de menu
            RoleRepresentation rr = realmResource.roles().get("USER").toRepresentation();
            usersResource.get(userId).roles().realmLevel().add(Arrays.asList(rr));
        }

        return rpta;
    }

    private CredentialRepresentation generarPassword(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        return credential;
    }

}