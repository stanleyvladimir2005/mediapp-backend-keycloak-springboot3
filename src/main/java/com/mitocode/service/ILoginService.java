package com.mitocode.service;

import com.mitocode.model.User;

public interface ILoginService {

    User checkUsername(String username);
    void changePassword(String password, String username);
}