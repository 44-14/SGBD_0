package com.main.schoolux.viewModels;

import com.persistence.entities.RoleEntity;
import com.persistence.entities.RolePermissionEntity;
import com.persistence.entities.UserEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;



public class RoleVM {
    private int id;
    private String label;
    private String abbreviation;
    private String description;
    private Collection<RolePermissionEntity> rolesPermissionsById;
    private Collection<UserEntity> usersById;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleVM that = (RoleVM) o;
        return id == that.id &&
                Objects.equals(label, that.label) &&
                Objects.equals(abbreviation, that.abbreviation) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, abbreviation, description);
    }


    public Collection<RolePermissionEntity> getRolesPermissionsById() {
        return rolesPermissionsById;
    }

    public void setRolesPermissionsById(Collection<RolePermissionEntity> rolesPermissionsById) {
        this.rolesPermissionsById = rolesPermissionsById;
    }


    public Collection<UserEntity> getUsersById() {
        return usersById;
    }

    public void setUsersById(Collection<UserEntity> usersById) {
        this.usersById = usersById;
    }


    @Override
    public String toString() {
        return "\nRoleEntity { " +
                "id=" + id +
                ",label=" + label +
                ",abbreviation=" + abbreviation +
                ",description=" + description +
                " } ";
    }


}
