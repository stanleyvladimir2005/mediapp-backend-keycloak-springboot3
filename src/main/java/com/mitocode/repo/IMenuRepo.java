package com.mitocode.repo;

import com.mitocode.model.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMenuRepo extends IGenericRepo<Menu, Integer> {

    @Query(value= """
            select m.* from menu_role mr 
            inner join user_role ur on ur.id_role = mr.id_role 
            inner join menu m on m.id_menu = mr.id_menu 
            inner join user_data u on u.id_user = ur.id_user 
            where u.username = :username
            """, nativeQuery = true)
    List<Menu> getMenusByUsername(String username);
}