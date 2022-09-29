package com.shopme.common.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zcmich
 * @created 29/09/2022- 16:26
 * @project ShopmeProject
 */
@Entity
@Table(name = "roles")
public class Role {
    private Integer id;
    private String name;
    private String description;

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    public Integer getId() {
        return id;
    }
}
