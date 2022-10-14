package com.shopme.admin.user;

import com.shopme.common.entity.User;
import org.springframework.data.jpa.repository.Modifying;
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
    public User getUserByEmail(String email);
    Long countById(Integer id);
    Long countByFirstName(String name);

    @Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean status);


}
