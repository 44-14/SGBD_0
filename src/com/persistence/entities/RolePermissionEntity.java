package com.persistence.entities;

import javax.persistence.*;
import java.util.Objects;



@NamedQueries(
        value = {
                @NamedQuery(name = "RolePermission.selectAll",
                        query = "SELECT rp from RolePermissionEntity rp"),
                @NamedQuery(name = "RolePermission.selectOneById",
                        query = "SELECT rp from RolePermissionEntity  rp where rp.id = :id"),
                @NamedQuery(name = "RolePermission.selectOneByComposite",
                        query = "SELECT rp from RolePermissionEntity  rp where rp.permissionsByIdPermission.id = :idPermission " +
                                "and rp.rolesByIdRole.id = :idRole "),
                @NamedQuery(name = "RolePermission.selectAllByIdRole",
                        query = "SELECT rp from RolePermissionEntity  rp where  rp.rolesByIdRole.id = :idRole "),
                @NamedQuery(name = "RolePermission.deleteAllByIdRole",
                        query = "DELETE from RolePermissionEntity  rp where  rp.rolesByIdRole.id = :idRole ")
        }

)


@Entity
@Table(name = "roles_permissions", schema = "schoolux", catalog = "")
public class RolePermissionEntity {
    private int id;
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



    @Override
    public String toString() {
        return "\nRolePermissionEntity { " +
                "id=" + id +
                ",\nRole lié =" + rolesByIdRole +
                ",\npermission liée=" + permissionsByIdPermission +
                " } ";
    }


}
