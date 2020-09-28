package com.main.schoolux.viewModels;

import com.persistence.entities.PermissionEntity;
import com.persistence.entities.RolePermissionEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;




public class PermissionVM {
    private int id;
    private String label;
    private String abbreviation;
    private String description;
    private Collection<RolePermissionEntity> rolesPermissionsById;

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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionVM that = (PermissionVM) o;
        return id == that.id &&
                Objects.equals(label, that.label) &&
                Objects.equals(abbreviation, that.abbreviation) &&
                Objects.equals(description, that.description);
    }

    public int hashCode() {
        return Objects.hash(id, label, abbreviation, description);
    }

    public Collection<RolePermissionEntity> getRolesPermissionsById() {
        return rolesPermissionsById;
    }

    public void setRolesPermissionsById(Collection<RolePermissionEntity> rolesPermissionsById) {
        this.rolesPermissionsById = rolesPermissionsById;
    }

    public String toString() {
        return "\nPermissionEntity { " +
                "id=" + id +
                ",label=" + label +
                ",abbreviation=" + abbreviation +
                ",description=" + description +
                " } ";
    }
}
