package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author zcmich
 * @created 30/09/2022- 14:15
 * @project ShopmeProject
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleRepositoryTests {
    @Autowired
    RoleRepository repo;

    @Test
    public void testCreateFirstRole(){
      Role roleAdmin = new Role("Admin", "manage everything");
      Role savedRole = repo.save(roleAdmin);
      assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateRestRoles(){
        Role roleSalesPerson = new Role("Salesperson", "manage product price," +
                "customers, shipping, orders and sales report");
        Role roleEditor = new Role("Editor", "manage categories," +
                "brands, products, articles and sales reportmenus");
        Role roleShipper = new Role("Shipper", "view products," +
                "view orders, and update order status");
        Role roleAssistant = new Role("Assistant", "manage questions and reviews");
        repo.saveAll(List.of(roleSalesPerson, roleEditor, roleShipper, roleAssistant));
    }

}
