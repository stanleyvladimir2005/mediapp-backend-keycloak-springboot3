package com.mitocode.serviceImpl;

import com.mitocode.model.User;
import com.mitocode.repo.ILoginRepo;
import com.mitocode.service.ILoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements ILoginService{

    //private final PasswordEncoder bcrypt;

    private final ILoginRepo repo;

    @Override
    public User checkUsername(String username) {
        return repo.checkUsername(username);
    }

    @Override
    public void changePassword(String password, String username) {
        //repo.changePassword(bcrypt.encode(password), username);
    }
}