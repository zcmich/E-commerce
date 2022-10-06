package com.shopme.admin.user;

import com.shopme.common.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author zcmich
 * @created 06/10/2022- 13:54
 * @project ShopmeProject
 */
public interface UserRepository extends CrudRepository<User, Integer> {
}
