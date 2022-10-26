package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author zcmich
 * @created 06/10/2022- 13:47
 * @project ShopmeProject
 */
@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {
    @Autowired
    UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

//    @BeforeEach
//    public void  setup(){
//        System.out.println("test");
//        System.out.println("test");
//
//    }

    @Test
    public void testCreateUserNewWithOneRole(){
        Role roleAdmin = entityManager.find(Role.class, 1);
        User user = new User("test@test", "pass", "Zafack", "Cliff Michel");
        user.addRole(roleAdmin);
        User saveduser = repo.save(user);
        assertThat(saveduser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserNewWithTwoRole(){
        User user = new User("test@test1", "pass", "zcmich", "Mike");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);

        user.addRole(roleEditor);
        user.addRole(roleAssistant);

        User saveduser = repo.save(user);
        assertThat(saveduser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUser(){
        Iterable<User> listUsers= repo.findAll();
        listUsers.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById(){
       User user = repo.findById(1).get();
        System.out.println(user);
       assertThat(user).isNotNull();
    }

    @Test
    public void testUpdateUserDetails(){
        User user = repo.findById(1).get();
        user.setEnabled(true);
        user.setEmail("testupdateUserDetails@gmail");
        repo.save(user);
    }

    @Test
    public void testUpdateUserRoles(){
        User user = repo.findById(2).get();

        Role roleEditor = new Role(3);
        Role roleSalesPerson= new Role(2);

        user.getRoles().remove(roleEditor);
        user.getRoles().add(roleSalesPerson);

        User savedUser = repo.save(user);
//        assertThat(user.getRoles()).
    }

    @Test
    public void testDeleteUser(){
        Integer userId = 2;
        repo.deleteById(userId);
    }

    @Test
    public void testGetUserByEmail(){
//        String email = "testingwork34125@gmail.com";
        String email = "aecllc.bnk@gmail.com";
        User user = repo.getUserByEmail(email);

        assertThat(user).isNotNull();
    }

    @Test
    public void testCountById(){
        Integer id = 1;
        Long countById = repo.countById(id);
        assertThat(countById).isNotNull().isGreaterThan(0);
        repo.countByFirstName("Cliff");
    }

    @Test
    public void testDisableUser(){
        Integer id = 7;
        repo.updateEnabledStatus(id, false);
    }

    @Test
    public void testEnableUser(){
        Integer id = 7;
        repo.updateEnabledStatus(id, true);
    }

    @Test
    public void testListFirstPage(){
        int pageNumber = 2;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = repo.findAll(pageable);

        List<User> lisUser = page.getContent();

        lisUser.forEach(System.out::println);

        assertThat(lisUser.size()).isEqualTo(pageSize);
    }

    @Test
    public void testSearchUsers(){
        String keyword = "bruce";

        int pageNumber = 0;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = repo.findAll(keyword,pageable);

        List<User> listUser = page.getContent();

        listUser.forEach(System.out::println);

        assertThat(listUser.size()).isGreaterThan(0);
    }


}
