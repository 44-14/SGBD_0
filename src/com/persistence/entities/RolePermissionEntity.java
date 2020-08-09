package com.persistence.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles_permissions", schema = "schoolux", catalog = "")
public class RolePermissionEntity {
    private int id;
    private Integer idRole;
    private Integer idPermission;
    private RoleEntity rolesByIdRole;
    private PermissionEntity permissionsByIdPermission;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_role", nullable = true)
    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    @Basic
    @Column(name = "id_permission", nullable = true)
    public Integer getIdPermission() {
        return idPermission;
    }

    public void setIdPermission(Integer idPermission) {
        this.idPermission = idPermission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermissionEntity that = (RolePermissionEntity) o;
        return id == that.id &&
                Objects.equals(idRole, that.idRole) &&
                Objects.equals(idPermission, that.idPermission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idRole, idPermission);
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
