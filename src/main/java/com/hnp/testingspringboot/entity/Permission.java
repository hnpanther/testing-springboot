package com.hnp.testingspringboot.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String permissionName;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(mappedBy = "permissions")
    private Set<User> users = new HashSet<>();

    public Permission() {
    }

    public Permission(String permissionName) {
        this.permissionName = permissionName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String name) {
        this.permissionName = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", permissionName='" + permissionName + '\'' +
                '}';
    }
}
