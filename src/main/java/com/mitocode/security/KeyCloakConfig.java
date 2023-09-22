package com.mitocode.security;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import jakarta.ws.rs.client.ClientBuilder;

public class KeyCloakConfig {
    public static Keycloak keycloak = null;
    //public final static String serverUrl = "http://localhost:9898/auth"; //KeyCloak Wildfly
    public final static String serverUrl = "http://localhost:9595"; //KeyCloak QUARKUS
    public final static String realm = "mediapp";
    public final static String clientId = "mediapp-backend";
    public final static String clientSecret = "sNZ7TT49tV8kJyxEcfH2zboBJq4llxNj"; //necesario en confidencial
    public final static String password = "123";

    public KeyCloakConfig() {
    }

    public static Keycloak getInstance(){
        if(keycloak == null){

            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(OAuth2Constants.PASSWORD)
                    .username("mitotest21@gmail.com") //usuario con privilegios de manage-realm, manage-users
                    .password(password)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .resteasyClient(ClientBuilder.newBuilder().build())
                    .build();
        }
        return keycloak;
    }
}
