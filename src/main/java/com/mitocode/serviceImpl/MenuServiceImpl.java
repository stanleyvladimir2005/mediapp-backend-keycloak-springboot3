package com.mitocode.serviceImpl;

import com.mitocode.model.Menu;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IMenuRepo;
import com.mitocode.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl extends CRUDImpl<Menu, Integer> implements IMenuService {

    @Autowired
    private IMenuRepo repo;


    @Override
    public List<Menu> getMenusByUsername(String username) {
        var contextSessionUser = SecurityContextHolder.getContext().getAuthentication().getName(); //Para obtener el usuario de la session de spring security
        return repo.getMenusByUsername(contextSessionUser);
    }

    @Override
    protected IGenericRepo<Menu, Integer> getRepo() {
        return repo;
    }
}