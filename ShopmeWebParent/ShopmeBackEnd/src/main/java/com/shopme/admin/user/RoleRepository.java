package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zcmich
 * @created 30/09/2022- 14:09
 * @project ShopmeProject
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

}
