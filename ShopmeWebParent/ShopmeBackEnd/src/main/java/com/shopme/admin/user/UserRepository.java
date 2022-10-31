package com.shopme.admin.user;

import com.shopme.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author zcmich
 * @created 06/10/2022- 13:54
 * @project ShopmeProject
 */
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    @Query(value = "SELECT u from User u where u.email = :email")
//    User getUserByEmail(@Param("email") String email);
    public User getUserByEmail(String email);
    Long countById(Integer id);
    Long countByFirstName(String name);

    @Query("SELECT u FROM User u WHERE concat(u.id, ' ', u.email, ' ', u.firstName, ' ', u.lastName) LIKE %?1%")
    Page<User> findAll(String keyword, Pageable pageable);
    @Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean status);


}
