package com.main.schoolux.viewModels;

import com.persistence.entities.RoleEntity;
import com.persistence.entities.UserEntity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;



public class UserVM {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String phoneNumber;
    private Date birthdate;
    private String gender;
    private String emailAddress;
    private boolean isActive;
    private Timestamp inscriptionDate;
    private String title;
    private String photo;
    private RoleEntity rolesByIdRole;
    private UserVM usersByIdParent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Timestamp getInscriptionDate() {
        return inscriptionDate;
    }

    public void setInscriptionDate(Timestamp inscriptionDate) {
        this.inscriptionDate = inscriptionDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserVM that = (UserVM) o;
        return id == that.id &&
                isActive == that.isActive &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(birthdate, that.birthdate) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(emailAddress, that.emailAddress) &&
                Objects.equals(inscriptionDate, that.inscriptionDate) &&
                Objects.equals(title, that.title) &&
                Objects.equals(photo, that.photo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, username, password, phoneNumber, birthdate, gender, emailAddress, isActive, inscriptionDate, title, photo);
    }

    public RoleEntity getRolesByIdRole() {
        return rolesByIdRole;
    }

    public void setRolesByIdRole(RoleEntity rolesByIdRole) {
        this.rolesByIdRole = rolesByIdRole;
    }

    public UserVM getUsersByIdParent() {
        return usersByIdParent;
    }

    public void setUsersByIdParent(UserVM usersByIdParent) {
        this.usersByIdParent = usersByIdParent;
    }





    @Override
    public String toString() {
        return "\nUserEntity { " +
                "id=" + id +
                ",isActive=" + isActive +
                ",firstName=" + firstName +
                ",lastName=" + lastName +
                ",username=" + username +
                ",password=" + password +
                ",phoneNumber=" + phoneNumber +
                ",birthdate=" + birthdate  +
                ",gender=" + gender +
                ",emailAddress=" + emailAddress  +
                ",inscriptionDate=" + inscriptionDate +
                ",title=" + title +
                ",photo=" + photo +
                ",role=" + rolesByIdRole +
                ",usersByIdParent=" + usersByIdParent +
                " } ";
    }


}









