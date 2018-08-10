package com.springboot.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String password;
    private boolean active;

    /**
     * Позволяет нам не запариваться по поводу хранения данных
     */
    @ElementCollection(targetClass = Role.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "user_role",joinColumns = @JoinColumn(name = "id"))/**Будет в отдельной таблице.Будет соединяться с текущей таблицей через id*/
    @Enumerated(EnumType.STRING)/**Таким образом мы говорим,что хотим хранить наш enum в виде строки*/
    private Set<Role> roles;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
