package com.mitocode.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl { //Para caso de acceso usando @PreAuthorize

    public boolean hasAccess(String path){
        boolean rpta = false;

        String methodRole = switch (path) {
            case "findAll" -> "ADMIN";
            case "findById" -> "USER,DBA";
            default -> "";
        };

        String methodRoles[] = methodRole.split(",");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        for(GrantedAuthority gra : auth.getAuthorities()){
            String rolUser = gra.getAuthority();
            log.info(rolUser);
            log.info(auth.getName());
            for(String rolMet : methodRoles){
                if(rolUser.equalsIgnoreCase(rolMet)){
                    rpta = true;
                    break;
                }
            }
        }
        return rpta;
    }
}