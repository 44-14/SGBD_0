package com.persistence.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles_permissions", schema = "schoolux", catalog = "")
public class RolePermissionEntity {
    private int id;
    private RoleEntity rolesByIdRole;
    private PermissionEntity permissionsByIdPermission;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermissionEntity that = (RolePermissionEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "id_role", referencedColumnName = "id")
    public RoleEntity getRolesByIdRole() {
        return rolesByIdRole;
    }

    public void setRolesByIdRole(RoleEntity rolesByIdRole) {
        this.rolesByIdRole = rolesByIdRole;
    }

    @ManyToOne
    @JoinColumn(name = "id_permission", referencedColumnName = "id")
    public PermissionEntity getPermissionsByIdPermission() {
        return permissionsByIdPermission;
    }

    public void setPermissionsByIdPermission(PermissionEntity permissionsByIdPermission) {
        this.permissionsByIdPermission = permissionsByIdPermission;
    }
}
