package com.shopme.admin.user;

import com.shopme.common.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author zcmich
 * @created 06/10/2022- 13:54
 * @project ShopmeProject
 */
public interface UserRepository extends CrudRepository<User, Integer> {
//    @Query(value = "SELECT u from User where u.email = :email", nativeQuery = true)
//    User getUserByEmail(@Param("email") String email);
    User getUserByEmail(String email);

}
